/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.LessonComment;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class LessonCommentDAO extends IntIdBaseDAO {

    final static LessonCommentDAO instance = new LessonCommentDAO("DTXSTORE_PU");

    public final static LessonCommentDAO getInstance() {
        return instance;
    }

    private LessonCommentDAO(String puName) {
        super(puName, LessonComment.class);
    }

    @Override
    public String getTableName() {
        return "lesson_comment";
    }

    public List<LessonComment> listCommentByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonComment.findByLessonId");
            q.setParameter("lessonId", lessonId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<LessonComment>();
        } finally {
            em.close();
        }
    }

    public void deleteCommentByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonComment.deleteByLessonId");
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

    public Map<Long, Double> getAvgRatingsByLessonIds(List<Long> lessonIds) {
        EntityManager em = getEntityManager();
        // 添加對空列表的檢查
        if (lessonIds == null || lessonIds.isEmpty()) {
            return new HashMap<>();
        }
        try {
            // 使用原生 SQL 或 JPA 查詢來一次性獲取多個教案的平均評分
            Query q = em.createNamedQuery(
                "LessonComment.getAvgRatingsByLessonIds");
            q.setParameter("lessonIds", lessonIds);
            
            List<Object[]> results = q.getResultList();
            Map<Long, Double> avgRatings = new HashMap<>();
            
            for (Object[] result : results) {
                Long lessonId = (Long) result[0];
                Double avgRating = ((Number) result[1]).doubleValue();
                avgRatings.put(lessonId, avgRating);
            }
            
            return avgRatings;
        } finally {
            em.close();
        }
    }

    public Map<Long, Integer> getCommentCountsByLessonIds(List<Long> lessonIds) {
        if (lessonIds == null || lessonIds.isEmpty()) {
            return new HashMap<>();
        }
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery(
                "LessonComment.getCommentCountsByLessonIds");
            q.setParameter("lessonIds", lessonIds);
            
            List<Object[]> results = q.getResultList();
            Map<Long, Integer> commentCounts = new HashMap<>();
            
            for (Object[] result : results) {
                Long lessonId = (Long) result[0];
                Integer count = ((Number) result[1]).intValue();
                commentCounts.put(lessonId, count);
            }
            
            return commentCounts;
        } finally {
            em.close();
        }
    }

    public OptionalDouble getAvgRatingByLessonId(Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LessonComment.getAvgRatingByLessonId");
            q.setParameter("lessonId", lessonId);
            Double result = (Double) q.getSingleResult();
            
            if (result != null) {
                return OptionalDouble.of(result);
            }
            return OptionalDouble.empty();
        } catch (Exception e) {
            return OptionalDouble.empty();
        } finally {
            em.close();
        }
    }
}
