/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonAchievementDAO;
import demo.freemarker.model.LessonAchievement;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonAchievementAPI implements API {

    private final static LessonAchievementAPI INSTANCE = new LessonAchievementAPI();

    public final static LessonAchievementAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonAchievementAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案成就管理 API";
    }

    public List<LessonAchievement> listLessonAchievement() {
        List<LessonAchievement> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = LessonAchievementDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonAchievement) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonAchievement getLessonAchievement(Long id) {
        try {
            return (LessonAchievement) LessonAchievementDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonAchievement(LessonAchievement lessonAchievement) {
        try {
            LessonAchievementDAO.getInstance().create(lessonAchievement);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonAchievementList(List<LessonAchievement> lessonAchievementList) {
        try {
            LessonAchievementDAO.getInstance().createAll(lessonAchievementList);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonAchievement(LessonAchievement lessonAchievement) {
        try {
            LessonAchievementDAO.getInstance().edit(lessonAchievement);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonAchievement(long id) {
        try {
            LessonAchievementDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteAchievementByLessonId(Long lessonId) {
        try {
            LessonAchievementDAO.getInstance().deleteAchievementByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonAchievement> listLessonAchievementByLessonId(Long lessonId) {
        try {
            return LessonAchievementDAO.getInstance().listAchievementByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public int countLessonAchievementByLessonId(Long lessonId) {
        try {
            return LessonAchievementDAO.getInstance().countAchievementByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
