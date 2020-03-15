
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for EventResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EventResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Accolades" type="{}Accolades" minOccurs="0"/>
 *         &lt;element name="Durability" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventSessionId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ExitPath" type="{}ExitPath"/>
 *         &lt;element name="InviteLifetimeInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LobbyInviteId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventResult", propOrder = {
        "accolades",
        "durability",
        "eventId",
        "eventSessionId",
        "exitPath",
        "inviteLifetimeInMilliseconds",
        "lobbyInviteId",
        "personaId"
})
@XmlSeeAlso({
        DragEventResult.class,
        TeamEscapeEventResult.class,
        RouteEventResult.class,
        PursuitEventResult.class
})
public class EventResult {

    @XmlElement(name = "Accolades")
    protected Accolades accolades;
    @XmlElement(name = "Durability")
    protected int durability;
    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "EventSessionId")
    protected long eventSessionId;
    @XmlElement(name = "ExitPath", required = true)
    @XmlSchemaType(name = "string")
    protected ExitPath exitPath;
    @XmlElement(name = "InviteLifetimeInMilliseconds")
    protected int inviteLifetimeInMilliseconds;
    @XmlElement(name = "LobbyInviteId")
    protected long lobbyInviteId;
    @XmlElement(name = "PersonaId")
    protected long personaId;

    /**
     * Gets the value of the accolades property.
     *
     * @return possible object is
     * {@link Accolades }
     */
    public Accolades getAccolades() {
        return accolades;
    }

    /**
     * Sets the value of the accolades property.
     *
     * @param value allowed object is
     *              {@link Accolades }
     */
    public void setAccolades(Accolades value) {
        this.accolades = value;
    }

    /**
     * Gets the value of the durability property.
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Sets the value of the durability property.
     */
    public void setDurability(int value) {
        this.durability = value;
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
     * Gets the value of the eventSessionId property.
     */
    public long getEventSessionId() {
        return eventSessionId;
    }

    /**
     * Sets the value of the eventSessionId property.
     */
    public void setEventSessionId(long value) {
        this.eventSessionId = value;
    }

    /**
     * Gets the value of the exitPath property.
     *
     * @return possible object is
     * {@link ExitPath }
     */
    public ExitPath getExitPath() {
        return exitPath;
    }

    /**
     * Sets the value of the exitPath property.
     *
     * @param value allowed object is
     *              {@link ExitPath }
     */
    public void setExitPath(ExitPath value) {
        this.exitPath = value;
    }

    /**
     * Gets the value of the inviteLifetimeInMilliseconds property.
     */
    public int getInviteLifetimeInMilliseconds() {
        return inviteLifetimeInMilliseconds;
    }

    /**
     * Sets the value of the inviteLifetimeInMilliseconds property.
     */
    public void setInviteLifetimeInMilliseconds(int value) {
        this.inviteLifetimeInMilliseconds = value;
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

    /**
     * Gets the value of the personaId property.
     */
    public long getPersonaId() {
        return personaId;
    }

    /**
     * Sets the value of the personaId property.
     */
    public void setPersonaId(long value) {
        this.personaId = value;
    }

}
