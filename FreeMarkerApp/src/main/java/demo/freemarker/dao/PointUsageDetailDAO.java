/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.PointUsageDetail;
import itri.sstc.framework.core.database.IntIdBaseDAO;

/**
 *
 * @author zhush
 */
public class PointUsageDetailDAO extends IntIdBaseDAO {

    final static PointUsageDetailDAO instance = new PointUsageDetailDAO("DTXSTORE_PU");

    public final static PointUsageDetailDAO getInstance() {
        return instance;
    }

    private PointUsageDetailDAO(String puName) {
        super(puName, PointUsageDetail.class);
    }

    @Override
    public String getTableName() {
        return "point_usage_detail";
    }
}
