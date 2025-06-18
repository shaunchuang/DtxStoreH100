package itri.sstc.framework.core.httpd;

/**
 * HTML MultiPart Form 資料物件
 *
 * @author schung
 */
public class MultiPart {

    public enum PartType {
        TEXT, FILE
    }

    protected PartType type;
    protected String contentType;
    protected String name;
    protected String filename;
    protected String value;
    protected byte[] bytes;

    public PartType getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public String getValue() {
        return value;
    }

    public byte[] getBytes() {
        return bytes;
    }

}
