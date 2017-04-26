
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de RegionInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 * 
 * 
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
     * Obtém o valor da propriedade countdownProposalInMilliseconds.
     * 
     */
    public int getCountdownProposalInMilliseconds() {
        return countdownProposalInMilliseconds;
    }

    /**
     * Define o valor da propriedade countdownProposalInMilliseconds.
     * 
     */
    public void setCountdownProposalInMilliseconds(int value) {
        this.countdownProposalInMilliseconds = value;
    }

    /**
     * Obtém o valor da propriedade directConnectTimeoutInMilliseconds.
     * 
     */
    public int getDirectConnectTimeoutInMilliseconds() {
        return directConnectTimeoutInMilliseconds;
    }

    /**
     * Define o valor da propriedade directConnectTimeoutInMilliseconds.
     * 
     */
    public void setDirectConnectTimeoutInMilliseconds(int value) {
        this.directConnectTimeoutInMilliseconds = value;
    }

    /**
     * Obtém o valor da propriedade dropOutTimeInMilliseconds.
     * 
     */
    public int getDropOutTimeInMilliseconds() {
        return dropOutTimeInMilliseconds;
    }

    /**
     * Define o valor da propriedade dropOutTimeInMilliseconds.
     * 
     */
    public void setDropOutTimeInMilliseconds(int value) {
        this.dropOutTimeInMilliseconds = value;
    }

    /**
     * Obtém o valor da propriedade eventLoadTimeoutInMilliseconds.
     * 
     */
    public int getEventLoadTimeoutInMilliseconds() {
        return eventLoadTimeoutInMilliseconds;
    }

    /**
     * Define o valor da propriedade eventLoadTimeoutInMilliseconds.
     * 
     */
    public void setEventLoadTimeoutInMilliseconds(int value) {
        this.eventLoadTimeoutInMilliseconds = value;
    }

    /**
     * Obtém o valor da propriedade heartbeatIntervalInMilliseconds.
     * 
     */
    public int getHeartbeatIntervalInMilliseconds() {
        return heartbeatIntervalInMilliseconds;
    }

    /**
     * Define o valor da propriedade heartbeatIntervalInMilliseconds.
     * 
     */
    public void setHeartbeatIntervalInMilliseconds(int value) {
        this.heartbeatIntervalInMilliseconds = value;
    }

    /**
     * Obtém o valor da propriedade udpRelayBandwidthInBps.
     * 
     */
    public int getUdpRelayBandwidthInBps() {
        return udpRelayBandwidthInBps;
    }

    /**
     * Define o valor da propriedade udpRelayBandwidthInBps.
     * 
     */
    public void setUdpRelayBandwidthInBps(int value) {
        this.udpRelayBandwidthInBps = value;
    }

    /**
     * Obtém o valor da propriedade udpRelayTimeoutInMilliseconds.
     * 
     */
    public int getUdpRelayTimeoutInMilliseconds() {
        return udpRelayTimeoutInMilliseconds;
    }

    /**
     * Define o valor da propriedade udpRelayTimeoutInMilliseconds.
     * 
     */
    public void setUdpRelayTimeoutInMilliseconds(int value) {
        this.udpRelayTimeoutInMilliseconds = value;
    }

}
