
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AchievementDefinitionPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AchievementDefinitionPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievementDefinitionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AchievementRanks" type="{}ArrayOfAchievementRankPacket" minOccurs="0"/>
 *         &lt;element name="BadgeDefinitionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CanProgress" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CurrentValue" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IsVisible" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ProgressText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatConversion" type="{}StatConversion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AchievementDefinitionPacket", propOrder = {
        "achievementDefinitionId",
        "achievementRanks",
        "badgeDefinitionId",
        "canProgress",
        "currentValue",
        "isVisible",
        "progressText",
        "statConversion"
})
public class AchievementDefinitionPacket {

    @XmlElement(name = "AchievementDefinitionId")
    protected int achievementDefinitionId;
    @XmlElement(name = "AchievementRanks")
    protected ArrayOfAchievementRankPacket achievementRanks;
    @XmlElement(name = "BadgeDefinitionId")
    protected int badgeDefinitionId;
    @XmlElement(name = "CanProgress")
    protected boolean canProgress;
    @XmlElement(name = "CurrentValue")
    protected long currentValue;
    @XmlElement(name = "IsVisible")
    protected boolean isVisible;
    @XmlElement(name = "ProgressText")
    protected String progressText;
    @XmlElement(name = "StatConversion", required = true)
    @XmlSchemaType(name = "string")
    protected StatConversion statConversion;

    /**
     * Gets the value of the achievementDefinitionId property.
     */
    public int getAchievementDefinitionId() {
        return achievementDefinitionId;
    }

    /**
     * Sets the value of the achievementDefinitionId property.
     */
    public void setAchievementDefinitionId(int value) {
        this.achievementDefinitionId = value;
    }

    /**
     * Gets the value of the achievementRanks property.
     *
     * @return possible object is
     * {@link ArrayOfAchievementRankPacket }
     */
    public ArrayOfAchievementRankPacket getAchievementRanks() {
        return achievementRanks;
    }

    /**
     * Sets the value of the achievementRanks property.
     *
     * @param value allowed object is
     *              {@link ArrayOfAchievementRankPacket }
     */
    public void setAchievementRanks(ArrayOfAchievementRankPacket value) {
        this.achievementRanks = value;
    }

    /**
     * Gets the value of the badgeDefinitionId property.
     */
    public int getBadgeDefinitionId() {
        return badgeDefinitionId;
    }

    /**
     * Sets the value of the badgeDefinitionId property.
     */
    public void setBadgeDefinitionId(int value) {
        this.badgeDefinitionId = value;
    }

    /**
     * Gets the value of the canProgress property.
     */
    public boolean isCanProgress() {
        return canProgress;
    }

    /**
     * Sets the value of the canProgress property.
     */
    public void setCanProgress(boolean value) {
        this.canProgress = value;
    }

    /**
     * Gets the value of the currentValue property.
     */
    public long getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the value of the currentValue property.
     */
    public void setCurrentValue(long value) {
        this.currentValue = value;
    }

    /**
     * Gets the value of the isVisible property.
     */
    public boolean isIsVisible() {
        return isVisible;
    }

    /**
     * Sets the value of the isVisible property.
     */
    public void setIsVisible(boolean value) {
        this.isVisible = value;
    }

    /**
     * Gets the value of the progressText property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProgressText() {
        return progressText;
    }

    /**
     * Sets the value of the progressText property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProgressText(String value) {
        this.progressText = value;
    }

    /**
     * Gets the value of the statConversion property.
     *
     * @return possible object is
     * {@link StatConversion }
     */
    public StatConversion getStatConversion() {
        return statConversion;
    }

    /**
     * Sets the value of the statConversion property.
     *
     * @param value allowed object is
     *              {@link StatConversion }
     */
    public void setStatConversion(StatConversion value) {
        this.statConversion = value;
    }

}
