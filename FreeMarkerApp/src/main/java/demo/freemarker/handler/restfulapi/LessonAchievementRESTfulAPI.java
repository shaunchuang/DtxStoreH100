/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.ConfigPropertyAPI;
import demo.freemarker.api.LessonAchievementAPI;
import demo.freemarker.api.LessonMainInfoAPI;
import demo.freemarker.core.GsonUtil;
import demo.freemarker.core.ImageUtil;
import demo.freemarker.model.LessonAchievement;
import demo.freemarker.model.LessonMainInfo;
import itri.sstc.framework.core.api.RESTfulAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonAchievementRESTfulAPI extends RESTfulAPI {

    public LessonAchievementRESTfulAPI() {
    }
    
    @Override
    public String getContextPath() {
        return "LessonAchievement/api/";
    }
    
    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得指定ID的標籤資料")
    private String getLessonAchievementByLessonId(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            if (lessonId > 0) {
                List<LessonAchievement> achList = LessonAchievementAPI.getInstance().listLessonAchievementByLessonId(lessonId);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return GsonUtil.toJson(achList);
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

    @RESTfulAPIDefine(url = "delete", methods = "post", description = "刪除指定ID的成就資料，並移除相關圖片檔案")
    private String deleteLessonAchievementById(HttpExchange exchange) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long achievementId = jsonRequest.optLong("achievementId");
    
            if (achievementId > 0) {
                // 取得成就資料，確認圖片路徑
                LessonAchievement achievement = LessonAchievementAPI.getInstance().getLessonAchievement(achievementId);
                if (achievement == null) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "找不到該成就資料");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    return jsonResponse.toString();
                }
    
                // 取得圖片的存放路徑
                String uploadDir = ConfigPropertyAPI.getInstance().getValueByKey("imagesDir");
                String unlockedIconUrl = achievement.getUnlockedIconUrl();
                String lockedIconUrl = achievement.getLockedIconUrl();
    
                // 刪除圖片
                boolean unlockedDeleted = ImageUtil.deleteImageFile(uploadDir, unlockedIconUrl);
                boolean lockedDeleted = ImageUtil.deleteImageFile(uploadDir, lockedIconUrl);
    
                System.out.println("DEBUG: 已刪除解鎖圖片: " + unlockedDeleted);
                System.out.println("DEBUG: 已刪除鎖定圖片: " + lockedDeleted);
    
                // 刪除成就資料
                LessonAchievementAPI.getInstance().deleteLessonAchievement(achievementId);
    
                jsonResponse.put("success", true);
                jsonResponse.put("message", "刪除成功");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return jsonResponse.toString();
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "刪除失敗，無效的成就 ID");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return jsonResponse.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "刪除失敗: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return jsonResponse.toString();
        }
    }

    @RESTfulAPIDefine(url = "save", methods = "post", description = "儲存編輯資料")
    private String saveLessonAchievementById(HttpExchange exchange) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        
        try {
            // 解析 multipart/form-data
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType != null && contentType.startsWith("multipart/form-data")) {
                // 取得 boundary
                String boundary = contentType.substring(contentType.indexOf("boundary=") + 9);
                byte[] requestBody = exchange.getRequestBody().readAllBytes();
                
                // 解析 form data
                Map<String, String> formData = parseMultipartFormData(requestBody, boundary);
                
                // 取得基本資料
                Long achievementId = Long.parseLong(formData.getOrDefault("id", "-1"));
                
                LessonAchievement achievement;
                String oldUnlockedIconUrl = null;
                String oldLockedIconUrl = null;

                if (achievementId > 0) {
                    achievement = LessonAchievementAPI.getInstance().getLessonAchievement(achievementId);
                    if (achievement == null) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "找不到該成就資料");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        return jsonResponse.toString();
                    }
                    // 保存舊圖片路徑
                    oldUnlockedIconUrl = achievement.getUnlockedIconUrl();
                    oldLockedIconUrl = achievement.getLockedIconUrl();
                } else {
                    achievement = new LessonAchievement();
                    achievement.setCreateTime(new Date());
                }
                
                // 更新基本資料
                // 取得 lessonId
                String lessonIdStr = formData.get("lessonId");
                if (lessonIdStr != null && lessonIdStr.matches("\\d+")) {
                    achievement.setLessonId(Long.parseLong(lessonIdStr));
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "無效的 lessonId");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    return jsonResponse.toString();
                }
                achievement.setApiName(formData.get("apiName"));
                achievement.setDisplayName(formData.get("displayName"));
                achievement.setDescription(formData.get("description"));
                achievement.setUnlockValue(Double.parseDouble(formData.getOrDefault("unlockValue", "0.0")));
                achievement.setHidden(Boolean.parseBoolean(formData.getOrDefault("hidden", "false")));
                achievement.setCreator(Long.parseLong(formData.getOrDefault("creator", "0")));
                
                //取得教案執行ID
                LessonMainInfo lesson = LessonMainInfoAPI.getInstance().getLessonMainInfo(achievement.getLessonId());
                String executionId = null;
                if(lesson.getExecutionId().contains("steamApp")){
                    executionId = parseExecutionId(lesson.getExecutionId());
                    if(executionId == null){
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "找不到執行ID");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        return jsonResponse.toString();
                    }
                }

                // 處理圖片檔案
                byte[] unlockedImageData = getFileDataFromMultipart(requestBody, boundary, "unlockedImage");
                byte[] lockedImageData = getFileDataFromMultipart(requestBody, boundary, "lockedImage");

                // 取得圖片存放目錄
                String uploadDir = ConfigPropertyAPI.getInstance().getValueByKey("imagesDir");

                // 如果有新的圖片，則刪除舊圖片並更新 URL
                if (unlockedImageData != null) {
                    if (oldUnlockedIconUrl != null) {
                        ImageUtil.deleteImageFile(uploadDir, oldUnlockedIconUrl);
                    }
                    String unlockedIconUrl = ImageUtil.saveImage(unlockedImageData, uploadDir, "lesson/" + executionId);
                    achievement.setUnlockedIconUrl(unlockedIconUrl);
                    jsonResponse.put("unlockedIconUrl", unlockedIconUrl);
                }
                
                if (lockedImageData != null) {
                    if (oldLockedIconUrl != null) {
                        ImageUtil.deleteImageFile(uploadDir, oldLockedIconUrl);
                    }
                    String lockedIconUrl = ImageUtil.saveImage(lockedImageData, uploadDir, "lesson/" + executionId);
                    achievement.setLockedIconUrl(lockedIconUrl);
                    jsonResponse.put("lockedIconUrl", lockedIconUrl);
                }
                
                // 儲存資料
                LessonAchievementAPI.getInstance().updateLessonAchievement(achievement);
                
                jsonResponse.put("success", true);
                jsonResponse.put("message", "儲存成功");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return jsonResponse.toString();
            } else {
                throw new IllegalArgumentException("不支援的Content-Type: " + contentType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "儲存失敗: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            return jsonResponse.toString();
        }
    }

    // 解析 multipart/form-data
    private Map<String, String> parseMultipartFormData(byte[] data, String boundary) {
        Map<String, String> result = new HashMap<>();
        String dataStr = new String(data, StandardCharsets.UTF_8);
        String[] parts = dataStr.split("--" + boundary);
        
        for (String part : parts) {
            if (part.contains("Content-Disposition: form-data;")) {
                String[] lines = part.split("\r\n");
                String name = null;
                for (String line : lines) {
                    if (line.contains("Content-Disposition:")) {
                        name = line.split("name=\"")[1].split("\"")[0];
                    } else if (!line.trim().isEmpty() && !line.contains("Content-Type:") && name != null) {
                        result.put(name, line.trim());
                        break;
                    }
                }
            }
        }
        return result;
    }

    // 從 multipart/form-data 中取得檔案內容
    private byte[] getFileDataFromMultipart(byte[] data, String boundary, String fieldName) {
        try {
            String boundaryStr = "--" + boundary;
            byte[] boundaryBytes = boundaryStr.getBytes(StandardCharsets.UTF_8);
            
            int fieldStartIndex = indexOf(data, ("name=\"" + fieldName + "\"").getBytes(StandardCharsets.UTF_8));
            if (fieldStartIndex == -1) {
                System.out.println("DEBUG: 找不到欄位 " + fieldName);
                return null;
            }
    
            int contentStartIndex = indexOf(data, "\r\n\r\n".getBytes(StandardCharsets.UTF_8), fieldStartIndex) + 4;
            if (contentStartIndex < 4) {
                System.out.println("DEBUG: 找不到檔案起始位置");
                return null;
            }
    
            int contentEndIndex = indexOf(data, boundaryBytes, contentStartIndex);
            if (contentEndIndex == -1) {
                contentEndIndex = data.length;
            } else {
                contentEndIndex -= 2; // 移除 `\r\n`
            }
    
            return Arrays.copyOfRange(data, contentStartIndex, contentEndIndex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("處理檔案時發生錯誤: " + e.getMessage());
        }
    }
    
    // 查找 byte 陣列中的索引
    private int indexOf(byte[] data, byte[] pattern) {
        return indexOf(data, pattern, 0);
    }
    
    private int indexOf(byte[] data, byte[] pattern, int start) {
        outer:
        for (int i = start; i <= data.length - pattern.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    private String parseExecutionId(String executionId) {
        if (executionId == null || executionId.isEmpty()) {
            return null; // 如果 executionId 為空，回傳 null
        }
    
        // 定義正則表達式來匹配 steamApp/{id} 格式
        Pattern pattern = Pattern.compile("^steamApp/(\\d+)$");
        Matcher matcher = pattern.matcher(executionId);
    
        if (matcher.find()) {
            return matcher.group(1); // 取得數字部分 (ID)
        } else {
            return null; // 若格式不符合，回傳 null
        }
    }
    
}
