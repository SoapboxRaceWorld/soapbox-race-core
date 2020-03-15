
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ArbitrationPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArbitrationPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AlternateEventDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="CarId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="EventDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="FinishReason" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FraudDetectionInfo" type="{}FraudDetection" minOccurs="0"/>
 *         &lt;element name="HacksDetected" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="PhysicsMetrics" type="{}ClientPhysicsMetrics" minOccurs="0"/>
 *         &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Response" type="{}SecurityResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArbitrationPacket", propOrder = {
        "alternateEventDurationInMilliseconds",
        "carId",
        "eventDurationInMilliseconds",
        "finishReason",
        "fraudDetectionInfo",
        "hacksDetected",
        "physicsMetrics",
        "rank",
        "response"
})
@XmlSeeAlso({
        RouteArbitrationPacket.class,
        TeamEscapeArbitrationPacket.class,
        DragArbitrationPacket.class,
        PursuitArbitrationPacket.class
})
public class ArbitrationPacket {

    @XmlElement(name = "AlternateEventDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long alternateEventDurationInMilliseconds;
    @XmlElement(name = "CarId")
    protected long carId;
    @XmlElement(name = "EventDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long eventDurationInMilliseconds;
    @XmlElement(name = "FinishReason")
    protected int finishReason;
    @XmlElement(name = "FraudDetectionInfo")
    protected FraudDetection fraudDetectionInfo;
    @XmlElement(name = "HacksDetected")
    @XmlSchemaType(name = "unsignedInt")
    protected long hacksDetected;
    @XmlElement(name = "PhysicsMetrics")
    protected ClientPhysicsMetrics physicsMetrics;
    @XmlElement(name = "Rank")
    protected int rank;
    @XmlElement(name = "Response")
    protected SecurityResponse response;

    /**
     * Gets the value of the alternateEventDurationInMilliseconds property.
     */
    public long getAlternateEventDurationInMilliseconds() {
        return alternateEventDurationInMilliseconds;
    }

    /**
     * Sets the value of the alternateEventDurationInMilliseconds property.
     */
    public void setAlternateEventDurationInMilliseconds(long value) {
        this.alternateEventDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the carId property.
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets the value of the carId property.
     */
    public void setCarId(long value) {
        this.carId = value;
    }

    /**
     * Gets the value of the eventDurationInMilliseconds property.
     */
    public long getEventDurationInMilliseconds() {
        return eventDurationInMilliseconds;
    }

    /**
     * Sets the value of the eventDurationInMilliseconds property.
     */
    public void setEventDurationInMilliseconds(long value) {
        this.eventDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the finishReason property.
     */
    public int getFinishReason() {
        return finishReason;
    }

    /**
     * Sets the value of the finishReason property.
     */
    public void setFinishReason(int value) {
        this.finishReason = value;
    }

    /**
     * Gets the value of the fraudDetectionInfo property.
     *
     * @return possible object is
     * {@link FraudDetection }
     */
    public FraudDetection getFraudDetectionInfo() {
        return fraudDetectionInfo;
    }

    /**
     * Sets the value of the fraudDetectionInfo property.
     *
     * @param value allowed object is
     *              {@link FraudDetection }
     */
    public void setFraudDetectionInfo(FraudDetection value) {
        this.fraudDetectionInfo = value;
    }

    /**
     * Gets the value of the hacksDetected property.
     */
    public long getHacksDetected() {
        return hacksDetected;
    }

    /**
     * Sets the value of the hacksDetected property.
     */
    public void setHacksDetected(long value) {
        this.hacksDetected = value;
    }

    /**
     * Gets the value of the physicsMetrics property.
     *
     * @return possible object is
     * {@link ClientPhysicsMetrics }
     */
    public ClientPhysicsMetrics getPhysicsMetrics() {
        return physicsMetrics;
    }

    /**
     * Sets the value of the physicsMetrics property.
     *
     * @param value allowed object is
     *              {@link ClientPhysicsMetrics }
     */
    public void setPhysicsMetrics(ClientPhysicsMetrics value) {
        this.physicsMetrics = value;
    }

    /**
     * Gets the value of the rank property.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     */
    public void setRank(int value) {
        this.rank = value;
    }

    /**
     * Gets the value of the response property.
     *
     * @return possible object is
     * {@link SecurityResponse }
     */
    public SecurityResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     *
     * @param value allowed object is
     *              {@link SecurityResponse }
     */
    public void setResponse(SecurityResponse value) {
        this.response = value;
    }

}
