/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.LessonStatisticsAPI;
import demo.freemarker.model.LessonStatistics;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonStatisticsRESTfulAPI extends RESTfulAPI {

    public LessonStatisticsRESTfulAPI() {
    }
    
    @Override
    public String getContextPath() {
        return "LessonStatistics/api/";
    }
    
    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得指定ID的標籤資料")
    private String getLessonStatisticsByLessonId(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            System.out.println("Lesson ID: " + lessonId);
            if (lessonId != null) {
                List<LessonStatistics> statisticsList = LessonStatisticsAPI.getInstance().listLessonStatisticsByLessonId(lessonId);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return EntityUtility.toJSONArrayString(statisticsList);
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

    @RESTfulAPIDefine(url = "delete", methods = "post", description = "刪除指定 ID 的統計資料")
    private String deleteLessonStatisticsById(HttpExchange exchange) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long statisticsId = jsonRequest.optLong("statisticsId");

            if (statisticsId > 0) {
                LessonStatistics statistics = LessonStatisticsAPI.getInstance().getLessonStatistics(statisticsId);
                if (statistics == null) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "找不到該統計資料");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    return jsonResponse.toString();
                }

                // 刪除統計資料
                LessonStatisticsAPI.getInstance().deleteLessonStatistics(statisticsId);

                jsonResponse.put("success", true);
                jsonResponse.put("message", "刪除成功");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return jsonResponse.toString();
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "刪除失敗，無效的統計 ID");
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

    @RESTfulAPIDefine(url = "save", methods = "post", description = "儲存或更新統計資料")
    private String saveLessonStatistics(HttpExchange exchange) throws IOException {
        JSONObject jsonResponse = new JSONObject();
    
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            JSONObject jsonRequest = new JSONObject(requestBody);
    
            Long statisticsId = jsonRequest.optLong("id", -1);
            LessonStatistics statistics;
    
            if (statisticsId > 0) {
                statistics = LessonStatisticsAPI.getInstance().getLessonStatistics(statisticsId);
                if (statistics == null) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "找不到該統計資料");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    return jsonResponse.toString();
                }
            } else {
                statistics = new LessonStatistics();
                statistics.setCreateTime(new Date());
            }
    
            // 只有當 JSON 提供該欄位時，才更新值，否則不改變現有值
            if (jsonRequest.has("lessonId")) {
                statistics.setLessonId(jsonRequest.getLong("lessonId"));
            }
            if (jsonRequest.has("apiName")) {
                statistics.setApiName(jsonRequest.getString("apiName"));
            }
            if (jsonRequest.has("displayName")) {
                statistics.setDisplayName(jsonRequest.getString("displayName"));
            }
            if (jsonRequest.has("description")) {
                statistics.setDescription(jsonRequest.getString("description"));
            }
            if (jsonRequest.has("statType")) {
                try {
                    statistics.setStatType(LessonStatistics.StatType.valueOf(jsonRequest.getString("statType")));
                } catch (IllegalArgumentException e) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "無效的 statType 值");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    return jsonResponse.toString();
                }
            }
            if (jsonRequest.has("statValue")) {
                statistics.setStatValue(jsonRequest.optDouble("statValue"));
            }
            if (jsonRequest.has("isIncreasedOnly")) {
                statistics.setIsIncreasedOnly(jsonRequest.getBoolean("isIncreasedOnly"));
            }
            if (jsonRequest.has("maxChange")) {
                statistics.setMaxChange(jsonRequest.optDouble("maxChange"));
            }
            if (jsonRequest.has("minValue")) {
                statistics.setMinValue(jsonRequest.optDouble("minValue"));
            }
            if (jsonRequest.has("maxValue")) {
                statistics.setMaxValue(jsonRequest.optDouble("maxValue"));
            }
            if (jsonRequest.has("defaultValue")) {
                statistics.setDefaultValue(jsonRequest.optDouble("defaultValue"));
            }
            if (jsonRequest.has("aggregateEnabled")) {
                statistics.setAggregateEnabled(jsonRequest.getBoolean("aggregateEnabled"));
            }
            if (jsonRequest.has("avgrateInterval")) {
                statistics.setAvgrateInterval(jsonRequest.optInt("avgrateInterval"));
            }
            if (jsonRequest.has("creator")) {
                statistics.setCreator(jsonRequest.getLong("creator"));
            }
    
            // 儲存資料
            LessonStatisticsAPI.getInstance().updateLessonStatistics(statistics);
    
            jsonResponse.put("success", true);
            jsonResponse.put("message", "儲存成功");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return jsonResponse.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "儲存失敗: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            return jsonResponse.toString();
        }
    }
}
