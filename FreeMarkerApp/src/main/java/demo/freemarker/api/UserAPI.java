/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.api;

import demo.freemarker.dao.UserDAO;
import demo.freemarker.model.User;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.database.IntIdDataEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 *
 * @author zhush
 */
public class UserAPI implements API {

    private final static UserAPI INSTANCE = new UserAPI();

    public final static UserAPI getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "UserAPI";
    }

    @Override
    public String getVersion() {
        return "20250211.01";
    }

    @Override
    public String getDescription() {
        return "使用者 API";
    }

    public List<User> listUser() {
        List<User> output = new ArrayList<>();
        List<IntIdDataEntity> list = UserDAO.getInstance().findEntities();
        for (IntIdDataEntity entity : list) {
            output.add((User) entity);
        }
        return output;
    }

    public User getUser(long id) {
        try {
            return (User) UserDAO.getInstance().findEntity(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void createUser(User user) {
        try {
            UserDAO.getInstance().create(user);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateUser(User user) {
        try {
            UserDAO.getInstance().edit(user);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void deleteUser(long id) {
        try {
            UserDAO.getInstance().destroy(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @APIDefine(description = "依name取得資料")
    public List<User> listUserByName(String name) {
        return UserDAO.getInstance().findByUserName(name);
    }

    public User getUserByAccount(String account) {
        return UserDAO.getInstance().findByAccount(account);
    }

    /**
     * 判斷用戶是否為當天首次登入
     * 
     * @param userId 用戶ID
     * @return 如果是當天首次登入返回true，否則返回false
     */
    public boolean isFirstLoginOfDay(Long userId) {
        try {
            User user = getUser(userId);
            if (user == null) {
                return false;
            }
            
            // 如果用戶從未登入過，肯定是首次登入
            if (user.getLastLoginDate() == null) {
                return true;
            }
            
            // 將日期格式化為yyyyMMdd以進行比較
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String todayStr = sdf.format(today);
            String lastLoginStr = sdf.format(user.getLastLoginDate());
            
            // 如果上次登入日期不是今天，則為當天首次登入
            return !todayStr.equals(lastLoginStr);
            
        } catch (Exception ex) {
            System.err.println("Error checking first login of day: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * 更新用戶的最後登入日期為今天
     * 
     * @param userId 用戶ID
     * @return 操作是否成功
     */
    public boolean updateLastLoginDate(Long userId) {
        try {
            User user = getUser(userId);
            if (user == null) {
                return false;
            }
            
            user.setLastLoginDate(new Date());
            updateUser(user);
            return true;
        } catch (Exception ex) {
            System.err.println("Error updating last login date: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public void updateUserPoints(Long userId, Long newPoints) {
        User user = getUser(userId);
        if (user != null) {
            user.setCurrentPoints(newPoints);
            updateUser(user);
        }
    }
    
}
