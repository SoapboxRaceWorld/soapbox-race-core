/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.jaxb.http.LobbyEntrantRemoved;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeEntrantRemoved", propOrder = {"lobbyEntrantRemoved"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeEntrantRemoved {
    @XmlElement(name = "LobbyEntrantRemoved", required = true)
    protected LobbyEntrantRemoved lobbyEntrantRemoved;
    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public LobbyEntrantRemoved getLobbyEntrantRemoved() {
        return lobbyEntrantRemoved;
    }

    public void setLobbyEntrantRemoved(LobbyEntrantRemoved lobbyEntrantRemoved) {
        this.lobbyEntrantRemoved = lobbyEntrantRemoved;
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