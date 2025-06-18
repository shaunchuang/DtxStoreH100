/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonAchievement;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonAchievementDAO extends IntIdBaseDAO {

    final static LessonAchievementDAO instance = new LessonAchievementDAO("DTXSTORE_PU");

    public final static LessonAchievementDAO getInstance() {
        return instance;
    }

    private LessonAchievementDAO(String puName) {
        super(puName, LessonAchievement.class);
    }

    @Override
    public String getTableName() {
        return "lesson_achievement";
    }
    
    public List<LessonAchievement> listAchievementByLessonId(Long lessonId){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonAchievement.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public int countAchievementByLessonId(Long lessonId){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonAchievement.countByLessonId");
            q.setParameter("lessonId", lessonId);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public void deleteAchievementByLessonId(Long lessonId){
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonAchievement.deleteByLessonId");
            q.setParameter("lessonId", lessonId);
            em.getTransaction().begin();
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }   
    }
}
