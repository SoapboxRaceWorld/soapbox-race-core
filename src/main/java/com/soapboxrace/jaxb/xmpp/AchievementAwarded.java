/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AchievementAwarded")
@XmlAccessorType(XmlAccessType.FIELD)
public class AchievementAwarded {
    @XmlElement(name = "AchievedOn")
    private String achievedOn;

    @XmlElement(name = "Clip")
    private String clip = "AchievementFlasherBase";

    @XmlElement(name = "Description")
    private String description = "";

    @XmlElement(name = "Icon")
    private String icon = "";

    @XmlElement(name = "Name")
    private String name = "";

    @XmlElement(name = "AchievementDefinitionId")
    private Long achievementDefinitionId;

    @XmlElement(name = "AchievementRankId")
    private Long achievementRankId;

    @XmlElement(name = "ClipLengthInSeconds")
    private float clipLengthInSeconds = 5f;

    @XmlElement(name = "IsRare")
    private boolean isRare;

    @XmlElement(name = "Points")
    private int points;

    @XmlElement(name = "Rarity")
    private float rarity;

    public String getAchievedOn() {
        return achievedOn;
    }

    public void setAchievedOn(String achievedOn) {
        this.achievedOn = achievedOn;
    }

    public String getClip() {
        return clip;
    }

    public void setClip(String clip) {
        this.clip = clip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAchievementDefinitionId() {
        return achievementDefinitionId;
    }

    public void setAchievementDefinitionId(Long achievementDefinitionId) {
        this.achievementDefinitionId = achievementDefinitionId;
    }

    public Long getAchievementRankId() {
        return achievementRankId;
    }

    public void setAchievementRankId(Long achievementRankId) {
        this.achievementRankId = achievementRankId;
    }

    public float getClipLengthInSeconds() {
        return clipLengthInSeconds;
    }

    public void setClipLengthInSeconds(float clipLengthInSeconds) {
        this.clipLengthInSeconds = clipLengthInSeconds;
    }

    public boolean isRare() {
        return isRare;
    }

    public void setRare(boolean rare) {
        isRare = rare;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getRarity() {
        return rarity;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }
}