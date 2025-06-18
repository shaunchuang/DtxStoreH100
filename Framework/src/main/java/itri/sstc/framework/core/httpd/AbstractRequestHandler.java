package itri.sstc.framework.core.httpd;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * HTTP 要求處理器抽象類別
 *
 * @author schung
 */
public abstract class AbstractRequestHandler implements HttpHandler {

    final static String ENCODE = HttpdConfig.get("encode", "utf-8");

    /**
     * 解析要求查詢字串
     *
     * @param exchange HttpExchange
     * @return
     */
    public final Map<String, String> parseRequestQuery(HttpExchange exchange) {
        // 取出查詢字串, ie: param1=1&param2=2...
        String queryString = exchange.getRequestURI().getQuery();
        Map<String, String> parameters = new HashMap<String, String>();
        if (queryString != null && !queryString.isEmpty()) {
            String[] _parameters = queryString.split("&");
            for (String p : _parameters) {
                String[] temp = p.split("=");
                if (temp.length == 2) {
                    parameters.put(temp[0], temp[1]);
                }
            }
        }
        return parameters;
    }

    /**
     * 解析要求參數 (包含 URL 查詢字串與 POST 表單資料)
     *
     * @param exchange HttpExchange
     * @return 參數映射表
     */
    public final Map<String, String> parseRequestParameters(HttpExchange exchange) {
        Map<String, String> parameters = new HashMap<>();

        // 解析 URL Query String 的參數
        try {
            String queryString = exchange.getRequestURI().getQuery();
            if (queryString != null && !queryString.isEmpty()) {
                parameters.putAll(parseQueryString(queryString));
            }
        } catch (Exception ex) {
        }

        // 如果是 POST 方法，解析請求體的參數
        try {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String requestBody = getRequestBody(exchange);
                parameters.putAll(parseQueryString(requestBody));
            }
        } catch (Exception ex) {
        }

        return parameters;
    }

    /**
     * 解析查詢字串 (key1=value1&key2=value2 格式)
     *
     * @param query 查詢字串
     * @return 參數映射表
     */
    private Map<String, String> parseQueryString(String query) {
        Map<String, String> parameters = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2); // 允許值為空
            if (keyValue.length > 0) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
                parameters.put(key, value);
            }
        }
        return parameters;
    }

    /**
     * 取得要求內容(POST上傳的資料)
     *
     * @param exchange
     * @return
     * @throws IOException
     */
    public final String getRequestBody(HttpExchange exchange) throws IOException {
        byte[] data = getRequestBodyBytes(exchange);
        // 強制使用 utf-8 編碼
        return new String(data, ENCODE);
    }

    public final byte[] getRequestBodyBytes(HttpExchange exchange) throws IOException {
        BufferedInputStream bfis = new BufferedInputStream(exchange.getRequestBody());
        //
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        while (true) {
            int size = bfis.read(buffer);
            if (size <= 0) {
                break;
            }
            baos.write(buffer, 0, size);
        }
        byte[] output = baos.toByteArray();
        baos.close();
        //
        return output;
    }

    /**
     * 設定 Http Response Headers
     *
     * @param exchange
     */
    public final void initResponse(HttpExchange exchange) {
        Properties prop = getResponseHeaders();
        Headers headers = exchange.getResponseHeaders();
        for (String key : prop.stringPropertyNames()) {
            headers.set(key, prop.getProperty(key));
        }
    }

    /**
     * 讀取 Cookie 表
     *
     * @param exchange
     * @return Cookie 表
     */
    public final Map<String, String> getCookies(HttpExchange exchange) {
        return CookieParser.parse(exchange.getRequestHeaders());
    }

    /**
     * 設定 Cookie
     *
     * @param exchange
     * @param cookie // "key=value;path=
     */
    public final void setCookie(HttpExchange exchange, String cookie) {
        exchange.getResponseHeaders().add("Set-Cookie", cookie);
    }

    /**
     * 取得 HTTP 回應的表頭設定
     *
     * @return HTTP 回應的表頭設定
     */
    public abstract Properties getResponseHeaders();

    /**
     * 取得HTTP 要求處理器的 Context Path
     *
     * @return Context Path
     */
    public abstract String getContextPath();

}
