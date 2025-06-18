package demo.freemarker.dto;

import demo.freemarker.model.Role;
import demo.freemarker.model.User;
import demo.freemarker.model.User.UserStatus;

import java.util.Date;
import java.util.List;

public class UserRoleDTO {
    private Long id;
    private String username;
    private String account;
    private String email;
    private String telCell;
    private String steamId;
    private Long currentPoints;
    private Date createdTime;
    private UserStatus status;
    private List<Role> roles;

    public UserRoleDTO() {}

    public UserRoleDTO(User user, List<Role> roles) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.account = user.getAccount();
        this.email = user.getEmail();
        this.telCell = user.getTelCell();
        this.steamId = user.getSteamId();
        this.currentPoints = user.getCurrentPoints();
        this.status = user.getStatus();
        this.createdTime = user.getCreatedTime();

        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
