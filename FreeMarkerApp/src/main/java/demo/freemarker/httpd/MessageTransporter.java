package demo.freemarker.httpd;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/*
        MessageTransporter transporter = MessageTransporter.getInstance();

        transporter.sendMqttMessage("home/sensor", "Temperature: 25°C");
        transporter.sendHttpMessage("https://api.example.com/notify", "{ \"message\": \"Hello\" }");
        transporter.sendEmail("user@example.com", "Test", "This is a test email.");
        transporter.sendSms("+1234567890", "Your OTP is 1234"); 
 */
public class MessageTransporter {

    private static volatile MessageTransporter instance = null; // Singleton

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    // 使用 BlockingQueue 為不同訊息類型建立佇列
    private final BlockingQueue<Message> httpQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> gcmQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> emailQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> mqttQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> smsQueue = new LinkedBlockingQueue<>();
    public String GCM_SERVER_KEY = "";
    public String GCM_API_URL = "";

    private static MqttClient mqttInstance = null;
    private static final String MQTT_BROKER_URL = "tcp://localhost:1883";
    private static final String MQTT_CLIENT_ID = "UniqueClientID";
    private static final String MQTT_TOPIC = "system/notification";
    // 記錄最近的訊息
    private static final Queue<String> mqttRevicedLog = new LinkedList<>();
    private static final int LOG_LIMIT = 10;  // 顯示最近 10 筆訊息

    private MessageTransporter() {
        startProcessingQueues();
    }

    public static MessageTransporter getInstance() {
        if (instance == null) {
            synchronized (MessageTransporter.class) {
                if (instance == null) {
                    instance = new MessageTransporter();
                }
            }
        }
        return instance;
    }

    private void startProcessingQueues() {
        processQueueHttp(httpQueue);
        processQueueGCM(emailQueue);
        processQueueMqtt(mqttQueue);
        processQueueEMail(emailQueue);
        processQueueSMS(smsQueue);
    }

    private void processQueueHttp(BlockingQueue<Message> queue) {
        executorService.execute(() -> {
            while (true) {
                try {
                    Message message = queue.take(); // 取得並移除隊列頭部的訊息 
                    System.out.println("[HTTP] Sending to URL: " + message.destination + " | Body: " + message.content);
                    // 整合 HTTP 客戶端（例如 Apache HttpClient 或 OkHttp） 
                    URL url = new URL(message.destination);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + GCM_SERVER_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");

                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(message.content.getBytes("UTF-8"));
                    }

                    int responseCode = conn.getResponseCode();
                    System.out.println("[HTTP] Response Code: " + responseCode);

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void processQueueGCM(BlockingQueue<Message> queue) {
        executorService.execute(() -> {
            while (true) {
                try {
                    Message message = queue.take(); // 取得並移除隊列頭部的訊息 
                    System.out.println("[MQTT] Sending to topic: " + message.destination + " | Message: " + message.content);
                    // 整合 MQTT 客戶端（例如 Eclipse Paho） 
                    URL url = new URL(GCM_API_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + GCM_SERVER_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");

                    String jsonPayload = "{"
                            + "\"to\": \"" + message.destination + "\","
                            + "\"data\": {\"message\": \"" + message.content + "\"}"
                            + "}";

                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(jsonPayload.getBytes("UTF-8"));
                    }

                    int responseCode = conn.getResponseCode();
                    System.out.println("[GCM] Response Code: " + responseCode);

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void processQueueMqtt(BlockingQueue<Message> queue) {
        executorService.execute(() -> {
            while (true) {
                try {
                    Message message = queue.take(); // 取得並移除隊列頭部的訊息 
                    System.out.println("[MQTT] Sending to topic: " + message.destination + " | Message: " + message.content);
                    // 整合 MQTT 客戶端（例如 Eclipse Paho） 
                    MqttMessage mqttMessage = new MqttMessage(message.content.getBytes());
                    mqttMessage.setQos(2);
                    mqttInstance.publish(message.destination, mqttMessage);
                    System.out.println("Message sent: " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void processQueueEMail(BlockingQueue<Message> queue) {
        executorService.execute(() -> {
            while (true) {
                try {
                    Message message = queue.take(); // 取得並移除隊列頭部的訊息
                    System.out.println("[EMAIL] Sending to: " + message.destination + " | Subject: " + message.title);
                    // 整合 JavaMail API
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void processQueueSMS(BlockingQueue<Message> queue) {
        executorService.execute(() -> {
            while (true) {
                try {
                    Message message = queue.take(); // 取得並移除隊列頭部的訊息 
                    System.out.println("[SMS] Sending to: " + message.destination + " | Message: " + message.content);
                    // 整合 SMS API（例如 Twilio）
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void sendMessage(String type, String topic, String payload) {
        if (type.startsWith(TransportType.MQTT)) {
            sendMqttMessage(topic, payload);
        } else if (type.startsWith(TransportType.GCM)) {
            sendGcm(topic, payload);
        } else if (type.startsWith(TransportType.SMS)) {
            sendSms(topic, payload);
        } else if (type.startsWith(TransportType.HTTP)) {
            sendHttpMessage(topic, payload);
        }
    }

    public void sendHttpMessage(String url, String body) {
        httpQueue.offer(new Message(url, "", body));
    }

    public void sendGcm(String topic, String payload) {
        smsQueue.offer(new Message(topic, "", payload));
    }

    public void sendEmail(String to, String subject, String content) {
        emailQueue.offer(new Message(to, subject, content));
    }

    public void sendMqttMessage(String topic, String payload) {
        mqttQueue.offer(new Message(topic, "", payload));
    }

    public void sendSms(String phoneNumber, String message) {
        smsQueue.offer(new Message(phoneNumber, "", message));
    }

    public static final class TransportType {

        public static final String MQTT = "MQTT";
        public static final String HTTP = "HTTP";
        public static final String EMAIL = "EMAIL";
        public static final String GCM = "GCM";
        public static final String SMS = "SMS";
        public static final List<String> DATA_FROM = Arrays.asList(new String[]{MQTT, HTTP, EMAIL, GCM, SMS});

        public static final Map<String, String> getListMap() {
            Map<String, String> _data = new LinkedHashMap<String, String>();
            _data.put(MQTT, "MQTT");
            _data.put(HTTP, "HTTP");
            _data.put(EMAIL, "EMAIL");
            _data.put(GCM, "GCM");
            _data.put(SMS, "SMS");
            return _data;
        }
    }

    // 訊息封裝類
    private static class Message {

        private final String destination;
        private final String title;
        private final String content;

        public Message(String destination, String title, String content) {
            this.destination = destination;
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return "To: " + destination + " | Title: " + title + " | Content: " + content;
        }
    }

    public static MqttClient getMqttInstance() throws MqttException {
        if (mqttInstance == null) {
            mqttInstance = new MqttClient(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);

            // 設置重連處理
            mqttInstance.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("MQTT Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String messageContent = new String(message.getPayload());
                    System.out.println("Received message: " + messageContent);

                    // 記錄收到的訊息
                    logMessage("SUBSCRIBE", topic, messageContent);

                    // 根據收到的訊息進行不同處理
                    processReceivedMessage(topic, messageContent);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 可選的：處理訊息送達完成的回調
                    System.out.println("Message delivery complete");
                }
            });

            // 嘗試連接 MQTT 伺服器
            mqttInstance.connect(options);
            mqttInstance.subscribe(MQTT_TOPIC);  // 訂閱主題
        }
        return mqttInstance;
    }

    public static void disconnectMqttInstance() throws MqttException {
        if (mqttInstance != null && mqttInstance.isConnected()) {
            mqttInstance.disconnect();
            mqttInstance = null;
        }
    }

    // 記錄收到的訊息
    private static void logMessage(String type, String topic, String message) {
        String logEntry = type + " - Topic: " + topic + ", Message: " + message;
        if (mqttRevicedLog.size() >= LOG_LIMIT) {
            mqttRevicedLog.poll(); // 移除最舊的訊息
        }
        mqttRevicedLog.offer(logEntry);
    }

    // 獲取最近的訊息日誌
    public static Queue<String> getMessageLog() {
        return mqttRevicedLog;
    }

    // 根據收到的訊息進行處理
    private static void processReceivedMessage(String topic, String message) {
        // 根據接收到的訊息內容進行業務邏輯處理
        if ("system/notification".equals(topic)) {
            if ("update".equals(message)) {
                // 當收到 "update" 訊息時執行某些操作
                System.out.println("Triggering update process due to received message: " + message);
                // 例如，可以觸發資料庫更新、通知其他系統或發佈新訊息
            } else {
                // 根據不同訊息進行相應的處理
                System.out.println("Processing other received message: " + message);
            }
        }
        // 你也可以根據 topic 和 message 做進一步的條件處理
    }
} 
