/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.model.Role;
import itri.sstc.framework.core.api.API;
import demo.freemarker.dao.RoleDAO;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhush
 */
public class RoleAPI implements API {

    private final static RoleAPI INSTANCE = new RoleAPI();

    public final static RoleAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "RoleAPI";
    }

    @Override
    public String getVersion() {
        return "20250211.01";
    }

    @Override
    public String getDescription() {
        return "角色 API";
    }

    public List<Role> listRoles() {
        List<Role> output = new ArrayList<>();
        List<IntIdDataEntity> list = RoleDAO.getInstance().findEntities();
        for (IntIdDataEntity entity : list) {
            output.add((Role) entity);
        }
        return output;
    }

    public Role getRole(long id) {
        return (Role) RoleDAO.getInstance().findEntity(id);
    }

    public void createRole(Role role) {
        RoleDAO.getInstance().create(role);
    }

    public void updateRole(Role role) {
        try {
            RoleDAO.getInstance().edit(role);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteRole(long id) {
        try {
            RoleDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Role getRoleByAlias(String alias) {
        try {
            return RoleDAO.getInstance().findByAlias(alias);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Role> listRolesByUserId(Long userId) {
        try {
            return RoleDAO.getInstance().listRolesByUserId(userId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
