package itri.sstc.framework.core.httpd;

import itri.sstc.framework.core.httpd.MultiPart.PartType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * HTML MultiPart Form 資料解析器
 *
 * @author schung
 */
public class MultipartParser {

    final static Charset CHARSET = Charset.forName("UTF-8");
    final static String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 解析 Multipart 資料
     *
     * @param boundary
     * @param payload
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<MultiPart> parseMultiPart(String boundary, byte[] payload) throws IOException {
        // 取出整個 multipart 的資料
        BufferedReader bfrd = new BufferedReader(new StringReader(new String(payload, CHARSET)));
        List<String> partDatas = new ArrayList<String>();
        StringBuilder sb = null;
        boolean multipart_data_begin = false;
        String temp;
        //
        parse_loop:
        while ((temp = bfrd.readLine()) != null) {
            if (temp.equals(String.format("--%s", boundary))) {
                if (!multipart_data_begin) {
                    sb = new StringBuilder();
                    multipart_data_begin = true;
                } else {
                    if (sb != null) {
                        partDatas.add(sb.toString().trim());
                    }
                    sb = new StringBuilder();
                    multipart_data_begin = true;
                }
            }
            // 最後一行不寫入
            if (temp.equals(String.format("--%s--", boundary))) {
                if (sb != null) {
                    partDatas.add(sb.toString().trim());
                    sb = null;
                }
                break parse_loop;
            }
            //
            if (multipart_data_begin) {
                if (!temp.trim().isEmpty()) {
                    sb.append(temp).append(LINE_SEPARATOR);
                }
            }
        }
        //
        List<MultiPart> parts = new ArrayList<MultiPart>();
        for (String partData : partDatas) {
            try {
                MultiPart part = parseData(partData);
                if (part != null) {
                    parts.add(part);
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return parts;
    }

    private static MultiPart parseData(String partData) throws IOException {
        String[] lines = partData.split(LINE_SEPARATOR);
        if (lines.length < 2) {
            return null;
        }
        // 去掉頭尾2行
        MultiPart part = new MultiPart();
        StringBuilder content = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].toLowerCase().startsWith("content-disposition:")) {
                String header = lines[i].toLowerCase().substring("content-disposition:".length()).trim();
                //
                if (header.startsWith("form-data;")) {
                    header = header.substring("form-data;".length()).trim();
                    String[] keyValues = header.split(";");
                    //
                    for (String keyValue : keyValues) {
                        if (keyValue.contains("=")) {
                            String[] data = keyValue.split("=");
                            if (data.length > 1) {
                                switch (data[0].trim()) {
                                    case "name":
                                        part.name = data[1].replaceAll("\"", "").trim();
                                        break;
                                    case "filename":
                                        part.filename = data[1].replaceAll("\"", "").trim();
                                        break;
                                    default:
                                }
                            }
                        }
                    }
                    if (part.filename == null) {
                        part.type = PartType.TEXT;
                    } else {
                        part.type = PartType.FILE;
                    }
                }
            } else if (lines[i].toLowerCase().startsWith("content-type:")) {
                part.contentType = lines[i].toLowerCase().substring("content-type:".length()).trim();
            } else {
                content.append(lines[i]).append(LINE_SEPARATOR);
            }
        }
        //
        if (part.type == PartType.FILE) {
            String temp = content.toString().trim();
            part.bytes = temp.getBytes(CHARSET);
        } else {
            part.value = content.toString().trim();
        }
        //
        return part;
    }

}
