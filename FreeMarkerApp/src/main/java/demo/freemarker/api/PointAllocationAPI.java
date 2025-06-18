/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.PointAllocationDAO;
import demo.freemarker.model.PointAllocation;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class PointAllocationAPI implements API {

    private final static PointAllocationAPI INSTANCE = new PointAllocationAPI();

    public final static PointAllocationAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "PointAllocationAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "點數分配管理 API";
    }

    public List<PointAllocation> listPointAllocation() {
        List<PointAllocation> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = PointAllocationDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((PointAllocation) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PointAllocation getPointAllocation(long id) {
        try {
            return (PointAllocation) PointAllocationDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createPointAllocation(PointAllocation pointAllocation) {
        try {
            PointAllocationDAO.getInstance().create(pointAllocation);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updatePointAllocation(PointAllocation pointAllocation) {
        try {
            PointAllocationDAO.getInstance().edit(pointAllocation);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deletePointAllocation(long id) {
        try {
            PointAllocationDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 獲取用戶當前可用積分餘額
     * @param userId 用戶ID
     * @return 總可用積分
     */
    public Long getUserAvailablePoints(Long userId) {
        try {
            return PointAllocationDAO.getInstance().getUserAvailablePoints(userId);
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching available points for user " + userId + ": " + ex.getMessage());
        }
    }

    public List<PointAllocation> listActivePointAllocations(Long userId) {
        try {
            return PointAllocationDAO.getInstance().findActivePointAllocations();
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching active point allocations for user " + userId + ": " + ex.getMessage());
        }
    }

    public void batchUpdate(List<PointAllocation> allocations) {
        try {
            PointAllocationDAO.getInstance().batchUpdate(allocations);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating point allocations: " + ex.getMessage());
        }
    }
}
