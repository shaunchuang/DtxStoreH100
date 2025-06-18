/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonTagDAO;
import demo.freemarker.model.LessonTag;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonTagAPI implements API {

    private final static LessonTagAPI INSTANCE = new LessonTagAPI();

    public final static LessonTagAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonTagAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案標籤管理 API";
    }

    public List<LessonTag> listLessonTag() {
        try {
            List<LessonTag> output = new ArrayList<>();
            List<IntIdDataEntity> list = LessonTagDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonTag) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonTag getLessonTag(long id) {
        try {
            return (LessonTag) LessonTagDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonTag(LessonTag lessonTag) {
        try {
            LessonTagDAO.getInstance().create(lessonTag);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonTag(LessonTag lessonTag) {
        try {
            LessonTagDAO.getInstance().edit(lessonTag);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonTag(long id) {
        try {
            LessonTagDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonTagByLessonId(Long lessonId) {
        try {
            LessonTagDAO.getInstance().deleteTagByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonTag> listLessonTagByLessonId(Long lessonId) {
        try {
            return LessonTagDAO.getInstance().listTagByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
