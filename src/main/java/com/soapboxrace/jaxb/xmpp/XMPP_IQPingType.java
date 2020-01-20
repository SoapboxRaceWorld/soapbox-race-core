/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_IQPingType", propOrder = { "type", "id", "from", "to", "ping" })
@XmlRootElement(name = "iq")
public class XMPP_IQPingType {
	@XmlAttribute(name = "type", required = true)
	private String type;
	@XmlAttribute(name = "id")
	private String id;
	@XmlAttribute(name = "from")
	private String from;
	@XmlAttribute(name = "to")
	private String to;
	@XmlElement(namespace = "urn:xmpp:ping")
	private String ping;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPing() {
		return ping;
	}

	public void setPing(String ping) {
		this.ping = ping;
	}
}