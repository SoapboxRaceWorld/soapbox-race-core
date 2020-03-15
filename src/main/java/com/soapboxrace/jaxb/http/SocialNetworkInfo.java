
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SocialNetworkInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SocialNetworkInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facebookName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialNetworkInfo", propOrder = {
        "facebookName"
})
public class SocialNetworkInfo {

    protected String facebookName;

    /**
     * Gets the value of the facebookName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFacebookName() {
        return facebookName;
    }

    /**
     * Sets the value of the facebookName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFacebookName(String value) {
        this.facebookName = value;
    }

}
