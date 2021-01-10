/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ACHIEVEMENT_RANK")
@NamedQueries({
        @NamedQuery(name = "AchievementRankEntity.updateRarity", query = "UPDATE AchievementRankEntity obj SET obj.rarity=:rarity WHERE obj.id=:id")
})
public class AchievementRankEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = AchievementEntity.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "achievement_id", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ACHIEVEMENT_RANK_ACHIEVEMENT_achievement_id"))
    private AchievementEntity achievementEntity;

    @Column
    private Integer points;

    @Column(name = "`rank`")
    private Integer rank;

    @Column
    private Float rarity;

    @Column(name = "reward_description")
    private String rewardDescription;

    @Column(name = "reward_type")
    private String rewardType;

    @Column(name = "reward_visual_style")
    private String rewardVisualStyle;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AchievementEntity getAchievementEntity() {
        return achievementEntity;
    }

    public void setAchievementEntity(AchievementEntity achievementEntity) {
        this.achievementEntity = achievementEntity;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Float getRarity() {
        return rarity;
    }

    public void setRarity(Float rarity) {
        this.rarity = rarity;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardVisualStyle() {
        return rewardVisualStyle;
    }

    public void setRewardVisualStyle(String rewardVisualStyle) {
        this.rewardVisualStyle = rewardVisualStyle;
    }

    public Integer getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Integer thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public boolean isRare() {
        return this.getRarity() <= 0.05f;
    }
}
