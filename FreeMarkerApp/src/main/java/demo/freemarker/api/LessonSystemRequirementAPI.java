/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonSystemRequirementDAO;
import demo.freemarker.model.LessonSystemRequirement;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonSystemRequirementAPI implements API {

    private final static LessonSystemRequirementAPI INSTANCE = new LessonSystemRequirementAPI();

    public final static LessonSystemRequirementAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonSystemRequirementAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案系統需求管理 API";
    }

    public List<LessonSystemRequirement> listLessonSystemRequirement() {
        List<LessonSystemRequirement> output = new ArrayList<>();
        List<IntIdDataEntity> list = LessonSystemRequirementDAO.getInstance().findEntities();
        for (IntIdDataEntity entity : list) {
            output.add((LessonSystemRequirement) entity);
        }
        return output;
    }

    public LessonSystemRequirement getLessonSystemRequirement(long id) {
        try {
            return (LessonSystemRequirement) LessonSystemRequirementDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonSystemRequirement(LessonSystemRequirement lessonSystemRequirement) {
        try {
            LessonSystemRequirementDAO.getInstance().create(lessonSystemRequirement);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonSystemRequirement(LessonSystemRequirement lessonSystemRequirement) {
        try {
            LessonSystemRequirementDAO.getInstance().edit(lessonSystemRequirement);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonSystemRequirement(long id) {
        try {
            LessonSystemRequirementDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonSystemRequirementByLessonId(Long lessonId) {
        try {
            LessonSystemRequirementDAO.getInstance().deleteSystemRequirementByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonSystemRequirement> listLessonSystemRequirementByLessonId(Long lessonId) {
        try {
            return LessonSystemRequirementDAO.getInstance().listSystemRequirementByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
