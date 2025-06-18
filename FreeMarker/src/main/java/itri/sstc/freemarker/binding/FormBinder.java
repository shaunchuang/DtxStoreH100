package itri.sstc.freemarker.binding;
 
import itri.sstc.freemarker.binding.support.Page;
import itri.sstc.freemarker.core.RequestData;
import itri.sstc.freemarker.utils.StringUtils;
 
import java.lang.reflect.Method; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormBinder {

    // 緩存類別與其 setter 方法
    public static <T> T bind(RequestData request, T target) {

        // 判斷是否為泛型類別 (例如 Page<Option>)
        Class<?> clazz = target.getClass();

        // 預先取得 targetName
        String targetName = resolveTargetName(target, clazz);

        // 初始化類別的 setter 方法緩存 (只執行一次)
        Map<Class<?>, Map<String, Method>> setterCache = new HashMap<>();
        Map<String, Method> setterMethods = setterCache.computeIfAbsent(clazz, cls -> {
            Map<String, Method> methods = new HashMap<>();
            for (Method method : cls.getMethods()) {
                if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                    String fieldName = method.getName().substring(3);
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    methods.put(targetName + "." + fieldName, method);
                }
            }
            return methods;
        });

        // 遍歷 HTTP 參數並綁定到物件
        for (Map.Entry<String, String> entry : request.query.entrySet()) {

            String fieldName = entry.getKey(); // 參數名稱
            String value = entry.getValue().trim();  // 參數值

            if (!StringUtils.startsWith(fieldName, targetName + ".")) {
                fieldName = targetName + "." + fieldName;
            }

            Method setter = setterMethods.get(fieldName);
            if (setter != null) {
                try {
                    // 轉換值並調用 setter 方法
                    Object convertedValue = convertValue(value, setter.getParameterTypes()[0]);
                    setter.invoke(target, convertedValue);

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

        }

        return (T) target;
    }

    // 綁定 Page<T> 類型 
    private static <T> Page<T> bindPage(RequestData request, Class<T> itemType) {
        Page<T> page = new Page<>(itemType);
        // 假設從請求中獲取分頁參數（具體根據需求修改）
        page.setPageNo(Integer.parseInt(request.query.get("pageNo")));
        page.setPageSize(Integer.parseInt(request.query.get("totalPages")));
        return page;
    }

    // 根據類型轉換值
    private static Object convertValue(String value, Class<?> targetType) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.valueOf(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.valueOf(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.valueOf(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.valueOf(value);
        } else if (targetType == Date.class) {
            return parseDate(value);
        } else {
            throw new IllegalArgumentException("不支持的類型：" + targetType.getName());
        }
    }

    private static Date parseDate(String value) {
        String[] formats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm"};
        for (String format : formats) {
            try {
                return new SimpleDateFormat(format).parse(value);
            } catch (ParseException ignored) {
                // 忽略異常，嘗試下一個格式
            }
        }
        throw new IllegalArgumentException("日期格式錯誤，請使用 yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm 或 yyyy-MM-dd");
    }

    /**
     * 字串首字母小寫
     */
    private static String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static <T> String resolveTargetName(T target, Class<?> clazz) {
        if (target instanceof Page<?>) {
            // 取得泛型類別名稱
            Class<?> genericType = ((Page<?>) target).getGenericType();
            return uncapitalize(genericType.getSimpleName() + "Page");
        } else {
            return uncapitalize(clazz.getSimpleName());
        }
    }
}
