/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_DragEntrantResultType", propOrder = {"eventDurationInMilliseconds", "eventSessionId",
        "finishReason", "personaId", "ranking", "topSpeed"})
@XmlRootElement(name = "DragEntrantResult")
public class XMPP_DragEntrantResultType {
    @XmlElement(name = "EventDurationInMilliseconds")
    private Long eventDurationInMilliseconds;
    @XmlElement(name = "EventSessionId")
    private Long eventSessionId;
    @XmlElement(name = "FinishReason")
    private Integer finishReason;
    @XmlElement(name = "PersonaId")
    private Long personaId;
    @XmlElement(name = "Ranking")
    private Integer ranking;
    @XmlElement(name = "TopSpeed")
    private Float topSpeed;

    public Long getEventDurationInMilliseconds() {
        return eventDurationInMilliseconds;
    }

    public void setEventDurationInMilliseconds(Long eventDurationInMilliseconds) {
        this.eventDurationInMilliseconds = eventDurationInMilliseconds;
    }

    public Long getEventSessionId() {
        return eventSessionId;
    }

    public void setEventSessionId(Long eventSessionId) {
        this.eventSessionId = eventSessionId;
    }

    public Integer getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(Integer finishReason) {
        this.finishReason = finishReason;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Float getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Float topSpeed) {
        this.topSpeed = topSpeed;
    }
}