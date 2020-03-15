
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
 * <p>Java class for PersonaBase complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PersonaBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Badges" type="{}ArrayOfBadgePacket" minOccurs="0"/>
 *         &lt;element name="IconIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Motto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Presence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UserId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaBase", propOrder = {
        "badges",
        "iconIndex",
        "level",
        "motto",
        "name",
        "personaId",
        "presence",
        "score",
        "userId"
})
public class PersonaBase {

    @XmlElement(name = "Badges")
    protected ArrayOfBadgePacket badges;
    @XmlElement(name = "IconIndex")
    protected int iconIndex;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "Motto")
    protected String motto;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "PersonaId")
    protected long personaId;
    @XmlElement(name = "Presence")
    protected Long presence;
    @XmlElement(name = "Score")
    protected int score;
    @XmlElement(name = "UserId")
    protected long userId;

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
     * Gets the value of the presence property.
     */
    public Long getPresence() {
        return presence;
    }

    /**
     * Sets the value of the presence property.
     */
    public void setPresence(Long value) {
        this.presence = value;
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
