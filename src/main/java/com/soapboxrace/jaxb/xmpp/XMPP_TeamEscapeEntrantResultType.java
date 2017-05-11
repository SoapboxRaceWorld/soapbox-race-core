package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_TeamEscapeEntrantResultType", propOrder = { "eventDurationInMilliseconds", "eventSessionId",
		"finishReason", "personaId", "ranking", "distanceToFinish", "fractionCompleted" })
@XmlRootElement(name = "TeamEscapeEntrantResult")
public class XMPP_TeamEscapeEntrantResultType {
	@XmlElement(name = "EventDurationInMilliseconds")
	private Long eventDurationInMilliseconds;
	@XmlElement(name = "EventSessionId")
	private Long eventSessionId;
	@XmlElement(name = "FinishReason")
	private Integer finishReason;
	@XmlElement(name = "PersonaId")
	private Long personaId;
	@XmlElement(name = "Ranking")
	private Short ranking;
	@XmlElement(name = "DistanceToFinish")
	private Float distanceToFinish;
	@XmlElement(name = "FractionCompleted")
	private Float fractionCompleted;

	public Long getEventDurationInMilliseconds() {
		return eventDurationInMilliseconds;
	}

	public void setEventDurationInMilliseconds(Long eventDurationInMilliseconds) {
		this.eventDurationInMilliseconds = eventDurationInMilliseconds;
	}

	public Long getEventSessionId() {
		return eventSessionId;
	}

	public void setEventSessionId(Long eventSessionId) {
		this.eventSessionId = eventSessionId;
	}

	public Integer getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(Integer finishReason) {
		this.finishReason = finishReason;
	}

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public Short getRanking() {
		return ranking;
	}

	public void setRanking(Short ranking) {
		this.ranking = ranking;
	}

	public Float getDistanceToFinish() {
		return distanceToFinish;
	}

	public void setDistanceToFinish(Float distanceToFinish) {
		this.distanceToFinish = distanceToFinish;
	}

	public Float getFractionCompleted() {
		return fractionCompleted;
	}

	public void setFractionCompleted(Float fractionCompleted) {
		this.fractionCompleted = fractionCompleted;
	}
}