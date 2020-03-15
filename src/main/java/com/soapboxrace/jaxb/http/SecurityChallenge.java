
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityChallenge complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SecurityChallenge">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChallengeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RightSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityChallenge", propOrder = {
        "challengeId",
        "leftSize",
        "pattern",
        "rightSize"
})
public class SecurityChallenge {

    @XmlElement(name = "ChallengeId")
    protected String challengeId;
    @XmlElement(name = "LeftSize")
    protected int leftSize;
    @XmlElement(name = "Pattern")
    protected String pattern;
    @XmlElement(name = "RightSize")
    protected int rightSize;

    /**
     * Gets the value of the challengeId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getChallengeId() {
        return challengeId;
    }

    /**
     * Sets the value of the challengeId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setChallengeId(String value) {
        this.challengeId = value;
    }

    /**
     * Gets the value of the leftSize property.
     */
    public int getLeftSize() {
        return leftSize;
    }

    /**
     * Sets the value of the leftSize property.
     */
    public void setLeftSize(int value) {
        this.leftSize = value;
    }

    /**
     * Gets the value of the pattern property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the value of the pattern property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Gets the value of the rightSize property.
     */
    public int getRightSize() {
        return rightSize;
    }

    /**
     * Sets the value of the rightSize property.
     */
    public void setRightSize(int value) {
        this.rightSize = value;
    }

}
