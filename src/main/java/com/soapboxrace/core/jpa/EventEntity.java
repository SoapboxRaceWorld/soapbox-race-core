/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "EVENT")
@NamedQueries({ //
        @NamedQuery(name = "EventEntity.findAll", query = "SELECT obj FROM EventEntity obj"), //
        @NamedQuery(name = "EventEntity.findByLevel", query = "SELECT obj FROM EventEntity obj WHERE :level >= obj.minLevel AND obj.isEnabled = true AND :level <= obj.maxLevel"), //
        @NamedQuery(name = "EventEntity.findAllRotatable", query = "SELECT obj FROM EventEntity obj WHERE obj" +
                ".isRotationEnabled=true")
})
public class EventEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int eventModeId;
    private int minLevel;
    private int maxLevel;
    private int carClassHash;
    private int minCarClassRating;
    private int maxCarClassRating;
    private int maxPlayers;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1", nullable = false)
    private boolean isEnabled;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isLocked;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    private boolean isRotationEnabled;
    private String name;
    private int baseRepReward;
    private float levelRepRewardMultiplier;
    private float finalRepRewardMultiplier;
    private float perfectStartRepMultiplier;
    private float topSpeedRepMultiplier;
    private float rank1RepMultiplier;
    private float rank2RepMultiplier;
    private float rank3RepMultiplier;
    private float rank4RepMultiplier;
    private float rank5RepMultiplier;
    private float rank6RepMultiplier;
    private float rank7RepMultiplier;
    private float rank8RepMultiplier;
    private int baseCashReward;
    private float levelCashRewardMultiplier;
    private float finalCashRewardMultiplier;
    private float perfectStartCashMultiplier;
    private float topSpeedCashMultiplier;
    private float rank1CashMultiplier;
    private float rank2CashMultiplier;
    private float rank3CashMultiplier;
    private float rank4CashMultiplier;
    private float rank5CashMultiplier;
    private float rank6CashMultiplier;
    private float rank7CashMultiplier;
    private float rank8CashMultiplier;
    private float minTopSpeedTrigger;
    private long legitTime;
    private float trackLength;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank1_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank1;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank2_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank2;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank3_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank3;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank4_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank4;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank5_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank5;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank6_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank6;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank7_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank7;

    @OneToOne(targetEntity = RewardTableEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "rewardTable_rank8_id", referencedColumnName = "ID")
    private RewardTableEntity rewardTableRank8;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventModeId() {
        return eventModeId;
    }

    public void setEventModeId(int eventModeId) {
        this.eventModeId = eventModeId;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinCarClassRating() {
        return minCarClassRating;
    }

    public void setMinCarClassRating(int minCarClassRating) {
        this.minCarClassRating = minCarClassRating;
    }

    public int getMaxCarClassRating() {
        return maxCarClassRating;
    }

    public void setMaxCarClassRating(int maxCarClassRating) {
        this.maxCarClassRating = maxCarClassRating;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public long getLegitTime() {
        return legitTime;
    }

    public void setLegitTime(long legitTime) {
        this.legitTime = legitTime;
    }

    public int getBaseRepReward() {
        return baseRepReward;
    }

    public void setBaseRepReward(int baseRepReward) {
        this.baseRepReward = baseRepReward;
    }

    public float getLevelRepRewardMultiplier() {
        return levelRepRewardMultiplier;
    }

    public void setLevelRepRewardMultiplier(float levelRepRewardMultiplier) {
        this.levelRepRewardMultiplier = levelRepRewardMultiplier;
    }

    public float getFinalRepRewardMultiplier() {
        return finalRepRewardMultiplier;
    }

    public void setFinalRepRewardMultiplier(float finalRepRewardMultiplier) {
        this.finalRepRewardMultiplier = finalRepRewardMultiplier;
    }

    public float getPerfectStartRepMultiplier() {
        return perfectStartRepMultiplier;
    }

    public void setPerfectStartRepMultiplier(float perfectStartRepMultiplier) {
        this.perfectStartRepMultiplier = perfectStartRepMultiplier;
    }

    public float getTopSpeedRepMultiplier() {
        return topSpeedRepMultiplier;
    }

    public void setTopSpeedRepMultiplier(float topSpeedRepMultiplier) {
        this.topSpeedRepMultiplier = topSpeedRepMultiplier;
    }

    public float getRank1RepMultiplier() {
        return rank1RepMultiplier;
    }

    public void setRank1RepMultiplier(float rank1RepMultiplier) {
        this.rank1RepMultiplier = rank1RepMultiplier;
    }

    public float getRank2RepMultiplier() {
        return rank2RepMultiplier;
    }

    public void setRank2RepMultiplier(float rank2RepMultiplier) {
        this.rank2RepMultiplier = rank2RepMultiplier;
    }

    public float getRank3RepMultiplier() {
        return rank3RepMultiplier;
    }

    public void setRank3RepMultiplier(float rank3RepMultiplier) {
        this.rank3RepMultiplier = rank3RepMultiplier;
    }

    public float getRank4RepMultiplier() {
        return rank4RepMultiplier;
    }

    public void setRank4RepMultiplier(float rank4RepMultiplier) {
        this.rank4RepMultiplier = rank4RepMultiplier;
    }

    public float getRank5RepMultiplier() {
        return rank5RepMultiplier;
    }

    public void setRank5RepMultiplier(float rank5RepMultiplier) {
        this.rank5RepMultiplier = rank5RepMultiplier;
    }

    public float getRank6RepMultiplier() {
        return rank6RepMultiplier;
    }

    public void setRank6RepMultiplier(float rank6RepMultiplier) {
        this.rank6RepMultiplier = rank6RepMultiplier;
    }

    public float getRank7RepMultiplier() {
        return rank7RepMultiplier;
    }

    public void setRank7RepMultiplier(float rank7RepMultiplier) {
        this.rank7RepMultiplier = rank7RepMultiplier;
    }

    public float getRank8RepMultiplier() {
        return rank8RepMultiplier;
    }

    public void setRank8RepMultiplier(float rank8RepMultiplier) {
        this.rank8RepMultiplier = rank8RepMultiplier;
    }

    public int getBaseCashReward() {
        return baseCashReward;
    }

    public void setBaseCashReward(int baseCashReward) {
        this.baseCashReward = baseCashReward;
    }

    public float getLevelCashRewardMultiplier() {
        return levelCashRewardMultiplier;
    }

    public void setLevelCashRewardMultiplier(float levelCashRewardMultiplier) {
        this.levelCashRewardMultiplier = levelCashRewardMultiplier;
    }

    public float getFinalCashRewardMultiplier() {
        return finalCashRewardMultiplier;
    }

    public void setFinalCashRewardMultiplier(float finalCashRewardMultiplier) {
        this.finalCashRewardMultiplier = finalCashRewardMultiplier;
    }

    public float getPerfectStartCashMultiplier() {
        return perfectStartCashMultiplier;
    }

    public void setPerfectStartCashMultiplier(float perfectStartCashMultiplier) {
        this.perfectStartCashMultiplier = perfectStartCashMultiplier;
    }

    public float getTopSpeedCashMultiplier() {
        return topSpeedCashMultiplier;
    }

    public void setTopSpeedCashMultiplier(float topSpeedCashMultiplier) {
        this.topSpeedCashMultiplier = topSpeedCashMultiplier;
    }

    public float getRank1CashMultiplier() {
        return rank1CashMultiplier;
    }

    public void setRank1CashMultiplier(float rank1CashMultiplier) {
        this.rank1CashMultiplier = rank1CashMultiplier;
    }

    public float getRank2CashMultiplier() {
        return rank2CashMultiplier;
    }

    public void setRank2CashMultiplier(float rank2CashMultiplier) {
        this.rank2CashMultiplier = rank2CashMultiplier;
    }

    public float getRank3CashMultiplier() {
        return rank3CashMultiplier;
    }

    public void setRank3CashMultiplier(float rank3CashMultiplier) {
        this.rank3CashMultiplier = rank3CashMultiplier;
    }

    public float getRank4CashMultiplier() {
        return rank4CashMultiplier;
    }

    public void setRank4CashMultiplier(float rank4CashMultiplier) {
        this.rank4CashMultiplier = rank4CashMultiplier;
    }

    public float getRank5CashMultiplier() {
        return rank5CashMultiplier;
    }

    public void setRank5CashMultiplier(float rank5CashMultiplier) {
        this.rank5CashMultiplier = rank5CashMultiplier;
    }

    public float getRank6CashMultiplier() {
        return rank6CashMultiplier;
    }

    public void setRank6CashMultiplier(float rank6CashMultiplier) {
        this.rank6CashMultiplier = rank6CashMultiplier;
    }

    public float getRank7CashMultiplier() {
        return rank7CashMultiplier;
    }

    public void setRank7CashMultiplier(float rank7CashMultiplier) {
        this.rank7CashMultiplier = rank7CashMultiplier;
    }

    public float getRank8CashMultiplier() {
        return rank8CashMultiplier;
    }

    public void setRank8CashMultiplier(float rank8CashMultiplier) {
        this.rank8CashMultiplier = rank8CashMultiplier;
    }

    public float getMinTopSpeedTrigger() {
        return minTopSpeedTrigger;
    }

    public void setMinTopSpeedTrigger(float minTopSpeedTrigger) {
        this.minTopSpeedTrigger = minTopSpeedTrigger;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public int getCarClassHash() {
        return carClassHash;
    }

    public void setCarClassHash(int carClassHash) {
        this.carClassHash = carClassHash;
    }

    public float getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(float trackLength) {
        this.trackLength = trackLength;
    }

    public RewardTableEntity getRewardTableRank1() {
        return rewardTableRank1;
    }

    public void setRewardTableRank1(RewardTableEntity rewardTableRank1) {
        this.rewardTableRank1 = rewardTableRank1;
    }

    public RewardTableEntity getRewardTableRank2() {
        return rewardTableRank2;
    }

    public void setRewardTableRank2(RewardTableEntity rewardTableRank2) {
        this.rewardTableRank2 = rewardTableRank2;
    }

    public RewardTableEntity getRewardTableRank3() {
        return rewardTableRank3;
    }

    public void setRewardTableRank3(RewardTableEntity rewardTableRank3) {
        this.rewardTableRank3 = rewardTableRank3;
    }

    public RewardTableEntity getRewardTableRank4() {
        return rewardTableRank4;
    }

    public void setRewardTableRank4(RewardTableEntity rewardTableRank4) {
        this.rewardTableRank4 = rewardTableRank4;
    }

    public RewardTableEntity getRewardTableRank5() {
        return rewardTableRank5;
    }

    public void setRewardTableRank5(RewardTableEntity rewardTableRank5) {
        this.rewardTableRank5 = rewardTableRank5;
    }

    public RewardTableEntity getRewardTableRank6() {
        return rewardTableRank6;
    }

    public void setRewardTableRank6(RewardTableEntity rewardTableRank6) {
        this.rewardTableRank6 = rewardTableRank6;
    }

    public RewardTableEntity getRewardTableRank7() {
        return rewardTableRank7;
    }

    public void setRewardTableRank7(RewardTableEntity rewardTableRank7) {
        this.rewardTableRank7 = rewardTableRank7;
    }

    public RewardTableEntity getRewardTableRank8() {
        return rewardTableRank8;
    }

    public void setRewardTableRank8(RewardTableEntity rewardTableRank8) {
        this.rewardTableRank8 = rewardTableRank8;
    }

    public boolean isRotationEnabled() {
        return isRotationEnabled;
    }

    public void setRotationEnabled(boolean rotationEnabled) {
        isRotationEnabled = rotationEnabled;
    }
}
