package itri.sstc.framework.core.httpd.file;

import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author schung
 */
public class FileRequestHandleTask extends Thread {

    public final static Logger logger = Logger.getLogger(FileRequestHandleTask.class.getName());

    private final static int BLOCK_SIZE = 1024; // Partial transmit 區塊大小 512KB

    private final HttpExchange exchange;

    protected FileRequestHandleTask(ThreadGroup group, HttpExchange exchange) {
        super(group, "FileRequestHandleTask");
        this.exchange = exchange;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        logger.log(Level.FINE, String.format("Release Thread[%d]", getId()));
    }

    @Override
    public void run() {
        String response;
        int responseCode = HttpURLConnection.HTTP_UNAVAILABLE;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            String fileId = exchange.getRequestURI().getPath();
            logger.log(Level.INFO, String.format("Thread[%d] GET %s", getId(), fileId));
            File file = getFile(fileId);
            //
            if (file == null) { // 檔案不存在
                response = String.format("Error 404 File %s not found.", fileId);
                responseCode = HttpURLConnection.HTTP_NOT_FOUND;
                baos.write(response.getBytes());
            } else if (file.isDirectory()) { // 目錄，自動導向 index
                file = file.getAbsoluteFile();
                File index = new File(file, "index.html");
                if (!index.exists()) {
                    response = String.format("Error 403 Director %s is Forbidden.", fileId);
                    responseCode = HttpURLConnection.HTTP_FORBIDDEN;
                    baos.write(response.getBytes());
                } else {
                    String old_path = exchange.getRequestURI().getPath();
                    if (old_path.endsWith("/")) {
                        old_path = old_path.substring(0, old_path.length() - 1);
                    }
                    //
                    String redirect = String.format("%s/index.html", old_path);
                    // System.out.println("*** " + redirect);
                    response = String.format("Redirect to %s", redirect);
                    exchange.getResponseHeaders().add("Location", redirect);
                    responseCode = HttpURLConnection.HTTP_MOVED_PERM;
                    baos.write(response.getBytes());
                }
            } else {
                String range = exchange.getRequestHeaders().getFirst("Range");
                if (range == null || !range.startsWith("bytes=")) {
                    logger.log(Level.FINE, "### handleFullContent");
                    handleFullContent(exchange, file, baos);
                } else {
                    logger.log(Level.FINE, "*** handlePartialContent");
                    handlePartialContent(exchange, file, baos);
                }
                if (enableNoCache(file)) {
                    exchange.getResponseHeaders().add("Cache-Control", "no-store");
                } else {
                    exchange.getResponseHeaders().add("Cache-Control", "public, max-age=3600");
                }
                Date lastmodified = new Date(file.lastModified());
                exchange.getResponseHeaders().add("Last-Modified", lastmodified.toGMTString());
                //
                responseCode = HttpURLConnection.HTTP_OK;
                /*
                exchange.getResponseHeaders().add("Cache-Control", "no-store");
                responseCode = HttpURLConnection.HTTP_OK;
                FileInputStream fs = new FileInputStream(file);
                final byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = fs.read(buffer)) >= 0) {
                    baos.write(buffer, 0, count);
                }
                fs.close();
                 */
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "", ex);
            responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
            ex.printStackTrace(new PrintStream(baos));
        }
        // 輸出至客戶端
        try {
            exchange.sendResponseHeaders(responseCode, 0);
            OutputStream output = exchange.getResponseBody();
            output.write(baos.toByteArray());
            output.flush();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "", ex);
        } finally {
            exchange.close();
        }
    }

    /**
     * 一次讀取完整資料
     *
     * @param exchange
     * @param file
     * @param output
     * @throws IOException
     */
    private void handleFullContent(HttpExchange exchange, File file, OutputStream output) throws IOException {
        FileInputStream fis = null;
        try {
            //  讀取檔案內容
            fis = new FileInputStream(file);
            final byte[] buffer = new byte[1024];
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
        logger.log(Level.INFO, "# " + _range);

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
            byte[] buffer = new byte[contentLength];
            fis.skip(start);
            int bytes = fis.read(buffer);
            output.write(buffer, 0, bytes);
            output.flush();
            if (bytes != contentLength) {
                logger.warning("Read bytes unmatch");
                contentLength = bytes;
            }
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
        //logger.log(Level.INFO, "# " + contentRange);
    }

    private File getFile(String fileId) {
        if (!fileId.startsWith(exchange.getHttpContext().getPath())) {
            return null;
        }
        //
        File file = new File("." + fileId);
        if (file.exists() && file.canRead()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 是否啟用 no-cache? 預設為不啟用
     *
     * @param file
     * @return
     */
    private boolean enableNoCache(File file) {
        String fileName = file.getName();
        int idx = fileName.lastIndexOf(".");
        boolean nocache = false;
        if (idx > 0) {
            String extName = fileName.substring(idx + 1);
            switch (extName) {
                case "html":
                    nocache = true;
                    break;
                default:
            }
        }
        return nocache;
    }

}
