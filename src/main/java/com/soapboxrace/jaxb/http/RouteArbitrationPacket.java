
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for RouteArbitrationPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RouteArbitrationPacket">
 *   &lt;complexContent>
 *     &lt;extension base="{}ArbitrationPacket">
 *       &lt;sequence>
 *         &lt;element name="BestLapDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="FractionCompleted" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="LongestJumpDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="NumberOfCollisions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PerfectStart" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SumOfJumpsDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="TopSpeed" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteArbitrationPacket", propOrder = {
        "bestLapDurationInMilliseconds",
        "fractionCompleted",
        "longestJumpDurationInMilliseconds",
        "numberOfCollisions",
        "perfectStart",
        "sumOfJumpsDurationInMilliseconds",
        "topSpeed"
})
public class RouteArbitrationPacket
        extends ArbitrationPacket {

    @XmlElement(name = "BestLapDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long bestLapDurationInMilliseconds;
    @XmlElement(name = "FractionCompleted")
    protected float fractionCompleted;
    @XmlElement(name = "LongestJumpDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long longestJumpDurationInMilliseconds;
    @XmlElement(name = "NumberOfCollisions")
    protected int numberOfCollisions;
    @XmlElement(name = "PerfectStart")
    protected int perfectStart;
    @XmlElement(name = "SumOfJumpsDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long sumOfJumpsDurationInMilliseconds;
    @XmlElement(name = "TopSpeed")
    protected float topSpeed;

    /**
     * Gets the value of the bestLapDurationInMilliseconds property.
     */
    public long getBestLapDurationInMilliseconds() {
        return bestLapDurationInMilliseconds;
    }

    /**
     * Sets the value of the bestLapDurationInMilliseconds property.
     */
    public void setBestLapDurationInMilliseconds(long value) {
        this.bestLapDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the fractionCompleted property.
     */
    public float getFractionCompleted() {
        return fractionCompleted;
    }

    /**
     * Sets the value of the fractionCompleted property.
     */
    public void setFractionCompleted(float value) {
        this.fractionCompleted = value;
    }

    /**
     * Gets the value of the longestJumpDurationInMilliseconds property.
     */
    public long getLongestJumpDurationInMilliseconds() {
        return longestJumpDurationInMilliseconds;
    }

    /**
     * Sets the value of the longestJumpDurationInMilliseconds property.
     */
    public void setLongestJumpDurationInMilliseconds(long value) {
        this.longestJumpDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the numberOfCollisions property.
     */
    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    /**
     * Sets the value of the numberOfCollisions property.
     */
    public void setNumberOfCollisions(int value) {
        this.numberOfCollisions = value;
    }

    /**
     * Gets the value of the perfectStart property.
     */
    public int getPerfectStart() {
        return perfectStart;
    }

    /**
     * Sets the value of the perfectStart property.
     */
    public void setPerfectStart(int value) {
        this.perfectStart = value;
    }

    /**
     * Gets the value of the sumOfJumpsDurationInMilliseconds property.
     */
    public long getSumOfJumpsDurationInMilliseconds() {
        return sumOfJumpsDurationInMilliseconds;
    }

    /**
     * Sets the value of the sumOfJumpsDurationInMilliseconds property.
     */
    public void setSumOfJumpsDurationInMilliseconds(long value) {
        this.sumOfJumpsDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the topSpeed property.
     */
    public float getTopSpeed() {
        return topSpeed;
    }

    /**
     * Sets the value of the topSpeed property.
     */
    public void setTopSpeed(float value) {
        this.topSpeed = value;
    }

}
