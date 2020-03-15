
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaPresence complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PersonaPresence">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="personaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="presence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaPresence", propOrder = {
        "personaId",
        "presence",
        "userId"
})
@XmlRootElement(name = "PersonaPresence")
public class PersonaPresence {

    protected long personaId;
    protected Long presence;
    protected long userId;

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
