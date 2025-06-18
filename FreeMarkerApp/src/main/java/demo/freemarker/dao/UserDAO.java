package demo.freemarker.dao;
 
import demo.freemarker.model.User;
import itri.sstc.framework.core.database.IntIdBaseDAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 使用者 DAO
 *
 * @author schung
 */
public class UserDAO extends IntIdBaseDAO {
    
    final static UserDAO instance = new UserDAO("DTXSTORE_PU");

    public final static UserDAO getInstance() {
        return instance;
    }

    private UserDAO(String puName) {
        super(puName, User.class);
    }

    @Override
    public String getTableName() {
        return "User";
    }
    
    public List<User> listUser(String param) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findAll");
            q.setParameter("name", param);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<User>();
        } finally {
            em.close();
        }
    }

    public List<User> findByUserName(String param) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByUserName");
            q.setParameter("name", param);
            return q.getResultList();
        } catch (Exception ex) {
            return new ArrayList<User>();
        } finally {
            em.close();
        }
    }
    
    public User findByAccount(String param) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("User.findByAccount");
            q.setParameter("account", param);
            return (User) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        } finally {
            em.close();
        }
    }

}
