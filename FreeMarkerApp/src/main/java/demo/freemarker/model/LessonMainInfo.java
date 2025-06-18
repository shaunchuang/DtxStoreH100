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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhush
 */
@Entity
@Table(name = "lesson_main_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonMainInfo.findByLessonName", query = "SELECT lmi FROM LessonMainInfo lmi WHERE lmi.lessonName = :lessonName"),
    @NamedQuery(name = "LessonMainInfo.listLessonMainInfoByCreator", query = "SELECT lmi FROM LessonMainInfo lmi WHERE lmi.creator = :creator")
})
public class LessonMainInfo implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 3433358033622918191L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"lesson_name\"", nullable = false, length = 255)
    private String lessonName;

    @Column(name = "\"lesson_brief\"", nullable = false, length = 3000)
    private String lessonBrief;

    @Lob
    @Column(name = "\"lesson_description\"", nullable = false)
    private String lessonDescription;

    @Column(name = "\"header_image_url\"", length = 3000)
    private String headerImageUrl;

    @Column(name = "\"release_date\"", nullable = false)
    private Date releaseDate;

    @Column(name = "\"min_age\"", nullable = false)
    private int minAge;

    @Column(name = "\"max_age\"", nullable = false)
    private int maxAge;

    @Column(name = "\"csrr_rate\"", length = 25)
    private String csrrRate;

    @Column(name = "\"price\"", nullable = false, precision = 10, scale = 2)
    private double price;

    @Column(name = "\"execution_id\"", nullable = false, unique = true, length = 255)
    private String executionId;

    @Column(name = "\"lesson_purpose\"", length = 3000)
    private String lessonPurpose;

    @Column(name = "\"usage_restriction\"", length = 3000)
    private String usageRestriction;

    @Column(name = "\"creator\"", nullable = false)
    private Long creator;

    @Column(name = "\"create_time\"", nullable = false)
    private Date createTime;

    @Column(name = "\"modifier\"")
    private Long modifier;

    @Column(name = "\"modify_time\"")
    private Date modifyTime;

    @Column(name = "\"status\"", nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonStatus status = LessonStatus.DRAFT;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonBrief() {
        return lessonBrief;
    }

    public void setLessonBrief(String lessonBrief) {
        this.lessonBrief = lessonBrief;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getCsrrRate() {
        return csrrRate;
    }

    public void setCsrrRate(String csrrRate) {
        this.csrrRate = csrrRate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getLessonPurpose() {
        return lessonPurpose;
    }

    public void setLessonPurpose(String lessonPurpose) {
        this.lessonPurpose = lessonPurpose;
    }

    public String getUsageRestriction() {
        return usageRestriction;
    }

    public void setUsageRestriction(String usageRestriction) {
        this.usageRestriction = usageRestriction;
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

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    public enum LessonStatus {
        DRAFT, REVIEWING, PUBLISHED, DELETED
    }
}
