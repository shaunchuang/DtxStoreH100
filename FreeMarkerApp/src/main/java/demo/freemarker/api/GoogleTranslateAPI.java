/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.core.HttpClientUtil;
import itri.sstc.framework.core.api.API;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class GoogleTranslateAPI implements API {

    private final static GoogleTranslateAPI INSTANCE = new GoogleTranslateAPI();

    public final static GoogleTranslateAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "GoogleTranslateAPI";
    }

    @Override
    public String getVersion() {
        return "20250217.01";
    }

    @Override
    public String getDescription() {
        return "Google翻譯 API";
    }

    public String translateString(String input) {
        try {
            String url = "http://localhost:8084/translate";
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String response = HttpClientUtil.sendPostRequest(url, headers, new JSONObject().put("text", input).toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
