
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
 * <p>Java class for ProductTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProductTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BundleItems" type="{}ArrayOfProductTrans" minOccurs="0"/>
 *         &lt;element name="CategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DurationMinute" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LongDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondaryIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VisualStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductTrans", propOrder = {
        "bundleItems",
        "categoryId",
        "currency",
        "description",
        "durationMinute",
        "hash",
        "icon",
        "level",
        "longDescription",
        "price",
        "priority",
        "productId",
        "productTitle",
        "productType",
        "secondaryIcon",
        "useCount",
        "visualStyle",
        "webIcon",
        "webLocation"
})
public class ProductTrans {

    @XmlElement(name = "BundleItems")
    protected ArrayOfProductTrans bundleItems;
    @XmlElement(name = "CategoryId")
    protected String categoryId;
    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "DurationMinute")
    protected int durationMinute;
    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "Icon")
    protected String icon;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "LongDescription")
    protected String longDescription;
    @XmlElement(name = "Price")
    protected double price;
    @XmlElement(name = "Priority")
    protected int priority;
    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "ProductTitle")
    protected String productTitle;
    @XmlElement(name = "ProductType")
    protected String productType;
    @XmlElement(name = "SecondaryIcon")
    protected String secondaryIcon;
    @XmlElement(name = "UseCount")
    protected int useCount;
    @XmlElement(name = "VisualStyle")
    protected String visualStyle;
    @XmlElement(name = "WebIcon")
    protected String webIcon;
    @XmlElement(name = "WebLocation")
    protected String webLocation;

    /**
     * Gets the value of the bundleItems property.
     *
     * @return possible object is
     * {@link ArrayOfProductTrans }
     */
    public ArrayOfProductTrans getBundleItems() {
        return bundleItems;
    }

    /**
     * Sets the value of the bundleItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfProductTrans }
     */
    public void setBundleItems(ArrayOfProductTrans value) {
        this.bundleItems = value;
    }

    /**
     * Gets the value of the categoryId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the value of the categoryId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCategoryId(String value) {
        this.categoryId = value;
    }

    /**
     * Gets the value of the currency property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

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
     * Gets the value of the durationMinute property.
     */
    public int getDurationMinute() {
        return durationMinute;
    }

    /**
     * Sets the value of the durationMinute property.
     */
    public void setDurationMinute(int value) {
        this.durationMinute = value;
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
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the longDescription property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the price property.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the priority property.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     */
    public void setPriority(int value) {
        this.priority = value;
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
     * Gets the value of the productTitle property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductTitle() {
        return productTitle;
    }

    /**
     * Sets the value of the productTitle property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductTitle(String value) {
        this.productTitle = value;
    }

    /**
     * Gets the value of the productType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * Gets the value of the secondaryIcon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSecondaryIcon() {
        return secondaryIcon;
    }

    /**
     * Sets the value of the secondaryIcon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSecondaryIcon(String value) {
        this.secondaryIcon = value;
    }

    /**
     * Gets the value of the useCount property.
     */
    public int getUseCount() {
        return useCount;
    }

    /**
     * Sets the value of the useCount property.
     */
    public void setUseCount(int value) {
        this.useCount = value;
    }

    /**
     * Gets the value of the visualStyle property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVisualStyle() {
        return visualStyle;
    }

    /**
     * Sets the value of the visualStyle property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVisualStyle(String value) {
        this.visualStyle = value;
    }

    /**
     * Gets the value of the webIcon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWebIcon() {
        return webIcon;
    }

    /**
     * Sets the value of the webIcon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWebIcon(String value) {
        this.webIcon = value;
    }

    /**
     * Gets the value of the webLocation property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWebLocation() {
        return webLocation;
    }

    /**
     * Sets the value of the webLocation property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWebLocation(String value) {
        this.webLocation = value;
    }

}
