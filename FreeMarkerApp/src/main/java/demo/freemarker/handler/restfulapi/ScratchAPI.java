/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.api.RESTfulAPI.RESTfulAPIDefine;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class ScratchAPI extends RESTfulAPI {

    public ScratchAPI() {
    }

    @Override
    public String getContextPath() {
        return "Scratch/api/";
    }

    @RESTfulAPIDefine(url = "ScratchAppImport", methods = "post", description = "取得 API 資訊")
    private String ScratchAppImport(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();

        return responseJson.toString();
    }
}
