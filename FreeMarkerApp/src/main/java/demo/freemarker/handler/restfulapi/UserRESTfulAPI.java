/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.UserAPI;
import demo.freemarker.core.SecurityUtils;
import demo.freemarker.model.User;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import itri.sstc.framework.core.httpd.session.HttpSession;
import itri.sstc.freemarker.core.RequestHandler.RequestMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class UserRESTfulAPI extends RESTfulAPI {
    public UserRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "/User/api/";
    }

    @RESTfulAPIDefine(url = "info", methods = "get", description = "取得 API 資訊")
    private String info(HttpExchange exchange) throws IOException {
        String json = String.format("{ \"name\": \"%s\", \"version\": \"%s\", \"desc\": \"%s\" }",
                UserAPI.getInstance().getName(),
                UserAPI.getInstance().getVersion(),
                UserAPI.getInstance().getDescription());
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "scheme", methods = "get", description = "取得資料表 Scheme")
    private String scheme(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.entityScheme(User.class);
    }

    @RESTfulAPIDefine(url = "list", methods = "get", description = "列出所有使用者")
    private String list(HttpExchange exchange) throws IOException {
        List<User> users = UserAPI.getInstance().listUser();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.toJSONArrayString(users);
    }

    // 這是一個示例，根據您的實際後端代碼調整
    @RESTfulAPIDefine(url = "currentPoints", methods = "get", description = "使用者目前可使用點數")
    public String getCurrentPoints(HttpExchange exchange) throws IOException {
        JSONObject response = new JSONObject();
        try{
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            User currentUser = SecurityUtils.getCurrentUser(exchange);
            if (currentUser != null) {
                response.put("currentPoints", currentUser.getCurrentPoints());
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "User not logged in");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response.toString();
    }
}
