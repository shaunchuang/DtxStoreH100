package itri.sstc.framework.core.httpd.file;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * 靜態檔案(.html, .js, .css, ...)要求處理
 * @author schung
 */
public class FileHandler implements HttpHandler {

    // public final static String WEB_HOME = "/html/";

    private final String root;
    private final ThreadGroup tasks = new ThreadGroup("HTML File Handler");

    public FileHandler(String root) {
       this.root = root;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (!method.equalsIgnoreCase("get")) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
            return;
        }
        FileRequestHandleTask task = new FileRequestHandleTask(tasks, exchange);
        task.start();
    }

}
