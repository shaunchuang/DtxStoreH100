/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonResearch;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonResearchDAO extends IntIdBaseDAO {

    final static LessonResearchDAO instance = new LessonResearchDAO("DTXSTORE_PU");

    public final static LessonResearchDAO getInstance() {
        return instance;
    }

    private LessonResearchDAO(String puName) {
        super(puName, LessonResearch.class);
    }

    @Override
    public String getTableName() {
        return "lesson_research";
    }

    public List<LessonResearch> listResearchByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonResearch.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void deleteResearchByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonResearch.deleteByLessonId");
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

