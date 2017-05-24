
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LobbyInvite complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LobbyInvite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InviteLifetimeInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InvitedByPersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="IsPrivate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LobbyInviteId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LobbyInvite", propOrder = {
    "eventId",
    "inviteLifetimeInMilliseconds",
    "invitedByPersonaId",
    "isPrivate",
    "lobbyInviteId"
})
public class LobbyInvite {

    @XmlElement(name = "EventId")
    protected int eventId;
    @XmlElement(name = "InviteLifetimeInMilliseconds")
    protected int inviteLifetimeInMilliseconds;
    @XmlElement(name = "InvitedByPersonaId")
    protected long invitedByPersonaId;
    @XmlElement(name = "IsPrivate")
    protected boolean isPrivate;
    @XmlElement(name = "LobbyInviteId")
    protected long lobbyInviteId;

    /**
     * Gets the value of the eventId property.
     * 
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     * 
     */
    public void setEventId(int value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the inviteLifetimeInMilliseconds property.
     * 
     */
    public int getInviteLifetimeInMilliseconds() {
        return inviteLifetimeInMilliseconds;
    }

    /**
     * Sets the value of the inviteLifetimeInMilliseconds property.
     * 
     */
    public void setInviteLifetimeInMilliseconds(int value) {
        this.inviteLifetimeInMilliseconds = value;
    }

    /**
     * Gets the value of the invitedByPersonaId property.
     * 
     */
    public long getInvitedByPersonaId() {
        return invitedByPersonaId;
    }

    /**
     * Sets the value of the invitedByPersonaId property.
     * 
     */
    public void setInvitedByPersonaId(long value) {
        this.invitedByPersonaId = value;
    }

    /**
     * Gets the value of the isPrivate property.
     * 
     */
    public boolean isIsPrivate() {
        return isPrivate;
    }

    /**
     * Sets the value of the isPrivate property.
     * 
     */
    public void setIsPrivate(boolean value) {
        this.isPrivate = value;
    }

    /**
     * Gets the value of the lobbyInviteId property.
     * 
     */
    public long getLobbyInviteId() {
        return lobbyInviteId;
    }

    /**
     * Sets the value of the lobbyInviteId property.
     * 
     */
    public void setLobbyInviteId(long value) {
        this.lobbyInviteId = value;
    }

}
