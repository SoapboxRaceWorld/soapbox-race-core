package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeTeamEscapeEntrantResult", propOrder = { "teamEscapeEntrantResult" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeTeamEscapeEntrantResult {
	@XmlElement(name = "TeamEscapeEntrantResult", required = true)
	private XMPP_TeamEscapeEntrantResultType teamEscapeEntrantResult;

	@XmlAttribute(name = "status")
	private Integer status = 1;
	@XmlAttribute(name = "ticket")
	private Integer ticket = 0;

	public XMPP_TeamEscapeEntrantResultType getTeamEscapeEntrantResult() {
		return teamEscapeEntrantResult;
	}

	public void setTeamEscapeEntrantResult(XMPP_TeamEscapeEntrantResultType teamEscapeEntrantResult) {
		this.teamEscapeEntrantResult = teamEscapeEntrantResult;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTicket() {
		return ticket;
	}

	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}
}