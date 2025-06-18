/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.DtxTagAPI;
import demo.freemarker.api.LessonTagAPI;
import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonTag;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonTagRESTfulAPI extends RESTfulAPI {

    public LessonTagRESTfulAPI() {
    }
    
    @Override
    public String getContextPath() {
        return "LessonTag/api/";
    }
    
    @RESTfulAPIDefine(url = "lesson", methods = "post", description = "取得指定ID的標籤資料")
    private String getLessonTagByLessonId(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        try {
            JSONObject jsonRequest = new JSONObject(requestBody);
            Long lessonId = jsonRequest.optLong("lessonId");
            System.out.println("Lesson ID: " + lessonId);
            if (lessonId != null) {
                List<LessonTag> tagList = LessonTagAPI.getInstance().listLessonTagByLessonId(lessonId);
                
                List<Long> tagIds = tagList.stream()
                        .map(lessonTag -> lessonTag.getTagId())
                        .collect(Collectors.toList());
                List<DtxTag> dtxTags = DtxTagAPI.getInstance().listDtxTagByIds(tagIds);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return EntityUtility.toJSONArrayString(dtxTags);
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
}
