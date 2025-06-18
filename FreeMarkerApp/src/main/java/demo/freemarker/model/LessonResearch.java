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
@Table(name = "\"lesson_research\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonResearch.findByLessonId", query = "SELECT lr FROM LessonResearch lr WHERE lr.lessonId = :lessonId"),
    @NamedQuery(name = "LessonResearch.deleteByLessonId", query = "DELETE FROM LessonResearch lr WHERE lr.lessonId = :lessonId")
})
public class LessonResearch implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1265305903938660652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

    @Column(name = "\"title\"", nullable = false, length = 4000)
    private String title;

    @Column(name = "\"research_type\"", nullable = false)
    private int researchType;

    @Column(name = "\"url\"", nullable = false, length = 4000)
    private String url;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResearchType() {
        return researchType;
    }

    public void setResearchType(int researchType) {
        this.researchType = researchType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
