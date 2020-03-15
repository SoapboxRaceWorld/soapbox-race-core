
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
 * <p>Java class for TeamEscapeEntrantResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TeamEscapeEntrantResult">
 *   &lt;complexContent>
 *     &lt;extension base="{}EntrantResult">
 *       &lt;sequence>
 *         &lt;element name="DistanceToFinish" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="FractionCompleted" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TeamEscapeEntrantResult", propOrder = {
        "distanceToFinish",
        "fractionCompleted"
})
public class TeamEscapeEntrantResult
        extends EntrantResult {

    @XmlElement(name = "DistanceToFinish")
    protected float distanceToFinish;
    @XmlElement(name = "FractionCompleted")
    protected float fractionCompleted;

    /**
     * Gets the value of the distanceToFinish property.
     */
    public float getDistanceToFinish() {
        return distanceToFinish;
    }

    /**
     * Sets the value of the distanceToFinish property.
     */
    public void setDistanceToFinish(float value) {
        this.distanceToFinish = value;
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

}
