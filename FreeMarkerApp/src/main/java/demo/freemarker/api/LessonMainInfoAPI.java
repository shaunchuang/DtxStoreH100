/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.LessonMainInfoDAO;
import demo.freemarker.dto.LessonMainDTO;
import demo.freemarker.dto.LessonManageDTO;
import demo.freemarker.model.LessonMainInfo;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class LessonMainInfoAPI implements API {

    private final static LessonMainInfoAPI INSTANCE = new LessonMainInfoAPI();

    public final static LessonMainInfoAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "LessonMainInfoAPI";
    }

    @Override
    public String getVersion() {
        return "20250212.01";
    }

    @Override
    public String getDescription() {
        return "教案管理 API";
    }

    @APIDefine(description = "列出所有教案")
    public final List<LessonMainInfo> listLessonMainInfo() {
        List<LessonMainInfo> output = new ArrayList<LessonMainInfo>();
        try {
            List<IntIdDataEntity> list = LessonMainInfoDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((LessonMainInfo) entity);
            }
            return output;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @APIDefine(description = "取得使用者教案")
    public final List<LessonMainInfo> listLessonMainInfoByCreator(Long userId) {
        try {
            return LessonMainInfoDAO.getInstance().findLessonMainInfoByCreator(userId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @APIDefine(description = "依教案ID取得資料")
    public LessonMainInfo getLessonMainInfo(long id) {
        try {
            return (LessonMainInfo) LessonMainInfoDAO.getInstance().findEntity(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @APIDefine(description = "新增教案資料")
    public void createLessonMainInfo(LessonMainInfo lessonMainInfo) {
        try {
            LessonMainInfoDAO.getInstance().create(lessonMainInfo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @APIDefine(description = "更新教案資料")
    public void updateLessonMainInfo(LessonMainInfo lessonMainInfo) {
        try {
            LessonMainInfoDAO.getInstance().edit(lessonMainInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "刪除教案資料")
    public void deleteLessonMainInfo(long id) {
        try {
            LessonMainInfoDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "取得排序篩選之教案資料")
    public JSONObject getLessonsWithPagination(List<Integer> tagIds, Integer minAge, Integer maxAge,
            Double minPrice, Double maxPrice, String sortField, String sortDirection, String sortTwoField,
            String sortTwoOrder, int currentPage, int pageSize, String lessonName, Long userId, Boolean isLibrary) {
        int offset = (currentPage - 1) * pageSize;
        List<LessonMainDTO> lessons = LessonMainInfoDAO.getInstance().findLessonsWithPagination(tagIds, minAge, maxAge,
                minPrice,
                maxPrice, sortField, sortDirection, sortTwoField,
                sortTwoOrder, offset, pageSize, lessonName, userId, isLibrary); // 傳遞 lessonName

        int totalCount = LessonMainInfoDAO.getInstance().countLessonsWithFilters(tagIds, minAge, maxAge, minPrice,
                maxPrice,
                lessonName, userId, isLibrary);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        JSONObject result = new JSONObject();
        result.put("lessons", lessons);
        result.put("totalCount", totalCount);
        result.put("totalPages", totalPages);
        return result;
    }

    public List<LessonManageDTO> listLessonManageInfo(Long userId) {
        List<LessonManageDTO> output = new ArrayList<LessonManageDTO>();

        return output;
    }

}
