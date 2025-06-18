package demo.freemarker.httpd;

import com.sun.net.httpserver.HttpExchange;
import itri.sstc.framework.core.httpd.AbstractRequestHandler;
import itri.sstc.framework.core.httpd.HttpdConfig;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;
import java.io.IOException; 
import java.net.HttpURLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RootAPI extends AbstractRequestHandler {
    private static final Logger logger = Logger.getLogger(RootAPI.class.getName());
    private final Properties defaultHeaders;

    public RootAPI() {
        long expiredTime = HttpSessionManager.getInstance().getExpiredTime();
        this.defaultHeaders = new Properties();
        this.defaultHeaders.setProperty("Content-Type", "text/html;charset=utf-8");
        this.defaultHeaders.setProperty("Connection", "Keep-Alive");
        this.defaultHeaders.setProperty("Keep-Alive", String.format("timeout=%d, max=100", (expiredTime / 1000L)));
        this.defaultHeaders.setProperty("Pragma", "no-cache");
        this.defaultHeaders.setProperty("Access-Control-Allow-Origin", "*");
        this.defaultHeaders.setProperty("Access-Control-Allow-Headers", "Content-Type,Authorization");
        this.defaultHeaders.setProperty("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE");
        this.defaultHeaders.setProperty("Server", "LiteHttpd");
        this.defaultHeaders.setProperty("X-Powered-By", "Java");
        
        if (Boolean.parseBoolean(HttpdConfig.get("https", "false"))) {
            this.defaultHeaders.setProperty("Strict-Transport-Security", "max-age=86400; includeSubDomains");
        }
        
        logger.log(Level.INFO, "新增 RootAPI 實例");
        
        try {
            MessageTransporter.getInstance(); 
            MessageTransporter.getMqttInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(RootAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getContextPath() {
        return "/";
    }

    @Override
    public Properties getResponseHeaders() {
        return defaultHeaders;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = getRelatedPath(exchange);
        
        if (path.isEmpty() || path.equals("/")) {
            redirectToHomePage(exchange);
            return;
        }
        
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        exchange.close();
    }

    private void redirectToHomePage(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Location", "/html/index.html");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_PERM, -1);
        exchange.close();
    }

    public final String getRelatedPath(HttpExchange exchange) {
        String uri = exchange.getRequestURI().getPath();
        int index = uri.indexOf(getContextPath());
        return index >= 0 ? uri.substring(index + getContextPath().length()) : "";
    }

    public HttpSession getSession(HttpExchange exchange) {
        return HttpSessionManager.getInstance().getSession(exchange, false);
    }
}
