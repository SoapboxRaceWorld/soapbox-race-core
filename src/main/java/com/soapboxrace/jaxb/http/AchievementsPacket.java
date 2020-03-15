
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
 * <p>Java class for AchievementsPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AchievementsPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Badges" type="{}ArrayOfBadgeDefinitionPacket" minOccurs="0"/>
 *         &lt;element name="Definitions" type="{}ArrayOfAchievementDefinitionPacket" minOccurs="0"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AchievementsPacket", propOrder = {
        "badges",
        "definitions",
        "personaId"
})
public class AchievementsPacket {

    @XmlElement(name = "Badges")
    protected ArrayOfBadgeDefinitionPacket badges;
    @XmlElement(name = "Definitions")
    protected ArrayOfAchievementDefinitionPacket definitions;
    @XmlElement(name = "PersonaId")
    protected long personaId;

    /**
     * Gets the value of the badges property.
     *
     * @return possible object is
     * {@link ArrayOfBadgeDefinitionPacket }
     */
    public ArrayOfBadgeDefinitionPacket getBadges() {
        return badges;
    }

    /**
     * Sets the value of the badges property.
     *
     * @param value allowed object is
     *              {@link ArrayOfBadgeDefinitionPacket }
     */
    public void setBadges(ArrayOfBadgeDefinitionPacket value) {
        this.badges = value;
    }

    /**
     * Gets the value of the definitions property.
     *
     * @return possible object is
     * {@link ArrayOfAchievementDefinitionPacket }
     */
    public ArrayOfAchievementDefinitionPacket getDefinitions() {
        return definitions;
    }

    /**
     * Sets the value of the definitions property.
     *
     * @param value allowed object is
     *              {@link ArrayOfAchievementDefinitionPacket }
     */
    public void setDefinitions(ArrayOfAchievementDefinitionPacket value) {
        this.definitions = value;
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

}
