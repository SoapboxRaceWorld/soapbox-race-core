
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
 * <p>Java class for SessionInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SessionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Challenge" type="{}SecurityChallenge" minOccurs="0"/>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionInfo", propOrder = {
        "challenge",
        "eventId",
        "sessionId"
})
public class SessionInfo {

    @XmlElement(name = "Challenge")
    protected SecurityChallenge challenge;
    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "SessionId")
    protected long sessionId;

    /**
     * Gets the value of the challenge property.
     *
     * @return possible object is
     * {@link SecurityChallenge }
     */
    public SecurityChallenge getChallenge() {
        return challenge;
    }

    /**
     * Sets the value of the challenge property.
     *
     * @param value allowed object is
     *              {@link SecurityChallenge }
     */
    public void setChallenge(SecurityChallenge value) {
        this.challenge = value;
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
     * Gets the value of the sessionId property.
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     */
    public void setSessionId(long value) {
        this.sessionId = value;
    }

}
