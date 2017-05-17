package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.soapboxrace.jaxb.http.LobbyEntrantAdded;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeEntrantAdded", propOrder = { "lobbyInvite" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeEntrantAdded {
	@XmlElement(name = "LobbyEntrantAdded", required = true)
	protected LobbyEntrantAdded lobbyInvite;
	@XmlAttribute(name = "status")
	protected int status = 1;
	@XmlAttribute(name = "ticket")
	protected int ticket = 0;

	public LobbyEntrantAdded getLobbyInvite() {
		return lobbyInvite;
	}

	public void setLobbyInvite(LobbyEntrantAdded lobbyInvite) {
		this.lobbyInvite = lobbyInvite;
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