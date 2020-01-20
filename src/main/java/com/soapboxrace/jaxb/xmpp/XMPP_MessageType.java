/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.core.bo.ParameterBO;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageType", propOrder = {"body", "subject"})
@XmlRootElement(name = "message")
public class XMPP_MessageType {

	@XmlTransient
	private ParameterBO parameterBO;

	@XmlElement(required = true)
	private String body;
	@XmlAttribute(name = "from")
	private String from = "";
	@XmlAttribute(name = "id")
	private String id = "JN_1234567";
	@XmlAttribute(name = "to")
	private String to;
	@XmlElement(required = true)
	private Long subject;

	public XMPP_MessageType() {
	}

	public XMPP_MessageType(ParameterBO parameterBO) {
		this.parameterBO = parameterBO;
		from = "sbrw.engine.engine@" + parameterBO.getStrParam("XMPP_IP");
	}

	public String getBody() {
		return body;
	}

	public void setBody(String value) {
		this.body = value;
	}

	public long getSubject() {
		return subject;
	}

	public void setSubject(long value) {
		this.subject = value;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String value) {
		this.from = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String value) {
		this.to = value;
	}

	public void setToPersonaId(Long personaId) {
		this.to = "sbrw." + personaId.toString() + "@" + parameterBO.getStrParam("XMPP_IP");
	}

}
