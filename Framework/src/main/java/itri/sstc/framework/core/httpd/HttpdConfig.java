package itri.sstc.framework.core.httpd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HTTPD 模組組態檔載入
 *
 * @author schung
 */
public class HttpdConfig {

    private final static String CONFIG_FILENAME = "./httpd.properties";
    private final static Properties config = new Properties();

    private HttpdConfig() {
    }

    // 載入設定
    public static void load() throws IOException {
        File configFile = new File(CONFIG_FILENAME);
        config.load(new FileReader(configFile));
        //
        for (Object key : config.keySet()) {
            Logger.getGlobal().log(Level.CONFIG, "{0} : {1}", new Object[]{key, config.get(key)});
        }
    }

    // 儲存設定
    public static void save() throws IOException {
        File settingFile = new File(CONFIG_FILENAME);
        BufferedReader reader = new BufferedReader(new FileReader(settingFile));
        //
        StringWriter writer = new StringWriter();
        String newLine = null;
        scan_loop:
        while ((newLine = reader.readLine()) != null) {
            if (newLine.trim().startsWith("# Update:")) {
                newLine = String.format("# Update: %s", new Date().toString());
            } else if (newLine.trim().startsWith("#")) {
                // Do nothing
            } else {
                String[] temp = newLine.split("=");
                if (temp.length > 0) { // Key=Value
                    String newValue = config.getProperty(temp[0], null);
                    if (newValue == null) {
                        newLine = String.format("%s=%s", temp[0], newValue);
                    }
                }
            }
            //
            writer.append(newLine).append("\r\n");
        }
        reader.close();
        //
        Writer output = new FileWriter(settingFile);
        output.write(writer.toString());
        output.flush();
        output.close();
        // config.store(new FileWriter(settingFile), "Update: " + new Date());
    }

    public static String get(String key, String defaultValue) {
        if (config.getProperty(key) == null) {
            return defaultValue;
        }
        return config.getProperty(key).trim();
    }

    public static void set(String key, String value) {
        if (value == null || value.trim().isEmpty()) {
            if (config.containsKey(key)) {
                config.remove(key);
            }
        } else {
            if (config.containsKey(key)) {
                config.replace(key, value);
            } else {
                config.setProperty(key, value);
            }
        }
    }

    public static Set<String> keys() {
        return config.stringPropertyNames();
    }

    public static Properties getConfig() {
        return (Properties) config.clone();
    }

}
