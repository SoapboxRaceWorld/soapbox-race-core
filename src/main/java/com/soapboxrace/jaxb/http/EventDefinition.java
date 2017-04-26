
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de EventDefinition complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 * 
 * 
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
     * Obtém o valor da propriedade carClassHash.
     * 
     */
    public int getCarClassHash() {
        return carClassHash;
    }

    /**
     * Define o valor da propriedade carClassHash.
     * 
     */
    public void setCarClassHash(int value) {
        this.carClassHash = value;
    }

    /**
     * Obtém o valor da propriedade coins.
     * 
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Define o valor da propriedade coins.
     * 
     */
    public void setCoins(int value) {
        this.coins = value;
    }

    /**
     * Obtém o valor da propriedade engagePoint.
     * 
     * @return
     *     possible object is
     *     {@link Vector3 }
     *     
     */
    public Vector3 getEngagePoint() {
        return engagePoint;
    }

    /**
     * Define o valor da propriedade engagePoint.
     * 
     * @param value
     *     allowed object is
     *     {@link Vector3 }
     *     
     */
    public void setEngagePoint(Vector3 value) {
        this.engagePoint = value;
    }

    /**
     * Obtém o valor da propriedade eventId.
     * 
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Define o valor da propriedade eventId.
     * 
     */
    public void setEventId(int value) {
        this.eventId = value;
    }

    /**
     * Obtém o valor da propriedade eventLocalization.
     * 
     */
    public int getEventLocalization() {
        return eventLocalization;
    }

    /**
     * Define o valor da propriedade eventLocalization.
     * 
     */
    public void setEventLocalization(int value) {
        this.eventLocalization = value;
    }

    /**
     * Obtém o valor da propriedade eventModeDescriptionLocalization.
     * 
     */
    public int getEventModeDescriptionLocalization() {
        return eventModeDescriptionLocalization;
    }

    /**
     * Define o valor da propriedade eventModeDescriptionLocalization.
     * 
     */
    public void setEventModeDescriptionLocalization(int value) {
        this.eventModeDescriptionLocalization = value;
    }

    /**
     * Obtém o valor da propriedade eventModeIcon.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventModeIcon() {
        return eventModeIcon;
    }

    /**
     * Define o valor da propriedade eventModeIcon.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventModeIcon(String value) {
        this.eventModeIcon = value;
    }

    /**
     * Obtém o valor da propriedade eventModeId.
     * 
     */
    public int getEventModeId() {
        return eventModeId;
    }

    /**
     * Define o valor da propriedade eventModeId.
     * 
     */
    public void setEventModeId(int value) {
        this.eventModeId = value;
    }

    /**
     * Obtém o valor da propriedade eventModeLocalization.
     * 
     */
    public int getEventModeLocalization() {
        return eventModeLocalization;
    }

    /**
     * Define o valor da propriedade eventModeLocalization.
     * 
     */
    public void setEventModeLocalization(int value) {
        this.eventModeLocalization = value;
    }

    /**
     * Obtém o valor da propriedade isEnabled.
     * 
     */
    public boolean isIsEnabled() {
        return isEnabled;
    }

    /**
     * Define o valor da propriedade isEnabled.
     * 
     */
    public void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    /**
     * Obtém o valor da propriedade isLocked.
     * 
     */
    public boolean isIsLocked() {
        return isLocked;
    }

    /**
     * Define o valor da propriedade isLocked.
     * 
     */
    public void setIsLocked(boolean value) {
        this.isLocked = value;
    }

    /**
     * Obtém o valor da propriedade laps.
     * 
     */
    public int getLaps() {
        return laps;
    }

    /**
     * Define o valor da propriedade laps.
     * 
     */
    public void setLaps(int value) {
        this.laps = value;
    }

    /**
     * Obtém o valor da propriedade length.
     * 
     */
    public float getLength() {
        return length;
    }

    /**
     * Define o valor da propriedade length.
     * 
     */
    public void setLength(float value) {
        this.length = value;
    }

    /**
     * Obtém o valor da propriedade maxClassRating.
     * 
     */
    public int getMaxClassRating() {
        return maxClassRating;
    }

    /**
     * Define o valor da propriedade maxClassRating.
     * 
     */
    public void setMaxClassRating(int value) {
        this.maxClassRating = value;
    }

    /**
     * Obtém o valor da propriedade maxEntrants.
     * 
     */
    public int getMaxEntrants() {
        return maxEntrants;
    }

    /**
     * Define o valor da propriedade maxEntrants.
     * 
     */
    public void setMaxEntrants(int value) {
        this.maxEntrants = value;
    }

    /**
     * Obtém o valor da propriedade maxLevel.
     * 
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Define o valor da propriedade maxLevel.
     * 
     */
    public void setMaxLevel(int value) {
        this.maxLevel = value;
    }

    /**
     * Obtém o valor da propriedade minClassRating.
     * 
     */
    public int getMinClassRating() {
        return minClassRating;
    }

    /**
     * Define o valor da propriedade minClassRating.
     * 
     */
    public void setMinClassRating(int value) {
        this.minClassRating = value;
    }

    /**
     * Obtém o valor da propriedade minEntrants.
     * 
     */
    public int getMinEntrants() {
        return minEntrants;
    }

    /**
     * Define o valor da propriedade minEntrants.
     * 
     */
    public void setMinEntrants(int value) {
        this.minEntrants = value;
    }

    /**
     * Obtém o valor da propriedade minLevel.
     * 
     */
    public int getMinLevel() {
        return minLevel;
    }

    /**
     * Define o valor da propriedade minLevel.
     * 
     */
    public void setMinLevel(int value) {
        this.minLevel = value;
    }

    /**
     * Obtém o valor da propriedade regionLocalization.
     * 
     */
    public int getRegionLocalization() {
        return regionLocalization;
    }

    /**
     * Define o valor da propriedade regionLocalization.
     * 
     */
    public void setRegionLocalization(int value) {
        this.regionLocalization = value;
    }

    /**
     * Obtém o valor da propriedade rewardModes.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getRewardModes() {
        return rewardModes;
    }

    /**
     * Define o valor da propriedade rewardModes.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setRewardModes(ArrayOfInt value) {
        this.rewardModes = value;
    }

    /**
     * Obtém o valor da propriedade timeLimit.
     * 
     */
    public float getTimeLimit() {
        return timeLimit;
    }

    /**
     * Define o valor da propriedade timeLimit.
     * 
     */
    public void setTimeLimit(float value) {
        this.timeLimit = value;
    }

    /**
     * Obtém o valor da propriedade trackLayoutMap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackLayoutMap() {
        return trackLayoutMap;
    }

    /**
     * Define o valor da propriedade trackLayoutMap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackLayoutMap(String value) {
        this.trackLayoutMap = value;
    }

    /**
     * Obtém o valor da propriedade trackLocalization.
     * 
     */
    public int getTrackLocalization() {
        return trackLocalization;
    }

    /**
     * Define o valor da propriedade trackLocalization.
     * 
     */
    public void setTrackLocalization(int value) {
        this.trackLocalization = value;
    }

}
