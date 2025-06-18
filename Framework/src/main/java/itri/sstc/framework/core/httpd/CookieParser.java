package itri.sstc.framework.core.httpd;

import com.sun.net.httpserver.Headers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cookie 解析器
 *
 * @author schung
 */
public class CookieParser {

    public final static Map<String, String> parse(Headers headers) {
        Map<String, String> cookies = new HashMap<>();
        if (headers.containsKey("Cookie")) {
            List<String> cookieList = headers.get("Cookie");
            for (String cookie : cookieList) {
                String[] datas = cookie.split(";");
                for (String data : datas) {
                    data = data.trim();
                    int idx = data.indexOf("=");
                    if (idx <= 0) {
                        continue;
                    }
                    String key = data.substring(0, idx);
                    String value = data.substring(idx + 1);
                    cookies.put(key, value);
                }
            }
        }
        return cookies;
    }

}
