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
@Table(name = "lesson_video")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonVideo.findByLessonId", query = "SELECT lv FROM LessonVideo lv WHERE lv.lessonId = :lessonId"),
    @NamedQuery(name = "LessonVideo.deleteByLessonId", query = "DELETE FROM LessonVideo lv WHERE lv.lessonId = :lessonId")
})
public class LessonVideo implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 433358033622918191L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

    @Column(name = "\"file_path\"", nullable = false, length = 2000)
    private String filePath;

    @Column(name = "\"description\"")
    private String description;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
