/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypePowerupActivated", propOrder = {"powerupActivated"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypePowerupActivated {
    @XmlElement(name = "PowerupActivated", required = true)
    protected XMPP_PowerupActivatedType powerupActivated;

    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public XMPP_PowerupActivatedType getPowerupActivated() {
        return powerupActivated;
    }

    public void setPowerupActivated(XMPP_PowerupActivatedType powerupActivated) {
        this.powerupActivated = powerupActivated;
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