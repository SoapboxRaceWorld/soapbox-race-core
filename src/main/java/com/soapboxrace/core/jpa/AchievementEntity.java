package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACHIEVEMENT")
@NamedQueries({
        @NamedQuery(name = "AchievementEntity.findByName", query = "SELECT a FROM AchievementEntity a WHERE a.name = :name"),
        @NamedQuery(name = "AchievementEntity.findAllByCategory", query = "SELECT a FROM AchievementEntity a WHERE a.category = :category"),
        @NamedQuery(name = "AchievementEntity.findAllVisible", query = "SELECT a FROM AchievementEntity a WHERE a.visible = true"),
})
public class AchievementEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean visible;

    @Column(name = "progress_text")
    private String progressText;

    @Column(name = "stat_conversion", columnDefinition = "ENUM('None', 'FromMetersToDistance', 'FromMillisecondsToMinutes')")
    private String statConversion;

    @Column(name = "category")
    private String category;

    @Column(name = "auto_update")
    private Boolean autoUpdate;

    @Column(name = "name")
    private String name;

    @Column(name = "update_trigger", columnDefinition = "TEXT")
    private String updateTrigger;

    @Column(name = "update_value", columnDefinition = "TEXT")
    private String updateValue;

    @OneToMany(targetEntity = AchievementRankEntity.class, mappedBy = "achievementEntity", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<AchievementRankEntity> ranks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public String getStatConversion() {
        return statConversion;
    }

    public void setStatConversion(String statConversion) {
        this.statConversion = statConversion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTrigger() {
        return updateTrigger;
    }

    public void setUpdateTrigger(String updateTrigger) {
        this.updateTrigger = updateTrigger;
    }

    public String getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(String updateValue) {
        this.updateValue = updateValue;
    }

    public List<AchievementRankEntity> getRanks() {
        return ranks;
    }

    public void setRanks(List<AchievementRankEntity> ranks) {
        this.ranks = ranks;
    }
}
