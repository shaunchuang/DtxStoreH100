/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.DtxTagDAO;
import demo.freemarker.model.DtxTag;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class DtxTagAPI implements API {

    private final static DtxTagAPI INSTANCE = new DtxTagAPI();

    public final static DtxTagAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "DtxTagAPI";
    }

    @Override
    public String getVersion() {
        return "20250207.01";
    }

    @Override
    public String getDescription() {
        return "標籤管理 API";
    }

    public List<DtxTag> listDtxTag() {
        List<DtxTag> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = DtxTagDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((DtxTag) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public DtxTag getDtxTag(long id) {
        try {
            return (DtxTag) DtxTagDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createDtxTag(DtxTag dtxTag) {
        try {
            DtxTagDAO.getInstance().create(dtxTag);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateDtxTag(DtxTag dtxTag) {
        try {
            DtxTagDAO.getInstance().edit(dtxTag);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteDtxTag(long id) {
        try {
            DtxTagDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<DtxTag> listDtxTagByType(int typeId) {
        try {
            return DtxTagDAO.getInstance().findByType(typeId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public DtxTag getDtxTagByName(String name) {
        try {
            return DtxTagDAO.getInstance().findByTagName(name);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean isDtxTagExist(String name) {
        try {
            DtxTag tag = DtxTagDAO.getInstance().findByTagName(name);
            if (tag != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<DtxTag> listDtxTagByIds(List<Long> tagIds) {
        try {
            if (tagIds.isEmpty()) {
                return List.of();
            }
            return DtxTagDAO.getInstance().findByIds(tagIds);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<DtxTag> listDtxTagByLessonId(Long lessonId) {
        try {
            return DtxTagDAO.getInstance().findByLessonId(lessonId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<DtxTag> listDtxTagByLessonIdAndType(Long lessonId, int type) {
        try {
            return DtxTagDAO.getInstance().findByLessonIdAndType(lessonId, type);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
