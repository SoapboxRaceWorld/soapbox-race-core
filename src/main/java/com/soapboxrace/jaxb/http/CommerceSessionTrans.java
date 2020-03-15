
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
 * <p>Java class for CommerceSessionTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommerceSessionTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Basket" type="{}BasketTrans" minOccurs="0"/>
 *         &lt;element name="EntitlementsToSell" type="{}EntitlementTrans" minOccurs="0"/>
 *         &lt;element name="UpdatedCar" type="{}OwnedCarTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommerceSessionTrans", propOrder = {
        "basket",
        "entitlementsToSell",
        "updatedCar"
})
public class CommerceSessionTrans {

    @XmlElement(name = "Basket")
    protected BasketTrans basket;
    @XmlElement(name = "EntitlementsToSell")
    protected EntitlementTrans entitlementsToSell;
    @XmlElement(name = "UpdatedCar")
    protected OwnedCarTrans updatedCar;

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
     * Gets the value of the entitlementsToSell property.
     *
     * @return possible object is
     * {@link EntitlementTrans }
     */
    public EntitlementTrans getEntitlementsToSell() {
        return entitlementsToSell;
    }

    /**
     * Sets the value of the entitlementsToSell property.
     *
     * @param value allowed object is
     *              {@link EntitlementTrans }
     */
    public void setEntitlementsToSell(EntitlementTrans value) {
        this.entitlementsToSell = value;
    }

    /**
     * Gets the value of the updatedCar property.
     *
     * @return possible object is
     * {@link OwnedCarTrans }
     */
    public OwnedCarTrans getUpdatedCar() {
        return updatedCar;
    }

    /**
     * Sets the value of the updatedCar property.
     *
     * @param value allowed object is
     *              {@link OwnedCarTrans }
     */
    public void setUpdatedCar(OwnedCarTrans value) {
        this.updatedCar = value;
    }

}
