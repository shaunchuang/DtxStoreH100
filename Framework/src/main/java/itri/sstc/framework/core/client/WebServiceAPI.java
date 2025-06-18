package itri.sstc.framework.core.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 外部資源 Web API 介面
 *
 * @author schung
 */
public class WebServiceAPI {

    protected static final Logger logger = Logger.getLogger(WebServiceAPI.class.getName());

    /**
     *
     * @param urlString
     * @param headers
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doGet(String urlString, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlString);
        //
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
        connection.connect();
        //
        int http_status = connection.getResponseCode();
        if (http_status >= HttpURLConnection.HTTP_OK && http_status < HttpURLConnection.HTTP_INTERNAL_ERROR) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp = null;
            while ((temp = bfrd.readLine()) != null) {
                sb.append(temp);
                sb.append("\r\n");
            }
            bfrd.close();
            connection.disconnect();
            return sb.toString();
        } else {
            throw new IOException(String.format("Http Connection Error: %d", http_status));
        }
    }

    /**
     *
     * @param urlString
     * @param postBody
     * @param headers
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doPost(String urlString, String postBody, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod("POST");
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.connect();
        //
        if (headers.containsKey("charset")) {
            connection.getOutputStream().write(postBody.getBytes(headers.get("charset")));
        }
        //
        int http_status = connection.getResponseCode();
        if (http_status >= HttpURLConnection.HTTP_OK && http_status < HttpURLConnection.HTTP_MULT_CHOICE) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp = null;
            while ((temp = bfrd.readLine()) != null) {
                sb.append(temp);
                sb.append("\r\n");
            }
            bfrd.close();
            connection.disconnect();
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            if (connection.getErrorStream() != null) {
                BufferedReader bfrd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String temp = null;
                while ((temp = bfrd.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                }
                bfrd.close();
            }
            connection.disconnect();
            throw new IOException("Http Connection Error: " + http_status + "\r\n" + sb.toString());
        }
    }

    /**
     *
     * @param urlString
     * @param putBody
     * @param headers
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doPut(String urlString, String putBody, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(connectTimeout);
        con.setReadTimeout(readTimeout);
        con.setRequestMethod("PUT");
        for (String key : headers.keySet()) {
            con.setRequestProperty(key, headers.get(key));
        }
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.connect();
        //
        if (headers.containsKey("charset")) {
            con.getOutputStream().write(putBody.getBytes(headers.get("charset")));
        }
        //
        int http_status = con.getResponseCode();
        if (http_status >= HttpURLConnection.HTTP_OK && http_status < HttpURLConnection.HTTP_MULT_CHOICE) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bfrd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            while ((temp = bfrd.readLine()) != null) {
                sb.append(temp);
                sb.append("\r\n");
            }
            bfrd.close();
            con.disconnect();
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            if (con.getErrorStream() != null) {
                BufferedReader bfrd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String temp = null;
                while ((temp = bfrd.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                }
                bfrd.close();
            }
            con.disconnect();
            throw new IOException("Http Connection Error: " + http_status + "\r\n" + sb.toString());
        }
    }

    /**
     * 上傳檔案
     *
     * @param urlString 上傳檔案API Url
     * @param fieldName 上傳檔案欄位名稱
     * @param file 要上傳的檔案
     * @param charset 字元集
     * @param connectTimeout 連線timeout
     * @param readTimeout 讀取timeout
     * @return 上傳檔案結果
     * @throws IOException
     */
    public static String doUpload(String urlString, Map<String, String> params, String fieldName, File file, String charset, int connectTimeout, int readTimeout) throws IOException {
        String responseMessage = null;
        //
        try {
            MultipartTask multipart = new MultipartTask(urlString, charset);
            for (String key : params.keySet()) {
                multipart.addFormField(key, params.get(key), "");
            }
            multipart.addFilePart(fieldName, file);
            responseMessage = multipart.exec();
        } catch (Exception ex) {
            throw new IOException("Upload File Error: " + ex.toString());
        }
        return responseMessage;
    }

    /**
     * 下載檔案
     *
     * @param urlString
     * @param headers
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static ByteBuffer doDownload(String urlString, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod("GET");
        if (headers.containsKey("contenttype")) {
            connection.setRequestProperty("Content-Type", headers.get("contenttype"));
        }
        if (headers.containsKey("charset")) {
            connection.setRequestProperty("charset", headers.get("charset"));
        }
        connection.connect();
        //
        int http_status = connection.getResponseCode();
        if (http_status >= HttpURLConnection.HTTP_OK && http_status < HttpURLConnection.HTTP_INTERNAL_ERROR) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bfis = new BufferedInputStream(connection.getInputStream());
            int available = 0;
            while ((available = bfis.available()) > 0) {
                byte[] temp = new byte[available];
                bfis.read(temp);
                baos.write(temp);
                baos.flush();
            }
            //
            ByteBuffer buffer = ByteBuffer.allocate(baos.size());
            buffer.put(baos.toByteArray());
            baos.close();
            connection.disconnect();
            return buffer;
        } else {
            throw new IOException(String.format("Http Connection Error: %d", http_status));
        }
    }

}
