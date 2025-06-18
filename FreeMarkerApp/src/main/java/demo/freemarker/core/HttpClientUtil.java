/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.core;

/**
 *
 * @author zhush
 */
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.util.Timeout;
/**
 * HTTP 工具類別，用於發送 GET、POST、PUT、DELETE 請求
 */
public final class HttpClientUtil {

    // 連線池管理，可設定最大併發連線數等參數
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER
            = PoolingHttpClientConnectionManagerBuilder.create()
                    .setMaxConnTotal(100) // 連線池中最大連線數，可依需求調整
                    .setMaxConnPerRoute(20) // 每個 route (同主機) 最大連線數
                    .build();

    // 建立可重用的 CloseableHttpClient
    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(Timeout.ofMilliseconds(10_000)) // 10秒連線逾時
            .setResponseTimeout(Timeout.ofMilliseconds(10_000)) // 10秒回應逾時
            .build();
    
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.custom()
            .setConnectionManager(CONNECTION_MANAGER)
            .setDefaultRequestConfig(requestConfig)
            .build();

    // 私有建構子，防止實例化
    private HttpClientUtil() {
    }

    /**
     * 發送 HTTP GET 請求
     *
     * @param url 目標網址
     * @param headers HTTP 標頭 (可選)
     * @return 回應的字串
     * @throws IOException 發生錯誤時拋出
     */
    public static String sendGetRequest(String url, Map<String, String> headers) throws IOException {
        HttpGet request = new HttpGet(url);
        addHeaders(request, headers);
        return executeRequest(request);
    }

    public static byte[] sendGetRequestForBytes(String url, Map<String, String> headers) throws IOException {
        HttpGet request = new HttpGet(url);
        addHeaders(request, headers);

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            int statusCode = response.getCode();
            HttpEntity entity = response.getEntity();

            if (statusCode >= 200 && statusCode < 300 && entity != null) {
                return EntityUtils.toByteArray(entity);
            } else {
                throw new IOException("HTTP error: " + statusCode);
            }
        } catch (Exception ex) {
            Logger.getLogger(HttpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
    /**
     * 發送 HTTP POST 請求
     *
     * @param url 目標網址
     * @param headers HTTP 標頭 (可選)
     * @param body JSON 或 其他文字格式的請求體
     * @return 回應的字串
     * @throws IOException 發生錯誤時拋出
     */
    public static String sendPostRequest(String url, Map<String, String> headers, String body) throws IOException {
        HttpPost request = new HttpPost(url);
        addHeaders(request, headers);
        // 如果確定是 JSON，可以設定為 "application/json; charset=UTF-8"
        request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        return executeRequest(request);
    }

    /**
     * 發送 HTTP PUT 請求
     *
     * @param url 目標網址
     * @param headers HTTP 標頭 (可選)
     * @param body JSON 或 其他文字格式的請求體
     * @return 回應的字串
     * @throws IOException 發生錯誤時拋出
     */
    public static String sendPutRequest(String url, Map<String, String> headers, String body) throws IOException {
        HttpPut request = new HttpPut(url);
        addHeaders(request, headers);
        request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        return executeRequest(request);
    }

    /**
     * 發送 HTTP DELETE 請求
     *
     * @param url 目標網址
     * @param headers HTTP 標頭 (可選)
     * @return 回應的字串
     * @throws IOException 發生錯誤時拋出
     */
    public static String sendDeleteRequest(String url, Map<String, String> headers) throws IOException {
        HttpDelete request = new HttpDelete(url);
        addHeaders(request, headers);
        return executeRequest(request);
    }

    /**
     * 執行 HTTP 請求，並處理回應
     *
     * @param request HTTP 請求
     * @return 回應的字串
     * @throws IOException 發生錯誤時拋出
     */
    private static String executeRequest(HttpUriRequestBase request) throws IOException {
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            int statusCode = response.getCode();
            HttpEntity entity = response.getEntity();
            String responseBody = entity != null ? EntityUtils.toString(entity, StandardCharsets.UTF_8) : "";

            if (statusCode >= 200 && statusCode < 300) {
                // 2xx 回應視為成功
                return responseBody;
            } else {
                // 非 2xx 回應，直接拋出錯誤
                throw new IOException("HTTP error: " + statusCode + " - " + responseBody);
            }
        } catch (ParseException ex) {
            // 如果需要，您也可改為 throw new IOException(ex) 以便呼叫端捕捉
            Logger.getLogger(HttpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 添加 HTTP 標頭
     *
     * @param request HTTP 請求
     * @param headers 標頭
     */
    private static void addHeaders(HttpUriRequestBase request, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach((key, value) -> request.addHeader(new BasicHeader(key, value)));
        }
    }

    /**
     * 若應用程式結束前想關閉 HttpClient，可呼叫本方法 （例如在 ServletContextListener 或 Spring
     * 的shutdown hook 中）
     */
    public static void closeHttpClient() {
        try {
            HTTP_CLIENT.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpClientUtil.class.getName()).log(Level.SEVERE,
                    "Error closing HTTP client", ex);
        }
    }
}
