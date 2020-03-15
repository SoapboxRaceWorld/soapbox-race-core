
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AchievementRankPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AchievementRankPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="AchievementRankId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsRare" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Points" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="Rarity" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="RewardDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RewardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RewardVisualStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="State" type="{}AchievementState"/>
 *         &lt;element name="ThresholdValue" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AchievementRankPacket", propOrder = {
        "achievedOn",
        "achievementRankId",
        "isRare",
        "points",
        "rank",
        "rarity",
        "rewardDescription",
        "rewardType",
        "rewardVisualStyle",
        "state",
        "thresholdValue"
})
public class AchievementRankPacket {

    @XmlElement(name = "AchievedOn", required = true)
    protected String achievedOn;
    @XmlElement(name = "AchievementRankId")
    protected int achievementRankId;
    @XmlElement(name = "IsRare")
    protected boolean isRare;
    @XmlElement(name = "Points")
    protected short points;
    @XmlElement(name = "Rank")
    protected short rank;
    @XmlElement(name = "Rarity")
    protected float rarity;
    @XmlElement(name = "RewardDescription")
    protected String rewardDescription;
    @XmlElement(name = "RewardType")
    protected String rewardType;
    @XmlElement(name = "RewardVisualStyle")
    protected String rewardVisualStyle;
    @XmlElement(name = "State", required = true)
    @XmlSchemaType(name = "string")
    protected AchievementState state;
    @XmlElement(name = "ThresholdValue")
    protected long thresholdValue;

    /**
     * Gets the value of the achievedOn property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAchievedOn() {
        return achievedOn;
    }

    /**
     * Sets the value of the achievedOn property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAchievedOn(String value) {
        this.achievedOn = value;
    }

    /**
     * Gets the value of the achievementRankId property.
     */
    public int getAchievementRankId() {
        return achievementRankId;
    }

    /**
     * Sets the value of the achievementRankId property.
     */
    public void setAchievementRankId(int value) {
        this.achievementRankId = value;
    }

    /**
     * Gets the value of the isRare property.
     */
    public boolean isIsRare() {
        return isRare;
    }

    /**
     * Sets the value of the isRare property.
     */
    public void setIsRare(boolean value) {
        this.isRare = value;
    }

    /**
     * Gets the value of the points property.
     */
    public short getPoints() {
        return points;
    }

    /**
     * Sets the value of the points property.
     */
    public void setPoints(short value) {
        this.points = value;
    }

    /**
     * Gets the value of the rank property.
     */
    public short getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     */
    public void setRank(short value) {
        this.rank = value;
    }

    /**
     * Gets the value of the rarity property.
     */
    public float getRarity() {
        return rarity;
    }

    /**
     * Sets the value of the rarity property.
     */
    public void setRarity(float value) {
        this.rarity = value;
    }

    /**
     * Gets the value of the rewardDescription property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRewardDescription() {
        return rewardDescription;
    }

    /**
     * Sets the value of the rewardDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRewardDescription(String value) {
        this.rewardDescription = value;
    }

    /**
     * Gets the value of the rewardType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRewardType() {
        return rewardType;
    }

    /**
     * Sets the value of the rewardType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRewardType(String value) {
        this.rewardType = value;
    }

    /**
     * Gets the value of the rewardVisualStyle property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRewardVisualStyle() {
        return rewardVisualStyle;
    }

    /**
     * Sets the value of the rewardVisualStyle property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRewardVisualStyle(String value) {
        this.rewardVisualStyle = value;
    }

    /**
     * Gets the value of the state property.
     *
     * @return possible object is
     * {@link AchievementState }
     */
    public AchievementState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     *
     * @param value allowed object is
     *              {@link AchievementState }
     */
    public void setState(AchievementState value) {
        this.state = value;
    }

    /**
     * Gets the value of the thresholdValue property.
     */
    public long getThresholdValue() {
        return thresholdValue;
    }

    /**
     * Sets the value of the thresholdValue property.
     */
    public void setThresholdValue(long value) {
        this.thresholdValue = value;
    }

}
