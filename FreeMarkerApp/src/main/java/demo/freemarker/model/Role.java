package demo.freemarker.model;

import itri.sstc.framework.core.database.IntIdDataEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 角色權限:角色類型
 */
@Entity
@Table(name = "\"role\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findByAlias", query = "SELECT r FROM Role r WHERE r.alias =  :alias"),
    @NamedQuery(name = "Role.listRolesByUserId", query = "SELECT r FROM Role r INNER JOIN UserRole ur ON r.id = ur.roleId WHERE ur.userId = :userId")
})
public class Role implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 3433358033622918691L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"alias\"", nullable = false, unique = true, length = 50)
    private String alias;

    @Column(name = "\"description\"", length = 255)
    private String description;

    public Role() {
    }
 
    public Role(Long id) {
        super();
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 41;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
