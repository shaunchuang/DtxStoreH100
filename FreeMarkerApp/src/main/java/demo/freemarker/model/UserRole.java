 
package demo.freemarker.model;
 
import itri.sstc.framework.core.database.BaseEntity.DBFieldDescription;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "\"user_role\"")
@XmlRootElement
@IdClass(UserRoleId.class)
@NamedQueries({
    @NamedQuery(name = "UserRole.findByUserId", query = "SELECT ur FROM UserRole ur WHERE ur.userId = :userId"),
    @NamedQuery(name = "UserRole.findByRoleId", query = "SELECT ur FROM UserRole ur WHERE ur.roleId = :roleId"),
    @NamedQuery(name = "UserRole.findAll", query = "SELECT ur FROM UserRole ur"),
    @NamedQuery(name = "UserRole.deleteByUserId", query = "DELETE FROM UserRole ur WHERE ur.userId = :userId"),
    @NamedQuery(name = "UserRole.deleteByRoleId", query = "DELETE FROM UserRole ur WHERE ur.roleId = :roleId")
})
public class UserRole implements Serializable {  
    private static final long serialVersionUID = 3047122696690398123L;
    
    @Id
    @Column(name = "\"user_id\"", nullable = false)
    @DBFieldDescription(name = "userId") 
    private Long userId;

    @Id
    @Column(name = "\"role_id\"", nullable = false)
    @DBFieldDescription(name = "roleId")
    private Long roleId;
 
    public UserRole() { 
    }
 
    public UserRole(Long userId, Long roleId) {
        super();
        this.userId = userId;
        this.roleId = roleId;
    } 

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
