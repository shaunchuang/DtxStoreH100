/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.DtxTagAPI;
import demo.freemarker.core.GsonUtil;
import demo.freemarker.model.DtxTag;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.database.EntityUtility;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class DtxTagRESTfulAPI extends RESTfulAPI {

    public DtxTagRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "/DtxTag/api/";
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
                DtxTagAPI.getInstance().getName(),
                DtxTagAPI.getInstance().getVersion(),
                DtxTagAPI.getInstance().getDescription()
        );
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "scheme", methods = "get", description = "取得資料表 Scheme")
    private String scheme(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.entityScheme(DtxTag.class);
    }

    @RESTfulAPIDefine(url = "list", methods = "get", description = "列出所有標籤")
    private String list(HttpExchange exchange) throws IOException {
        JSONObject responseBody = new JSONObject();
        List<DtxTag> tags = new ArrayList<DtxTag>();
        try {
            String typeValue = getValueOfKeyInPath(exchange.getRequestURI(), "type");
            if(typeValue == null) {
                tags = DtxTagAPI.getInstance().listDtxTag();
            } else {
                tags = DtxTagAPI.getInstance().listDtxTagByType(Integer.parseInt(typeValue));
            }
        } catch (Exception ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }
        responseBody.put("success", Boolean.TRUE);
        responseBody.put("tagList", tags);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseBody.toString();
    }
    
   
    @RESTfulAPIDefine(url = "get", methods = "get", description = "取得指定ID的標籤資料")
    private String getTagById(HttpExchange exchange) throws IOException {
        JSONObject responseBody = new JSONObject();
        Long tagId = null;
        try {
            tagId = Long.parseLong(getValueOfKeyInPath(exchange.getRequestURI(), "id"));
        } catch (Exception ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }

        DtxTag tag = DtxTagAPI.getInstance().getDtxTag(tagId);
        String tagString = GsonUtil.toJson(tag);
        responseBody.put("success", Boolean.TRUE);
        responseBody.put("tagItem", new JSONObject(tagString));
        
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseBody.toString();
    }

    /**
     * 取得指定類型ID的標籤資料
     *
     * @param exchange
     * @return 帳號資料(JSON)
     * @throws IOException
     */
    /*@RESTfulAPIDefine(url = "get/type", methods = "get", description = "取得指定類型ID的標籤資料")
    private String getTagsByType(HttpExchange exchange) throws IOException {
        // 指定ID
        int typeId = 0;
        try {
            typeId = Integer.parseInt(getValueOfKeyInPath(exchange.getRequestURI(), "type"));
        } catch (Exception ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }

        if (typeId == 0) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }

        List<DtxTag> tags = DtxTagAPI.getInstance().listDtxTagByType(typeId);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return EntityUtility.toJSONArrayString(tags);
    }*/

}
