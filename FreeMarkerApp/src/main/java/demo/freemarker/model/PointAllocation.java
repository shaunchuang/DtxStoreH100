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
@Table(name = "\"point_allocation\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PointAllocation.getUserAvailablePoints", query = "SELECT COALESCE(SUM(p.remainingPoints), 0) FROM PointAllocation p WHERE p.userId = :userId AND (p.expireAt IS NULL OR p.expireAt > CURRENT_DATE)"),
    @NamedQuery(name = "PointAllocation.listActivePointAllocations", query = "SELECT p FROM PointAllocation p WHERE p.expireAt IS NULL OR p.expireAt > CURRENT_DATE")
})
public class PointAllocation implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1265127903938660652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "points", nullable = false)
    private Long points;

    @Column(name = "source_type", length = 50, nullable = false)
    private String sourceType; 

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "allocated_at", nullable = false)
    private Date allocatedAt;

    @Column(name = "expire_at")
    private Date expireAt;

    @Column(name = "remaining_points", nullable = false)
    private Long remainingPoints;

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

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(Date allocatedAt) {
        this.allocatedAt = allocatedAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Long getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(Long remainingPoints) {
        this.remainingPoints = remainingPoints;
    }
}
