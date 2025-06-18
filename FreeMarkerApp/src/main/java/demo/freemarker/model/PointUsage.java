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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhush
 */

@Entity
@Table(name = "\"point_usage\"")
@XmlRootElement
public class PointUsage implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1265125853938660652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "\"user_id\"", nullable = false)
    private Long userId;

    @Column(name = "\"usage_type\"", length = 50, nullable = false)
    private String usageType;   // 例: purchase_course, redeem_reward

    @Column(name = "\"description\"", length = 255)
    private String description;

    @Column(name = "\"used_at\"", nullable = false)
    private Date usedAt;

    @Column(name = "\"total_used\"", nullable = false)
    private Long totalUsed;

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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Date usedAt) {
        this.usedAt = usedAt;
    }

    public Long getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Long totalUsed) {
        this.totalUsed = totalUsed;
    }
}
