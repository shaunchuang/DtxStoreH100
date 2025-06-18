package itri.sstc.freemarker.binding;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class BindingHttpContext {

    private final Map<String, Object> formData;
    private final Map<String, List<String>> validationErrors;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BindingHttpContext(Map<String, Object> formData) {
        this.formData = formData;
        this.validationErrors = new HashMap<>();
    }

    // 获取绑定状态（支持嵌套路径） 
    public BindStatus getBindStatus(String path, boolean htmlEscape) {
        Object value = getNestedValue(formData, path);
        String expression = path; // 使用路径作为 expression
        List<String> errors = validationErrors.getOrDefault(path, Collections.emptyList());

        String stringValue = "";
        if (value != null) { // 确保 value 不为 null
            if (value instanceof Date) { // 使用 instanceof 判断类型
                stringValue = sdf.format((Date) value);
            } else {
                stringValue = value.toString(); // 对于其他类型直接调用 toString
            }
        }

        if (htmlEscape) {
            stringValue = escapeHtml(stringValue);
        }

        return new BindStatus(stringValue, expression, errors);
    }

    public BindStatus getBindStatus(String path) {
        return getBindStatus(path, false);
    }

    // 添加验证错误
    public void addValidationError(String path, String errorMessage) {
        validationErrors.computeIfAbsent(path, k -> new ArrayList<>()).add(errorMessage);
    }

    // 解析嵌套路径（如 "post.title"） 
    private Object getNestedValue(Map<String, Object> data, String path) {
        String[] parts = path.split("\\.");
        if (parts.length == 2) {
            Object target = data.get(parts[0]);
            for (Method method : target.getClass().getMethods()) {
                if (method.getName().startsWith("get")) {
                    String fieldName = method.getName().substring(3);
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    if (fieldName.equals(parts[1])) {
                        try {
                            // 確認方法參數數量，如果是無參數方法傳遞空陣列
                            if (method.getParameterCount() == 0) {
                                return method.invoke(target); // 無參數方法
                            } else {
                                return method.invoke(target, new Object[0]); // 有參數方法
                            }
                        } catch (Exception e) {
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    // HTML 转义
    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
