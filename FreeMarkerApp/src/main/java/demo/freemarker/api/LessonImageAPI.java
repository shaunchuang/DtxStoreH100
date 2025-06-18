/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonImageDAO;
import demo.freemarker.model.LessonImage;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonImageAPI implements API {

    private final static LessonImageAPI INSTANCE = new LessonImageAPI();

    public final static LessonImageAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonImageAPI";
    }

    @Override
    public String getVersion() {
        return "20250211.01";
    }

    @Override
    public String getDescription() {
        return "教案影片管理 API";
    }

    public List<LessonImage> listLessonImages() {
        List<LessonImage> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = LessonImageDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonImage) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonImage getLessonImage(long id) {
        try {
            return (LessonImage) LessonImageDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonImage(LessonImage lessonImage) {
        try {
            LessonImageDAO.getInstance().create(lessonImage);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonImageList(List<LessonImage> lessonImages) {
        try {
            LessonImageDAO.getInstance().createAll(lessonImages);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonImage(LessonImage lessonImage) {
        try {
            LessonImageDAO.getInstance().edit(lessonImage);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonImage(long id) {
        try {
            LessonImageDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonImageByLessonId(Long lessonId) {
        try {
            LessonImageDAO.getInstance().deleteImageByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonImage> listLessonImageByLessonId(Long lessonId) {
        try {
            return LessonImageDAO.getInstance().listImageByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
