/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.UserRoleDAO;
import demo.freemarker.model.UserRole;
import itri.sstc.framework.core.api.API;
import java.util.List;

/**
 *
 * @author zhush
 */
public class UserRoleAPI implements API {

    private final static UserRoleAPI INSTANCE = new UserRoleAPI();

    public final static UserRoleAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "UserRoleAPI";
    }

    @Override
    public String getVersion() {
        return "20250211.01";
    }

    @Override
    public String getDescription() {
        return "使用者角色 API";
    }

    @APIDefine(description = "新增使用者角色Map")
    public void createUserRole(UserRole userRole) {
        try {
            UserRoleDAO.getInstance().createUserRole(userRole);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @APIDefine(description = "新增使用者角色Map")
    public void createUserRole(Long userId, Long roleId) {
        try {
            UserRoleDAO.getInstance().createUserRole(userId, roleId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public List<UserRole> listUserRolesByUserId(Long userId) {
        try {
           return UserRoleDAO.getInstance().getRolesByUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
