/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.UserFavoriteLessonAPI;
import demo.freemarker.model.UserFavoriteLesson;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class UserFavoriteLessonRESTfulAPI extends RESTfulAPI {

    public UserFavoriteLessonRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "UserFavoriteLesson/api/";
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
                UserFavoriteLessonAPI.getInstance().getName(),
                UserFavoriteLessonAPI.getInstance().getVersion(),
                UserFavoriteLessonAPI.getInstance().getDescription()
        );
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "scheme", methods = "get", description = "取得資料表 Scheme")
    private String scheme(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.entityScheme(UserFavoriteLesson.class);
    }

    @RESTfulAPIDefine(url = "listFavorite", methods = "get", description = "取得使用者收藏之教案")
    private String listFavorite(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        JSONObject jsonRequest;
        List<UserFavoriteLesson> uflList = new ArrayList<>();
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            jsonRequest = new JSONObject(requestBody.toString());
            Long userId = jsonRequest.getLong("currentUserId");
            uflList = UserFavoriteLessonAPI.getInstance().listByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            responseJson.put("message", "取得教案清單失敗");
            responseJson.put("success", Boolean.FALSE);
            return responseJson.toString();
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.toJSONArrayString(uflList);
    }
    
    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得該使用者是否收藏該教案")
    private String checkFavorite(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        // 解析 JSON
        JSONObject jsonRequest;
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            jsonRequest = new JSONObject(requestBody.toString());
            Long userId = jsonRequest.getLong("userId");
            Long lessonId = jsonRequest.getLong("lessonId");
            UserFavoriteLesson ufl = UserFavoriteLessonAPI.getInstance().getUserFavoriteLessonByUserIdAndLessonId(userId, lessonId);
            if (ufl != null) {
                responseJson.put("isFavor", Boolean.TRUE);
                responseJson.put("success", Boolean.TRUE);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return responseJson.toString();
            } else {
                responseJson.put("isFavor", Boolean.FALSE);
                responseJson.put("success", Boolean.TRUE);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return responseJson.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            responseJson.put("message", "收藏教案失敗");
            responseJson.put("success", Boolean.FALSE);
            return responseJson.toString();
        }

    }

    @RESTfulAPIDefine(url = "create", methods = "post", description = "取得資料表 Scheme")
    private String addFavorite(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        // 解析 JSON
        JSONObject jsonRequest;
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            jsonRequest = new JSONObject(requestBody.toString());
            Long userId = jsonRequest.getLong("userId");
            Long lessonId = jsonRequest.getLong("lessonId");
            UserFavoriteLesson ufl = new UserFavoriteLesson(userId, lessonId, new Date());
            UserFavoriteLessonAPI.getInstance().createUserFavoriteLesson(ufl);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            responseJson.put("message", "收藏教案失敗");
            responseJson.put("success", Boolean.FALSE);
            return responseJson.toString();
        }
        responseJson.put("message", "收藏教案成功");
        responseJson.put("success", Boolean.TRUE);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }

    @RESTfulAPIDefine(url = "delete", methods = "post", description = "取得資料表 Scheme")
    private String removeFavorite(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        // 解析 JSON
        JSONObject jsonRequest;
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            jsonRequest = new JSONObject(requestBody.toString());
            Long userId = jsonRequest.getLong("userId");
            Long lessonId = jsonRequest.getLong("lessonId");
            UserFavoriteLesson ufl = UserFavoriteLessonAPI.getInstance().getUserFavoriteLessonByUserIdAndLessonId(userId, lessonId);
            UserFavoriteLessonAPI.getInstance().deleteUserFavoriteLesson(ufl.getId());
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            responseJson.put("message", "移除收藏教案失敗");
            responseJson.put("success", Boolean.FALSE);
            return responseJson.toString();
        }
        responseJson.put("message", "移除收藏教案成功");
        responseJson.put("success", Boolean.TRUE);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }
}
