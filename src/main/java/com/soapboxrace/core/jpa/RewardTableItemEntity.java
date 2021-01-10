/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "REWARD_TABLE_ITEM")
public class RewardTableItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String script;

    @ManyToOne(targetEntity = RewardTableEntity.class, optional = false)
    @JoinColumn(name = "rewardTableEntity_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_REWARD_TABLE_ITEM_REWARD_TABLE_rewardTableEntity_ID"))
    private RewardTableEntity rewardTableEntity;

    @Column
    private Double dropWeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public RewardTableEntity getRewardTableEntity() {
        return rewardTableEntity;
    }

    public void setRewardTableEntity(RewardTableEntity rewardTableEntity) {
        this.rewardTableEntity = rewardTableEntity;
    }

    public Double getDropWeight() {
        return dropWeight;
    }

    public void setDropWeight(Double dropWeight) {
        this.dropWeight = dropWeight;
    }
}
