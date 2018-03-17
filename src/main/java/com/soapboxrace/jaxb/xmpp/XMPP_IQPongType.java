package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_IQPongType", propOrder = { "from", "to", "id", "type" })
@XmlRootElement(name = "iq")
public class XMPP_IQPongType {

	@XmlAttribute(name = "from")
	private String from;
	@XmlAttribute(name = "to")
	private String to;
	@XmlAttribute(name = "id")
	private String id;
	@XmlAttribute(name = "type", required = true)
	private String type = "result";

	public XMPP_IQPongType() {
	}

	public XMPP_IQPongType(String id, String xmppIp) {
		from = String.format("sbrw.engine.engine@%s/EA_Chat", xmppIp);
		to = xmppIp;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}