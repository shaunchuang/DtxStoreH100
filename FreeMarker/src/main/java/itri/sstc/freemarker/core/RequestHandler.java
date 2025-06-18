package itri.sstc.freemarker.core;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import itri.sstc.freemarker.binding.BindingHttpContext;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * FreeMarker Request 處理器
 *
 * @author schung
 */
public abstract class RequestHandler {

    private static final Logger logger = Logger.getLogger(RequestHandler.class.getName());

    /**
     * 宣告的 Method必須傳入: RequestData
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RequestMapping {

        String pattern() default "/";

        String description() default "";
    }

    private final Map<String, Method> mapping = new HashMap<String, Method>();

    /**
     * 取得 Ftl Request 處理器名稱(註冊用的 UID)
     *
     * @return
     */
    public abstract String getName();

    public void init() {
        Class _class = this.getClass();
        Method[] methods = _class.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotationsByType(RequestMapping.class);
            if (annotations.length > 0) {
                RequestMapping urlPatten = (RequestMapping) annotations[0];
                String pattern = String.format("%s%s", getName(), urlPatten.pattern());
                mapping.put(pattern, method);
                logger.info(String.format("Assign method %s to pattern %s", method.getName(), pattern));
            }
        }
    }

    /**
     * 取得 FTL 樣版資料(包含 FTL 檔名及 資料模型)
     *
     * @param data
     * @return FTL 樣版資料(TemplateData)
     */
    public final TemplateData getTemplateData(RequestData data) throws Exception {
        logger.info(String.format("[%s]", data.relatedPath));

        Method method = mapping.get(data.relatedPath);
        if (method != null) {
            Model model = new Model();
            logger.info("加入全域變數");
            BindingHttpContext httpContext = new BindingHttpContext(model);
            model.put("springMacroRequestContext", httpContext);
            model.put("requestContext", Language.getInstance());
            model.put("base", "/html");

            String templateFile = (String) method.invoke(this, data, model);
            setCookieValues(data.exchange, data.cookies);
            if (templateFile.startsWith("redirect:")) {
                data.exchange.getResponseHeaders().set("Location", templateFile.substring("redirect:".length()));
                data.exchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_PERM, 0);
                data.exchange.close();
                return null; 
            } else if (templateFile.startsWith("goback:")) {
                // 讓客戶端退回上一頁    
                data.exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                DataOutputStream output = new DataOutputStream(data.exchange.getResponseBody());
                output.write(("<script>alert('" + templateFile.substring("goback:".length()) + "');history.back();</script>").getBytes());
                output.flush();
                output.close();
                data.exchange.close();
                return null;
            } else if (templateFile.startsWith("open:")) {
                // 讓客戶端退回上一頁    
                data.exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                DataOutputStream output = new DataOutputStream(data.exchange.getResponseBody());
                output.write(("<script>window.open('" + templateFile.substring("open:".length()) + "', '_blank');history.back();</script>").getBytes());
                output.flush();
                output.close();
                data.exchange.close();
                return null;
            } else {
                templateFile = "./" + getName() + templateFile + ".ftl";
                return new TemplateData(model, templateFile);
            }
        } else {
            String errorMsg = String.format("[%s] not exist", data.relatedPath);
            logger.warning(errorMsg);
            throw new FileNotFoundException(errorMsg);
        }
    }

    /**
     * 生成文件內容
     *
     * @param data 資料模型
     * @return 文件內容(ByteBuffer)
     * @throws IOException
     * @throws TemplateException
     */
    public ByteBuffer render(final RequestData data) {
        long start = System.currentTimeMillis();

        try {
            if (data.cookies.containsKey(Language.LANG_COOKIE_NAME)) {
                String locale = data.cookies.get(Language.LANG_COOKIE_NAME);
                Language.getInstance().setLocale(locale);
            }

            TemplateData templateData = getTemplateData(data);  // 這次如果錯誤，會被拋出

            if (templateData == null || templateData.templateFile == null) {
                throw new FileNotFoundException(String.format("Template not found for URL: %s", data.relatedPath));
            }

            // 取得 FTL 樣版
            Template template = FMTemplateEngine.getInstance().getTemplat(templateData.templateFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                // 進行 FTL 樣版套版
                template.process(templateData.model, new OutputStreamWriter(baos));
            } catch (TemplateException | IOException ex) {
                logger.severe(String.format("Error processing template [%s]: %s", templateData.templateFile, ex.getMessage()));
                return getErrorResponse("Template Processing Error", getStackTrace(ex));
            }

            baos.flush();
            byte[] bytes = baos.toByteArray();
            ByteBuffer content = ByteBuffer.wrap(bytes);
            long stop = System.currentTimeMillis();
            logger.info(String.format("FTL Process time: %dms", (stop - start)));

            return content;
        } catch (FileNotFoundException ex) {
            logger.warning(String.format("File not found: %s", ex.getMessage()));
            return getErrorResponse("404 Not Found", ex.getMessage());
        } catch (Exception ex) {
            logger.severe(String.format("Unhandled error processing request [%s]: %s", data.relatedPath, getStackTrace(ex)));
            return getErrorResponse("500 Internal Server Error", getStackTrace(ex));
        }
    }

    private ByteBuffer getErrorResponse(String status, String message) {
        String htmlResponse = String.format(
                "<html><head><title>%s</title></head><body>"
                + "<h1>%s</h1><pre>%s</pre></body></html>",
                status, status, message
        );
        return ByteBuffer.wrap(htmlResponse.getBytes(StandardCharsets.UTF_8));
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    public void setCookieValues(HttpExchange exchange, Map<String, String> cookies) {
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            String key = entry.getKey() ;
            if (!key.endsWith("JSESSION"))
                exchange.getResponseHeaders().add("Set-Cookie", key + "=" + entry.getValue() + "; Max-Age=" + Constants.COOKIE_EXPIRES_TIME + "; Path=/;");
        }
    }

    /**
     * 從 Http Request Query String 取得查詢參數
     *
     * @param uri Http Request URI
     * @param key 查詢參數Key
     * @return 查詢參數
     */
    public String getValueOfKeyInQuery(final URI uri, final String key) {
        String query = uri.getQuery();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                if (pair.startsWith(String.format("%s=", key))) {
                    int idx = pair.indexOf("=");
                    String value = pair.substring(idx + 1);
                    return value;
                }
            }
        }
        return null;
    }

}
