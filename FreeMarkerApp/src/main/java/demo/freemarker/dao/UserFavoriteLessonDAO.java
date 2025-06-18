/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.UserFavoriteLesson;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author zhush
 */
public class UserFavoriteLessonDAO extends IntIdBaseDAO {

    final static UserFavoriteLessonDAO instance = new UserFavoriteLessonDAO("DTXSTORE_PU");

    public final static UserFavoriteLessonDAO getInstance() {
        return instance;
    }

    private UserFavoriteLessonDAO(String puName) {
        super(puName, UserFavoriteLesson.class);
    }

    @Override
    public String getTableName() {
        return "user_favorite_lesson";
    }

    public List<UserFavoriteLesson> findByUserId(Long userId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("UserFavoriteLesson.findByUserId");
            q.setParameter("userId", userId);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
    public UserFavoriteLesson findByUserIdAndLessonId(Long userId, Long lessonId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("UserFavoriteLesson.findByUserIdAndLessonId");
            q.setParameter("lessonId", lessonId);
            q.setParameter("userId", userId);
            return (UserFavoriteLesson) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }
}
