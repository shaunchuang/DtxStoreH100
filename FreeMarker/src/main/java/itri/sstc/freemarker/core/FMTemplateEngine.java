package itri.sstc.freemarker.core;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration; 
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;

/**
 * FreeMarker 樣版引擎
 *
 * @author schung
 */
public class FMTemplateEngine {

    private static final Logger logger = Logger.getLogger(FMTemplateEngine.class.getName());
    final static FMTemplateEngine INSTANCE = new FMTemplateEngine();
    static Version VERSION = new Version(2, 3, 20);

    public final static FMTemplateEngine getInstance() {
        return INSTANCE;
    }

    private FileTemplateLoader loader;
    private Configuration config;
    private Map<String, RequestHandler> handlers = new HashMap<String, RequestHandler>();

    private FMTemplateEngine() {
    }

    public final void init(File baseDir) throws IOException, TemplateModelException, TemplateException {
        Properties ftlConfig = new Properties();
        try {
            ftlConfig.load(new FileReader("./freemarker.properties"));
        } catch (IOException ioe) {
        }
        //
        for (Object _key : ftlConfig.keySet()) {
            String key = (String) _key;
            if (key.startsWith("Handler.")) {
                String configText = ftlConfig.getProperty(key);
                String[] values = configText.split(",");
                if (values.length == 0) {
                    continue;
                }
                String className = values[0].trim();
                //
                Object handler;
                try {
                    Constructor constructor = Class.forName(className).getConstructor(new Class[0]);
                    handler = constructor.newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                    logger.log(Level.WARNING, String.format("Class %s 沒有定義適合的建構子", className));
                    continue;
                }
                //
                if (handler instanceof RequestHandler) {
                    RequestHandler requestHandler = (RequestHandler) handler;
                    String id = requestHandler.getName();
                    handlers.put(id, requestHandler);
                    logger.info(String.format("建立 %s FTL處理器, ClassName: %s", id, className));
                    requestHandler.init();
                    logger.info(String.format("初始化 %s FTL處理器 OK", id));
                }
            }
        }
        try {
            logger.info("初始化 FreeMarker 樣版引擎 ...");
            loader = new FileTemplateLoader(baseDir);
//            loader.setEmulateCaseSensitiveFileSystem(true);
            //
//            config = new Configuration(VERSION);
            
            config = new Configuration();
            config.setTemplateLoader(loader);
            // Some other recommended settings:
            config.setEncoding(Locale.TRADITIONAL_CHINESE, "utf-8");
            config.setLocale(Locale.TRADITIONAL_CHINESE);
            config.setTimeZone(TimeZone.getDefault());  
//            config.setTemplateUpdateDelayMilliseconds(5000); // 每5秒檢查ftl模板文件是否有更新
            config.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250)); // 最大緩存250個模板
             
config.setTemplateExceptionHandler(new TemplateExceptionHandler() {
    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        try {
            out.write("<h2>系統錯誤</h2>");
            out.write("<p>請聯絡管理員，並提供以下錯誤訊息：</p>");
            out.write("<pre style='color: red;'>" + te.getMessage() + "</pre>");
        } catch (IOException e) {
            throw new TemplateException("無法輸出錯誤訊息", e, env);
        }
    }
});
            //config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER); 
//            config.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_20));
            config.setURLEscapingCharset("UTF-8");
            config.setDateFormat("yyyy-MM-dd");
            config.setTimeFormat("HH:mm:ss");
            config.setBooleanFormat("true,false");
            config.setNumberFormat("0.####");  
            config.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
//            config.setSharedVariable("functions", "html"); 
            //
            logger.info("初始化 FreeMarker 樣版引擎 OK");
        } catch (IOException ex) { 
            logger.severe(String.format("初始化 FreeMarker樣版引擎 失敗: %s", ex.getLocalizedMessage()));
        }
    }

    public final RequestHandler getHandler(String name) {
        return handlers.get(name);
    }

    public final Template getTemplat(String ftlName) throws IOException {
        return config.getTemplate(ftlName);
    }

    /**
     * 依 FTL 樣版檔名判斷 ContentType
     *
     * @param temp FTL 樣版檔名
     * @return ContentType
     */
    public final String getContentType(String temp) {
        temp = temp.replace(".ftl", "");
        int idx = temp.lastIndexOf("/");
        if (idx > 0) {
            temp = temp.substring(idx);
        }
        //
        MimetypesFileTypeMap map = new MimetypesFileTypeMap();
        String fileType;
        if (temp.indexOf(".") < 1) {
            fileType = "text/html";
        } else {
            fileType = URLConnection.guessContentTypeFromName(temp);
        }
        fileType += "; charset=utf-8";
        return fileType; // "text/html; charset=utf-8";
    }

}
