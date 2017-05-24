package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_LobbyInvite", propOrder = {
    "eventId",
    "inviteLifetimeInMilliseconds",
    "invitedByPersonaId",
    "isPrivate",
    "lobbyInviteId"
})
@XmlRootElement(name = "LobbyInvite")
public class XMPP_LobbyInvite {

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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int value) {
        this.eventId = value;
    }

    public int getInviteLifetimeInMilliseconds() {
        return inviteLifetimeInMilliseconds;
    }

    public void setInviteLifetimeInMilliseconds(int value) {
        this.inviteLifetimeInMilliseconds = value;
    }

    public long getInvitedByPersonaId() {
        return invitedByPersonaId;
    }

    public void setInvitedByPersonaId(long value) {
        this.invitedByPersonaId = value;
    }

    public boolean isIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean value) {
        this.isPrivate = value;
    }

    public long getLobbyInviteId() {
        return lobbyInviteId;
    }

    public void setLobbyInviteId(long value) {
        this.lobbyInviteId = value;
    }

}
