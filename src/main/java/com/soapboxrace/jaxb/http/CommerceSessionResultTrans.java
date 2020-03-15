
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CommerceSessionResultTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommerceSessionResultTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InvalidBasket" type="{}InvalidBasketTrans" minOccurs="0"/>
 *         &lt;element name="InventoryItems" type="{}ArrayOfInventoryItemTrans" minOccurs="0"/>
 *         &lt;element name="Status" type="{}CommerceResultStatus"/>
 *         &lt;element name="UpdatedCar" type="{}OwnedCarTrans" minOccurs="0"/>
 *         &lt;element name="Wallets" type="{}ArrayOfWalletTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommerceSessionResultTrans", propOrder = {
        "invalidBasket",
        "inventoryItems",
        "status",
        "updatedCar",
        "wallets"
})
public class CommerceSessionResultTrans {

    @XmlElement(name = "InvalidBasket")
    protected InvalidBasketTrans invalidBasket;
    @XmlElement(name = "InventoryItems")
    protected ArrayOfInventoryItemTrans inventoryItems;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected CommerceResultStatus status;
    @XmlElement(name = "UpdatedCar")
    protected OwnedCarTrans updatedCar;
    @XmlElement(name = "Wallets")
    protected ArrayOfWalletTrans wallets;

    /**
     * Gets the value of the invalidBasket property.
     *
     * @return possible object is
     * {@link InvalidBasketTrans }
     */
    public InvalidBasketTrans getInvalidBasket() {
        return invalidBasket;
    }

    /**
     * Sets the value of the invalidBasket property.
     *
     * @param value allowed object is
     *              {@link InvalidBasketTrans }
     */
    public void setInvalidBasket(InvalidBasketTrans value) {
        this.invalidBasket = value;
    }

    /**
     * Gets the value of the inventoryItems property.
     *
     * @return possible object is
     * {@link ArrayOfInventoryItemTrans }
     */
    public ArrayOfInventoryItemTrans getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets the value of the inventoryItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfInventoryItemTrans }
     */
    public void setInventoryItems(ArrayOfInventoryItemTrans value) {
        this.inventoryItems = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link CommerceResultStatus }
     */
    public CommerceResultStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link CommerceResultStatus }
     */
    public void setStatus(CommerceResultStatus value) {
        this.status = value;
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

    /**
     * Gets the value of the wallets property.
     *
     * @return possible object is
     * {@link ArrayOfWalletTrans }
     */
    public ArrayOfWalletTrans getWallets() {
        return wallets;
    }

    /**
     * Sets the value of the wallets property.
     *
     * @param value allowed object is
     *              {@link ArrayOfWalletTrans }
     */
    public void setWallets(ArrayOfWalletTrans value) {
        this.wallets = value;
    }

}
