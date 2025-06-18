/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.ConfigPropertyDAO;
import demo.freemarker.model.ConfigProperty;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.StringIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class ConfigPropertyAPI implements API {

    private final static ConfigPropertyAPI INSTANCE = new ConfigPropertyAPI();

    public final static ConfigPropertyAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "ConfigPropertyAPI";
    }

    @Override
    public String getVersion() {
        return "20250212.01";
    }

    @Override
    public String getDescription() {
        return "全域變數管理 API";
    }

    @APIDefine(description = "列出所有設定")
    public final List<ConfigProperty> listConfig() {
        List<ConfigProperty> output = new ArrayList<ConfigProperty>();
        try {
            List<StringIdDataEntity> list = ConfigPropertyDAO.getInstance().findEntities();
            for (StringIdDataEntity entity : list) {
                output.add((ConfigProperty) entity);
            }
            return output;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "由Key取出ConfigProperty物件")
    public ConfigProperty getConfigPropertyByKey(String key) {
        try {
            return (ConfigProperty) ConfigPropertyDAO.getInstance().findEntity(key);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "由Key取出Value數值")
    public String getValueByKey(String key) {
        try {
            ConfigProperty cp = (ConfigProperty) ConfigPropertyDAO.getInstance().findEntity(key);
            return cp.getGlobalValue();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
