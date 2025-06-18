/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.UserFavoriteLessonDAO;
import demo.freemarker.model.UserFavoriteLesson;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class UserFavoriteLessonAPI implements API {

    private final static UserFavoriteLessonAPI INSTANCE = new UserFavoriteLessonAPI();

    public final static UserFavoriteLessonAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "UserFavoriteLessonAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "使用者收藏教案管理 API";
    }

    public List<UserFavoriteLesson> listUserFavoriteLesson() {
        List<UserFavoriteLesson> output = new ArrayList<>();
        List<IntIdDataEntity> list = UserFavoriteLessonDAO.getInstance().findEntities();
        for (IntIdDataEntity entity : list) {
            output.add((UserFavoriteLesson) entity);
        }
        return output;
    }

    public UserFavoriteLesson getUserFavoriteLesson(long id) {
        return (UserFavoriteLesson) UserFavoriteLessonDAO.getInstance().findEntity(id);
    }

    public void createUserFavoriteLesson(UserFavoriteLesson userFavoriteLesson) {
        UserFavoriteLessonDAO.getInstance().create(userFavoriteLesson);
    }

    public void updateUserFavoriteLesson(UserFavoriteLesson userFavoriteLesson) {
        try {
            UserFavoriteLessonDAO.getInstance().edit(userFavoriteLesson);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteUserFavoriteLesson(long id) {
        try {
            UserFavoriteLessonDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public List<UserFavoriteLesson> listByUserId(Long userId) {
        try {
            return UserFavoriteLessonDAO.getInstance().findByUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public UserFavoriteLesson getUserFavoriteLessonByUserIdAndLessonId(Long userId, Long lessonId) {
        try {
            return UserFavoriteLessonDAO.getInstance().findByUserIdAndLessonId(userId, lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
