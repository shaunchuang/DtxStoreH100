/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.model;

import itri.sstc.framework.core.database.StringIdDataEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhush
 */
@Entity
@Table(name = "config_property")
@XmlRootElement
public class ConfigProperty implements StringIdDataEntity, Serializable {
    
    private static final long serialVersionUID = 3433358033025418191L;
    
    @Id
    @Column(name = "id", nullable = false, length = 255) 
    private String id; 
    
    
    @Column(name = "global_value", nullable = false, length = 2000) 
    private String globalValue; // 全域變數的值

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(String globalValue) {
        this.globalValue = globalValue;
    }

}
