package itri.sstc.freemarker;


import itri.sstc.freemarker.core.FMTemplateEngine;
import itri.sstc.freemarker.core.Language;
import itri.sstc.framework.core.Config;
import itri.sstc.framework.core.httpd.HttpService;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 初始化表單相關資料
 *
 * @author schung
 */
public class Server {

    static String SERVER_NAME = "FreeMarkerService";

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.out.println("工作目錄: " + System.getProperty("user.dir"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
        } catch (Exception ex) {
        }
        try {
            Config.load();
        } catch (Exception ex) {
        }
        try {
            Language.getInstance().init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            final HttpService httpd = new HttpService();
            //
            // 樣版檔根目錄
            //FMTemplateEngine.getInstance().init(new File("./FTL"));
            FMTemplateEngine.getInstance().init(new File("./ftl"));
            //
            httpd.startService();
            //
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    httpd.stopService();
                    Logger.getGlobal().log(Level.INFO, String.format("%s shutdown", SERVER_NAME));
                }
            });
            HttpService.log(Level.INFO, String.format("Start %s OK", SERVER_NAME));
        } catch (Exception ex) {
            HttpService.log(Level.SEVERE, String.format("Start %s Error, exit(1)", SERVER_NAME), ex);
        }
    }

}
