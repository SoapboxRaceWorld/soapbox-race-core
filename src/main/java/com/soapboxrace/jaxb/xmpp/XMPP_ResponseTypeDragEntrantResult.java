package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeDragEntrantResult", propOrder = { "dragEntrantResult" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeDragEntrantResult {
	@XmlElement(name = "DragEntrantResult", required = true)
	private XMPP_DragEntrantResultType dragEntrantResult;

	@XmlAttribute(name = "status")
	private Integer status = 1;
	@XmlAttribute(name = "ticket")
	private Integer ticket = 0;

	public XMPP_DragEntrantResultType getDragEntrantResult() {
		return dragEntrantResult;
	}

	public void setDragEntrantResult(XMPP_DragEntrantResultType dragEntrantResult) {
		this.dragEntrantResult = dragEntrantResult;
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