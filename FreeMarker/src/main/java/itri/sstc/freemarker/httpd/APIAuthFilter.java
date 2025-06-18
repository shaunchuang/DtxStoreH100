package itri.sstc.freemarker.httpd;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.HttpService;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;

import java.io.IOException;
import java.util.Map;
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
        if (session == null) {
            session = HttpSessionManager.getInstance().createSession(exchange);
            HttpService.log(Level.INFO, "Create new Session: " + session.getSessionId());
            exchange.setAttribute("HttpSessionId", session.getSessionId());
            exchange.getResponseHeaders().add("Set-Cookie", session.toCookieString()); 
        } else {
            session.updateLastAccessTime();
            if (exchange.getAttribute("sessionId") == null || exchange.getAttribute("sessionId").equals(session.getSessionId())) {
                exchange.setAttribute("sessionId", session.getSessionId());
            }
        }
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return "API 授權檢查器";
    }

}
