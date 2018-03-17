package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.soapboxrace.jaxb.http.LobbyEntrantRemoved;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeEntrantRemoved", propOrder = { "lobbyExit" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeEntrantRemoved {
	@XmlElement(name = "LobbyEntrantRemoved", required = true)
	protected LobbyEntrantRemoved lobbyExit;
	@XmlAttribute(name = "status")
	protected int status = 1;
	@XmlAttribute(name = "ticket")
	protected int ticket = 0;

	public LobbyEntrantRemoved getLobbyExit() {
		return lobbyExit;
	}

	public void setLobbyExit(LobbyEntrantRemoved lobbyExit) {
		this.lobbyExit = lobbyExit;
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