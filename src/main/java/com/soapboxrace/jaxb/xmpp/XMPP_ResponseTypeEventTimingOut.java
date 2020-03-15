/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeEventTimingOut", propOrder = {"eventTimingOut"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeEventTimingOut {
    @XmlElement(name = "EventTimingOut", required = true)
    protected XMPP_EventTimingOutType eventTimingOut;

    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public XMPP_EventTimingOutType getEventTimingOut() {
        return eventTimingOut;
    }

    public void setEventTimingOut(XMPP_EventTimingOutType eventTimingOut) {
        this.eventTimingOut = eventTimingOut;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}