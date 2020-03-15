
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
 * <p>Java class for LobbyCountdown complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LobbyCountdown">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsWaiting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LobbyCountdownInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LobbyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="LobbyStuckDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LobbyCountdown", propOrder = {
        "eventId",
        "isWaiting",
        "lobbyCountdownInMilliseconds",
        "lobbyId",
        "lobbyStuckDurationInMilliseconds"
})
public class LobbyCountdown {

    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "IsWaiting")
    protected boolean isWaiting;
    @XmlElement(name = "LobbyCountdownInMilliseconds")
    protected int lobbyCountdownInMilliseconds;
    @XmlElement(name = "LobbyId")
    protected long lobbyId;
    @XmlElement(name = "LobbyStuckDurationInMilliseconds")
    protected int lobbyStuckDurationInMilliseconds;

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
     * Gets the value of the isWaiting property.
     */
    public boolean isIsWaiting() {
        return isWaiting;
    }

    /**
     * Sets the value of the isWaiting property.
     */
    public void setIsWaiting(boolean value) {
        this.isWaiting = value;
    }

    /**
     * Gets the value of the lobbyCountdownInMilliseconds property.
     */
    public int getLobbyCountdownInMilliseconds() {
        return lobbyCountdownInMilliseconds;
    }

    /**
     * Sets the value of the lobbyCountdownInMilliseconds property.
     */
    public void setLobbyCountdownInMilliseconds(int value) {
        this.lobbyCountdownInMilliseconds = value;
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
     * Gets the value of the lobbyStuckDurationInMilliseconds property.
     */
    public int getLobbyStuckDurationInMilliseconds() {
        return lobbyStuckDurationInMilliseconds;
    }

    /**
     * Sets the value of the lobbyStuckDurationInMilliseconds property.
     */
    public void setLobbyStuckDurationInMilliseconds(int value) {
        this.lobbyStuckDurationInMilliseconds = value;
    }

}
