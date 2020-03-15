
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
 * <p>Java class for EntitlementTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EntitlementTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Items" type="{}ArrayOfEntitlementItemTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntitlementTrans", propOrder = {
        "items"
})
public class EntitlementTrans {

    @XmlElement(name = "Items")
    protected ArrayOfEntitlementItemTrans items;

    /**
     * Gets the value of the items property.
     *
     * @return possible object is
     * {@link ArrayOfEntitlementItemTrans }
     */
    public ArrayOfEntitlementItemTrans getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     *
     * @param value allowed object is
     *              {@link ArrayOfEntitlementItemTrans }
     */
    public void setItems(ArrayOfEntitlementItemTrans value) {
        this.items = value;
    }

}
