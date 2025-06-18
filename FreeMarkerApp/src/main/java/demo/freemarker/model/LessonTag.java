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
@Table(name = "\"lesson_tag\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonTag.findByLessonId", query = "SELECT lt FROM LessonTag lt WHERE lt.lessonId = :lessonId"),
    @NamedQuery(name = "LessonTag.findByLessonIds", query = "SELECT lt FROM LessonTag lt WHERE lt.lessonId IN :lessonIds"),
    @NamedQuery(name = "LessonTag.deleteByLessonId", query = "DELETE FROM LessonTag lt WHERE lt.lessonId = :lessonId")
})
public class LessonTag implements IntIdDataEntity, Serializable {
    
    private static final long serialVersionUID = 433358033622918191L;

    public LessonTag() {
        
    }
    
    public LessonTag(Long lessonId, Long tagId, Long creator, Date createTime) {
        this.lessonId = lessonId;
        this.tagId = tagId;
        this.creator = creator;
        this.createTime = createTime;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

    @Column(name = "\"tag_id\"", nullable = false)
    private Long tagId;

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

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
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
