/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.model;

import itri.sstc.framework.core.database.IntIdDataEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhush
 */
@Entity
@Table(name = "\"lesson_system_requirement\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LessonSystemRequirement.findByLessonId", query = "SELECT lsr FROM LessonSystemRequirement lsr WHERE lsr.lessonId = :lessonId"),
    @NamedQuery(name = "LessonSystemRequirement.deleteByLessonId", query = "DELETE FROM LessonSystemRequirement lsr WHERE lsr.lessonId = :lessonId")
})
public class LessonSystemRequirement implements IntIdDataEntity, Serializable {

    private static final long serialVersionUID = 433358233626918191L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "platform", length = 1000)
    private String platform;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_type", nullable = false, length = 50)
    private RequirementType requirementType = RequirementType.MINIMUM;

    @Column(name = "operating_system", length = 1000)
    private String operatingSystem;

    @Column(name = "processor", length = 1000)
    private String processor;

    @Column(name = "memory", length = 1000)
    private String memory;

    @Column(name = "graphics_card", length = 1000)
    private String graphicsCard;

    @Column(name = "directx_version", length = 1000)
    private String directxVersion;

    @Column(name = "sound_card", length = 1000)
    private String soundCard;

    @Column(name = "storage_space", length = 1000)
    private String storageSpace;

    @Column(name = "network", length = 1000)
    private String network;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "hardware_devices", length = 1000)
    private String hardwareDevices;

    public enum RequirementType {
        MINIMUM, RECOMMENDED
    }

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public RequirementType getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(RequirementType requirementType) {
        this.requirementType = requirementType;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDirectxVersion() {
        return directxVersion;
    }

    public void setDirectxVersion(String directxVersion) {
        this.directxVersion = directxVersion;
    }

    public String getSoundCard() {
        return soundCard;
    }

    public void setSoundCard(String soundCard) {
        this.soundCard = soundCard;
    }

    public String getStorageSpace() {
        return storageSpace;
    }

    public void setStorageSpace(String storageSpace) {
        this.storageSpace = storageSpace;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHardwareDevices() {
        return hardwareDevices;
    }

    public void setHardwareDevices(String hardwareDevices) {
        this.hardwareDevices = hardwareDevices;
    }

}
