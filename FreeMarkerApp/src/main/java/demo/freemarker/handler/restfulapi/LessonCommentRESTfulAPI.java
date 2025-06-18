/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.LessonCommentAPI;
import demo.freemarker.api.UserAPI;
import demo.freemarker.core.GsonUtil;
import demo.freemarker.core.PointUtil;
import demo.freemarker.model.LessonComment;
import demo.freemarker.model.User;
import itri.sstc.framework.core.api.RESTfulAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author zhush
 */
public class LessonCommentRESTfulAPI extends RESTfulAPI {

    public LessonCommentRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "LessonComment/api/";
    }

    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得指定ID的標籤資料")
    private String getLessonCommentByLessonId(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);
        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            System.out.println("Lesson ID: " + lessonId);
            if (lessonId != null) {
                List<LessonComment> commentList = LessonCommentAPI.getInstance().listLessonCommentByLessonId(lessonId);

                // 轉換為帶有附加用戶信息的列表
                List<Map<String, Object>> enhancedComments = new ArrayList<>();

                for (LessonComment comment : commentList) {
                    // 創建一個包含評論數據的Map
                    Map<String, Object> commentMap = new HashMap<>();
                    // 複製原始評論數據
                    commentMap.put("id", comment.getId());
                    commentMap.put("lessonId", comment.getLessonId());
                    commentMap.put("commentText", comment.getCommentText());
                    commentMap.put("rating", comment.getRating());
                    commentMap.put("creator", comment.getCreator());
                    commentMap.put("createTime", comment.getCreateTime());

                    // 添加格式化的用戶名稱
                    User user = UserAPI.getInstance().getUser(comment.getCreator());
                    String userName = user.getUsername();  // 獲取實際用戶名

                    // 進行姓名隱私保護處理
                    String displayName;
                    if (userName != null && !userName.isEmpty()) {
                        // 只保留第一個字，其他替換為 "O"
                        char firstChar = userName.charAt(0);
                        StringBuilder masked = new StringBuilder();
                        masked.append(firstChar);
                        
                        // 根據姓名長度決定添加多少個 "O"
                        for (int i = 1; i < userName.length(); i++) {
                            masked.append("O");
                        }
                        
                        displayName = masked.toString();
                    } else {
                        displayName = "用戶 " + comment.getCreator();
                    }

                    // 將處理過的顯示名稱添加到commentMap
                    commentMap.put("displayName", displayName);

                    enhancedComments.add(commentMap);
                }

                // 使用GsonUtil轉換為JSON
                String jsonResponse = GsonUtil.toJson(enhancedComments);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return jsonResponse;
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

    @RESTfulAPIDefine(url = "create", methods = "post", description = "創建新的教案評論")
    private String createLessonComment(HttpExchange exchange) throws IOException {
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject jsonRequest = new JSONObject(requestBody);

            Long lessonId = jsonRequest.optLong("lessonId");
            String commentText = jsonRequest.optString("commentText");
            int rating = jsonRequest.optInt("rating");
            Long creator = jsonRequest.optLong("creator");

            if (lessonId == null || commentText == null || rating < 1 || rating > 5 || creator == null) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
                return null;
            }

            // 創建新評論
            LessonComment comment = new LessonComment();
            comment.setLessonId(lessonId);
            comment.setCommentText(commentText);
            comment.setRating((byte) rating);
            comment.setCreator(creator);
            comment.setCreateTime(new Date());

            // 保存評論
            LessonCommentAPI.getInstance().createLessonComment(comment);

            // 獎勵積分 - 使用前面實現的積分系統
            PointUtil.getInstance().awardLessonRatingPoints(creator);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("commentId", comment.getId());
            response.put("currentPoints", PointUtil.getInstance().getUserAvailablePoints(creator));

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
            return null;
        }
    }
}
