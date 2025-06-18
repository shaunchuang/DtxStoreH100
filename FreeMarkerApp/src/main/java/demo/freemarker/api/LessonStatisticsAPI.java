/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonStatisticsDAO;
import demo.freemarker.model.LessonStatistics;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonStatisticsAPI implements API {

    private final static LessonStatisticsAPI INSTANCE = new LessonStatisticsAPI();

    public final static LessonStatisticsAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonStatisticsAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案統計管理 API";
    }

    public List<LessonStatistics> listLessonStatistics() {
        try {
            List<LessonStatistics> output = new ArrayList<>();
            List<IntIdDataEntity> list = LessonStatisticsDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonStatistics) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonStatistics getLessonStatistics(long id) {
        return (LessonStatistics) LessonStatisticsDAO.getInstance().findEntity(id);
    }

    public void createLessonStatistics(LessonStatistics lessonStatistics) {
        try {
            LessonStatisticsDAO.getInstance().create(lessonStatistics);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonStatisticsList(List<LessonStatistics> lessonStatistics) {
        try {
            LessonStatisticsDAO.getInstance().createAll(lessonStatistics);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonStatistics(LessonStatistics lessonStatistics) {
        try {
            LessonStatisticsDAO.getInstance().edit(lessonStatistics);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonStatistics(long id) {
        try {
            LessonStatisticsDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonStatisticsByLessonId(Long lessonId) {
        try {
            LessonStatisticsDAO.getInstance().deleteStatisticsByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonStatistics> listLessonStatisticsByLessonId(Long lessonId) {
        try {
            return LessonStatisticsDAO.getInstance().listStatisticsByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public int countLessonStatisticsByLessonId(Long lessonId) {
        try {
            return LessonStatisticsDAO.getInstance().countStatisticsByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
