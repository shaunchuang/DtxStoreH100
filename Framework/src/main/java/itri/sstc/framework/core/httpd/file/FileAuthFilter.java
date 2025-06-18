package itri.sstc.framework.core.httpd.file;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.HttpdConfig;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 檔案存取授權檢查器
 *
 * @author schung
 */
public class FileAuthFilter extends Filter {

    private static final Logger logger = Logger.getLogger(FileAuthFilter.class.getName());

    private final PublicFilenameFilter filter = new PublicFilenameFilter();

    public FileAuthFilter() {
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        File target = getFile(exchange);
        if (target == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.close();
            return;
        }
        //
        if (target.isDirectory()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
            exchange.close();
            return;
        }
        //
        boolean ignored = checkIgnored(target);
        if (ignored) {
            chain.doFilter(exchange);
            return;
        }
        // 需要檢查是否授權
        HttpSession session = HttpSessionManager.getInstance().getSession(exchange, false);
        if (session != null) {
            session.updateLastAccessTime();
            chain.doFilter(exchange);
            return;
        } else {
            logger.log(Level.WARNING, "Request with no session");
        }
        // Forward to default file.
        String location = String.format("%s%s", exchange.getHttpContext().getPath(), HttpdConfig.get("html.default", "index.html"));
        exchange.getResponseHeaders().add("Location", location);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SEE_OTHER, 0);
        exchange.close();
        //
        logger.log(Level.FINE, "Redirect to default page");
    }

    @Override
    public String description() {
        return "檔案存取授權檢查器";
    }

    private boolean checkIgnored(File target) {
        // 檢查是否有設定忽略
        File folder = target.getParentFile();
        File[] files = folder.listFiles(filter);
        return (files.length > 0);
    }

    private File getFile(HttpExchange exchange) {
        String fileId = exchange.getRequestURI().getPath();
        //
        fileId = "." + fileId;
        File file = new File(fileId);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

}
