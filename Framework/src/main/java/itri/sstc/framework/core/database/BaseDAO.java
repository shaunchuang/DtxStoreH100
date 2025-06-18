package itri.sstc.framework.core.database;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 基本資料儲取物件(Id為整數型態)
 *
 * @author schung
 */
public abstract class BaseDAO {

    protected final static Logger logger = Logger.getLogger("itri.sstc.framework.core.database.BaseDAO");

    protected Properties jpaProperties;

    private EntityManagerFactory emf = null;
    @SuppressWarnings("rawtypes")
    protected Class entityClass;

    @SuppressWarnings("rawtypes")
    public BaseDAO(EntityManagerFactory emf, Class entityClass) {
        this.emf = emf;
        this.entityClass = entityClass;
    }

    @SuppressWarnings("rawtypes")
    public BaseDAO(String puName, Class entityClass) {
        this.jpaProperties = new Properties();
        try {
            this.jpaProperties.load(new FileReader(String.format("./%s.properties", puName)));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "", ex);
            System.exit(1);
        }
        this.emf = Persistence.createEntityManagerFactory((puName == null) ? "PU" : puName, this.jpaProperties);
        this.entityClass = entityClass;
    }

    public Class getEntityCLass() {
        return entityClass;
    }

    /**
     * 指定資料物件類別
     *
     * @param entityClass 資料物件類別
     */
    @SuppressWarnings("rawtypes")
    protected void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * 清除資料表
     */
    public final void truncate() {
        EntityManager em = getEntityManager();
        try {
            String tableName = getTableName();
            //
            String sql = null;
            String sql_ext = null;
            String jdbcDriver = jpaProperties.getProperty("javax.persistence.jdbc.driver", "");
            if (jdbcDriver.equals("org.hsqldb.jdbcDriver")) {
                // For HSQLDB 用的指令
                sql = String.format("TRUNCATE TABLE \"%s\"", tableName);
                //
                Field id = null;
                //
                List<Field> fields = EntityUtility.getColumnFields(entityClass);
                for (Field field : fields) {
                    if (field.getName().equals("id")) {
                        id = field;
                        break;
                    }
                }
                //
                if (id != null) {
                    String idType = id.getType().getSimpleName();
                    switch (idType) {
                        case "Integer":
                        case "Long":
                            sql_ext = String.format("ALTER TABLE PUBLIC.\"%s\" ALTER COLUMN \"id\" RESTART WITH 1", tableName);
                            break;
                        case "String":
                            break;
                        default:
                    }
                }
            } else if (jdbcDriver.equals("com.mysql.jdbc.Driver")) {
                sql = String.format("TRUNCATE TABLE `%s`", tableName);
            } else {
                sql = String.format("TRUNCATE TABLE %s", tableName);
            }
            //
            em.getTransaction().begin();
            if (sql != null) {
                em.createNativeQuery(sql).executeUpdate();
                if (sql_ext != null) {
                    em.createNativeQuery(sql_ext).executeUpdate();
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "BaseDao.truncate()", ex);
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }

    public final void sync() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.flush();
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "BaseDao.truncate()", ex);
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public int getEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<IntIdDataEntity> rt = cq.from(entityClass);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public final String toConditionString(Class dataClass, Map<String, String> params) {
        Field[] fields = dataClass.getDeclaredFields();
        //
        StringBuilder conditionString = new StringBuilder();
        for (Field field : fields) {
            String name = field.getName();
            Column[] columns = field.getAnnotationsByType(Column.class);
            if (columns != null && columns.length > 0) {
                String condition = params.get(name);
                if (condition == null) {
                    continue;
                }
                //
                Object value = null;
                switch (field.getType().getName()) {
                    case "java.lang.Boolean":
                    case "boolean":
                        value = Boolean.parseBoolean(condition);
                        break;
                    case "java.lang.Byte":
                    case "byte":
                        value = Byte.parseByte(condition);
                        break;
                    case "java.lang.Character":
                    case "char":
                        value = "'" + condition.charAt(0) + "'";
                        break;
                    case "java.lang.Short":
                    case "short":
                        value = Short.parseShort(condition);
                        break;
                    case "java.lang.Integer":
                    case "int":
                        value = Integer.parseInt(condition);
                        break;
                    case "java.lang.Long":
                    case "long":
                        value = Long.parseLong(condition);
                        break;
                    case "java.lang.Float":
                    case "float":
                        value = Float.parseFloat(condition);
                        break;
                    case "java.lang.Double":
                    case "double":
                        value = Double.parseDouble(condition);
                        break;
                    case "java.lang.String":
                        value = "'" + condition + "'";
                        break;
                    case "java.util.Date":
                        LocalDateTime.parse(condition);
                        value = "'" + condition + "'";
                        break;
                    default:
                        value = null;
                }
                if (value != null) {
                    conditionString.append(String.format("record.%s = %s AND ", name, value));
                }
            }
        }
        conditionString.trimToSize();
        if (!conditionString.isEmpty()) {
            conditionString.delete(conditionString.length() - 4, conditionString.length());
        }
        return conditionString.toString();
    }

    /**
     * 取得表格名稱
     *
     * @return 表格名稱
     */
    public abstract String getTableName();

}
