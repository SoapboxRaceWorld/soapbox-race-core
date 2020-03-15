
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
 * <p>Java class for DragEntrantResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DragEntrantResult">
 *   &lt;complexContent>
 *     &lt;extension base="{}EntrantResult">
 *       &lt;sequence>
 *         &lt;element name="TopSpeed" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DragEntrantResult", propOrder = {
        "topSpeed"
})
public class DragEntrantResult
        extends EntrantResult {

    @XmlElement(name = "TopSpeed")
    protected float topSpeed;

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
