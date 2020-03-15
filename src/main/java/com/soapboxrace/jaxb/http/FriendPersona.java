
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FriendPersona complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FriendPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iconIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="presence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="socialNetwork" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FriendPersona", propOrder = {
        "iconIndex",
        "level",
        "name",
        "originalName",
        "personaId",
        "presence",
        "socialNetwork",
        "userId"
})
public class FriendPersona {

    protected int iconIndex;
    protected int level;
    protected String name;
    protected String originalName;
    protected long personaId;
    protected Long presence;
    protected int socialNetwork;
    protected long userId;

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
     * Gets the value of the originalName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets the value of the originalName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOriginalName(String value) {
        this.originalName = value;
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
     * Gets the value of the socialNetwork property.
     */
    public int getSocialNetwork() {
        return socialNetwork;
    }

    /**
     * Sets the value of the socialNetwork property.
     */
    public void setSocialNetwork(int value) {
        this.socialNetwork = value;
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
