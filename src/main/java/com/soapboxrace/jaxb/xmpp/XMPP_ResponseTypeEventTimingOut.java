package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeEventTimingOut", propOrder = { "eventTimingOut" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeEventTimingOut {
	@XmlElement(name = "EventTimingOut", required = true)
	protected XMPP_EventTimingOutType eventTimingOut;

	@XmlAttribute(name = "status")
	protected int status = 1;
	@XmlAttribute(name = "ticket")
	protected int ticket = 0;

	public XMPP_EventTimingOutType getEventTimingOut() {
		return eventTimingOut;
	}

	public void setEventTimingOut(XMPP_EventTimingOutType eventTimingOut) {
		this.eventTimingOut = eventTimingOut;
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