package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChallengeType", propOrder = { "challengeId", "leftSize", "pattern", "rightSize" })
public class ChallengeType {

	@XmlElement(name = "ChallengeId", required = true)
	protected String challengeId;
	@XmlElement(name = "LeftSize")
	protected int leftSize;
	@XmlElement(name = "Pattern", required = true)
	protected String pattern;
	@XmlElement(name = "RightSize")
	protected int rightSize;

	public String getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(String value) {
		this.challengeId = value;
	}

	public int getLeftSize() {
		return leftSize;
	}

	public void setLeftSize(int value) {
		this.leftSize = value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String value) {
		this.pattern = value;
	}

	public int getRightSize() {
		return rightSize;
	}

	public void setRightSize(int value) {
		this.rightSize = value;
	}

}
