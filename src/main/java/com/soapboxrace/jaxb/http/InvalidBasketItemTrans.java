
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
 * <p>Java class for InvalidBasketItemTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="InvalidBasketItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidBasketItemTrans", propOrder = {
        "index",
        "reason"
})
public class InvalidBasketItemTrans {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Reason")
    protected int reason;

    /**
     * Gets the value of the index property.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the reason property.
     */
    public int getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     */
    public void setReason(int value) {
        this.reason = value;
    }

}
