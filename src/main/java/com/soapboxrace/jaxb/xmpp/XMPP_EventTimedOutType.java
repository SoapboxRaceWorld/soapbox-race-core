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
@XmlType(name = "XMPP_EventTimedOutType", propOrder = {"eventSessionId"})
public class XMPP_EventTimedOutType {
    @XmlElement(name = "EventSessionId", required = true)
    private Long eventSessionId;

    public Long getEventSessionId() {
        return eventSessionId;
    }

    public void setEventSessionId(Long eventSessionId) {
        this.eventSessionId = eventSessionId;
    }
}