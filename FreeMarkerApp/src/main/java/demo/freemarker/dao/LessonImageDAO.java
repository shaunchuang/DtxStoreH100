/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonImage;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonImageDAO extends IntIdBaseDAO {

    final static LessonImageDAO instance = new LessonImageDAO("DTXSTORE_PU");

    public final static LessonImageDAO getInstance() {
        return instance;
    }

    private LessonImageDAO(String puName) {
        super(puName, LessonImage.class);
    }

    @Override
    public String getTableName() {
        return "lesson_image";
    }

    public List<LessonImage> listImageByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonImage.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void deleteImageByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonImage.deleteByLessonId");
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
