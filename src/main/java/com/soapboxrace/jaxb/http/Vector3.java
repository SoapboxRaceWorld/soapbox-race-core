
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
 * <p>Java class for Vector3 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Vector3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="X" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Y" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Z" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vector3", propOrder = {
        "x",
        "y",
        "z"
})
public class Vector3 {

    @XmlElement(name = "X")
    protected float x;
    @XmlElement(name = "Y")
    protected float y;
    @XmlElement(name = "Z")
    protected float z;

    /**
     * Gets the value of the x property.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     */
    public void setX(float value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     */
    public void setY(float value) {
        this.y = value;
    }

    /**
     * Gets the value of the z property.
     */
    public float getZ() {
        return z;
    }

    /**
     * Sets the value of the z property.
     */
    public void setZ(float value) {
        this.z = value;
    }

}
