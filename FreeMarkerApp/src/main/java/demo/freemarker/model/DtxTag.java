/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.model;

import itri.sstc.framework.core.database.IntIdDataEntity;
import java.io.Serializable;
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
@Table(name = "\"dtx_tag_old\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DtxTag.findAll", query = "SELECT tag FROM DtxTag tag"),
    @NamedQuery(name = "DtxTag.findById", query = "SELECT tag FROM DtxTag tag WHERE tag.id = :id"),
    @NamedQuery(name = "DtxTag.findByTagName", query = "SELECT tag FROM DtxTag tag WHERE tag.tagName = :tagName"),
    @NamedQuery(name = "DtxTag.findByTagType", query = "SELECT tag FROM DtxTag tag WHERE tag.tagType = :tagType"),
    @NamedQuery(name = "DtxTag.findByIds", query = "SELECT tag FROM DtxTag tag WHERE tag.id IN :ids"),
    @NamedQuery(name = "DtxTag.findByLessonId", query = "SELECT tag FROM DtxTag tag WHERE tag.id IN (SELECT lt.tagId FROM LessonTag lt WHERE lt.lessonId = :lessonId)"),
    @NamedQuery(name = "DtxTag.findByLessonIdAndTag", query = "SELECT tag FROM DtxTag tag WHERE tag.id IN (SELECT lt.tagId FROM LessonTag lt WHERE lt.lessonId = :lessonId) AND tag.tagType = :tagType")
})
public class DtxTag implements IntIdDataEntity, Serializable {
    
    private static final long serialVersionUID = 1165305903938660652L;

    public DtxTag() {
    }
    
    public DtxTag(int tagType, String tagName, String tagDescription) {
        this.tagType = tagType;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"", unique = true, nullable = false)
    @DBFieldDescription(name = "流水號(PK)") 
    private Long id;

    @Column(name = "\"tag_type\"", nullable = false)
    @DBFieldDescription(name = "標籤類型")
    private int tagType;

    @Column(name = "\"tag_name\"", nullable = false)
    @DBFieldDescription(name = "標籤名稱")
    private String tagName;

    @Column(name = "\"tag_description\"", nullable = true)
    @DBFieldDescription(name = "說明")
    private String tagDescription;

    // Getter 與 Setter 方法
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
}
