
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
 * <p>Java class for RegionInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RegionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CountdownProposalInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DirectConnectTimeoutInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DropOutTimeInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EventLoadTimeoutInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HeartbeatIntervalInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UdpRelayBandwidthInBps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UdpRelayTimeoutInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegionInfo", propOrder = {
        "countdownProposalInMilliseconds",
        "directConnectTimeoutInMilliseconds",
        "dropOutTimeInMilliseconds",
        "eventLoadTimeoutInMilliseconds",
        "heartbeatIntervalInMilliseconds",
        "udpRelayBandwidthInBps",
        "udpRelayTimeoutInMilliseconds"
})
public class RegionInfo {

    @XmlElement(name = "CountdownProposalInMilliseconds")
    protected int countdownProposalInMilliseconds;
    @XmlElement(name = "DirectConnectTimeoutInMilliseconds")
    protected int directConnectTimeoutInMilliseconds;
    @XmlElement(name = "DropOutTimeInMilliseconds")
    protected int dropOutTimeInMilliseconds;
    @XmlElement(name = "EventLoadTimeoutInMilliseconds")
    protected int eventLoadTimeoutInMilliseconds;
    @XmlElement(name = "HeartbeatIntervalInMilliseconds")
    protected int heartbeatIntervalInMilliseconds;
    @XmlElement(name = "UdpRelayBandwidthInBps")
    protected int udpRelayBandwidthInBps;
    @XmlElement(name = "UdpRelayTimeoutInMilliseconds")
    protected int udpRelayTimeoutInMilliseconds;

    /**
     * Gets the value of the countdownProposalInMilliseconds property.
     */
    public int getCountdownProposalInMilliseconds() {
        return countdownProposalInMilliseconds;
    }

    /**
     * Sets the value of the countdownProposalInMilliseconds property.
     */
    public void setCountdownProposalInMilliseconds(int value) {
        this.countdownProposalInMilliseconds = value;
    }

    /**
     * Gets the value of the directConnectTimeoutInMilliseconds property.
     */
    public int getDirectConnectTimeoutInMilliseconds() {
        return directConnectTimeoutInMilliseconds;
    }

    /**
     * Sets the value of the directConnectTimeoutInMilliseconds property.
     */
    public void setDirectConnectTimeoutInMilliseconds(int value) {
        this.directConnectTimeoutInMilliseconds = value;
    }

    /**
     * Gets the value of the dropOutTimeInMilliseconds property.
     */
    public int getDropOutTimeInMilliseconds() {
        return dropOutTimeInMilliseconds;
    }

    /**
     * Sets the value of the dropOutTimeInMilliseconds property.
     */
    public void setDropOutTimeInMilliseconds(int value) {
        this.dropOutTimeInMilliseconds = value;
    }

    /**
     * Gets the value of the eventLoadTimeoutInMilliseconds property.
     */
    public int getEventLoadTimeoutInMilliseconds() {
        return eventLoadTimeoutInMilliseconds;
    }

    /**
     * Sets the value of the eventLoadTimeoutInMilliseconds property.
     */
    public void setEventLoadTimeoutInMilliseconds(int value) {
        this.eventLoadTimeoutInMilliseconds = value;
    }

    /**
     * Gets the value of the heartbeatIntervalInMilliseconds property.
     */
    public int getHeartbeatIntervalInMilliseconds() {
        return heartbeatIntervalInMilliseconds;
    }

    /**
     * Sets the value of the heartbeatIntervalInMilliseconds property.
     */
    public void setHeartbeatIntervalInMilliseconds(int value) {
        this.heartbeatIntervalInMilliseconds = value;
    }

    /**
     * Gets the value of the udpRelayBandwidthInBps property.
     */
    public int getUdpRelayBandwidthInBps() {
        return udpRelayBandwidthInBps;
    }

    /**
     * Sets the value of the udpRelayBandwidthInBps property.
     */
    public void setUdpRelayBandwidthInBps(int value) {
        this.udpRelayBandwidthInBps = value;
    }

    /**
     * Gets the value of the udpRelayTimeoutInMilliseconds property.
     */
    public int getUdpRelayTimeoutInMilliseconds() {
        return udpRelayTimeoutInMilliseconds;
    }

    /**
     * Sets the value of the udpRelayTimeoutInMilliseconds property.
     */
    public void setUdpRelayTimeoutInMilliseconds(int value) {
        this.udpRelayTimeoutInMilliseconds = value;
    }

}
