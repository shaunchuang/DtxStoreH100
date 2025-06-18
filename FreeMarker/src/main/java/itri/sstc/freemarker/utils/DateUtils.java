package itri.sstc.freemarker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String dateStr) {
        return parse(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat fmt = new SimpleDateFormat();
        Date date = null;
        try {
            fmt.applyPattern(pattern);
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            try {
                fmt.applyPattern("yyyy-MM-dd HH:mm");
                date = fmt.parse(dateStr);
            } catch (ParseException e1) {
                fmt.applyPattern("yyyy-MM-dd");
                try {
                    date = fmt.parse(dateStr);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return date;
    }

    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat fmt = new SimpleDateFormat();
        fmt.applyPattern(pattern);
        return fmt.format(date);
    }

    public static Long parseLong() {
        return parseLong(new Date());
    }

    public static Long parseLong(Date date) {
        return date.getTime();
    }

    /**
     * 把日期對像根據生成指定格式的字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 把日期字符串生成指定格式的日期對像
     *
     * @param str
     * @param format
     * @return
     * @throws Exception
     */
    public static Date formatString(String str, String format) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * 生成當前年月日字符串
     *
     * @return
     * @throws Exception
     */
    public static String getCurrentDateStr() throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static String getCurrentYearMonthDay() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    //    // 获取当前年月
    public static String getCurrentYearMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        return dateFormat.format(new Date());
    }

    /**
     * 獲取指定範圍內的日期集合
     *
     * @param before
     * @param end
     * @return
     * @throws Exception
     */
    public static List<String> getRangeDates(String before, String end) throws Exception {
        List<String> datas = new ArrayList<String>();
        Calendar cb = Calendar.getInstance();
        Calendar ce = Calendar.getInstance();
        cb.setTime(formatString(before, "yyyy-MM-dd"));
        ce.setTime(formatString(end, "yyyy-MM-dd"));
        datas.add(formatDate(cb.getTime(), "yyyy-MM-dd"));
        while (cb.before(ce)) {
            cb.add(Calendar.DAY_OF_MONTH, 1);
            datas.add(formatDate(cb.getTime(), "yyyy-MM-dd"));
        }
        return datas;
    }

    /**
     * 獲取指定範圍內的月份集合
     *
     * @param before
     * @param end
     * @return
     * @throws Exception
     */
    public static List<String> getRangeMonth(String before, String end) throws Exception {
        List<String> months = new ArrayList<String>();
        Calendar cb = Calendar.getInstance();
        Calendar ce = Calendar.getInstance();
        cb.setTime(formatString(before, "yyyy-MM"));
        ce.setTime(formatString(end, "yyyy-MM"));
        months.add(formatDate(cb.getTime(), "yyyy-MM"));
        while (cb.before(ce)) {
            cb.add(Calendar.MONTH, 1);
            months.add(formatDate(cb.getTime(), "yyyy-MM"));
        }
        return months;
    }

//    public static void main(String[] args) throws Exception {
//        /*List<String> datas=getRangeDatas("2017-10-28","2017-11-02");
//		for(String data:datas){
//			System.out.println(data);
//		}*/
//        List<String> months = getRangeMonth("2017-09", "2018-12");
//        for (String month : months) {
//            System.out.println(month);
//        }
//    }
}
