/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonNewsDAO;
import demo.freemarker.model.LessonNews;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonNewsAPI implements API {

    private final static LessonNewsAPI INSTANCE = new LessonNewsAPI();

    public final static LessonNewsAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonNewsAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案新聞管理 API";
    }

    public List<LessonNews> listLessonNews() {
        List<LessonNews> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = LessonNewsDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonNews) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public LessonNews getLessonNews(long id) {
        try {
            return (LessonNews) LessonNewsDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonNews(LessonNews lessonNews) {
        try {
            LessonNewsDAO.getInstance().create(lessonNews);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createLessonNewsList(List<LessonNews> lessonNewsList) {
        try {
            LessonNewsDAO.getInstance().createAll(lessonNewsList);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateLessonNews(LessonNews lessonNews) {
        try {
            LessonNewsDAO.getInstance().edit(lessonNews);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonNews(long id) {
        try {
            LessonNewsDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonNewsByLessonId(Long lessonId) {
        try {
            LessonNewsDAO.getInstance().deleteNewsByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonNews> listLessonNewsByLessonId(Long lessonId) {
        try {
            return LessonNewsDAO.getInstance().listNewsByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
