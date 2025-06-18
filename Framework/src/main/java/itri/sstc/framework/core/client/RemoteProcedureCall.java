package itri.sstc.framework.core.client;

import itri.sstc.framework.core.Config;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 遠端程序呼叫
 *
 * @author schung
 */
public class RemoteProcedureCall {

    private static final Logger logger = Logger.getLogger(RemoteProcedureCall.class.getName());

    /**
     * 透過 RPC 執行
     *
     * @param headers HTTP Request Headers
     * @param apiName API 名稱
     * @return 執行結果
     */
    public final static String callAPI(Map<String, String> headers, String moduleName, String apiName) {
        Level logLevel = Level.INFO;
        //
        String protocol = Config.get(String.format("%s.protocol", apiName), "http");
        String host = Config.get(String.format("%s.host", apiName), "127.0.0.1");
        int port = Integer.parseInt(Config.get(String.format("%s.port", apiName), "9001"));
        //
        String url = String.format("%s://%s:%d/%s/api/%s", protocol, host, port, moduleName, apiName);
        logger.log(logLevel, String.format("RPC: Call API %s in %s ...", apiName, url));
        try {
            long start = System.currentTimeMillis();
            String rsp = WebServiceAPI.doGet(url, headers, 10000, 10000);
            long stop = System.currentTimeMillis();
            logger.log(logLevel, String.format("RPC: Call API %s in %s OK. Spend time: %d ms", apiName, url, (stop - start)));
            return rsp;
        } catch (IOException ex) {
            logger.severe(String.format("RPC: Call API %s in %s Error: %s", apiName, url, ex.getMessage()));
            return null;
        }
    }

}
