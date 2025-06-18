package demo.freemarker.dao;

import demo.freemarker.model.UserRole;
import itri.sstc.framework.core.database.IntIdBaseDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * 使用者 DAO
 *
 * @author schung
 */
public class UserRoleDAO extends IntIdBaseDAO {

    final static UserRoleDAO instance = new UserRoleDAO("DTXSTORE_PU");

    public final static UserRoleDAO getInstance() {
        return instance;
    }

    private UserRoleDAO(String puName) {
        super(puName, UserRole.class);
    }

    @Override
    public String getTableName() {
        return "user_role";
    }

    // 新增 UserRole
    public void createUserRole(Long userId, Long roleId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            UserRole userRole = new UserRole(userId, roleId);
            em.persist(userRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    // 新增 UserRole
    public void createUserRole(UserRole userRole) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(userRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // 查詢 UserRole by UserId
    public List<UserRole> getRolesByUserId(Long userId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findByUserId", UserRole.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 查詢 UserRole by RoleId
    public List<UserRole> getUsersByRoleId(Long roleId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findByRoleId", UserRole.class);
            query.setParameter("roleId", roleId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 查詢所有 UserRole
    public List<UserRole> getAllUserRoles() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<UserRole> query = em.createNamedQuery("UserRole.findAll", UserRole.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 刪除 UserRole by UserId
    public void deleteUserRolesByUserId(Long userId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("UserRole.deleteByUserId").setParameter("userId", userId).executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // 刪除 UserRole by RoleId
    public void deleteUserRolesByRoleId(Long roleId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("UserRole.deleteByRoleId").setParameter("roleId", roleId).executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // 更新 UserRole (改變角色 ID)
    public void updateUserRole(Long userId, Long oldRoleId, Long newRoleId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            UserRole userRole = em.find(UserRole.class, new UserRole(userId, oldRoleId));
            if (userRole != null) {
                userRole.setRoleId(newRoleId);
                em.merge(userRole);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
