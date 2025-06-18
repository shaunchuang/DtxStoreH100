package itri.sstc.framework.core.httpd.manage;

import com.sun.management.OperatingSystemMXBean;
import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.httpd.HttpService;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;

import org.json.JSONObject;


/**
 * 伺服器管理要求處理
 *
 * @author schung
 */
public class ManageHandler extends RESTfulAPI {

    private final HttpService service;

    public ManageHandler(final HttpService service) {
        this.service = service;
    }

    @Override
    public String getContextPath() {
        return "/Httpd/api/";
    }

    /**
     * 取得 API 資訊
     *
     * @param exchange
     * @throws IOException
     */
    @RESTfulAPIDefine(url = "info", methods = "get", description = "取得 API 資訊")
    private String info(HttpExchange exchange) throws IOException {
        String json = String.format("{ \"name\": \"%s\", \"version\": \"%s\", \"desc\": \"%s\" }",
                service.getName(),
                HttpService.VERSION,
                service.getClass().getName()
        );
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "status", methods = "get", description = "取得伺服器狀態資訊")
    private String status(HttpExchange exchange) throws IOException {
        JSONObject status = new JSONObject();
        try {
            OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
            int threadCount = Thread.activeCount();
            //
            long totalMemory = osMBean.getTotalMemorySize();
            long freeMemory = osMBean.getFreeMemorySize();
            //
            float cpuUsage = Math.round(osMBean.getProcessCpuLoad() * 100.0f);
            float memoryUsage = Math.round((totalMemory - freeMemory) / (1024 * 1024 * 1024)); // GB
            //
            status.put("threadCount", threadCount);
            status.put("memoryUsage", memoryUsage);
            status.put("cpuUsage", cpuUsage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return status.toString(2);
    }

}
