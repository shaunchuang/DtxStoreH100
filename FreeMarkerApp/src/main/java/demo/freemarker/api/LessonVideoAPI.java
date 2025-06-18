/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonVideoDAO;
import demo.freemarker.model.LessonVideo;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonVideoAPI implements API {

    private final static LessonVideoAPI INSTANCE = new LessonVideoAPI();

    public final static LessonVideoAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonVideoAPI";
    }

    @Override
    public String getVersion() {
        return "20250211.01";
    }

    @Override
    public String getDescription() {
        return "教案影片管理 API";
    }

    public List<LessonVideo> listLessonVideos() {
        try {
            List<LessonVideo> output = new ArrayList<>();
            List<IntIdDataEntity> list = LessonVideoDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonVideo) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonVideo getLessonVideo(long id) {
        try {
            return (LessonVideo) LessonVideoDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonVideo(LessonVideo lessonVideo) {
        try {
            LessonVideoDAO.getInstance().create(lessonVideo);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonVideo(LessonVideo lessonVideo) {
        try {
            LessonVideoDAO.getInstance().edit(lessonVideo);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonVideo(long id) {
        try {
            LessonVideoDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonVideoByLessonId(Long lessonId) {
        try {
            LessonVideoDAO.getInstance().deleteVideoByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonVideo> listLessonVideoByLessonId(Long lessonId) {
        try {
            return LessonVideoDAO.getInstance().listVideoByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
