
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
 * <p>Java class for UdpRelayCryptoTicket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UdpRelayCryptoTicket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CryptoTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SessionKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TicketIv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UdpRelayCryptoTicket", propOrder = {
        "cryptoTicket",
        "sessionKey",
        "ticketIv"
})
public class UdpRelayCryptoTicket {

    @XmlElement(name = "CryptoTicket")
    protected String cryptoTicket;
    @XmlElement(name = "SessionKey")
    protected String sessionKey;
    @XmlElement(name = "TicketIv")
    protected String ticketIv;

    /**
     * Gets the value of the cryptoTicket property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCryptoTicket() {
        return cryptoTicket;
    }

    /**
     * Sets the value of the cryptoTicket property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCryptoTicket(String value) {
        this.cryptoTicket = value;
    }

    /**
     * Gets the value of the sessionKey property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Sets the value of the sessionKey property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSessionKey(String value) {
        this.sessionKey = value;
    }

    /**
     * Gets the value of the ticketIv property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTicketIv() {
        return ticketIv;
    }

    /**
     * Sets the value of the ticketIv property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTicketIv(String value) {
        this.ticketIv = value;
    }

}
