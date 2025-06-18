package itri.sstc.freemarker.httpd;

import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.httpd.AbstractRequestHandler;
import itri.sstc.framework.core.httpd.HttpdConfig;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;
import itri.sstc.freemarker.core.FMTemplateEngine;
import itri.sstc.freemarker.core.RequestData;
import itri.sstc.freemarker.core.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FreeMarker Http Request 處理器
 *
 * @author schung
 */
// 因為不相容於 RESTfulAPI, 只能實作 AbstractRequestHandler
public class FreemarkerAPI extends AbstractRequestHandler {

    private static final Logger logger = Logger.getLogger(RESTfulAPI.class.getName());

    private final Properties defaultHeaders;

    public FreemarkerAPI() {
        // 初始化預設的回應標頭
        long expiredTime = HttpSessionManager.getInstance().getExpiredTime();
        this.defaultHeaders = new Properties();
        //this.defaultHeaders.setProperty("Content-Type", "application/json;charset=utf-8");
        this.defaultHeaders.setProperty("Content-Type", "text/html;charset=utf-8");
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
        logger.log(Level.INFO, "新增 RootAPI 實例");
    }

    @Override
    public String getContextPath() { 
        return "/ftl/";
    }

    @Override
    public Properties getResponseHeaders() {
        return defaultHeaders;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        initResponse(exchange);
        //
        String path = getRelatedPath(exchange);
        String[] requestPath = path.split("/");
        //
        if (requestPath.length > 0) {
            try {
                RequestHandler handler = FMTemplateEngine.getInstance().getHandler(requestPath[0]);
                //
                if (handler != null) {
                    RequestData data = new RequestData(this, exchange);
                    ByteBuffer content = handler.render(data);
                    if (content != null && content.hasArray()) { 
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        DataOutputStream output = new DataOutputStream(exchange.getResponseBody());
                        output.write(content.array());
                        output.flush();
                        output.close();
                        content.clear();
                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, 0);
                    }
                } else {
                    logger.log(Level.WARNING, String.format("NOT_FOUND Http Request %s", requestPath[0]));
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getLocalizedMessage());
                PrintWriter pwr = new PrintWriter(new OutputStreamWriter(exchange.getResponseBody()));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                ex.printStackTrace(pwr);
                pwr.flush();
            }
        } else {
            logger.log(Level.WARNING, String.format("FORBIDDEN Http Request %s ***", (path + path.indexOf("/"))));
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
        }
        //
        exchange.close();
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

}
