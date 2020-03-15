
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InventoryItemTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="InventoryItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EntitlementTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InventoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingUseCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResellPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StringHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VirtualItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryItemTrans", propOrder = {
        "entitlementTag",
        "expirationDate",
        "hash",
        "inventoryId",
        "productId",
        "remainingUseCount",
        "resellPrice",
        "status",
        "stringHash",
        "virtualItemType"
})
public class InventoryItemTrans {

    @XmlElement(name = "EntitlementTag")
    protected String entitlementTag;
    @XmlElement(name = "ExpirationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected String expirationDate;
    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "InventoryId")
    protected long inventoryId;
    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "RemainingUseCount")
    protected long remainingUseCount;
    @XmlElement(name = "ResellPrice")
    protected double resellPrice;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StringHash")
    protected String stringHash;
    @XmlElement(name = "VirtualItemType")
    protected String virtualItemType;

    /**
     * Gets the value of the entitlementTag property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEntitlementTag() {
        return entitlementTag;
    }

    /**
     * Sets the value of the entitlementTag property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEntitlementTag(String value) {
        this.entitlementTag = value;
    }

    /**
     * Gets the value of the expirationDate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the hash property.
     */
    public int getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     */
    public void setHash(int value) {
        this.hash = value;
    }

    /**
     * Gets the value of the inventoryId property.
     */
    public long getInventoryId() {
        return inventoryId;
    }

    /**
     * Sets the value of the inventoryId property.
     */
    public void setInventoryId(long value) {
        this.inventoryId = value;
    }

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
     * Gets the value of the remainingUseCount property.
     */
    public long getRemainingUseCount() {
        return remainingUseCount;
    }

    /**
     * Sets the value of the remainingUseCount property.
     */
    public void setRemainingUseCount(long value) {
        this.remainingUseCount = value;
    }

    /**
     * Gets the value of the resellPrice property.
     */
    public double getResellPrice() {
        return resellPrice;
    }

    /**
     * Sets the value of the resellPrice property.
     */
    public void setResellPrice(double value) {
        this.resellPrice = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the stringHash property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStringHash() {
        return stringHash;
    }

    /**
     * Sets the value of the stringHash property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStringHash(String value) {
        this.stringHash = value;
    }

    /**
     * Gets the value of the virtualItemType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVirtualItemType() {
        return virtualItemType;
    }

    /**
     * Sets the value of the virtualItemType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVirtualItemType(String value) {
        this.virtualItemType = value;
    }

}
