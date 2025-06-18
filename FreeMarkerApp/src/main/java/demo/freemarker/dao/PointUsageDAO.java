/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.PointUsage;
import itri.sstc.framework.core.database.IntIdBaseDAO;

/**
 *
 * @author zhush
 */
public class PointUsageDAO extends IntIdBaseDAO {

    final static PointUsageDAO instance = new PointUsageDAO("DTXSTORE_PU");

    public final static PointUsageDAO getInstance() {
        return instance;
    }

    private PointUsageDAO(String puName) {
        super(puName, PointUsage.class);
    }

    @Override
    public String getTableName() {
        return "point_usage";
    }
}
