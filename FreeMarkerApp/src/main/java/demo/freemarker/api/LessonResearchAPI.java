/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonResearchDAO;
import demo.freemarker.model.LessonResearch;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonResearchAPI implements API {

    private final static LessonResearchAPI INSTANCE = new LessonResearchAPI();

    public final static LessonResearchAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonResearchAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案研究管理 API";
    }

    public List<LessonResearch> listLessonResearch() {
        List<LessonResearch> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = LessonResearchDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonResearch) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonResearch getLessonResearch(long id) {
        try {
            return (LessonResearch) LessonResearchDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonResearch(LessonResearch lessonResearch) {
        try {
            LessonResearchDAO.getInstance().create(lessonResearch);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonResearch(LessonResearch lessonResearch) {
        try {
            LessonResearchDAO.getInstance().edit(lessonResearch);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonResearch(long id) {
        try {
            LessonResearchDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteResearchByLessonId(Long lessonId) {
        try {
            LessonResearchDAO.getInstance().deleteResearchByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonResearch> listLessonResearchByLessonId(Long lessonId) {
        try {
            return LessonResearchDAO.getInstance().listResearchByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
