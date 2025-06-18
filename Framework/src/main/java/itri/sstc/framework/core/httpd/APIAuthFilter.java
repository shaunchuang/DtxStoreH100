package itri.sstc.framework.core.httpd;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;

/**
 * API 授權檢查器
 *
 * @author schung
 */
public class APIAuthFilter extends Filter {

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        HttpSession session = HttpSessionManager.getInstance().getSession(exchange, false);
        //
        if (session != null) {
            session.updateLastAccessTime();
            if (exchange.getAttribute("sessionId") == null || exchange.getAttribute("sessionId").equals(session.getSessionId())) {
                exchange.setAttribute("sessionId", session.getSessionId());
            }
            chain.doFilter(exchange);
        } else {
            HttpService.log(Level.WARNING, String.format("%s, No Session", exchange.getRequestURI().getPath()));
            String json = String.format("{ \"status\": %d }", HttpURLConnection.HTTP_UNAUTHORIZED);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
            //
            DataOutputStream output = new DataOutputStream(exchange.getResponseBody());
            output.write(json.getBytes("utf-8"));
            output.flush();
            output.close();
            //
            exchange.close();
        }
    }

    @Override
    public String description() {
        return "API 授權檢查器";
    }

}
