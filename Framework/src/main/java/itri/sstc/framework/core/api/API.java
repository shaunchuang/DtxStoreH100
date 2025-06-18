package itri.sstc.framework.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.logging.Logger;

/**
 * API(只定義資料存取介面，資料封裝與傳輸由引用的程式端處理)
 *
 * @author schung
 */
public interface API {

    public final static Logger logger = Logger.getLogger(API.class.getName());

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface APIDefine {

        String description() default "";
    }

    /**
     * 取得 API 名稱(英數字不含空白)
     *
     * @return API 名稱(英數字不含空白)
     */
    @APIDefine(description = "取得 API 名稱")
    public String getName();

    /**
     * 取得 API 版本號
     *
     * @return API 版本號
     */
    @APIDefine(description = "API 版本號")
    public String getVersion();

    /**
     * 取得 API 描述
     *
     * @return API 描述
     */
    @APIDefine(description = "取得 API 描述")
    public String getDescription();

}
