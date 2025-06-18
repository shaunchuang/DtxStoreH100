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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhush
 */
@Entity
@Table(name = "\"point_usage_detail\"")
@XmlRootElement
public class PointUsageDetail implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 1265125743938660652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 與 PointUsage 的多對一關係
     */
    @Column(name = "\"usage_id\"", nullable = false)
    private Long usageId;

    /**
     * 與 PointAllocation 的多對一關係
     */
    @Column(name = "\"allocation_id\"", nullable = false)
    private Long allocationId;

    @Column(name = "\"used_point\"", nullable = false)
    private Long usedPoint;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Long getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(Long usedPoint) {
        this.usedPoint = usedPoint;
    }

}
