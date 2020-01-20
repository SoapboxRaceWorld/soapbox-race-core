/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ACHIEVEMENT_REWARD")
@NamedQueries({
        @NamedQuery(name = "AchievementRewardEntity.findByDescription", query = "SELECT obj FROM " +
                "AchievementRewardEntity obj WHERE obj.internalRewardDescription = :description")
})
public class AchievementRewardEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rewardScript;

    @Column(name = "reward_description")
    private String rewardDescription;

    @Column(name = "internal_reward_description")
    private String internalRewardDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRewardScript() {
        return rewardScript;
    }

    public void setRewardScript(String rewardScript) {
        this.rewardScript = rewardScript;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public String getInternalRewardDescription() {
        return internalRewardDescription;
    }

    public void setInternalRewardDescription(String internalRewardDescription) {
        this.internalRewardDescription = internalRewardDescription;
    }
}
