/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.PointUsageDAO;
import demo.freemarker.model.PointUsage;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class PointUsageAPI implements API {

    private final static PointUsageAPI INSTANCE = new PointUsageAPI();

    public final static PointUsageAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "PointUsageAPI";
    }

    @Override
    public String getVersion() {
        return "20250213.01";
    }

    @Override
    public String getDescription() {
        return "點數使用管理 API";
    }

    public List<PointUsage> listPointUsage() {
        List<PointUsage> output = new ArrayList<>();
        try {
            List<IntIdDataEntity> list = PointUsageDAO.getInstance().findEntities();
            for (IntIdDataEntity entity : list) {
                output.add((PointUsage) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PointUsage getPointUsage(long id) {
        try {
            return (PointUsage) PointUsageDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createPointUsage(PointUsage pointUsage) {
        try {
            PointUsageDAO.getInstance().create(pointUsage);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updatePointUsage(PointUsage pointUsage) {
        try {
            PointUsageDAO.getInstance().edit(pointUsage);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deletePointUsage(long id) {
        try {
            PointUsageDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
