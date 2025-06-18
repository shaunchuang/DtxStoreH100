/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.LessonImageAPI;
import demo.freemarker.model.LessonImage;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonImageRESTfulAPI extends RESTfulAPI {

    public LessonImageRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "LessonImage/api/";
    }

    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得指定ID的標籤資料")
    private String getLessonImageByLessonId(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            System.out.println("Lesson ID: " + lessonId);
            if(lessonId != null ){
                List<LessonImage> imageList = LessonImageAPI.getInstance().listLessonImageByLessonId(lessonId);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return EntityUtility.toJSONArrayString(imageList);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }
    }

    /**
     * 刪除教案圖片的 API 端點
     * 可以通過圖片ID刪除某張教案圖片
     */
    @RESTfulAPIDefine(url = "delete", methods = "post", description = "刪除指定ID的教案圖片")
    private String deleteLessonImage(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Delete Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long imageId = jsonRequest.optLong("imageId");
            System.out.println("Deleting Image ID: " + imageId);
            
            if(imageId != null && imageId > 0){
                LessonImageAPI.getInstance().deleteLessonImage(imageId);
                
                JSONObject response = new JSONObject();
                response.put("success", true);
                response.put("message", "教案圖片已成功刪除");
                
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return response.toString();
            } else {
                JSONObject response = new JSONObject();
                response.put("success", false);
                response.put("message", "未提供有效的圖片ID");
                
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject response = new JSONObject();
            response.put("success", false);
            response.put("message", "刪除圖片時發生錯誤: " + e.getMessage());
            
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            return response.toString();
        }
    }
}
