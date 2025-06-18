/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.DtxTag;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class DtxTagDAO extends IntIdBaseDAO {

    final static DtxTagDAO instance = new DtxTagDAO("DTXSTORE_PU");

    public final static DtxTagDAO getInstance() {
        return instance;
    }

    private DtxTagDAO(String puName) {
        super(puName, DtxTag.class);
    }

    @Override
    public String getTableName() {
        return "dtx_tag";
    }

    public List<DtxTag> findAll() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findAll");
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<DtxTag>();
        } finally {
            em.close();
        }
    }

    public DtxTag findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findById");
            q.setParameter("Id", id);
            return (DtxTag) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public DtxTag findByTagName(String tagName) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findByTagName");
            q.setParameter("tagName", tagName);
            return (DtxTag) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<DtxTag> findByType(int type) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findByTagType");
            q.setParameter("tagType", type);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<DtxTag> findByIds(List<Long> ids) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findByIds");
            q.setParameter("ids", ids);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<DtxTag> findByLessonId(Long lessonId){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<DtxTag> findByLessonIdAndType(Long lessonId, int type){ 
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("DtxTag.findByLessonIdAndTag");
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

}
