
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BadgePacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BadgePacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievementRankId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BadgeDefinitionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsRare" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Rarity" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SlotId" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BadgePacket", propOrder = {
        "achievementRankId",
        "badgeDefinitionId",
        "isRare",
        "rarity",
        "slotId"
})
public class BadgePacket {

    @XmlElement(name = "AchievementRankId")
    protected int achievementRankId;
    @XmlElement(name = "BadgeDefinitionId")
    protected int badgeDefinitionId;
    @XmlElement(name = "IsRare")
    protected boolean isRare;
    @XmlElement(name = "Rarity")
    protected float rarity;
    @XmlElement(name = "SlotId")
    protected short slotId;

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
     * Gets the value of the slotId property.
     */
    public short getSlotId() {
        return slotId;
    }

    /**
     * Sets the value of the slotId property.
     */
    public void setSlotId(short value) {
        this.slotId = value;
    }

}
