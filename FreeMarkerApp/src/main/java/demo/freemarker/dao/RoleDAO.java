package demo.freemarker.dao;

import demo.freemarker.model.Role;
import itri.sstc.framework.core.database.IntIdBaseDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 使用者 DAO
 *
 * @author schung
 */
public class RoleDAO extends IntIdBaseDAO {

    final static RoleDAO instance = new RoleDAO("DTXSTORE_PU");

    public final static RoleDAO getInstance() {
        return instance;
    }

    private RoleDAO(String puName) {
        super(puName, Role.class);
    }

    @Override
    public String getTableName() {
        return "role";
    }
    
    public Role findByAlias(String param) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Role.findByAlias");
            q.setParameter("name", param);
            return (Role) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Role> listRolesByUserId(Long userId) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Role.listRolesByUserId");
            q.setParameter("userId", userId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
