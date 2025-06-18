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
@Table(name = "\"user_favorite_lesson\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserFavoriteLesson.findByUserId", query = "SELECT ufl FROM UserFavoriteLesson ufl WHERE ufl.userId = :userId"),
    @NamedQuery(name = "UserFavoriteLesson.findByUserIdAndLessonId", query = "SELECT ufl FROM UserFavoriteLesson ufl WHERE ufl.userId = :userId AND ufl.lessonId = :lessonId"),
})
public class UserFavoriteLesson implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1268735903938660652L;

    public UserFavoriteLesson() {
    }

    public UserFavoriteLesson(Long userId, Long lessonId, Date createTime) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.createTime = createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "\"user_id\"", nullable = false)
    private Long userId;

    @Column(name = "\"lesson_id\"", nullable = false)
    private Long lessonId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
