package itri.sstc.framework.core.database;

/**
 * 基本資料物件(Id為整數型態)
 *
 * @author schung
 */
public interface IntIdDataEntity extends BaseEntity {

    public Long getId();

    public void setId(Long id);

}
