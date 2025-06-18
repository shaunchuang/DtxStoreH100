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
@Table(name = "\"lesson_comment\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonComment.findByLessonId", query = "SELECT lc FROM LessonComment lc WHERE lc.lessonId = :lessonId"),
    @NamedQuery(name = "LessonComment.countByLessonId", query = "SELECT COUNT(lc) FROM LessonComment lc WHERE lc.lessonId = :lessonId"),
    @NamedQuery(name = "LessonComment.deleteByLessonId", query = "DELETE FROM LessonComment lc WHERE lc.lessonId = :lessonId"),
    // 添加批量查詢平均評分的 NamedQuery
    @NamedQuery(name = "LessonComment.getAvgRatingsByLessonIds", 
                query = "SELECT lc.lessonId, AVG(lc.rating) FROM LessonComment lc WHERE lc.lessonId IN :lessonIds GROUP BY lc.lessonId"),
    // 添加批量查詢評論數量的 NamedQuery 
    @NamedQuery(name = "LessonComment.getCommentCountsByLessonIds", 
                query = "SELECT lc.lessonId, COUNT(lc) FROM LessonComment lc WHERE lc.lessonId IN :lessonIds GROUP BY lc.lessonId"),
    @NamedQuery(name = "LessonComment.getAvgRatingByLessonId", 
                query = "SELECT AVG(lc.rating) FROM LessonComment lc WHERE lc.lessonId = :lessonId")
})
public class LessonComment implements IntIdDataEntity, Serializable {
    
    private static final long serialVersionUID = 852741963L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Column(name = "\"creator\"", nullable = false)
    private Long creator;

    @Column(name = "\"create_time\"", nullable = false)
    private Date createTime;
    
    @Column(name = "\"modifier\"")
    private Long modifier;
    
    @Column(name = "\"modify_time\"")
    private Date modifyTime;

    @Override
    public Long getId() {
        return id;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
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
    
    
    
}
