package itri.sstc.framework.core.api;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.AbstractRequestHandler;
import itri.sstc.framework.core.httpd.HttpdConfig;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * RESTfulAPI 宣告
 *
 * @author schung
 */
public abstract class RESTfulAPI extends AbstractRequestHandler {

    private static final Logger logger = Logger.getLogger(RESTfulAPI.class.getName());

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RESTfulAPIDefine {

        String url() default "/";

        String methods() default "get";

        String description() default "API描述";

    }

    private final Properties defaultHeaders;
    // K: urlPattern, V: Method
    private final Map<String, Method> apiMap = new HashMap<String, Method>();
    private final Set<RESTfulAPIDefine> apis = new HashSet<RESTfulAPIDefine>();

    private final ExecutorService execService = Executors.newFixedThreadPool(10);
    private final long apiTimeout = Long.valueOf(HttpdConfig.get("apiTimeout", "30000"));
    private final boolean dumpData = (logger != null) && (logger.getLevel() != null) && (logger.getLevel().intValue() <= Level.FINE.intValue());

    public RESTfulAPI() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                execService.shutdown();
            }
        });
        // 初始化預設的回應標頭
        long expiredTime = HttpSessionManager.getInstance().getExpiredTime();
        this.defaultHeaders = new Properties();
        this.defaultHeaders.setProperty("Content-Type", "application/json;charset=utf-8");
        this.defaultHeaders.setProperty("Connection", "Keep-Alive");
        this.defaultHeaders.setProperty("Keep-Alive", String.format("timeout=%d, max=100", (expiredTime / 1000L)));
        this.defaultHeaders.setProperty("Pragma", "no-cache");
        //
        this.defaultHeaders.setProperty("Access-Control-Allow-Origin", "*");
        this.defaultHeaders.setProperty("Access-Control-Allow-Headers", "Content-Type,Authorization");
        this.defaultHeaders.setProperty("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
        //
        this.defaultHeaders.setProperty("Server", "LiteHttpd");
        this.defaultHeaders.setProperty("X-Powered-By", "Java");
        // 強制使用HTTPS
        if (Boolean.parseBoolean(HttpdConfig.get("https", "false"))) {
            this.defaultHeaders.setProperty("Strict-Transport-Security", "max-age=86400; includeSubDomains");
        }
        //
        Class clazz = this.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            RESTfulAPIDefine apiDefine = method.getAnnotation(RESTfulAPIDefine.class);
            if (apiDefine != null) {
                this.apis.add(apiDefine);
                this.apiMap.put(apiDefine.url(), method);
                logger.fine(String.format("# ADD Method %s to Pattern %s", method.getName(), apiDefine.url()));
            }
        }
    }

    /**
     * 列出所有定義的URL
     *
     * @return 所有定義的 API URL
     */
    public final List<String> listAPI() {
        return Arrays.asList(apiMap.keySet().toArray(new String[0]));
    }

    public final void log(Level level, String message) {
        logger.log(level, String.format("\"%s\" %s", this.getContextPath(), message));
    }

    public final void log(Level level, String message, Throwable tw) {
        logger.log(level, String.format("\"%s\" %s", this.getContextPath(), message), tw);
    }

    public final void release() {
    }

    @Override
    public final void handle(final HttpExchange exchange) throws IOException {
        this.execService.execute(new Runnable() {
            @Override
            public void run() {
                //System.out.println("*** handle http request in new Thread ... " + exchange.getRequestURI().getPath());
                try {
                    long begin = System.currentTimeMillis();
                    handleRequest(exchange);
                    long duration = System.currentTimeMillis() - begin;
                    if (duration > apiTimeout) {
                        logger.warning(String.format("%s %s processing time over  %d ms", exchange.getRequestMethod(), exchange.getRequestURI().toString(), duration));
                    }
                } catch (Exception ex) {
                    logger.severe(String.format("%s %s processing error: %s", exchange.getRequestMethod(), exchange.getRequestURI().toString(), ex.getMessage()));
                } finally {
                    exchange.close();
                }
                //System.out.println("*** handle http request in new Thread done. " + exchange.getRequestURI().getPath());
            }
        });
    }

    /**
     * 執行 Http Request 處理
     *
     * @param exchange
     */
    private void handleRequest(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            log(Level.INFO, "Request OPTIONS");
            // 為了處理 CORS，加上以下程式碼
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, -1); // HttpURLConnection.HTTP_NO_CONTENT = 204
        } else {
            initResponse(exchange);
            String[] requestPath = getRelatedPath(exchange).split("/");
            if (requestPath.length > 0) {
                String apiName = requestPath[0];
                log(Level.INFO, String.format("Call API %s", apiName));
                //
                if (apiMap.containsKey(apiName)) {
                    String json = null;
                    Method method = apiMap.get(apiName);
                    String httpReqMethod = exchange.getRequestMethod().toLowerCase();
                    try {
                        RESTfulAPIDefine apiDefine = method.getAnnotation(RESTfulAPIDefine.class);
                        // 檢查 HTP Method 是否在 RESTfulAPIDefine 宣告中
                        String methods = apiDefine.methods();
                        if (methods.contains(httpReqMethod)) {
                            method.setAccessible(true);
                            json = (String) method.invoke(this, exchange);
                        } else {
                            log(Level.WARNING, String.format("BAD_METHOD %s for Request %s", method, exchange.getRequestURI().toString()));
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        ex.printStackTrace();
                        log(Level.SEVERE, String.format("Call %s Error: %s", method.getName(), ex.getMessage()));
                    }
                    //
                    if (json != null) {
                        DataOutputStream output = new DataOutputStream(exchange.getResponseBody());
                        output.write(json.getBytes("utf-8"));
                        output.flush();
                        output.close();
                    }
                    //
                    if (dumpData) {
                        StringBuilder sb = new StringBuilder();
                        Headers responseHeaders = exchange.getResponseHeaders();
                        for (String key : responseHeaders.keySet()) {
                            sb.append("\r\n").append(key).append(": ");
                            List<String> values = responseHeaders.get(key);
                            for (String value : values) {
                                sb.append(value).append(";");
                            }
                        }
                        sb.append(String.format("%nResponse: %s%n", json));
                        log(Level.INFO, sb.toString());
                    }
                } else if (apiName.equals("listAPIDefines")) {
                    JSONArray array = new JSONArray();
                    for (RESTfulAPIDefine apiDefine : apis) {
                        JSONObject json = new JSONObject();
                        json.put("description", apiDefine.description());
                        json.put("url", apiDefine.url());
                        array.put(json);
                    }
                    //
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    DataOutputStream output = new DataOutputStream(exchange.getResponseBody());
                    output.write(array.toString().getBytes("utf-8"));
                    output.flush();
                    output.close();
                } else {
                    log(Level.WARNING, String.format("BAD_REQUEST: %s", exchange.getRequestURI().toString()));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    if (dumpData) {
                        StringBuilder sb = new StringBuilder();
                        Headers requestHeaders = exchange.getRequestHeaders();
                        for (String key : requestHeaders.keySet()) {
                            sb.append("\r\n").append(key).append(": ");
                            List<String> values = requestHeaders.get(key);
                            for (String value : values) {
                                sb.append(value).append(";");
                            }
                        }
                        log(Level.INFO, sb.toString());
                    }
                }
            } else {
                log(Level.WARNING, String.format("FORBIDDEN Http Request %s", exchange.getRequestURI().toString()));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
            }
        }
    }

    @Override
    public Properties getResponseHeaders() {
        return defaultHeaders;
    }

    /**
     * 取得關聯於 Context 的相對路徑
     *
     * @param exchange
     * @return
     */
    public final String getRelatedPath(HttpExchange exchange) {
        String uri = exchange.getRequestURI().getPath();
        int index = uri.indexOf(getContextPath());
        String path = uri.substring(index + getContextPath().length());
        return path;
    }

    /**
     * 取得 HttpSession
     *
     * @param exchange
     * @return HttpSession
     */
    public HttpSession getSession(HttpExchange exchange) {
        return HttpSessionManager.getInstance().getSession(exchange, false);
    }

    /**
     * 取得起迄時間
     *
     * @param uri ?/begin/DATE/ + /end/DATE/
     * @return { beginDate, endDate }
     */
    public Date[] getDuration(URI uri) {
        String begin = getValueOfKeyInPath(uri, "begin");
        String end = getValueOfKeyInPath(uri, "end");
        try {
            Date[] duration = new Date[2];
            duration[0] = Date.from(LocalDate.from(DateTimeFormatter.ISO_DATE.parse(begin)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            duration[1] = Date.from(LocalDate.from(DateTimeFormatter.ISO_DATE.parse(end)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return duration;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(String.format("Duration String format Error: %s", ex.getMessage().replaceAll("\"", "'")));
        }
    }

    /**
     * 將 URI 路徑拆解成字串陣列
     *
     * @param exchange
     * @return
     */
    public final List<String> getPathParameter(HttpExchange exchange) {
        String path = getRelatedPath(exchange);
        String[] parts = path.split("/");
        return Arrays.asList(parts);
    }

    /**
     * 從 Http Request URI 路徑中解析參數
     *
     * @param uri Http Request URI
     * @param key 參數Key
     * @return 跟在參數Key後面的值(?/key/value/?)
     */
    public final String getValueOfKeyInPath(final URI uri, final String key) {
        String path = uri.getPath();
        String pathKey = String.format("/%s/", key);
        int start = path.indexOf(pathKey);
        if (start <= 0) {
            return null;
        }
        start += pathKey.length();
        int end = path.indexOf("/", start);
        if (end > start) {
            return path.substring(start, end);
        } else {
            return path.substring(start);
        }
    }

    /**
     * 從 Http Request Query String 取得查詢參數
     *
     * @param uri Http Request URI
     * @param key 查詢參數Key
     * @return 查詢參數
     */
    public final String getValueOfKeyInQuery(final URI uri, final String key) {
        String query = uri.getQuery();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                if (pair.startsWith(String.format("%s=", key))) {
                    int idx = pair.indexOf("=");
                    String value = pair.substring(idx + 1);
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * 從 HttpSession 取得設定參數
     *
     * @param exchange
     * @param key 設定參數Key
     * @return 設定參數
     */
    public final Object getValueOfKeyInSession(final HttpExchange exchange, final String key) {
        HttpSession session = HttpSessionManager.getInstance().getSession(exchange, false);
        if (session != null) {
            return session.getValue(key);
        } else {
            return null;
        }
    }

    /**
     * 轉向其他網址
     *
     * @param exchange
     * @param location 其他網址
     * @throws IOException
     */
    public final void redirectTo(final HttpExchange exchange, final String location) throws IOException {
        exchange.getResponseHeaders().add("Location", location);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SEE_OTHER, 0);
        exchange.close();
    }

}
