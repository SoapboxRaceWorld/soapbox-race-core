
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
 * <p>Java class for CarClass complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CarClass">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarClassHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxRating" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="MinRating" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarClass", propOrder = {
        "carClassHash",
        "maxRating",
        "minRating"
})
public class CarClass {

    @XmlElement(name = "CarClassHash")
    protected int carClassHash;
    @XmlElement(name = "MaxRating")
    protected short maxRating;
    @XmlElement(name = "MinRating")
    protected short minRating;

    /**
     * Gets the value of the carClassHash property.
     */
    public int getCarClassHash() {
        return carClassHash;
    }

    /**
     * Sets the value of the carClassHash property.
     */
    public void setCarClassHash(int value) {
        this.carClassHash = value;
    }

    /**
     * Gets the value of the maxRating property.
     */
    public short getMaxRating() {
        return maxRating;
    }

    /**
     * Sets the value of the maxRating property.
     */
    public void setMaxRating(short value) {
        this.maxRating = value;
    }

    /**
     * Gets the value of the minRating property.
     */
    public short getMinRating() {
        return minRating;
    }

    /**
     * Sets the value of the minRating property.
     */
    public void setMinRating(short value) {
        this.minRating = value;
    }

}
