
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
 * <p>Java class for InvalidBasketTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="InvalidBasketTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Basket" type="{}BasketTrans" minOccurs="0"/>
 *         &lt;element name="InvalidItems" type="{}ArrayOfInvalidBasketItemTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidBasketTrans", propOrder = {
        "basket",
        "invalidItems"
})
public class InvalidBasketTrans {

    @XmlElement(name = "Basket")
    protected BasketTrans basket;
    @XmlElement(name = "InvalidItems")
    protected ArrayOfInvalidBasketItemTrans invalidItems;

    /**
     * Gets the value of the basket property.
     *
     * @return possible object is
     * {@link BasketTrans }
     */
    public BasketTrans getBasket() {
        return basket;
    }

    /**
     * Sets the value of the basket property.
     *
     * @param value allowed object is
     *              {@link BasketTrans }
     */
    public void setBasket(BasketTrans value) {
        this.basket = value;
    }

    /**
     * Gets the value of the invalidItems property.
     *
     * @return possible object is
     * {@link ArrayOfInvalidBasketItemTrans }
     */
    public ArrayOfInvalidBasketItemTrans getInvalidItems() {
        return invalidItems;
    }

    /**
     * Sets the value of the invalidItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfInvalidBasketItemTrans }
     */
    public void setInvalidItems(ArrayOfInvalidBasketItemTrans value) {
        this.invalidItems = value;
    }

}
