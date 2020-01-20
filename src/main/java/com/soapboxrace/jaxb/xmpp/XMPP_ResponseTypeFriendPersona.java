/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeFriendPersona", propOrder = { "friendPersona" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeFriendPersona
{
    @XmlElement(name = "FriendPersona", required = true)
    protected XMPP_FriendPersonaType friendPersona;

    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public XMPP_FriendPersonaType getFriendPersona()
    {
        return friendPersona;
    }

    public void setFriendPersona(XMPP_FriendPersonaType friendPersona)
    {
        this.friendPersona = friendPersona;
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