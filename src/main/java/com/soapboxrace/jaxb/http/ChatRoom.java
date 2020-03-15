
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
 * <p>Java class for chatRoom complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="chatRoom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channelCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="longName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chatRoom", propOrder = {
        "channelCount",
        "longName",
        "shortName"
})
public class ChatRoom {

    protected int channelCount;
    protected String longName;
    protected String shortName;

    /**
     * Gets the value of the channelCount property.
     */
    public int getChannelCount() {
        return channelCount;
    }

    /**
     * Sets the value of the channelCount property.
     */
    public void setChannelCount(int value) {
        this.channelCount = value;
    }

    /**
     * Gets the value of the longName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Sets the value of the longName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongName(String value) {
        this.longName = value;
    }

    /**
     * Gets the value of the shortName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

}
