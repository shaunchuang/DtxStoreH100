package itri.sstc.freemarker.core;

import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.CookieParser;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.freemarker.httpd.FreemarkerAPI;

import java.util.List;
import java.util.Map;

/**
 * 要求資料封裝
 *
 * @author schung
 */
public class RequestData {

    private final FreemarkerAPI parent;

    public final HttpExchange exchange;
    public final HttpSession session;
    public final Map<String, String> cookies;
    public final Map<String, String> query;
    public final String relatedPath;
    public final List<String> paths;
    public final String body;

    public RequestData(FreemarkerAPI parent, HttpExchange exchange) {
        this.parent = parent;
        this.exchange = exchange;
        //
        this.session = parent.getSession(exchange);
        this.cookies = CookieParser.parse(exchange.getRequestHeaders());
        //this.query = parent.parseRequestQuery(exchange);
        this.query = parent.parseRequestParameters(exchange);
        this.relatedPath = parent.getRelatedPath(exchange);
        this.paths = parent.getPathParameter(exchange);
        String _body;
        try {
            _body = parent.getRequestBody(exchange);
        } catch (Exception ex) {
            _body = null;
        }
        this.body = _body;
    }

    public Object getAttribute(String name) {
        return exchange.getAttribute(name);
    }

    public void setAttribute(String name, Object attribute) {
        exchange.setAttribute(name, attribute);
    }


}
