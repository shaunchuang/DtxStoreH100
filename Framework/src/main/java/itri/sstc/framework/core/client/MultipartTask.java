package itri.sstc.framework.core.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 *
 * @author www.codejava.net
 *
 */
public class MultipartTask {

    private static final String LINE_FEED = "\r\n";

    static int UPLOAD_BUFFER_SIZE = 4096;

    private final String boundary = "--------" + UUID.randomUUID().toString();

    private ByteArrayOutputStream buffer;
    private PrintWriter writer;
    private String requestURL;
    private final Map<String, String> headers;

    /**
     * This constructor initializes a new HTTP POST request with content type is
     * set to multipart/form-data
     *
     * @param requestURL
     * @param charset
     * @throws IOException
     */
    public MultipartTask(String requestURL, String charset) throws Exception {
        SSLUtility.ignoreSsl();
        this.requestURL = requestURL;
        this.headers = new HashMap<String, String>();
        this.headers.put("charset", charset);
        // creates a unique boundary based on time stamp
        // -----------------------------9051914041544843365972754266
        //
        buffer = new ByteArrayOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(buffer, charset), true);
        //
        writer.append(LINE_FEED);
        writer.flush();
    }

    public MultipartTask(String requestURL, Map<String, String> headers) throws Exception {
        SSLUtility.ignoreSsl();
        this.requestURL = requestURL;
        this.headers = headers;
        // creates a unique boundary based on time stamp
        // -----------------------------9051914041544843365972754266
        //
        String charset = this.headers.getOrDefault("charset", "utf-8");
        this.buffer = new ByteArrayOutputStream();
        this.writer = new PrintWriter(new OutputStreamWriter(this.buffer, charset), true);
        //
        this.writer.append(LINE_FEED);
        this.writer.flush();
    }

    /**
     * Adds a header field to the request.
     *
     * @param name - name of the header field
     * @param value - value of the header field
     * @return MultipartTask
     */
    public MultipartTask addHeaderField(String name, String value) {
        writer.append(String.format("%s: %s", name, value)).append(LINE_FEED);
        writer.flush();
        return this;
    }

    /**
     * Adds a form field to the request
     *
     * @param disposition disposition strings
     * @param value field value
     * @param contentType contentType
     * @return MultipartTask
     */
    public MultipartTask addFormField(String[] disposition, String value, String contentType) {
        StringBuilder sb = new StringBuilder(" ");
        for (String data : disposition) {
            sb.append(data).append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        //
        writer.flush();
        writer.append("--").append(boundary).append(LINE_FEED);
        writer.append("Content-Disposition: ").append(sb.toString()).append(LINE_FEED);
        writer.append("Content-Type: ").append(contentType).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
        return this;
    }

    /**
     * Adds a form field to the request
     *
     * @param name field name
     * @param value field value
     * @param contentType contentType
     * @return MultipartTask
     */
    public MultipartTask addFormField(String name, String value, String contentType) {
        String[] disposition = {"form-data", String.format("name=\"%s\"", name), String.format("filename=\"%s\"", UUID.randomUUID().toString())};
        return addFormField(disposition, value, contentType);
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @return
     * @throws IOException
     */
    public MultipartTask addFilePart(String fieldName, File uploadFile) throws IOException {
        String fileName = uploadFile.getName();
        String contentType = URLConnection.guessContentTypeFromName(fileName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        //
        FileInputStream fileInput = new FileInputStream(uploadFile);
        writer.flush();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: ").append(String.format("form-data; name=\"%s\"; filename=\"%s\"", fieldName, fieldName)).append(LINE_FEED);
        writer.append(String.format("Content-Type: %s", contentType)).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
        // 讀入檔案內容並寫入 buffer
        byte[] temp = new byte[UPLOAD_BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = fileInput.read(temp)) != -1) {
            buffer.write(temp, 0, bytesRead);
        }
        fileInput.close();
        //
        writer.append(LINE_FEED);
        writer.flush();
        return this;
    }

    /**
     * 執行 Multi-part 上傳並接收伺服端回傳值
     *
     * @return
     * @throws IOException
     */
    public String exec() throws IOException {
        writer.append("--" + boundary + "--");
        writer.flush();
        writer.close();
        //
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        for (String key : headers.keySet()) {
            conn.setRequestProperty(key, headers.get(key));
        }
        //
        conn.setRequestProperty("User-Agent", "CodeJava Agent");
        conn.setRequestProperty("Connection", "keep-alive");
        // Content-Type 不能被 Overwrite
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("Content-Length", "" + buffer.size());
        //
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //
        StringBuilder log = new StringBuilder();
        Map<String, List<String>> headers = conn.getRequestProperties();
        for (String key : headers.keySet()) {
            if (!headers.get(key).isEmpty()) {
                List<String> values = headers.get(key);
                StringBuilder sb = new StringBuilder(" ");
                for (String value : values) {
                    sb.append(value).append(";");
                }
                sb.deleteCharAt(sb.length() - 1);
                //
                log.append(key).append(": ").append(sb.toString());
            }
        }
        log.append("\r\n").append(new String(buffer.toByteArray()));
        WebServiceAPI.logger.info(log.toString());
        //
        conn.getOutputStream().write(buffer.toByteArray());
        conn.getOutputStream().flush();
        //
        // checks server's status code first
        BufferedReader bfrd;
        int status = conn.getResponseCode();
        if (status >= HttpURLConnection.HTTP_OK && status < HttpURLConnection.HTTP_MULT_CHOICE) {
            bfrd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            bfrd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        //
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = bfrd.readLine()) != null) {
            sb.append(temp);
            sb.append(LINE_FEED);
        }
        bfrd.close();
        conn.disconnect();
        //
        return sb.toString();
    }

}
