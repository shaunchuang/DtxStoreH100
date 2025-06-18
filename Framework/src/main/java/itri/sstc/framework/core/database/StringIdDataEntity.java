package itri.sstc.framework.core.database;

/**
 * 基本資料物件(Id為字串型態)
 *
 * @author schung
 */
public interface StringIdDataEntity extends BaseEntity {

    public String getId();

    public void setId(String id);

}
