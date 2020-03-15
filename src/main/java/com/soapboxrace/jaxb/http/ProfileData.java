
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
 * <p>Java class for ProfileData complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProfileData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Badges" type="{}ArrayOfBadgePacket" minOccurs="0"/>
 *         &lt;element name="Boost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cash" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="IconIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Motto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PercentToLevel" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Rating" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="RepAtCurrentLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ccar" type="{}ArrayOfPersonaCCar" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileData", propOrder = {
        "badges",
        "boost",
        "cash",
        "iconIndex",
        "level",
        "motto",
        "name",
        "percentToLevel",
        "personaId",
        "rating",
        "rep",
        "repAtCurrentLevel",
        "score",
        "ccar"
})
public class ProfileData {

    @XmlElement(name = "Badges")
    protected ArrayOfBadgePacket badges;
    @XmlElement(name = "Boost")
    protected double boost;
    @XmlElement(name = "Cash")
    protected double cash;
    @XmlElement(name = "IconIndex")
    protected int iconIndex;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "Motto")
    protected String motto;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "PercentToLevel")
    protected float percentToLevel;
    @XmlElement(name = "PersonaId")
    protected long personaId;
    @XmlElement(name = "Rating")
    protected double rating;
    @XmlElement(name = "Rep")
    protected double rep;
    @XmlElement(name = "RepAtCurrentLevel")
    protected int repAtCurrentLevel;
    @XmlElement(name = "Score")
    protected int score;
    protected ArrayOfPersonaCCar ccar;

    /**
     * Gets the value of the badges property.
     *
     * @return possible object is
     * {@link ArrayOfBadgePacket }
     */
    public ArrayOfBadgePacket getBadges() {
        return badges;
    }

    /**
     * Sets the value of the badges property.
     *
     * @param value allowed object is
     *              {@link ArrayOfBadgePacket }
     */
    public void setBadges(ArrayOfBadgePacket value) {
        this.badges = value;
    }

    /**
     * Gets the value of the boost property.
     */
    public double getBoost() {
        return boost;
    }

    /**
     * Sets the value of the boost property.
     */
    public void setBoost(double value) {
        this.boost = value;
    }

    /**
     * Gets the value of the cash property.
     */
    public double getCash() {
        return cash;
    }

    /**
     * Sets the value of the cash property.
     */
    public void setCash(double value) {
        this.cash = value;
    }

    /**
     * Gets the value of the iconIndex property.
     */
    public int getIconIndex() {
        return iconIndex;
    }

    /**
     * Sets the value of the iconIndex property.
     */
    public void setIconIndex(int value) {
        this.iconIndex = value;
    }

    /**
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the motto property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMotto() {
        return motto;
    }

    /**
     * Sets the value of the motto property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMotto(String value) {
        this.motto = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the percentToLevel property.
     */
    public float getPercentToLevel() {
        return percentToLevel;
    }

    /**
     * Sets the value of the percentToLevel property.
     */
    public void setPercentToLevel(float value) {
        this.percentToLevel = value;
    }

    /**
     * Gets the value of the personaId property.
     */
    public long getPersonaId() {
        return personaId;
    }

    /**
     * Sets the value of the personaId property.
     */
    public void setPersonaId(long value) {
        this.personaId = value;
    }

    /**
     * Gets the value of the rating property.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     */
    public void setRating(double value) {
        this.rating = value;
    }

    /**
     * Gets the value of the rep property.
     */
    public double getRep() {
        return rep;
    }

    /**
     * Sets the value of the rep property.
     */
    public void setRep(double value) {
        this.rep = value;
    }

    /**
     * Gets the value of the repAtCurrentLevel property.
     */
    public int getRepAtCurrentLevel() {
        return repAtCurrentLevel;
    }

    /**
     * Sets the value of the repAtCurrentLevel property.
     */
    public void setRepAtCurrentLevel(int value) {
        this.repAtCurrentLevel = value;
    }

    /**
     * Gets the value of the score property.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     */
    public void setScore(int value) {
        this.score = value;
    }

    /**
     * Gets the value of the ccar property.
     *
     * @return possible object is
     * {@link ArrayOfPersonaCCar }
     */
    public ArrayOfPersonaCCar getCcar() {
        return ccar;
    }

    /**
     * Sets the value of the ccar property.
     *
     * @param value allowed object is
     *              {@link ArrayOfPersonaCCar }
     */
    public void setCcar(ArrayOfPersonaCCar value) {
        this.ccar = value;
    }

}
