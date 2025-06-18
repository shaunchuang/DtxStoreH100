package demo.freemarker.model;

import itri.sstc.framework.core.database.IntIdDataEntity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用戶資料
 *
 * @author schung
 */
@Entity
@Table(name = "\"user\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT m FROM User m "),
    @NamedQuery(name = "User.findByUserName", query = "SELECT m FROM User m WHERE m.username LIKE :username"),
    @NamedQuery(name = "User.findByAccount", query = "SELECT m FROM User m WHERE m.account = :account")
})
public class User implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1822843077531976309L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"username\"", nullable = false, length = 50)
    private String username;
    
    @Column(name = "\"account\"", nullable = false, unique = true, length = 50)
    private String account;

    @Column(name = "\"password\"", nullable = false, length = 100)
    private String password;

    @Column(name = "\"email\"", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "\"tel_cell\"", nullable = false, unique = true, length = 100)
    private String telCell;

    @Column(name = "\"steam_id\"", unique = true, length = 50)
    private String steamId;

    @Column(name = "\"current_points\"", nullable = false)
    private Long currentPoints = 0L;

    @Column(name = "\"last_login_date\"")
    @Temporal(TemporalType.DATE)
    private Date lastLoginDate;

    @Column(name = "\"status\"", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.WAIT_FOR_COMPLETED;

    @Column(name = "\"create_time\"", nullable = false)
    private Date createdTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelCell() {
        return telCell;
    }

    public void setTelCell(String telCell) {
        this.telCell = telCell;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public Long getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Long currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    public enum UserStatus {
        APPROVED, WAIT_FOR_COMPLETED
    }

}
