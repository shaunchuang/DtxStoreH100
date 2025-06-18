package itri.sstc.framework.core.database;

import itri.sstc.framework.core.database.BaseEntity.DBFieldDescription;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import org.json.JSONObject;

/**
 * 資料處理工具
 *
 * @author schung
 */
public class EntityUtility {

    /**
     * 將資料 Map 中的資料指派給 Entity 物件
     *
     * @param entity Entity 物件
     * @param values 資料 Map(K: 欄位名稱, V: 數值)
     * @return Entity 物件
     * @throws java.io.IOException
     */
    public final static BaseEntity fromValueMap(BaseEntity entity, Map<String, Object> values) throws IOException {
        List<Field> fields = getColumnFields(entity.getClass());
        //
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!values.containsKey(fieldName)) {
                continue;
            }
            //
            Annotation[] basics = field.getAnnotationsByType(Basic.class);
            boolean optional = true; // 是否可以不顯示
            if (basics.length != 0) {
                Basic basic = (Basic) basics[0];
                optional = basic.optional();
            }
            //
            field.setAccessible(true);
            Object value = values.get(fieldName);
            try {
                boolean isNull = false;
                if (value == null || value.toString().trim().equalsIgnoreCase("null")) {
                    isNull = true;
                }
                //
                Annotation[] columns = field.getAnnotationsByType(Column.class);
                if (columns.length != 1) {
                    continue;
                }
                //
                if (isNull && optional) {
                    continue;
                }
                // Java 欄位資料型別
                String type = field.getGenericType().getTypeName();
                //
                Object destValue = null;
                switch (type) {
                    case "java.lang.Boolean":
                    case "boolean":
                        destValue = Boolean.valueOf(value.toString());
                        break;
                    case "java.lang.Byte":
                    case "byte":
                        destValue = Byte.valueOf(value.toString());
                        break;
                    case "java.lang.Short":
                    case "short":
                        destValue = Short.valueOf(value.toString());
                        break;
                    case "java.lang.Integer":
                    case "int":
                        destValue = Integer.valueOf(value.toString());
                        break;
                    case "java.lang.Long":
                    case "long":
                        destValue = Long.valueOf(value.toString());
                        break;
                    case "java.lang.Float":
                    case "float":
                        destValue = Float.valueOf(value.toString());
                        break;
                    case "java.lang.Double":
                    case "double":
                        destValue = Double.valueOf(value.toString());
                        break;
                    case "java.lang.Character":
                    case "char":
                        destValue = value.toString().charAt(0);
                        break;
                    case "java.lang.String":
                        destValue = value.toString();
                        break;
                    case "java.util.Date":
                        String datetimeString = value.toString();
                        if (!datetimeString.trim().isEmpty()) {
                            // ex. 2024-03-18T08:50:30.052
                            if (destValue == null) {
                                try {
                                    destValue = Date.from(LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(datetimeString)).atZone(ZoneId.systemDefault()).toInstant());
                                } catch (Exception ex) {
                                }
                            }
                            if (destValue == null) {
                                try {
                                    destValue = Date.from(LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(datetimeString)).atStartOfDay(ZoneId.systemDefault()).toInstant());
                                } catch (Exception ex) {
                                }
                            }
                        }
                        break;
                    default:
                        destValue = null;
                }
                if (destValue != null) {
                    field.set(entity, destValue);
                }
            } catch (IllegalAccessException | IllegalArgumentException ie) {
            } catch (Exception ex) {
                throw new IOException(String.format("解析欄位 %s=%s 錯誤: %s", fieldName, value, ex.getMessage()));
            }
        }
        return entity;
    }

    /**
     * 取得資料表物件的 Scheme
     *
     * @param entity 資料表物件
     * @return Scheme(JSON Format)
     */
    public final static String entityScheme(Class entity) {
        List<Field> fields = getColumnFields(entity);
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (Field field : fields) {
            String name = field.getName();
            //
            Annotation[] dbFields = field.getAnnotationsByType(DBFieldDescription.class);
            if (dbFields.length == 0) {
                continue;
            }
            //
            Annotation[] columns = field.getAnnotationsByType(Column.class);
            if (columns.length != 1) {
                continue;
            }
            //
            Column column = (Column) columns[0];
            String dbFieldName = ((DBFieldDescription) dbFields[0]).name();
            String dbFieldDescript = ((DBFieldDescription) dbFields[0]).descript();
            String dataType = field.getType().getName();
            int length = column.length();
            boolean nullable = column.nullable();
            boolean unique = column.unique();
            String json = String.format("{ \"name\": \"%s\", \"fieldName\": \"%s\", \"fieldDescript\": \"%s\", \"type\": \"%s\", \"length\": %d, \"nullable\": %s, \"unique\": %s },", name, dbFieldName, dbFieldDescript, dataType, length, nullable, unique);
            sb.append(json);
        }
        sb.deleteCharAt(sb.length() - 1);
        //
        return String.format("[ %s ]", sb.toString().trim());
    }

    /**
     * 將資料庫物件src複製到資料庫物件dest
     *
     * @param src 資料庫物件
     * @param dest 資料庫物件
     */
    public final static void copyTo(BaseEntity src, BaseEntity dest) {
        if (src.getClass() != dest.getClass()) {
            throw new java.lang.IllegalArgumentException(String.format("Class type %s and %s not matched", src.getClass().getName(), dest.getClass().getName()));
        }
        //
        List<Field> fields = getColumnFields(src.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(src);
                if (value != null) {
                    field.set(dest, value);
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
            }
        }
    }

    /**
     * 將資料物件列表輸出成 JSON Array 格式字串
     *
     * @param list 資料物件列表
     * @return JSON Array 格式字串
     */
    public final static String toJSONArrayString(List list) {
        if (!list.isEmpty() && (list.get(0) instanceof BaseEntity)) {
            return toJSONArrayString(list.toArray());
        } else {
            return "[]";
        }
    }

    /**
     * 將資料物件列表輸出成 JSON Array 格式字串
     *
     * @param list 資料物件列表
     * @return JSON Array 格式字串
     */
    public final static String toJSONArrayString(Object[] list) {
        if (list.length > 0 && (list[0] instanceof BaseEntity)) {
            StringBuilder sb = new StringBuilder(" ");
            for (Object entity : list) {
                sb.append(toJSONString((BaseEntity) entity)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return String.format("[ %s ]", sb.toString().trim());
        } else {
            return "[]";
        }
    }

    /**
     * 將資料物件輸出成 JSON 字串格式
     *
     * @param entity 資料物件
     * @return JSON 字串
     */
    public final static String toJSONString(BaseEntity entity) {
        List<Field> fields = getColumnFields(entity.getClass());
        //
        StringBuilder sb = new StringBuilder();
        
        for (Field field : fields) {
            String fieldName = field.getName();
            // 是否可以不顯示
            Annotation[] basics = field.getAnnotationsByType(Basic.class);
            boolean optional = true;
            if (basics.length != 0) {
                Basic basic = (Basic) basics[0];
                optional = basic.optional();
            }
            //
            String type = field.getGenericType().getTypeName();
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                // 值為 Null 且可不顯示
                if (value == null && optional) {
                    continue;
                }
                //
                String valueString = null;
                if (value != null) { // 仍要顯示
                    switch (type) {
                        case "java.lang.Boolean":
                        case "boolean":
                            valueString = Boolean.toString(((Boolean) value));
                            break;
                        case "java.lang.Byte":
                        case "byte":
                            valueString = String.format("%d", ((Byte) value));
                            break;
                        case "java.lang.Short":
                        case "short":
                            valueString = String.format("%d", ((Short) value));
                            break;
                        case "java.lang.Integer":
                        case "int":
                            valueString = String.format("%d", ((Integer) value));
                            break;
                        case "java.lang.Long":
                        case "long":
                            valueString = String.format("%d", ((Long) value));
                            break;
                        case "java.lang.Float":
                        case "float":
                            valueString = String.format("%f", ((Float) value));
                            break;
                        case "java.lang.Double":
                        case "double":
                            valueString = String.format("%f", ((Double) value));
                            break;
                        case "java.lang.Character":
                        case "char":
                            // char 不會為 null, 預設值 = 0x00;
                            if (((Character) value) == 0x00) {
                                valueString = "null";
                            } else {
                                valueString = String.format("\"%s\"", ((Character) value).charValue());
                            }
                            break;
                        case "java.lang.String":
                            String temp = ((String) value);
                            //System.out.println("** " + temp);
                            temp = temp.replaceAll("\"", "\\\""); // 在 JSON 中不能直接使用 ", 改成 '
                            temp = temp.replaceAll("\t", "\\t"); // 在 JSON 中不能使用 TAB
                            valueString = String.format("\"%s\"", temp);
                            break;
                        case "java.util.Date":
                            Annotation[] temporals = field.getAnnotationsByType(Temporal.class);
                            if (temporals.length != 0) {
                                Temporal temporal = (Temporal) temporals[0];
                                switch (temporal.value().name()) {
                                    case "DATE":
                                        valueString = String.format("\"%s\"", DateTimeFormatter.ISO_LOCAL_DATE.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                        break;
                                    case "TIME":
                                        valueString = String.format("\"%s\"", DateTimeFormatter.ISO_LOCAL_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                        break;
                                    case "TIMESTAMP":
                                        valueString = String.format("\"%s\"", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                        break;
                                }
                            } else {
                                valueString = String.format("\"%s\"", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                            }
                            break;
                        default:
                            if (value != null) { // 如果不是 NULL，一律調用 Object.toString() 取值
                                valueString = String.format("\"%s\"", value.toString());
                            } else {
                                valueString = null;
                            }
                    }
                    if (valueString == null) {
                        continue;
                    }
                } else {
                    valueString = "null";
                }
                //
                String pairString = String.format("\"%s\": %s", fieldName, valueString);
                sb.append(pairString).append(",");
            } catch (IllegalAccessException | IllegalArgumentException ex) {
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return String.format("{ %s }", sb.toString());
    }

    /**
     * 將資料物件輸出成 JSONObject 物件
     *
     * @param entity 資料物件
     * @return JSONObject 物件
     */
    public final static JSONObject toJSONObject(BaseEntity entity) {
        List<Field> fields = getColumnFields(entity.getClass());
        JSONObject json = new JSONObject();
        for (Field field : fields) {
            String fieldName = field.getName();
            // 是否可以不顯示
            Annotation[] basics = field.getAnnotationsByType(Basic.class);
            boolean optional = true;
            if (basics.length != 0) {
                Basic basic = (Basic) basics[0];
                optional = basic.optional();
            }
            String type = field.getGenericType().getTypeName();
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                // 值為 Null 且可不顯示
                if (value == null && optional) {
                    continue;
                }
                if (value == null) {
                    json.put(fieldName, JSONObject.NULL);
                    continue;
                }
                switch (type) {
                    case "java.lang.Boolean":
                    case "boolean":
                    case "java.lang.Byte":
                    case "byte":
                    case "java.lang.Short":
                    case "short":
                    case "java.lang.Integer":
                    case "int":
                    case "java.lang.Long":
                    case "long":
                    case "java.lang.Float":
                    case "float":
                    case "java.lang.Double":
                    case "double":
                        json.put(fieldName, value);
                        break;
                    case "java.lang.Character":
                    case "char":
                        // char 不會為 null, 預設值 = 0x00;
                        if (((Character) value) == 0x00) {
                            json.put(fieldName, JSONObject.NULL);
                        } else {
                            json.put(fieldName, value.toString());
                        }
                        break;
                    case "java.lang.String":
                        json.put(fieldName, value);
                        break;
                    case "java.util.Date":
                        Annotation[] temporals = field.getAnnotationsByType(Temporal.class);
                        if (temporals.length != 0) {
                            Temporal temporal = (Temporal) temporals[0];
                            switch (temporal.value().name()) {
                                case "DATE":
                                    json.put(fieldName, DateTimeFormatter.ISO_LOCAL_DATE.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                    break;
                                case "TIME":
                                    json.put(fieldName, DateTimeFormatter.ISO_LOCAL_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                    break;
                                case "TIMESTAMP":
                                    json.put(fieldName, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                                    break;
                            }
                        } else {
                            json.put(fieldName, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(((Date) value).toInstant().atZone(ZoneId.systemDefault())));
                        }
                        break;
                    default:
                        json.put(fieldName, value.toString());
                }
            } catch (IllegalAccessException | IllegalArgumentException ex) {
            }
        }
        return json;
    }

    /**
     * 取得所有標註為 Column 的變數(含父層類別)
     *
     * @param entityClass
     * @return 標註為 Column 的變數變數列表
     */
    public final static List<Field> getColumnFields(Class entityClass) {
        Class targetClass = entityClass;
        List<Field> fieldList = new ArrayList<Field>();
        int count = 0;
        while (true) {
            if (targetClass == null || targetClass.equals(BaseEntity.class) || targetClass.equals(Object.class)) {
                break;
            }
            // 取得本類別的變數宣告並加入列表
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldModifier = Modifier.toString(field.getModifiers());
                // 只處理 local variables
                if (fieldModifier.contains("static")) {
                    continue;
                }
                //
                Annotation[] columns = field.getAnnotationsByType(Column.class);
                if (columns.length == 0) {
                    continue;
                }
                //
                fieldList.add(field);
            }
            count++;
            // 指向上一層類別
            targetClass = targetClass.getSuperclass();
        }
        //
        return fieldList;
    }

}
