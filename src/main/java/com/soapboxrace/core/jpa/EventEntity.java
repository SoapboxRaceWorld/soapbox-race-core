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
    @Column(columnDefinition = "BIT DEFAULT 1", nullable = false)
    private boolean isEnabled;
    @Column(columnDefinition = "BIT DEFAULT 0", nullable = false)
    private boolean isLocked;
    @Column(columnDefinition = "BIT DEFAULT 0", nullable = false)
    private boolean isRotationEnabled;
    private String name;
    private long rewardsTimeLimit;
    private long legitTime;
    private float trackLength;
    @Column(columnDefinition = "integer default 60000")
    private int lobbyCountdownTime = 60000;
    @Column(columnDefinition = "integer default 60000")
    private int dnfTimerTime = 60000;
    @Column(columnDefinition = "BIT DEFAULT 1", nullable = false)
    private boolean isRaceAgainEnabled;
    @Column(columnDefinition = "BIT DEFAULT 1", nullable = false)
    private boolean isDnfEnabled;

    @ManyToOne(targetEntity = EventRewardEntity.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "singleplayer_reward_config_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_SINGLEPLAYER_REWARD_CONFIG_ID"))
    private EventRewardEntity singleplayerRewardConfig;

    @ManyToOne(targetEntity = EventRewardEntity.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "multiplayer_reward_config_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_MULTIPLAYER_REWARD_CONFIG_ID"))
    private EventRewardEntity multiplayerRewardConfig;

    @ManyToOne(targetEntity = EventRewardEntity.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "private_reward_config_id", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_PRIVATE_REWARD_CONFIG_ID"))
    private EventRewardEntity privateRewardConfig;

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

    public long getRewardsTimeLimit() {
        return rewardsTimeLimit;
    }

    public void setRewardsTimeLimit(long rewardsTimeLimit) {
        this.rewardsTimeLimit = rewardsTimeLimit;
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

    public int getLobbyCountdownTime() {
        return lobbyCountdownTime;
    }

    public void setLobbyCountdownTime(int lobbyCountdownTime) {
        this.lobbyCountdownTime = lobbyCountdownTime;
    }

    public int getDnfTimerTime() {
        return dnfTimerTime;
    }

    public void setDnfTimerTime(int dnfTimerTime) {
        this.dnfTimerTime = dnfTimerTime;
    }

    public boolean isRotationEnabled() {
        return isRotationEnabled;
    }

    public void setRotationEnabled(boolean rotationEnabled) {
        isRotationEnabled = rotationEnabled;
    }

    public boolean isDnfEnabled() {
        return isDnfEnabled;
    }

    public void setDnfEnabled(boolean dnfEnabled) {
        isDnfEnabled = dnfEnabled;
    }

    public boolean isRaceAgainEnabled() {
        return isRaceAgainEnabled;
    }

    public void setRaceAgainEnabled(boolean raceAgainEnabled) {
        isRaceAgainEnabled = raceAgainEnabled;
    }

    public long getLegitTime() {
        return legitTime;
    }

    public void setLegitTime(long legitTime) {
        this.legitTime = legitTime;
    }

    public EventRewardEntity getMultiplayerRewardConfig() {
        return multiplayerRewardConfig;
    }

    public void setMultiplayerRewardConfig(EventRewardEntity multiplayerRewardConfig) {
        this.multiplayerRewardConfig = multiplayerRewardConfig;
    }

    public EventRewardEntity getPrivateRewardConfig() {
        return privateRewardConfig;
    }

    public void setPrivateRewardConfig(EventRewardEntity privateRewardConfig) {
        this.privateRewardConfig = privateRewardConfig;
    }

    public EventRewardEntity getSingleplayerRewardConfig() {
        return singleplayerRewardConfig;
    }

    public void setSingleplayerRewardConfig(EventRewardEntity singleplayerRewardConfig) {
        this.singleplayerRewardConfig = singleplayerRewardConfig;
    }
}
