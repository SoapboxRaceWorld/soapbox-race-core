
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
 * <p>Java class for User_Settings complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="User_Settings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarCacheAgeLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsRaceNowEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MaxCarCacheSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinRaceNowLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VoipAvailable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="activatedHolidaySceneryGroups" type="{}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="activeHolidayIds" type="{}ArrayOfLong" minOccurs="0"/>
 *         &lt;element name="disactivatedHolidaySceneryGroups" type="{}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="firstTimeLogin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="maxLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="starterPackApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User_Settings", propOrder = {
        "carCacheAgeLimit",
        "isRaceNowEnabled",
        "maxCarCacheSize",
        "minRaceNowLevel",
        "voipAvailable",
        "activatedHolidaySceneryGroups",
        "activeHolidayIds",
        "disactivatedHolidaySceneryGroups",
        "firstTimeLogin",
        "maxLevel",
        "starterPackApplied",
        "userId"
})
public class UserSettings {

    @XmlElement(name = "CarCacheAgeLimit")
    protected int carCacheAgeLimit;
    @XmlElement(name = "IsRaceNowEnabled")
    protected boolean isRaceNowEnabled;
    @XmlElement(name = "MaxCarCacheSize")
    protected int maxCarCacheSize;
    @XmlElement(name = "MinRaceNowLevel")
    protected int minRaceNowLevel;
    @XmlElement(name = "VoipAvailable")
    protected boolean voipAvailable;
    protected ArrayOfString activatedHolidaySceneryGroups;
    protected ArrayOfLong activeHolidayIds;
    protected ArrayOfString disactivatedHolidaySceneryGroups;
    protected boolean firstTimeLogin;
    protected int maxLevel;
    protected boolean starterPackApplied;
    protected long userId;

    /**
     * Gets the value of the carCacheAgeLimit property.
     */
    public int getCarCacheAgeLimit() {
        return carCacheAgeLimit;
    }

    /**
     * Sets the value of the carCacheAgeLimit property.
     */
    public void setCarCacheAgeLimit(int value) {
        this.carCacheAgeLimit = value;
    }

    /**
     * Gets the value of the isRaceNowEnabled property.
     */
    public boolean isIsRaceNowEnabled() {
        return isRaceNowEnabled;
    }

    /**
     * Sets the value of the isRaceNowEnabled property.
     */
    public void setIsRaceNowEnabled(boolean value) {
        this.isRaceNowEnabled = value;
    }

    /**
     * Gets the value of the maxCarCacheSize property.
     */
    public int getMaxCarCacheSize() {
        return maxCarCacheSize;
    }

    /**
     * Sets the value of the maxCarCacheSize property.
     */
    public void setMaxCarCacheSize(int value) {
        this.maxCarCacheSize = value;
    }

    /**
     * Gets the value of the minRaceNowLevel property.
     */
    public int getMinRaceNowLevel() {
        return minRaceNowLevel;
    }

    /**
     * Sets the value of the minRaceNowLevel property.
     */
    public void setMinRaceNowLevel(int value) {
        this.minRaceNowLevel = value;
    }

    /**
     * Gets the value of the voipAvailable property.
     */
    public boolean isVoipAvailable() {
        return voipAvailable;
    }

    /**
     * Sets the value of the voipAvailable property.
     */
    public void setVoipAvailable(boolean value) {
        this.voipAvailable = value;
    }

    /**
     * Gets the value of the activatedHolidaySceneryGroups property.
     *
     * @return possible object is
     * {@link ArrayOfString }
     */
    public ArrayOfString getActivatedHolidaySceneryGroups() {
        return activatedHolidaySceneryGroups;
    }

    /**
     * Sets the value of the activatedHolidaySceneryGroups property.
     *
     * @param value allowed object is
     *              {@link ArrayOfString }
     */
    public void setActivatedHolidaySceneryGroups(ArrayOfString value) {
        this.activatedHolidaySceneryGroups = value;
    }

    /**
     * Gets the value of the activeHolidayIds property.
     *
     * @return possible object is
     * {@link ArrayOfLong }
     */
    public ArrayOfLong getActiveHolidayIds() {
        return activeHolidayIds;
    }

    /**
     * Sets the value of the activeHolidayIds property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLong }
     */
    public void setActiveHolidayIds(ArrayOfLong value) {
        this.activeHolidayIds = value;
    }

    /**
     * Gets the value of the disactivatedHolidaySceneryGroups property.
     *
     * @return possible object is
     * {@link ArrayOfString }
     */
    public ArrayOfString getDisactivatedHolidaySceneryGroups() {
        return disactivatedHolidaySceneryGroups;
    }

    /**
     * Sets the value of the disactivatedHolidaySceneryGroups property.
     *
     * @param value allowed object is
     *              {@link ArrayOfString }
     */
    public void setDisactivatedHolidaySceneryGroups(ArrayOfString value) {
        this.disactivatedHolidaySceneryGroups = value;
    }

    /**
     * Gets the value of the firstTimeLogin property.
     */
    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    /**
     * Sets the value of the firstTimeLogin property.
     */
    public void setFirstTimeLogin(boolean value) {
        this.firstTimeLogin = value;
    }

    /**
     * Gets the value of the maxLevel property.
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Sets the value of the maxLevel property.
     */
    public void setMaxLevel(int value) {
        this.maxLevel = value;
    }

    /**
     * Gets the value of the starterPackApplied property.
     */
    public boolean isStarterPackApplied() {
        return starterPackApplied;
    }

    /**
     * Sets the value of the starterPackApplied property.
     */
    public void setStarterPackApplied(boolean value) {
        this.starterPackApplied = value;
    }

    /**
     * Gets the value of the userId property.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     */
    public void setUserId(long value) {
        this.userId = value;
    }

}
