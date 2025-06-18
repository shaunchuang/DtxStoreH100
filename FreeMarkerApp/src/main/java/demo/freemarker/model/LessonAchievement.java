/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.model;

import itri.sstc.framework.core.database.IntIdDataEntity;
import java.io.Serializable;
import java.util.Date;
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
 *
 * @author zhush
 */
@Entity                      
@Table(name = "\"lesson_achievement\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonAchievement.findByLessonId", query = "SELECT la FROM LessonAchievement la WHERE la.lessonId = :lessonId"),
    @NamedQuery(name = "LessonAchievement.findByApiName", query = "SELECT la FROM LessonAchievement la WHERE la.apiName = :apiName"),
    @NamedQuery(name = "LessonAchievement.countByLessonId", query = "SELECT COUNT(la) FROM LessonAchievement la WHERE la.lessonId = :lessonId"),
    @NamedQuery(name = "LessonAchievement.deleteByLessonId", query = "DELETE FROM LessonAchievement la WHERE la.lessonId = :lessonId")
})
public class LessonAchievement implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 3433358033022918191L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

    @Column(name = "\"api_name\"", nullable = false, unique = true, length = 255)
    private String apiName;

    @Column(name = "\"display_name\"", nullable = false, length = 255)
    private String displayName;

    @Column(name = "\"description\"", length = 2083)
    private String description;

    @Column(name = "\"unlock_value\"")
    private Double unlockValue = 0.0;

    @Column(name = "\"hidden\"")
    private boolean hidden = false;

    @Column(name = "\"unlocked_icon_url\"", length = 2083)
    private String unlockedIconUrl;

    @Column(name = "\"locked_icon_url\"", length = 2083)
    private String lockedIconUrl;

    @Column(name = "\"creator\"", nullable = false)
    private Long creator;

    @Column(name = "\"create_time\"", nullable = false)
    private Date createTime;
    

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnlockValue() {
        return unlockValue;
    }

    public void setUnlockValue(Double unlockValue) {
        this.unlockValue = unlockValue;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getUnlockedIconUrl() {
        return unlockedIconUrl;
    }

    public void setUnlockedIconUrl(String unlockedIconUrl) {
        this.unlockedIconUrl = unlockedIconUrl;
    }

    public String getLockedIconUrl() {
        return lockedIconUrl;
    }

    public void setLockedIconUrl(String lockedIconUrl) {
        this.lockedIconUrl = lockedIconUrl;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
