
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
 * <p>Java class for PursuitEventResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PursuitEventResult">
 *   &lt;complexContent>
 *     &lt;extension base="{}EventResult">
 *       &lt;sequence>
 *         &lt;element name="Heat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PursuitEventResult", propOrder = {
        "heat"
})
public class PursuitEventResult
        extends EventResult {

    @XmlElement(name = "Heat")
    protected float heat;

    /**
     * Gets the value of the heat property.
     */
    public float getHeat() {
        return heat;
    }

    /**
     * Sets the value of the heat property.
     */
    public void setHeat(float value) {
        this.heat = value;
    }

}
