package itri.sstc.framework.core.httpd;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import itri.sstc.framework.core.httpd.file.FileAuthFilter;
import itri.sstc.framework.core.httpd.file.FileHandler;
import itri.sstc.framework.core.httpd.manage.ManageHandler;
import itri.sstc.framework.core.httpd.ssl.SSLContextUtil;
import itri.sstc.framework.core.httpd.ssl.HttpsConfiguratorImpl;
import itri.sstc.framework.core.httpd.session.HttpSessionManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;

/**
 * HTTP 服務器
 *
 * @author schung
 */
public final class HttpService {

    private final static Logger logger = Logger.getLogger(HttpService.class.getName());

    public final static String VERSION = "20241222";

    private final HttpServer server;
    private final boolean https;
    private final String rootContext;
    private final int http_port;
    private final Map<String, HttpContext> contexts = new TreeMap<String, HttpContext>();
    //
    private boolean isRunning;

    public HttpService() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this(null);
    }

    public HttpService(Executor executor) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        logger.config(String.format("LiteHttpd Service 版本 %s", VERSION));
        //
        try {
            HttpdConfig.load();
        } catch (IOException ioe) {
        }
        //
        this.http_port = Integer.parseInt(HttpdConfig.get("port", "8080"));
        String temp = HttpdConfig.get("context", "/");
        if (temp.endsWith("/")) {
            this.rootContext = temp.substring(0, temp.length() - 1);
        } else {
            this.rootContext = temp;
        }
        this.https = Boolean.parseBoolean(HttpdConfig.get("https", "false"));
        //
        try {
            if (this.https) {
                HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(this.http_port), 0);
                File keyStoreFile = new File(HttpdConfig.get("keystore", "./litehpptd.keystore"));
                String passwd = HttpdConfig.get("pass", "02750963");
                SSLContext context = SSLContextUtil.getSslContext(keyStoreFile, passwd);
                HttpsConfigurator httpsConfig = new HttpsConfiguratorImpl(context);
                httpsServer.setHttpsConfigurator(httpsConfig);
                //
                this.server = httpsServer;
                logger.log(Level.CONFIG, String.format("在網路埠 %d 建立 HTTPS 服務", this.http_port));
            } else {
                this.server = HttpServer.create(new InetSocketAddress(this.http_port), 0);
                logger.log(Level.CONFIG, String.format("在網路埠 %d 建立 HTTP 服務", this.http_port));
            }
        } catch (Exception ex) {
            throw new IOException(String.format("在網路埠 %d 建立 %s 服務失敗: %s", this.http_port, ((https) ? "HTTPS" : "HTTP"), ex.getMessage()));
        }
        String html = HttpdConfig.get("html.home", "");
        if (html.trim().isEmpty()) {
            logger.log(Level.CONFIG, "不需設置靜態檔案要求處理器");
        } else {
            try {
                // 設置 HTTP 靜態檔案要求處理器
                FileHandler fileHandler = new FileHandler(html);
                logger.log(Level.CONFIG, String.format("設置靜態檔案要求處理器: %s", fileHandler.getRoot()));
                HttpContext context = this.server.createContext(fileHandler.getRoot(), fileHandler);
                boolean auth = Boolean.parseBoolean(HttpdConfig.get("html.auth", "false"));
                if (auth) {
                    context.getFilters().add(new FileAuthFilter());
                    logger.log(Level.CONFIG, String.format("設置靜態檔案要求授權檢查器: %s", fileHandler.getRoot()));
                }
            } catch (Exception ex) {
            }
        }
        if (Boolean.parseBoolean(HttpdConfig.get("httpd.manage", "false"))) {
            // 設置 HTTP 伺服器管理要求處理器
            ManageHandler manageHandler = new ManageHandler(this);
            logger.log(Level.CONFIG, String.format("設置 HTTP 伺服器管理要求處理器: %s", manageHandler.getContextPath()));
            HttpContext context = this.server.createContext(manageHandler.getContextPath(), manageHandler);
        }
        //
        for (String key : HttpdConfig.keys()) {
            if (key.startsWith("Handler.")) {
                String configText = HttpdConfig.get(key, "");
                String[] values = configText.split(",");
                if (values.length == 0) {
                    continue;
                }
                //
                String handlerClassName = values[0];
                //
                Object handler = null;
                try {
                    Constructor constructor = Class.forName(handlerClassName).getConstructor(new Class[0]);
                    handler = constructor.newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                    logger.log(Level.WARNING, String.format("Class %s 沒有定義適合的建構子", handlerClassName));
                    continue;
                }
                //
                if (handler != null && handler instanceof AbstractRequestHandler) {
                    AbstractRequestHandler requestHandler = (AbstractRequestHandler) handler;
                    String path = requestHandler.getContextPath();
                    if (!path.startsWith("/")) {
                        path = "/" + path;
                    }
                    path = String.format("%s%s", this.rootContext, path);
                    HttpContext ctx = addContext(path, requestHandler);
                    //
                    String filterClassName = null;
                    if (values.length == 2) {
                        filterClassName = values[1];
                    }
                    Object filter = null;
                    if (filterClassName != null && !filterClassName.trim().isEmpty()) {
                        try {
                            Constructor constructor = Class.forName(filterClassName).getConstructor(new Class[0]);
                            filter = constructor.newInstance();
                        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                            logger.log(Level.WARNING, String.format("Class %s 沒有定義適合的建構子", filterClassName));
                        }
                    }
                    if (filter != null && filter instanceof Filter) {
                        ctx.getFilters().add((Filter) filter);
                        logger.log(Level.CONFIG, String.format("對 %s : %s 設置授權檢查器 ", ((https) ? "HTTPS" : "HTTP"), ctx.getPath()));
                    }
                }
            }
        }
        //
        this.server.setExecutor(executor);
        logger.log(Level.INFO, "初始化 LiteHttpd Service 完成");
    }

    @Override
    protected void finalize() throws Throwable {
        if (server != null) {
            server.stop(0);
        }
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public final String getName() {
        return "LiteHttpd";
    }

    public final HttpServer getHttpServer() {
        return this.server;
    }

    public final String getProtocol() {
        return https ? "HTTPS" : "HTTP";
    }

    public int getPort() {
        return http_port;
    }

    public String getRootContext() {
        return rootContext;
    }

    public Map<String, HttpContext> getContexts() {
        return contexts;
    }

    public InetSocketAddress getInetSocketAddress() {
        return (server == null) ? null : server.getAddress();
    }

    /**
     * 指派處理器
     *
     * @param name 處理器名稱
     * @param handler 處理器
     * @return HttpContext
     */
    public final HttpContext addContext(String name, HttpHandler handler) {
        if (server != null) {
            HttpContext context = server.createContext(name, handler);
            contexts.put(name, context);
            logger.log(Level.CONFIG, String.format("設置 %s 要求處理器 API %s Class %s", ((https) ? "HTTPS" : "HTTP"), name, handler.getClass().getName()));
            return context;
        } else {
            return null;
        }
    }

    public final void startService() {
        if (Boolean.parseBoolean(HttpdConfig.get("session.enable", "true"))) {
            HttpSessionManager.getInstance().start();
        }
        server.start();
        isRunning = true;
        logger.log(Level.INFO, String.format("啟動 LiteHttpd Service(Port %d)", this.http_port));
    }

    public final void stopService() {
        server.stop(5); // 延遲 5 秒停止
        isRunning = false;
        HttpSessionManager.getInstance().stop();
        logger.log(Level.INFO, "停止 LiteHttpd Service");
    }

    public final boolean isRunning() {
        return isRunning;
    }

    public final static void log(Level level, String message) {
        logger.log(level, message);
    }

    public final static void log(Level level, String message, Throwable tw) {
        logger.log(level, message, tw);
    }

    public final static void warning(String message) {
        logger.warning(message);
    }

    public final static void severe(String message, Throwable tw) {
        logger.log(Level.SEVERE, message, tw);
    }

}
