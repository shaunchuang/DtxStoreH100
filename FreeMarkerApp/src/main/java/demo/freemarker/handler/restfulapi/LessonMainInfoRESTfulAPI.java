/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;

import demo.freemarker.api.ConfigPropertyAPI;
import demo.freemarker.api.DtxTagAPI;
import demo.freemarker.api.LessonAchievementAPI;
import demo.freemarker.api.LessonCommentAPI;
import demo.freemarker.api.LessonImageAPI;
import demo.freemarker.api.LessonMainInfoAPI;
import demo.freemarker.api.LessonNewsAPI;
import demo.freemarker.api.LessonResearchAPI;
import demo.freemarker.api.LessonStatisticsAPI;
import demo.freemarker.api.LessonSystemRequirementAPI;
import demo.freemarker.api.LessonTagAPI;
import demo.freemarker.api.LessonVideoAPI;
import demo.freemarker.core.ImageUtil;
import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonAchievement;
import demo.freemarker.model.LessonImage;
import demo.freemarker.model.LessonMainInfo;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonMainInfoRESTfulAPI extends RESTfulAPI {

    public LessonMainInfoRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "/LessonMainInfo/api/";
    }

    @RESTfulAPIDefine(url = "info", methods = "get", description = "取得 API 資訊")
    private String info(HttpExchange exchange) throws IOException {
        String json = String.format("{ \"name\": \"%s\", \"version\": \"%s\", \"desc\": \"%s\" }",
                LessonMainInfoAPI.getInstance().getName(),
                LessonMainInfoAPI.getInstance().getVersion(),
                LessonMainInfoAPI.getInstance().getDescription());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "scheme", methods = "get", description = "取得資料表 Scheme")
    private String scheme(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.entityScheme(LessonMainInfo.class);
    }

    @RESTfulAPIDefine(url = "list", methods = "get", description = "取得教案清單")
    private String list(HttpExchange exchange) throws IOException {
        List<LessonMainInfo> lessons = LessonMainInfoAPI.getInstance().listLessonMainInfo();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.toJSONArrayString(lessons);
    }

    @RESTfulAPIDefine(url = "get", methods = "get", description = "取得教案詳細資訊")
    private String get(HttpExchange exchange) throws IOException {
        String lessonId = getValueOfKeyInPath(exchange.getRequestURI(), "lessonId");
        System.out.println("lessonId" + lessonId);
        LessonMainInfo lesson = LessonMainInfoAPI.getInstance().getLessonMainInfo(Long.parseLong(lessonId));
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.toJSONString(lesson);
    }

    @RESTfulAPIDefine(url = "filterlist", methods = "post", description = "取得篩選排序後教案清單")
    private String filterList(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);

            // 取得 tagList，若不存在則回傳空的 List
            JSONArray tagArray = jsonRequest.optJSONArray("tagList");
            List<Integer> tagIds = new ArrayList<>();
            if (tagArray != null) {
                for (int i = 0; i < tagArray.length(); i++) {
                    tagIds.add(tagArray.optInt(i));
                }
            }
            System.out.println("tagIdsArray: " + tagIds.toString());
            // sliderValue 預設值設定
            double minPrice = 0.0;
            double maxPrice = 3500.0;
            int minAge = 3;
            int maxAge = 65;
            JSONObject sliderValue = jsonRequest.optJSONObject("sliderValue");
            if (sliderValue != null) {
                minPrice = sliderValue.optDouble("minPrice", minPrice);
                maxPrice = sliderValue.optDouble("maxPrice", maxPrice);
                minAge = sliderValue.optInt("minAge", minAge);
                maxAge = sliderValue.optInt("maxAge", maxAge);
            }

            // sort 預設為空字串
            String sortOneField = "";
            String sortOneOrder = "";
            String sortTwoField = "";
            String sortTwoOrder = "";
            JSONObject sort = jsonRequest.optJSONObject("sort");
            if (sort != null) {
                sortOneField = sort.optString("sortOneField", "");
                sortOneOrder = sort.optString("sortOneOrder", "");
                sortTwoField = sort.optString("sortTwoField", "");
                sortTwoOrder = sort.optString("sortTwoOrder", "");
            }

            // lessonName 預設為空字串
            String lessonName = jsonRequest.optString("lessonName", "");
            Long userId = jsonRequest.optLong("userId");
            Boolean isLibrary = jsonRequest.optBoolean("isLibrary", false);
            // pageData 預設值
            int currentPage = 1;
            int pageSize = 9;
            JSONObject pageData = jsonRequest.optJSONObject("pageData");
            if (pageData != null) {
                currentPage = pageData.optInt("currentPage", currentPage);
                pageSize = pageData.optInt("pageSize", pageSize);
            }
            System.out.println("cUserId: " + userId);
            // 調用 API 方法取得結果
            responseJson = LessonMainInfoAPI.getInstance().getLessonsWithPagination(
                    tagIds, minAge, maxAge, minPrice, maxPrice,
                    sortOneField, sortOneOrder, sortTwoField, sortTwoOrder,
                    currentPage, pageSize, lessonName, userId, isLibrary);

            System.out.println("responseJson： " + responseJson.toString());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }
    }

    @RESTfulAPIDefine(url = "manageList", methods = "post", description = "取得教案詳細資訊")
    private String manageList(HttpExchange exchange) throws IOException {
        try {
            JSONObject responseBody = new JSONObject();
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long userId = jsonRequest.optLong("userId");
            List<LessonMainInfo> lessons = LessonMainInfoAPI.getInstance().listLessonMainInfoByCreator(userId);
            JSONArray lessonArray = new JSONArray();
            for (LessonMainInfo lesson : lessons) {
                JSONObject lessonJson = new JSONObject();
                lessonJson.put("id", lesson.getId());
                lessonJson.put("lessonName", lesson.getLessonName());
                List<DtxTag> tagList = DtxTagAPI.getInstance().listDtxTagByLessonIdAndType(userId, 1);
                if (tagList.isEmpty()) {
                    lessonJson.put("disease", "無");
                } else {
                    lessonJson.put("disease", tagList.get(0).getTagName());
                }
                lessonJson.put("achievementCount",
                        LessonAchievementAPI.getInstance().countLessonAchievementByLessonId(lesson.getId()));
                lessonJson.put("statCount",
                        LessonStatisticsAPI.getInstance().countLessonStatisticsByLessonId(lesson.getId()));
                lessonJson.put("distributionCount", 0);
                OptionalDouble averageOpt = LessonCommentAPI.getInstance().listLessonComment().stream()
                        .filter((comment) -> (comment.getLessonId().equals(lesson.getId())))
                        .mapToInt(comment -> comment.getRating().intValue())
                        .average();
                lessonJson.put("commentLevel", averageOpt.orElse(0));
                lessonJson.put("status", lesson.getStatus().name());
                lessonArray.put(lessonJson);
            }
            responseBody.put("data", lessonArray);
            System.out.println("responseBody" + responseBody.toString());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseBody.toString();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }
    }

    @RESTfulAPIDefine(url = "delete", methods = "post", description = "刪除教案資料")
    private String deleteLessonMainInfo(HttpExchange exchange) throws IOException {
        JSONObject responseBody = new JSONObject();

        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body: " + requestBody);
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            
            if (lessonId == 0) {
                responseBody.put("success", false);
                responseBody.put("message", "教案 ID 不存在");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
                return responseBody.toString();
            }
            // 取得圖片的存放路徑
            String uploadDir = ConfigPropertyAPI.getInstance().getValueByKey("imagesDir");
            LessonMainInfo lesson = LessonMainInfoAPI.getInstance().getLessonMainInfo(lessonId);
            
            // 刪除成就資訊
            List<LessonAchievement> achievementList = LessonAchievementAPI.getInstance()
                    .listLessonAchievementByLessonId(lessonId);
            for (LessonAchievement achievement : achievementList) {
                ImageUtil.deleteImageFile(uploadDir, achievement.getUnlockedIconUrl());
                ImageUtil.deleteImageFile(uploadDir, achievement.getLockedIconUrl());
            }
            LessonAchievementAPI.getInstance().deleteAchievementByLessonId(lessonId);
            // 刪除統計資訊
            LessonStatisticsAPI.getInstance().deleteLessonStatisticsByLessonId(lessonId);
            // 刪除教案新聞
            LessonNewsAPI.getInstance().deleteLessonNewsByLessonId(lessonId);
            // 刪除教案研究
            LessonResearchAPI.getInstance().deleteResearchByLessonId(lessonId);
            // 刪除教案標籤
            LessonTagAPI.getInstance().deleteLessonTagByLessonId(lessonId);
            // 刪除教案評論
            LessonCommentAPI.getInstance().deleteLessonCommentByLessonId(lessonId);
            // 刪除教案圖片
            List<LessonImage> lessonImages = LessonImageAPI.getInstance().listLessonImageByLessonId(lessonId);
            for (LessonImage lessonImage : lessonImages) {
                ImageUtil.deleteImageFile(uploadDir, lessonImage.getFilePath());
            }
            LessonImageAPI.getInstance().deleteLessonImageByLessonId(lessonId);
            // 刪除教案影片
            LessonVideoAPI.getInstance().deleteLessonVideoByLessonId(lessonId);
            // 刪除教案系統需求
            LessonSystemRequirementAPI.getInstance().deleteLessonSystemRequirementByLessonId(lessonId);
            // 刪除教案資料
            ImageUtil.deleteImageFile(uploadDir, lesson.getHeaderImageUrl());
            // 取得 executionId 並刪除executionId 對應資料夾
            String executionString = lesson.getExecutionId();
            String executionId = parseExecutionId(executionString);
            if (executionId != null) {
                String steamImagesPath = uploadDir + "/steamImages/" + executionId;
                File executionDir = new File(steamImagesPath);
                
                if (executionDir.exists()) {
                    deleteDirectory(executionDir);
                    System.out.println("成功刪除資料夾：" + steamImagesPath);
                } else {
                    System.out.println("資料夾不存在：" + steamImagesPath);
                }
            }
            LessonMainInfoAPI.getInstance().deleteLessonMainInfo(lessonId);

            responseBody.put("success", true);
            responseBody.put("message", "教案資料刪除成功");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseBody.toString();
        } catch (Exception e) {
            e.printStackTrace();
            responseBody.put("success", false);
            responseBody.put("message", "教案資料刪除失敗");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseBody.toString();
        }
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
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
