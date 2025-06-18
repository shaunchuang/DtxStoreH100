package itri.sstc.framework.core.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 基本資料儲取物件(Id為字串型態)
 *
 * @author schung
 */
public abstract class StringIdBaseDAO extends BaseDAO {

    public StringIdBaseDAO(EntityManagerFactory emf, Class entityClass) {
        super(emf, entityClass);
    }

    public StringIdBaseDAO(String puName, Class entityClass) {
        super(puName, entityClass);
    }

    /**
     * 新增資料
     *
     * @param entity 資料物件
     */
    public void create(StringIdDataEntity entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * 批次新增
     *
     * @param entities 資料物件列表
     */
    public void createAll(List entities) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (Object entity : entities) {
                em.persist((StringIdDataEntity) entity);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    /**
     * 修改資料
     *
     * @param entity 資料物件
     * @throws Exception
     */
    public void edit(StringIdDataEntity entity) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = entity.getId();
                if (findEntity(id) == null) {
                    throw new RuntimeException("The " + entityClass.getCanonicalName() + " with ID " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 刪除資料
     *
     * @param id 資料流水號
     * @throws Exception
     */
    public void destroy(Integer id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            StringIdDataEntity entity;
            entity = (StringIdDataEntity) em.getReference(entityClass, id);
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public StringIdDataEntity findEntity(String id) {
        EntityManager em = getEntityManager();
        try {
            return (StringIdDataEntity) em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<StringIdDataEntity> findEntities() {
        return findEntities(true, -1, -1);
    }

    public List<StringIdDataEntity> findEntities(int maxResults, int firstResult) {
        return findEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<StringIdDataEntity> findEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
