
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
 * <p>Java class for UdpRelayInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UdpRelayInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Host" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UdpRelayInfo", propOrder = {
        "host",
        "port"
})
public class UdpRelayInfo {

    @XmlElement(name = "Host")
    protected String host;
    @XmlElement(name = "Port")
    protected int port;

    /**
     * Gets the value of the host property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the port property.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     */
    public void setPort(int value) {
        this.port = value;
    }

}
