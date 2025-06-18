package itri.sstc.framework.core.httpd.ssl;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import itri.sstc.framework.core.httpd.HttpService;
import java.util.logging.Level;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

/**
 *
 * @author schung
 */
public class HttpsConfiguratorImpl extends HttpsConfigurator {

    public HttpsConfiguratorImpl(SSLContext sslContext) {
        super(sslContext);
    }

    @Override
    public void configure(HttpsParameters params) {
        try {
            // initialise the SSL context
            SSLContext context = getSSLContext();
            SSLEngine engine = context.createSSLEngine();
            params.setNeedClientAuth(false);
            params.setCipherSuites(engine.getEnabledCipherSuites());
            params.setProtocols(engine.getEnabledProtocols());
            // Set the SSL parameters
            SSLParameters sslParameters = context.getSupportedSSLParameters();
            params.setSSLParameters(sslParameters);
        } catch (Exception ex) {
            HttpService.log(Level.SEVERE, "Failed to create HTTPS port");
        }
    }

}
