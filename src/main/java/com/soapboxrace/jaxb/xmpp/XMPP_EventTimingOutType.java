package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_EventTimingOutType", propOrder = { "eventSessionId", "timeInMilliseconds" })
public class XMPP_EventTimingOutType {
	@XmlElement(name = "EventSessionId", required = true)
	private Long eventSessionId;
	@XmlElement(name = "TimeInMilliseconds", required = true)
	private Integer timeInMilliseconds = 60000;

	public Long getEventSessionId() {
		return eventSessionId;
	}

	public void setEventSessionId(Long eventSessionId) {
		this.eventSessionId = eventSessionId;
	}

	public Integer getTimeInMilliseconds() {
		return timeInMilliseconds;
	}

	public void setTimeInMilliseconds(Integer timeInMilliseconds) {
		this.timeInMilliseconds = timeInMilliseconds;
	}
}