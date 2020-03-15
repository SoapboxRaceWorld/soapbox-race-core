/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_EventSessionType", propOrder = {"challenge", "eventId", "sessionId"})
public class XMPP_EventSessionType {

    @XmlElement(name = "Challenge", required = true)
    protected ChallengeType challenge;
    @XmlElement(name = "EventId")
    protected long eventId;
    @XmlElement(name = "SessionId")
    protected long sessionId;

    public ChallengeType getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeType value) {
        this.challenge = value;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

}
