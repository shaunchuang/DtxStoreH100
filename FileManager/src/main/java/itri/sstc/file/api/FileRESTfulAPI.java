package itri.sstc.file.api;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import itri.sstc.framework.core.Config;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.httpd.MultiPart;
import itri.sstc.framework.core.httpd.MultipartParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 檔案管理 RESTfulAPI
 *
 * @author schung
 */
public class FileRESTfulAPI extends RESTfulAPI {

    // 所有檔案放在此目錄下
    protected static File ROOT = new File(Config.get("filemanager.folder", "./root"));

    private final static String MULTI_PART = "multipart/form-data";
    private final static int BLOCK_SIZE = 524288; // Partial transmit 區塊大小 512KB

    @Override
    public String getContextPath() {
        return "/File/api/";
    }

    @RESTfulAPIDefine(url = "info", methods = "get", description = "取得 API 資訊")
    private String info(HttpExchange exchange) throws IOException {
        String json = String.format("{ \"name\": \"%s\", \"version\": \"%s\", \"desc\": \"%s\" }",
                FileAPI.getInstance().getName(),
                FileAPI.getInstance().getVersion(),
                FileAPI.getInstance().getDescription()
        );
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return json;
    }

    @RESTfulAPIDefine(url = "isFileExist", methods = "get", description = "檢查指定路徑/檔案是否存在")
    private String isFileExist(HttpExchange exchange) throws IOException {
        JSONObject response = new JSONObject();
        boolean exist;
        String path = getPath(exchange.getRequestURI());
        System.out.println("path：" + path);
        if (path == null) {
            exist = false;
        } else {
            File target = new File(ROOT, path);
            System.out.println("target file path：" + target.getAbsolutePath());
            exist = target.exists();
        }
        //
        response.put("exist", exist);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return response.toString(2);
    }

    @RESTfulAPIDefine(url = "file", methods = "get", description = "取得指定檔案")
    private String file(HttpExchange exchange) throws IOException {
        String path = getPath(exchange.getRequestURI());
        System.out.println("path：" + path);
        if (path == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }
        //
        File target = new File(ROOT, path);
        System.out.println("target path：" + target.getAbsolutePath());
        System.out.println("target getPath：" + target.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int responseCode = HttpURLConnection.HTTP_NOT_FOUND;
        try {
            String range = exchange.getRequestHeaders().getFirst("Range");
            boolean isPartialContent = (range != null && range.startsWith("bytes="));
            
            // 設置通用的標頭
            exchange.getResponseHeaders().add("Cache-Control", "no-store");
            Date lastmodified = new Date(target.lastModified());
            exchange.getResponseHeaders().add("Last-Modified", lastmodified.toGMTString());
            
            // 設置內容類型
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(target.getName());
            if (mimeType != null) {
                exchange.getResponseHeaders().add("Content-Type", mimeType);
            }
            
            // 檢查檔案大小，針對較大的視頻文件進行特殊處理
            long fileSize = target.length();
            
            if (!isPartialContent) {
                // 對於完整內容，在這裡發送標頭
                FileAPI.logger.log(Level.FINE, "### handleFullContent");
                responseCode = HttpURLConnection.HTTP_OK;
                exchange.sendResponseHeaders(responseCode, 0);
                
                // 對於超大檔案（例如超過10MB的視頻），使用直接流傳輸而不是通過ByteArrayOutputStream
                if (fileSize > 10485760 && mimeType != null && (mimeType.startsWith("video/") || mimeType.startsWith("audio/"))) {
                    OutputStream output = exchange.getResponseBody();
                    try (FileInputStream fis = new FileInputStream(target)) {
                        final byte[] buffer = new byte[65536]; // 使用64KB的緩衝區
                        int count;
                        while ((count = fis.read(buffer)) >= 0) {
                            output.write(buffer, 0, count);
                            // 減少 flush 次數，每 1MB 數據才 flush 一次
                            if (count == buffer.length && fis.available() > buffer.length) {
                                continue;  // 如果緩衝區已滿且還有更多數據，不進行 flush
                            }
                            output.flush();
                        }
                    }
                    return "";
                } else {
                    // 對於較小檔案，使用原來的處理方式
                    handleFullContent(exchange, target, baos);
                }
            } else {
                // 對於部分內容，在 handlePartialContent 中處理標頭
                log(Level.FINE, "*** handlePartialContent");
                handlePartialContent(exchange, target, baos);
                // 不再發送標頭，因為 handlePartialContent 已經處理了
            }
            
            // 將緩衝區數據寫入響應
            OutputStream output = exchange.getResponseBody();
            output.write(baos.toByteArray());
            output.flush();
            return "";
        } catch (Exception ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            return ex.getMessage();
        }
    }

    @RESTfulAPIDefine(url = "list", methods = "get", description = "列出指定路徑下的所有檔案")
    private String list(HttpExchange exchange) throws IOException {
        System.out.println("list");
        String path = getPath(exchange.getRequestURI());
        if (path == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }
        //
        JSONArray list = new JSONArray();
        try {
            List<File> files = FileAPI.getInstance().list(path);
            for (File file : files) {
                if (file.isHidden()) {
                    continue;
                }
                JSONObject elem = new JSONObject();
                elem.put("dir", file.isDirectory());
                elem.put("name", file.getName());
                list.put(elem);
            }
        } catch (Exception ex) {
        }
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return list.toString(2);
    }

    @RESTfulAPIDefine(url = "listAll", methods = "get", description = "列出指定路徑下的所有檔案(含子目錄)")
    private String listAll(HttpExchange exchange) throws IOException {
        String path = getPath(exchange.getRequestURI());
        if (path == null) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return null;
        }
        //
        JSONObject output = new JSONObject();
        try {
            File dir = new File(String.format("%s/%s", FileAPI.ROOT, path));
            List<File> files = FileAPI.getInstance().list(path);
            JSONArray list = new JSONArray();
            for (File file : files) {
                if (file.isHidden()) {
                    continue;
                }
                list.put(toJSON(file));
            }
            output.put("name", dir.getName());
            output.put("dir", true);
            output.put("children", list);
        } catch (Exception ex) {
        }
        //
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return output.toString(2);
    }

    @RESTfulAPIDefine(url = "upload", methods = "post", description = "上傳檔案")
    private String upload(HttpExchange exchange) throws IOException {
        String path = getPath(exchange.getRequestURI());
        File dest = new File(ROOT, path);
        if (!dest.exists()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            return String.format("Folder %s not exist", path);
        }
        //
        Headers headers = exchange.getRequestHeaders();
        String contentType = headers.getFirst("Content-Type");
        boolean success = false;
        String message;
        if (contentType.startsWith(MULTI_PART)) {
            String boundary = contentType.substring(contentType.indexOf("boundary=") + 9);
            byte[] payload = getRequestBodyBytes(exchange);
            List<MultiPart> parts = MultipartParser.parseMultiPart(boundary, payload); //
            //
            File uploadFile = null;
            byte[] data = null;
            boolean overwrite = true;
            for (MultiPart part : parts) {
                if (part.getType().equals(MultiPart.PartType.FILE)) {
                    String filename = part.getFilename();
                    // 保存內容類型，雖然這裡沒有使用它，但將來可能用於決定副檔名
                    String partContentType = part.getContentType();
                    data = part.getBytes();
                    uploadFile = new File(dest, filename);
                } else if (part.getType().equals(MultiPart.PartType.TEXT)) {
                    if (part.getName().equals("overwrite")) {
                        try {
                            overwrite = Boolean.parseBoolean(part.getValue());
                        } catch (Exception ex) {
                        }
                    }
                }
            }
            //
            if (uploadFile == null) {
                message = String.format("No file uploaded");
            } else {
                if (overwrite) {
                    try {
                        FileOutputStream fos = new FileOutputStream(uploadFile);
                        fos.write(data);
                        fos.close();
                        //
                        // 組成上傳檔案的URL字串
                        String url = String.format("%sfile/path/%s/%s", getContextPath(), path, URLEncoder.encode(uploadFile.getName(), "utf-8"));
                        success = true;
                        message = url;
                    } catch (Exception ex) {
                        message = String.format("Write file error: %s", ex.getMessage());
                    }
                } else {
                    message = String.format("File %s already exist", uploadFile.getName());
                }
            }
            JSONObject response = new JSONObject();
            response.put("success", success);
            response.put("message", message);
            //
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return response.toString(2);
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            return "Not Http Form Request";
        }
    }

    private JSONObject toJSON(File file) {
        JSONObject output = new JSONObject();
        output.put("name", file.getName());
        output.put("dir", file.isDirectory());
        //
        JSONArray list = new JSONArray();
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (File child : children) {
                JSONObject json = toJSON(child);
                list.put(json);
            }
            output.put("children", list);
        }
        //
        return output;
    }

    private String getPath(URI uri) {
        String path = uri.getPath();
        int idx = path.indexOf("/path/");
        if (idx < 0) {
            return null;
        } else {
            String output = path.substring(idx + "/path/".length());
            return output;
        }
    }

    /**
     * 一次讀取全部資料
     *
     * @param exchange
     * @param file
     * @param output
     * @throws IOException
     */
    private void handleFullContent(HttpExchange exchange, File file, OutputStream output) throws IOException {
        FileInputStream fis = null;
        try { //  讀取檔案內容
            fis = new FileInputStream(file);
            final byte[] buffer = new byte[32768]; // 增加到 32KB 提升讀取效率
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                output.write(buffer, 0, count);
                output.flush();
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 一次讀取部份資料
     *
     * @param exchange
     * @param file
     * @param output
     * @throws IOException
     */
    private void handlePartialContent(HttpExchange exchange, File file, OutputStream output) throws IOException {
        String _range = exchange.getRequestHeaders().getFirst("Range");
        log(Level.INFO, String.format("# %s", _range));

        long fileLength = file.length();
        String[] range = _range.trim().substring("bytes=".length()).trim().split("-");
        long start = Long.parseLong(range[0]); // 檔案資料起始點
        long end; // 檔案資料結束點
        if (range.length > 1) {
            end = Long.parseLong(range[1]);
        } else {
            end = start + BLOCK_SIZE - 1;
            if (fileLength <= end) {
                end = fileLength - 1;
            }
        }
        int contentLength = (int) (end - start + 1);
        //
        FileInputStream fis = null;
        try {
            // 讀取檔案內容
            fis = new FileInputStream(file);
            fis.skip(start);
            
            // 對於較大的內容塊，使用分段讀取提高效率
            if (contentLength > 1048576) { // 如果超過 1MB，分段讀取
                byte[] buffer = new byte[32768]; // 32KB 的讀取緩衝區
                int bytesRead;
                int totalBytesRead = 0;
                
                while (totalBytesRead < contentLength && 
                       (bytesRead = fis.read(buffer, 0, Math.min(buffer.length, contentLength - totalBytesRead))) != -1) {
                    output.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    
                    // 每讀取 256KB 才刷新一次，減少 I/O 操作
                    if (totalBytesRead % 262144 == 0) {
                        output.flush();
                    }
                }
                
                contentLength = totalBytesRead;
            } else {
                // 對於小塊內容，一次性讀取
                byte[] buffer = new byte[contentLength];
                int bytes = fis.read(buffer);
                output.write(buffer, 0, bytes);
                if (bytes != contentLength) {
                    contentLength = bytes;
                }
            }
            
            // 最後確保所有數據都已發送
            output.flush();
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        String contentRange = String.format("bytes %d-%d/%d", start, end, fileLength);
        exchange.getResponseHeaders().set("Content-Length", "" + contentLength);
        exchange.getResponseHeaders().set("Content-Range", contentRange);
        
        // 發送部分內容的響應標頭 (206 Partial Content)
        exchange.sendResponseHeaders(206, 0);
        //logger.log(Level.INFO, "# " + contentRange);
    }

}
