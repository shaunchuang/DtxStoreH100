/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.RoleAPI;
import demo.freemarker.model.Role;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 *
 * @author zhush
 */
public class RoleRESTfulAPI extends RESTfulAPI {
    
    public RoleRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "Role/api/";
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
               RoleAPI.getInstance().getName(),
               RoleAPI.getInstance().getVersion(),
               RoleAPI.getInstance().getDescription()
        );
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "scheme", methods = "get", description = "取得資料表 Scheme")
    private String scheme(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.entityScheme(Role.class);
    }
}
