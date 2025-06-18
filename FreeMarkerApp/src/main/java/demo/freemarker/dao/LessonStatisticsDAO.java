/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonStatistics;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 *
 * @author zhush
 */
public class LessonStatisticsDAO extends IntIdBaseDAO {

    final static LessonStatisticsDAO instance = new LessonStatisticsDAO("DTXSTORE_PU");

    public final static LessonStatisticsDAO getInstance() {
        return instance;
    }

    private LessonStatisticsDAO(String puName) {
        super(puName, LessonStatistics.class);
    }

    @Override
    public String getTableName() {
        return "lesson_statistics";
    }

    public List<LessonStatistics> listStatisticsByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonStatistics.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public int countStatisticsByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonStatistics.countByLessonId");
            q.setParameter("lessonId", lessonId);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public void deleteStatisticsByLessonId(Long lessonId){
        EntityManager em = getEntityManager();
        try{
            Query q = em.createNamedQuery("LessonStatistics.deleteByLessonId");
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
