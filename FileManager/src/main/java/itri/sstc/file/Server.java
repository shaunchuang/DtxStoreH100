package itri.sstc.file;

import itri.sstc.framework.core.Config;
import itri.sstc.framework.core.httpd.HttpService;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 *
 * @author schung
 */
public class Server {

    static String SERVER_NAME = "FileService";

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
        } catch (Exception ex) {
        }
        try {
            Config.load();
        } catch (Exception ex) {
        }
        try {
            final HttpService httpd = new HttpService();
            //
            httpd.startService();
            //
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    httpd.stopService();
                    HttpService.log(Level.INFO, String.format("%s shutdown", SERVER_NAME));
                }
            });
            HttpService.log(Level.INFO, String.format("Start %s OK", SERVER_NAME));
        } catch (Exception ex) {
            HttpService.log(Level.SEVERE, String.format("Start %s Error, exit(1): %s", SERVER_NAME, ex.getMessage()));
        }
    }

}
