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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "\"lesson_statistics\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonStatistics.findByLessonId", query = "SELECT ls FROM LessonStatistics ls WHERE ls.lessonId = :lessonId"),
    @NamedQuery(name = "LessonStatistics.findByApiName", query = "SELECT ls FROM LessonStatistics ls WHERE ls.apiName = :apiName"),
    @NamedQuery(name = "LessonStatistics.countByLessonId", query = "SELECT COUNT(ls) FROM LessonStatistics ls WHERE ls.lessonId = :lessonId"),
    @NamedQuery(name = "LessonStatistics.findByLessonIdAndApiName", query = "SELECT ls FROM LessonStatistics ls WHERE ls.lessonId = :lessonId AND ls.apiName = :apiName"),
    @NamedQuery(name = "LessonStatistics.deleteByLessonId", query = "DELETE FROM LessonStatistics ls WHERE ls.lessonId = :lessonId")
})
public class LessonStatistics implements IntIdDataEntity, Serializable {
    
    private static final long serialVersionUID = 433358033982918191L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

    @Column(name = "\"api_name\"", nullable = false, unique = true, length = 255)
    private String apiName;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"stat_type\"", nullable = true, length = 50)
    private StatType statType;

    @Column(name = "\"display_name\"", nullable = true, length = 1000)
    private String displayName = "";

    @Column(name = "\"description\"")
    private String description;

    @Column(name = "\"stat_value\"", columnDefinition = "FLOAT DEFAULT 0")
    private Double statValue;

    @Column(name = "\"is_increased_only\"", columnDefinition = "BIT DEFAULT 0")
    private boolean isIncreasedOnly;

    @Column(name = "\"max_change\"")
    private Double maxChange;

    @Column(name = "\"min_value\"")
    private Double minValue;

    @Column(name = "\"max_value\"")
    private Double maxValue;

    @Column(name = "\"default_value\"", columnDefinition = "FLOAT DEFAULT 0")
    private Double defaultValue;

    @Column(name = "\"aggregate_enabled\"", columnDefinition = "BIT DEFAULT 0")
    private boolean aggregateEnabled;

    @Column(name = "\"avgrate_interval\"")
    private Integer avgrateInterval;
    
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

    public StatType getStatType() {
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
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

    public Double getStatValue() {
        return statValue;
    }

    public void setStatValue(Double statValue) {
        this.statValue = statValue;
    }

    public boolean isIsIncreasedOnly() {
        return isIncreasedOnly;
    }

    public void setIsIncreasedOnly(boolean isIncreasedOnly) {
        this.isIncreasedOnly = isIncreasedOnly;
    }

    public Double getMaxChange() {
        return maxChange;
    }

    public void setMaxChange(Double maxChange) {
        this.maxChange = maxChange;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isAggregateEnabled() {
        return aggregateEnabled;
    }

    public void setAggregateEnabled(boolean aggregateEnabled) {
        this.aggregateEnabled = aggregateEnabled;
    }

    public Integer getAvgrateInterval() {
        return avgrateInterval;
    }

    public void setAvgrateInterval(Integer avgrateInterval) {
        this.avgrateInterval = avgrateInterval;
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
    
    
    
    
    public enum StatType {
        INT, FLOAT, AVGRATE
    }
}
