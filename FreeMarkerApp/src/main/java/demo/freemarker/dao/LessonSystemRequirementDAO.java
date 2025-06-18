/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonSystemRequirement;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonSystemRequirementDAO extends IntIdBaseDAO {

    final static LessonSystemRequirementDAO instance = new LessonSystemRequirementDAO("DTXSTORE_PU");

    public final static LessonSystemRequirementDAO getInstance() {
        return instance;
    }

    private LessonSystemRequirementDAO(String puName) {
        super(puName, LessonSystemRequirement.class);
    }

    @Override
    public String getTableName() {
        return "lesson_system_requirement";
    }
    
    public List<LessonSystemRequirement> listSystemRequirementByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonSystemRequirement.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public void deleteSystemRequirementByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonSystemRequirement.deleteByLessonId");
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
