
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
 * <p>Java class for ClientPhysicsMetrics complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ClientPhysicsMetrics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccelerationAverage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="AccelerationMaximum" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="AccelerationMedian" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SpeedAverage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SpeedMaximum" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SpeedMedian" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientPhysicsMetrics", propOrder = {
        "accelerationAverage",
        "accelerationMaximum",
        "accelerationMedian",
        "speedAverage",
        "speedMaximum",
        "speedMedian"
})
public class ClientPhysicsMetrics {

    @XmlElement(name = "AccelerationAverage")
    protected float accelerationAverage;
    @XmlElement(name = "AccelerationMaximum")
    protected float accelerationMaximum;
    @XmlElement(name = "AccelerationMedian")
    protected float accelerationMedian;
    @XmlElement(name = "SpeedAverage")
    protected float speedAverage;
    @XmlElement(name = "SpeedMaximum")
    protected float speedMaximum;
    @XmlElement(name = "SpeedMedian")
    protected float speedMedian;

    /**
     * Gets the value of the accelerationAverage property.
     */
    public float getAccelerationAverage() {
        return accelerationAverage;
    }

    /**
     * Sets the value of the accelerationAverage property.
     */
    public void setAccelerationAverage(float value) {
        this.accelerationAverage = value;
    }

    /**
     * Gets the value of the accelerationMaximum property.
     */
    public float getAccelerationMaximum() {
        return accelerationMaximum;
    }

    /**
     * Sets the value of the accelerationMaximum property.
     */
    public void setAccelerationMaximum(float value) {
        this.accelerationMaximum = value;
    }

    /**
     * Gets the value of the accelerationMedian property.
     */
    public float getAccelerationMedian() {
        return accelerationMedian;
    }

    /**
     * Sets the value of the accelerationMedian property.
     */
    public void setAccelerationMedian(float value) {
        this.accelerationMedian = value;
    }

    /**
     * Gets the value of the speedAverage property.
     */
    public float getSpeedAverage() {
        return speedAverage;
    }

    /**
     * Sets the value of the speedAverage property.
     */
    public void setSpeedAverage(float value) {
        this.speedAverage = value;
    }

    /**
     * Gets the value of the speedMaximum property.
     */
    public float getSpeedMaximum() {
        return speedMaximum;
    }

    /**
     * Sets the value of the speedMaximum property.
     */
    public void setSpeedMaximum(float value) {
        this.speedMaximum = value;
    }

    /**
     * Gets the value of the speedMedian property.
     */
    public float getSpeedMedian() {
        return speedMedian;
    }

    /**
     * Sets the value of the speedMedian property.
     */
    public void setSpeedMedian(float value) {
        this.speedMedian = value;
    }

}
