package itri.sstc.framework.core.httpd.ssl;

import itri.sstc.framework.core.httpd.HttpService;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.logging.Level;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSLContext 工具
 * @author schung
 */
public class SSLContextUtil {

    public static SSLContext getSslContext(File keyStoreFile, String passwd) throws Exception {
        HttpService.log(Level.INFO, "取得 SSLContext ...");
        SSLContext sslContext = SSLContext.getInstance("TLS");
        HttpService.log(Level.INFO,"取得 SSLContext OK");
        //
        KeyStore keyStore = loadKeyStore(keyStoreFile, passwd);
        //
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, passwd.toCharArray());
        //
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);
        HttpService.log(Level.INFO,"初始化 SSLContext ...");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        HttpService.log(Level.INFO,"初始化 SSLContext OK");
        return sslContext;
    }

    private static KeyStore loadKeyStore(File keyStoreFile, String passwd) throws Exception {
        HttpService.log(Level.INFO,"載入 KeyStore " + keyStoreFile.getName() + " ...");
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream fis;
        fis = new FileInputStream(keyStoreFile);
        keyStore.load(fis, passwd.toCharArray());
        //
        HttpService.log(Level.INFO,"載入 KeyStore " + keyStoreFile.getName() + " OK");
        return keyStore;
    }

}
