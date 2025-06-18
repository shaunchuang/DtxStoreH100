/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonTag;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonTagDAO extends IntIdBaseDAO {

    final static LessonTagDAO instance = new LessonTagDAO("DTXSTORE_PU");

    public final static LessonTagDAO getInstance() {
        return instance;
    }

    private LessonTagDAO(String puName) {
        super(puName, LessonTag.class);
    }

    @Override
    public String getTableName() {
        return "lesson_tag";
    }

    public List<LessonTag> listTagByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonTag.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
    public List<LessonTag> listTagByLessonIds(List<Long> lessonIds) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonTag.findByLessonIds");
            q.setParameter("lessonIds", lessonIds);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void deleteTagByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonTag.deleteByLessonId");
            q.setParameter("lessonId", lessonId);
            em.getTransaction().begin();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        } finally {
            em.close();
        }
    }
}
