/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.jaxb.http.Entrants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_LobbyLaunchedType", propOrder = {"cryptoTickets", "entrants", "eventSession", "isNewRelayServer", "lobbyId", "udpRelayHost", "udpRelayPort"})
public class XMPP_LobbyLaunchedType {

    @XmlElement(name = "CryptoTickets", required = true)
    protected XMPP_CryptoTicketsType cryptoTickets;
    @XmlElement(name = "Entrants", required = true)
    protected Entrants entrants;
    @XmlElement(name = "EventSession", required = true)
    protected XMPP_EventSessionType eventSession;
    @XmlElement(name = "IsNewRelayServer", required = true)
    protected boolean isNewRelayServer;
    @XmlElement(name = "LobbyId")
    protected long lobbyId;
    @XmlElement(name = "UdpRelayHost", required = true)
    protected String udpRelayHost;
    @XmlElement(name = "UdpRelayPort", required = true)
    protected int udpRelayPort;

    public XMPP_CryptoTicketsType getCryptoTickets() {
        return cryptoTickets;
    }

    public void setCryptoTickets(XMPP_CryptoTicketsType value) {
        this.cryptoTickets = value;
    }

    public Entrants getEntrants() {
        return entrants;
    }

    public void setEntrants(Entrants entrants) {
        this.entrants = entrants;
    }

    public XMPP_EventSessionType getEventSession() {
        return eventSession;
    }

    public void setEventSession(XMPP_EventSessionType value) {
        this.eventSession = value;
    }

    public boolean isNewRelayServer() {
        return isNewRelayServer;
    }

    public void setNewRelayServer(boolean isNewRelayServer) {
        this.isNewRelayServer = isNewRelayServer;
    }

    public long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getUdpRelayHost() {
        return udpRelayHost;
    }

    public void setUdpRelayHost(String value) {
        this.udpRelayHost = value;
    }

    public int getUdpRelayPort() {
        return udpRelayPort;
    }

    public void setUdpRelayPort(int udpRelayPort) {
        this.udpRelayPort = udpRelayPort;
    }

}
