package itri.sstc.freemarker.core;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 *
 * @author schung
 */
public class Language {

    private static final Logger logger = Logger.getLogger(Language.class.getName());

    public final static String DEFAULT_LOCALE = "zh_TW";
    public final static String LANG_COOKIE_NAME = "_freemarker_lang";

    private final static Language INSTANCE = new Language();

    public final static Language getInstance() {
        return INSTANCE;
    }

    private Map<String, Properties> langMap = new TreeMap<String, Properties>();
    private String locale;

    public final void init() {
        logger.info("初始化語言庫 ..."); 
        locale = DEFAULT_LOCALE;
        File root = new File("./");
        File[] props = root.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (!name.startsWith("messages_") || !name.endsWith(".properties")) {
                    return false;
                }
                //
                String lang = name.replace("messages_", "").replace(".properties", "");
                if (!lang.isBlank()) {
                    try {
                        Properties message = new Properties();
                        message.load(new FileReader(new File(dir, name)));
                        langMap.put(lang, message);
                        logger.info(String.format("Load language for %s, file=%s", lang, name));
                    } catch (Exception ex) {
                        logger.severe(String.format("Load %s Error: %s", name, ex.getLocalizedMessage()));
                    }
                }
                return !lang.isBlank();
            }
        });
        logger.info("初始化語言庫 OK"); 
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMessage() {
        return "hello";
    }

    public String getMessage(String code, String defaultText) {
        if (langMap.get(locale) != null) {
            return langMap.get(locale).getProperty(code, defaultText);
        } else {
            return defaultText;
        }
    }

}
