package itri.sstc.framework.core.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 基本資料儲取物件(Id為整數型態)
 *
 * @author schung
 */
public abstract class IntIdBaseDAO extends BaseDAO {

    @SuppressWarnings("rawtypes")
    public IntIdBaseDAO(EntityManagerFactory emf, Class entityClass) {
        super(emf, entityClass);
    }

    @SuppressWarnings("rawtypes")
    public IntIdBaseDAO(String puName, Class entityClass) {
        super(puName, entityClass);
    }

    /**
     * 新增資料
     *
     * @param entity 資料物件
     */
    public void create(IntIdDataEntity entity) {
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            EntityTransaction trans = em.getTransaction();
            trans.begin();
            for (Object entity : entities) {
                em.persist((IntIdDataEntity) entity);
            }
            trans.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public void edit(IntIdDataEntity entity) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = entity.getId();
                if (findEntity(id) == null) {
                    throw new RuntimeException(String.format("The %s with ID %d no longer exists.", entityClass.getCanonicalName(), id));
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
    public void destroy(Long id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntIdDataEntity entity;
            entity = (IntIdDataEntity) em.getReference(entityClass, id);
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public IntIdDataEntity findEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return (IntIdDataEntity) em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<IntIdDataEntity> findEntities() {
        return findEntities(true, -1, -1);
    }

    public List<IntIdDataEntity> findEntities(int maxResults, int firstResult) {
        return findEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<IntIdDataEntity> findEntities(boolean all, int maxResults, int firstResult) {
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
