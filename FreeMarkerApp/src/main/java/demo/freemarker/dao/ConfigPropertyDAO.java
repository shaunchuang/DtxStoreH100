/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.dao;

import demo.freemarker.model.ConfigProperty;
import itri.sstc.framework.core.database.StringIdBaseDAO;

/**
 *
 * @author zhush
 */
public class ConfigPropertyDAO extends StringIdBaseDAO {

    final static ConfigPropertyDAO instance = new ConfigPropertyDAO("DTXSTORE_PU");

    public final static ConfigPropertyDAO getInstance() {
        return instance;
    }

    private ConfigPropertyDAO(String puName) {
        super(puName, ConfigProperty.class);
    }

    @Override
    public String getTableName() {
        return "config_property";
    }
}
