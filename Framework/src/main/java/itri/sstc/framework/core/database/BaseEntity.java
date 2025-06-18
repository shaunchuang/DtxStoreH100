package itri.sstc.framework.core.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 資料表欄位定義
 *
 * @author schung
 */
public interface BaseEntity {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DBFieldDescription {
        String name() default "";
        String descript() default "";
    }

}
