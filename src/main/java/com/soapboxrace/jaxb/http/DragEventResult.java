
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
 * <p>Java class for DragEventResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DragEventResult">
 *   &lt;complexContent>
 *     &lt;extension base="{}EventResult">
 *       &lt;sequence>
 *         &lt;element name="Entrants" type="{}ArrayOfDragEntrantResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DragEventResult", propOrder = {
        "entrants"
})
public class DragEventResult
        extends EventResult {

    @XmlElement(name = "Entrants")
    protected ArrayOfDragEntrantResult entrants;

    /**
     * Gets the value of the entrants property.
     *
     * @return possible object is
     * {@link ArrayOfDragEntrantResult }
     */
    public ArrayOfDragEntrantResult getEntrants() {
        return entrants;
    }

    /**
     * Sets the value of the entrants property.
     *
     * @param value allowed object is
     *              {@link ArrayOfDragEntrantResult }
     */
    public void setEntrants(ArrayOfDragEntrantResult value) {
        this.entrants = value;
    }

}
