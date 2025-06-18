/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.PointUsageDetailDAO;
import demo.freemarker.model.PointUsageDetail;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class PointUsageDetailAPI implements API {

    private final static PointUsageDetailAPI INSTANCE = new PointUsageDetailAPI();

    public final static PointUsageDetailAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "PointUsageDetailAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "點數使用明細管理 API";
    }

    public List<PointUsageDetail> listPointUsageDetail() {
        try {
            List<PointUsageDetail> output = new ArrayList<>();
            List<IntIdDataEntity> list = PointUsageDetailDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((PointUsageDetail) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PointUsageDetail getPointUsageDetail(long id) {
        try {
            return (PointUsageDetail) PointUsageDetailDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createPointUsageDetail(PointUsageDetail pointUsageDetail) {
        try {
            PointUsageDetailDAO.getInstance().create(pointUsageDetail);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void batchCreate(List<PointUsageDetail> pointUsageDetails) {
        try {
            PointUsageDetailDAO.getInstance().createAll(pointUsageDetails);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updatePointUsageDetail(PointUsageDetail pointUsageDetail) {
        try {
            PointUsageDetailDAO.getInstance().edit(pointUsageDetail);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deletePointUsageDetail(long id) {
        try {
            PointUsageDetailDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
