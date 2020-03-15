
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
 * <p>Java class for LuckyDrawItem complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LuckyDrawItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingUseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ResellPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="VirtualItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VirtualItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WasSold" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuckyDrawItem", propOrder = {
        "description",
        "hash",
        "icon",
        "remainingUseCount",
        "resellPrice",
        "virtualItem",
        "virtualItemType",
        "wasSold"
})
public class LuckyDrawItem {

    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "Icon")
    protected String icon;
    @XmlElement(name = "RemainingUseCount")
    protected int remainingUseCount;
    @XmlElement(name = "ResellPrice")
    protected float resellPrice;
    @XmlElement(name = "VirtualItem")
    protected String virtualItem;
    @XmlElement(name = "VirtualItemType")
    protected String virtualItemType;
    @XmlElement(name = "WasSold")
    protected boolean wasSold;

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the icon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the value of the icon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIcon(String value) {
        this.icon = value;
    }

    /**
     * Gets the value of the remainingUseCount property.
     */
    public int getRemainingUseCount() {
        return remainingUseCount;
    }

    /**
     * Sets the value of the remainingUseCount property.
     */
    public void setRemainingUseCount(int value) {
        this.remainingUseCount = value;
    }

    /**
     * Gets the value of the resellPrice property.
     */
    public float getResellPrice() {
        return resellPrice;
    }

    /**
     * Sets the value of the resellPrice property.
     */
    public void setResellPrice(float value) {
        this.resellPrice = value;
    }

    /**
     * Gets the value of the virtualItem property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVirtualItem() {
        return virtualItem;
    }

    /**
     * Sets the value of the virtualItem property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVirtualItem(String value) {
        this.virtualItem = value;
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

    /**
     * Gets the value of the wasSold property.
     */
    public boolean isWasSold() {
        return wasSold;
    }

    /**
     * Sets the value of the wasSold property.
     */
    public void setWasSold(boolean value) {
        this.wasSold = value;
    }

}
