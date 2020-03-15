
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
 * <p>Java class for EventDefinition complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EventDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarClassHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Coins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EngagePoint" type="{}Vector3" minOccurs="0"/>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventLocalization" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventModeDescriptionLocalization" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventModeIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventModeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventModeLocalization" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Laps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Length" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="MaxClassRating" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxEntrants" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinClassRating" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinEntrants" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RegionLocalization" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RewardModes" type="{}ArrayOfInt" minOccurs="0"/>
 *         &lt;element name="TimeLimit" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="TrackLayoutMap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrackLocalization" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventDefinition", propOrder = {
        "carClassHash",
        "coins",
        "engagePoint",
        "eventId",
        "eventLocalization",
        "eventModeDescriptionLocalization",
        "eventModeIcon",
        "eventModeId",
        "eventModeLocalization",
        "isEnabled",
        "isLocked",
        "laps",
        "length",
        "maxClassRating",
        "maxEntrants",
        "maxLevel",
        "minClassRating",
        "minEntrants",
        "minLevel",
        "regionLocalization",
        "rewardModes",
        "timeLimit",
        "trackLayoutMap",
        "trackLocalization"
})
public class EventDefinition {

    @XmlElement(name = "CarClassHash")
    protected int carClassHash;
    @XmlElement(name = "Coins")
    protected int coins;
    @XmlElement(name = "EngagePoint")
    protected Vector3 engagePoint;
    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "EventLocalization")
    protected int eventLocalization;
    @XmlElement(name = "EventModeDescriptionLocalization")
    protected int eventModeDescriptionLocalization;
    @XmlElement(name = "EventModeIcon")
    protected String eventModeIcon;
    @XmlElement(name = "EventModeId")
    protected int eventModeId;
    @XmlElement(name = "EventModeLocalization")
    protected int eventModeLocalization;
    @XmlElement(name = "IsEnabled")
    protected boolean isEnabled;
    @XmlElement(name = "IsLocked")
    protected boolean isLocked;
    @XmlElement(name = "Laps")
    protected int laps;
    @XmlElement(name = "Length")
    protected float length;
    @XmlElement(name = "MaxClassRating")
    protected int maxClassRating;
    @XmlElement(name = "MaxEntrants")
    protected int maxEntrants;
    @XmlElement(name = "MaxLevel")
    protected int maxLevel;
    @XmlElement(name = "MinClassRating")
    protected int minClassRating;
    @XmlElement(name = "MinEntrants")
    protected int minEntrants;
    @XmlElement(name = "MinLevel")
    protected int minLevel;
    @XmlElement(name = "RegionLocalization")
    protected int regionLocalization;
    @XmlElement(name = "RewardModes")
    protected ArrayOfInt rewardModes;
    @XmlElement(name = "TimeLimit")
    protected float timeLimit;
    @XmlElement(name = "TrackLayoutMap")
    protected String trackLayoutMap;
    @XmlElement(name = "TrackLocalization")
    protected int trackLocalization;

    /**
     * Gets the value of the carClassHash property.
     */
    public int getCarClassHash() {
        return carClassHash;
    }

    /**
     * Sets the value of the carClassHash property.
     */
    public void setCarClassHash(int value) {
        this.carClassHash = value;
    }

    /**
     * Gets the value of the coins property.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the value of the coins property.
     */
    public void setCoins(int value) {
        this.coins = value;
    }

    /**
     * Gets the value of the engagePoint property.
     *
     * @return possible object is
     * {@link Vector3 }
     */
    public Vector3 getEngagePoint() {
        return engagePoint;
    }

    /**
     * Sets the value of the engagePoint property.
     *
     * @param value allowed object is
     *              {@link Vector3 }
     */
    public void setEngagePoint(Vector3 value) {
        this.engagePoint = value;
    }

    /**
     * Gets the value of the eventId property.
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     */
    public void setEventId(int value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the eventLocalization property.
     */
    public int getEventLocalization() {
        return eventLocalization;
    }

    /**
     * Sets the value of the eventLocalization property.
     */
    public void setEventLocalization(int value) {
        this.eventLocalization = value;
    }

    /**
     * Gets the value of the eventModeDescriptionLocalization property.
     */
    public int getEventModeDescriptionLocalization() {
        return eventModeDescriptionLocalization;
    }

    /**
     * Sets the value of the eventModeDescriptionLocalization property.
     */
    public void setEventModeDescriptionLocalization(int value) {
        this.eventModeDescriptionLocalization = value;
    }

    /**
     * Gets the value of the eventModeIcon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEventModeIcon() {
        return eventModeIcon;
    }

    /**
     * Sets the value of the eventModeIcon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEventModeIcon(String value) {
        this.eventModeIcon = value;
    }

    /**
     * Gets the value of the eventModeId property.
     */
    public int getEventModeId() {
        return eventModeId;
    }

    /**
     * Sets the value of the eventModeId property.
     */
    public void setEventModeId(int value) {
        this.eventModeId = value;
    }

    /**
     * Gets the value of the eventModeLocalization property.
     */
    public int getEventModeLocalization() {
        return eventModeLocalization;
    }

    /**
     * Sets the value of the eventModeLocalization property.
     */
    public void setEventModeLocalization(int value) {
        this.eventModeLocalization = value;
    }

    /**
     * Gets the value of the isEnabled property.
     */
    public boolean isIsEnabled() {
        return isEnabled;
    }

    /**
     * Sets the value of the isEnabled property.
     */
    public void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    /**
     * Gets the value of the isLocked property.
     */
    public boolean isIsLocked() {
        return isLocked;
    }

    /**
     * Sets the value of the isLocked property.
     */
    public void setIsLocked(boolean value) {
        this.isLocked = value;
    }

    /**
     * Gets the value of the laps property.
     */
    public int getLaps() {
        return laps;
    }

    /**
     * Sets the value of the laps property.
     */
    public void setLaps(int value) {
        this.laps = value;
    }

    /**
     * Gets the value of the length property.
     */
    public float getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     */
    public void setLength(float value) {
        this.length = value;
    }

    /**
     * Gets the value of the maxClassRating property.
     */
    public int getMaxClassRating() {
        return maxClassRating;
    }

    /**
     * Sets the value of the maxClassRating property.
     */
    public void setMaxClassRating(int value) {
        this.maxClassRating = value;
    }

    /**
     * Gets the value of the maxEntrants property.
     */
    public int getMaxEntrants() {
        return maxEntrants;
    }

    /**
     * Sets the value of the maxEntrants property.
     */
    public void setMaxEntrants(int value) {
        this.maxEntrants = value;
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
     * Gets the value of the minClassRating property.
     */
    public int getMinClassRating() {
        return minClassRating;
    }

    /**
     * Sets the value of the minClassRating property.
     */
    public void setMinClassRating(int value) {
        this.minClassRating = value;
    }

    /**
     * Gets the value of the minEntrants property.
     */
    public int getMinEntrants() {
        return minEntrants;
    }

    /**
     * Sets the value of the minEntrants property.
     */
    public void setMinEntrants(int value) {
        this.minEntrants = value;
    }

    /**
     * Gets the value of the minLevel property.
     */
    public int getMinLevel() {
        return minLevel;
    }

    /**
     * Sets the value of the minLevel property.
     */
    public void setMinLevel(int value) {
        this.minLevel = value;
    }

    /**
     * Gets the value of the regionLocalization property.
     */
    public int getRegionLocalization() {
        return regionLocalization;
    }

    /**
     * Sets the value of the regionLocalization property.
     */
    public void setRegionLocalization(int value) {
        this.regionLocalization = value;
    }

    /**
     * Gets the value of the rewardModes property.
     *
     * @return possible object is
     * {@link ArrayOfInt }
     */
    public ArrayOfInt getRewardModes() {
        return rewardModes;
    }

    /**
     * Sets the value of the rewardModes property.
     *
     * @param value allowed object is
     *              {@link ArrayOfInt }
     */
    public void setRewardModes(ArrayOfInt value) {
        this.rewardModes = value;
    }

    /**
     * Gets the value of the timeLimit property.
     */
    public float getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the value of the timeLimit property.
     */
    public void setTimeLimit(float value) {
        this.timeLimit = value;
    }

    /**
     * Gets the value of the trackLayoutMap property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTrackLayoutMap() {
        return trackLayoutMap;
    }

    /**
     * Sets the value of the trackLayoutMap property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTrackLayoutMap(String value) {
        this.trackLayoutMap = value;
    }

    /**
     * Gets the value of the trackLocalization property.
     */
    public int getTrackLocalization() {
        return trackLocalization;
    }

    /**
     * Sets the value of the trackLocalization property.
     */
    public void setTrackLocalization(int value) {
        this.trackLocalization = value;
    }

}
