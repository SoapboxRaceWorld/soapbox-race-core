
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for EntrantResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EntrantResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="EventSessionId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="FinishReason" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Ranking" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntrantResult", propOrder = {
        "eventDurationInMilliseconds",
        "eventSessionId",
        "finishReason",
        "personaId",
        "ranking"
})
@XmlSeeAlso({
        RouteEntrantResult.class,
        TeamEscapeEntrantResult.class,
        DragEntrantResult.class
})
public class EntrantResult {

    @XmlElement(name = "EventDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long eventDurationInMilliseconds;
    @XmlElement(name = "EventSessionId")
    protected long eventSessionId;
    @XmlElement(name = "FinishReason")
    protected int finishReason;
    @XmlElement(name = "PersonaId")
    protected long personaId;
    @XmlElement(name = "Ranking")
    protected int ranking;

    /**
     * Gets the value of the eventDurationInMilliseconds property.
     */
    public long getEventDurationInMilliseconds() {
        return eventDurationInMilliseconds;
    }

    /**
     * Sets the value of the eventDurationInMilliseconds property.
     */
    public void setEventDurationInMilliseconds(long value) {
        this.eventDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the eventSessionId property.
     */
    public long getEventSessionId() {
        return eventSessionId;
    }

    /**
     * Sets the value of the eventSessionId property.
     */
    public void setEventSessionId(long value) {
        this.eventSessionId = value;
    }

    /**
     * Gets the value of the finishReason property.
     */
    public int getFinishReason() {
        return finishReason;
    }

    /**
     * Sets the value of the finishReason property.
     */
    public void setFinishReason(int value) {
        this.finishReason = value;
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
     * Gets the value of the ranking property.
     */
    public int getRanking() {
        return ranking;
    }

    /**
     * Sets the value of the ranking property.
     */
    public void setRanking(int value) {
        this.ranking = value;
    }

}
