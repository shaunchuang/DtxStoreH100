/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonCommentDAO;
import demo.freemarker.model.LessonComment;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class LessonCommentAPI implements API {

    private final static LessonCommentAPI INSTANCE = new LessonCommentAPI();

    public final static LessonCommentAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonCommentAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "教案評論管理 API";
    }

    @APIDefine(description = "列出所有教案評論")
    public final List<LessonComment> listLessonComment() {
        try {
            List<LessonComment> output = new ArrayList<LessonComment>();
            List<IntIdDataEntity> list = LessonCommentDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonComment) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "依教案評論ID取得資料")
    public LessonComment getLessonComment(long id) {
        try {
            return (LessonComment) LessonCommentDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "新增教案評論資料")
    public void createLessonComment(LessonComment lessonComment) {
        try {
            LessonCommentDAO.getInstance().create(lessonComment);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "更新教案評論資料")
    public void updateLessonComment(LessonComment lessonComment) {
        try {
            LessonCommentDAO.getInstance().edit(lessonComment);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "刪除教案評論資料")
    public void deleteLessonComment(long id) {
        try {
            LessonCommentDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteLessonCommentByLessonId(Long lessonId) {
        try {
            LessonCommentDAO.getInstance().deleteCommentByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<LessonComment> listLessonCommentByLessonId(Long lessonId) {
        try {
            return LessonCommentDAO.getInstance().listCommentByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
