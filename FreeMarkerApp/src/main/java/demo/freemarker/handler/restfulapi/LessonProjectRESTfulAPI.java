package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.core.SecurityUtils;
import demo.freemarker.model.User;
import itri.sstc.framework.core.Config;
import itri.sstc.framework.core.api.RESTfulAPI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonProjectRESTfulAPI extends RESTfulAPI {

    public LessonProjectRESTfulAPI() {
    }

    @Override
    public String getContextPath() {
        return "/LessonProject/api/";
    }

    @RESTfulAPIDefine(url = "upload", methods = "post", description = "上傳HTML turbowarp教案")
    private String upload(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("=== HTML上傳請求開始 ===");
        System.out.println("收到的請求大小: " + requestBody.length() + " 字節");
        // 只顯示前100個字符，避免日誌過長
        System.out.println("請求內容預覽: " + requestBody.substring(0, Math.min(100, requestBody.length())) + "...");
        
        try {
            // 解析請求內容
            JSONObject jsonRequest = new JSONObject(requestBody);
            String filename = jsonRequest.getString("filename");
            String htmlContent = jsonRequest.getString("htmlContent");
            
            System.out.println("解析成功 - 檔案名稱: " + filename);
            System.out.println("HTML內容長度: " + htmlContent.length() + " 字節");
            System.out.println("HTML內容預覽: " + htmlContent.substring(0, Math.min(100, htmlContent.length())) + "...");
            
            // 驗證檔案名稱，防止目錄遍歷攻擊
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
                System.out.println("錯誤: 檔案名稱含有非法字符");
                responseJson.put("success", false);
                responseJson.put("message", "無效的檔案名稱");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                return responseJson.toString();
            }
            
            // lessonProject 底下的分類資料夾
            User currentUser = SecurityUtils.getCurrentUser(exchange);
            String userFolder = (currentUser != null) ? String.valueOf(currentUser.getId()) : "anonymous";
            System.out.println("用戶資料夾: " + userFolder);
            
            // 所有檔案放在此目錄下
            // 修改根目錄配置，將 HTML 文件存放到正確的資料夾
            File ROOT = new File(Config.get("html.folder.path", "./html/lessonProject")); // Updated path
            System.out.println("根目錄配置: " + ROOT.getAbsolutePath());

            // 建立儲存路徑
            // 根據專案配置獲取 HTML 資料夾路徑，或使用預設路徑
            String baseHtmlDir = ROOT.getAbsolutePath();
            System.out.println("HTML基礎目錄: " + baseHtmlDir);
            
            // 使用時間戳記防止檔案名稱衝突
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            System.out.println("生成時間戳記: " + timestamp);
            
            // 建立目標資料夾 (如果不存在)
            File userDir = new File(baseHtmlDir + File.separator + userFolder);
            System.out.println("檢查用戶目錄是否存在: " + userDir.getAbsolutePath());
            if (!userDir.exists()) {
                System.out.println("用戶目錄不存在，嘗試創建");
                if (!userDir.mkdirs()) {
                    System.out.println("錯誤: 無法創建用戶目錄");
                    throw new IOException("無法建立用戶目錄: " + userDir.getAbsolutePath());
                }
                System.out.println("用戶目錄創建成功");
            } else {
                System.out.println("用戶目錄已存在");
            }
            
            // 處理檔案名稱，確保唯一性
            String finalFilename = timestamp + "_" + filename;
            if (!finalFilename.toLowerCase().endsWith(".html")) {
                finalFilename += ".html";
            }
            System.out.println("最終檔案名稱: " + finalFilename);
            
            // 建立完整的檔案路徑
            File outputFile = new File(userDir, finalFilename);
            System.out.println("完整檔案路徑: " + outputFile.getAbsolutePath());
            
            // 寫入 HTML 內容到檔案
            System.out.println("開始寫入檔案...");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(htmlContent);
                writer.flush();
                System.out.println("檔案寫入成功");
            }
            
            // 檢查檔案是否已創建
            if (outputFile.exists() && outputFile.length() > 0) {
                System.out.println("驗證檔案已創建: 成功 (大小: " + outputFile.length() + " 字節)");
            } else {
                System.out.println("警告: 檔案可能未成功創建或為空");
            }
            
            // 建立 URL 路徑供前端使用
            String urlPath = "/html/dtxstore/lessons/" + userFolder + "/" + finalFilename;
            System.out.println("對外訪問URL: " + urlPath);
            
            // 設置成功回應
            responseJson.put("success", true);
            responseJson.put("message", "HTML 檔案已成功上傳");
            responseJson.put("filePath", outputFile.getAbsolutePath());
            responseJson.put("url", urlPath);
            
            System.out.println("處理成功，返回結果");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        } catch (JSONException e) {
            System.err.println("JSON 解析錯誤: " + e.getMessage());
            e.printStackTrace();
            responseJson.put("success", false);
            responseJson.put("message", "無效的請求格式: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        } catch (Exception e) {
            System.err.println("處理 HTML 上傳時發生錯誤: " + e.getMessage());
            e.printStackTrace();
            responseJson.put("success", false);
            responseJson.put("message", "伺服器錯誤: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        } finally {
            System.out.println("=== HTML上傳請求結束 ===");
        }
        
        return responseJson.toString();
    }

    @RESTfulAPIDefine(url = "open/html/{userFolder}/{filename}", methods = "get", description = "開啟HTML檔案")
    private void openHtml(HttpExchange exchange, String userFolder, String filename) throws IOException {
        System.out.println("=== HTML開啟請求開始 ===");

        try {
            // 驗證參數
            if (userFolder == null || filename == null || userFolder.isEmpty() || filename.isEmpty()) {
                System.out.println("錯誤: 缺少必要參數");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                return;
            }

            System.out.println("用戶資料夾: " + userFolder);
            System.out.println("檔案名稱: " + filename);

            // 構建檔案路徑
            File ROOT = new File(Config.get("html.folder.path", "./html/lessonProject"));
            File file = new File(ROOT, userFolder + File.separator + filename);

            // 驗證檔案是否存在
            if (!file.exists() || !file.isFile()) {
                System.out.println("錯誤: 檔案不存在或無效");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                return;
            }

            // 設置回應標頭
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
            if (mimeType != null) {
                exchange.getResponseHeaders().add("Content-Type", mimeType);
            }
            exchange.getResponseHeaders().add("Cache-Control", "no-store");
            exchange.getResponseHeaders().add("Last-Modified", new Date(file.lastModified()).toGMTString());

            // 讀取檔案內容並回應
            System.out.println("開啟檔案: " + file.getAbsolutePath());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, file.length());
            try (OutputStream output = exchange.getResponseBody();
                 FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = fis.read(buffer)) > 0) {
                    output.write(buffer, 0, count);
                }
            }

            System.out.println("處理成功，返回檔案內容");
        } catch (Exception e) {
            System.err.println("處理 HTML 開啟時發生錯誤: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        } finally {
            System.out.println("=== HTML開啟請求結束 ===");
        }
    }
}
