
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
 * <p>Java class for LobbyInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LobbyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Countdown" type="{}LobbyCountdown" minOccurs="0"/>
 *         &lt;element name="Entrants" type="{}ArrayOfLobbyEntrantInfo" minOccurs="0"/>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsInviteEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LobbyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="LobbyInviteId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LobbyInfo", propOrder = {
        "countdown",
        "entrants",
        "eventId",
        "isInviteEnabled",
        "lobbyId",
        "lobbyInviteId"
})
public class LobbyInfo {

    @XmlElement(name = "Countdown")
    protected LobbyCountdown countdown;
    @XmlElement(name = "Entrants")
    protected ArrayOfLobbyEntrantInfo entrants;
    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "IsInviteEnabled")
    protected boolean isInviteEnabled;
    @XmlElement(name = "LobbyId")
    protected long lobbyId;
    @XmlElement(name = "LobbyInviteId")
    protected long lobbyInviteId;

    /**
     * Gets the value of the countdown property.
     *
     * @return possible object is
     * {@link LobbyCountdown }
     */
    public LobbyCountdown getCountdown() {
        return countdown;
    }

    /**
     * Sets the value of the countdown property.
     *
     * @param value allowed object is
     *              {@link LobbyCountdown }
     */
    public void setCountdown(LobbyCountdown value) {
        this.countdown = value;
    }

    /**
     * Gets the value of the entrants property.
     *
     * @return possible object is
     * {@link ArrayOfLobbyEntrantInfo }
     */
    public ArrayOfLobbyEntrantInfo getEntrants() {
        return entrants;
    }

    /**
     * Sets the value of the entrants property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLobbyEntrantInfo }
     */
    public void setEntrants(ArrayOfLobbyEntrantInfo value) {
        this.entrants = value;
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
     * Gets the value of the isInviteEnabled property.
     */
    public boolean isIsInviteEnabled() {
        return isInviteEnabled;
    }

    /**
     * Sets the value of the isInviteEnabled property.
     */
    public void setIsInviteEnabled(boolean value) {
        this.isInviteEnabled = value;
    }

    /**
     * Gets the value of the lobbyId property.
     */
    public long getLobbyId() {
        return lobbyId;
    }

    /**
     * Sets the value of the lobbyId property.
     */
    public void setLobbyId(long value) {
        this.lobbyId = value;
    }

    /**
     * Gets the value of the lobbyInviteId property.
     */
    public long getLobbyInviteId() {
        return lobbyInviteId;
    }

    /**
     * Sets the value of the lobbyInviteId property.
     */
    public void setLobbyInviteId(long value) {
        this.lobbyInviteId = value;
    }

}
