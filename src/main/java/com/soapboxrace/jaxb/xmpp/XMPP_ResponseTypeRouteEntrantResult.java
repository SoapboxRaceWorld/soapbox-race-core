package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeRouteEntrantResult", propOrder = { "routeEntrantResult" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeRouteEntrantResult {
	@XmlElement(name = "RouteEntrantResult", required = true)
	private XMPP_RouteEntrantResultType routeEntrantResult;

	@XmlAttribute(name = "status")
	private Integer status = 1;
	@XmlAttribute(name = "ticket")
	private Integer ticket = 0;

	public XMPP_RouteEntrantResultType getRouteEntrantResult() {
		return routeEntrantResult;
	}

	public void setRouteEntrantResult(XMPP_RouteEntrantResultType routeEntrantResult) {
		this.routeEntrantResult = routeEntrantResult;
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