
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
 * <p>Java class for BasketItemTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BasketItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasketItemTrans", propOrder = {
        "productId",
        "quantity"
})
public class BasketItemTrans {

    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "Quantity")
    protected int quantity;

    /**
     * Gets the value of the productId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Gets the value of the quantity property.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

}
