/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "TREASURE_HUNT_CONFIG")
@NamedQueries({
        @NamedQuery(name = "TreasureHuntConfigEntity.findConfigForStreak", query = "SELECT obj FROM " +
                "TreasureHuntConfigEntity obj WHERE obj.streak <= :streak ORDER BY obj.streak DESC")
})
public class TreasureHuntConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "streak")
    private Integer streak;

    @ManyToOne(targetEntity = RewardTableEntity.class)
    @JoinColumn(referencedColumnName = "ID", name = "reward_table_id", foreignKey = @ForeignKey(name = "FK_TREASURE_HUNT_CONFIG_REWARD_TABLE_reward_table_id"))
    private RewardTableEntity rewardTableEntity;

    @Column(name = "base_rep")
    private Float baseRep;

    @Column(name = "base_cash")
    private Float baseCash;

    @Column(name = "rep_multiplier")
    private Float repMultiplier;

    @Column(name = "cash_multiplier")
    private Float cashMultiplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStreak() {
        return streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public RewardTableEntity getRewardTableEntity() {
        return rewardTableEntity;
    }

    public void setRewardTableEntity(RewardTableEntity rewardTableEntity) {
        this.rewardTableEntity = rewardTableEntity;
    }

    public Float getBaseRep() {
        return baseRep;
    }

    public void setBaseRep(Float baseRep) {
        this.baseRep = baseRep;
    }

    public Float getBaseCash() {
        return baseCash;
    }

    public void setBaseCash(Float baseCash) {
        this.baseCash = baseCash;
    }

    public Float getRepMultiplier() {
        return repMultiplier;
    }

    public void setRepMultiplier(Float repMultiplier) {
        this.repMultiplier = repMultiplier;
    }

    public Float getCashMultiplier() {
        return cashMultiplier;
    }

    public void setCashMultiplier(Float cashMultiplier) {
        this.cashMultiplier = cashMultiplier;
    }
}
