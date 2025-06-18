package itri.sstc.framework.core.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 簡化的日誌紀錄訊息格式
 *
 * @author schung
 */
public class MiniFormatter extends Formatter {

    final static String LINE_SEP = System.getProperty("line.separator", "\r\n"); // 換行符號
    final static String FIELD_SEP = "\t"; // System.getProperty("\t", "\t"); // 欄位分隔符號
    // 日期格式
    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        //
        sb.append(FORMATTER.format(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())).append(FIELD_SEP);
        sb.append(record.getLevel().getLocalizedName()).append(FIELD_SEP);
        sb.append(getSource(record)).append(FIELD_SEP);
        sb.append(formatMessage(record));
        sb.append(LINE_SEP);
        //
        Throwable throwable = record.getThrown();
        if (throwable != null) {
            StringWriter sink = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sink, true));
            sb.append(sink.toString());
        }
        //
        return sb.toString();
    }

    private String getSource(LogRecord record) {
        return getSimpleClassName(record) + "." + getSourceMethodName(record);
    }

    private String getSimpleClassName(LogRecord record) {
        String className = record.getSourceClassName();
        if (className == null) {
            return "<Unknown>";
        }
        int idx = className.lastIndexOf(".");
        if (idx > 1) {
            return className.substring(idx + 1);
        } else {
            return className;
        }
    }

    private String getSourceMethodName(LogRecord record) {
        String methodName = record.getSourceMethodName();
        if (methodName == null) {
            return "<Unknow>";
        }
        return methodName;
    }

}
