
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
 * <p>Java class for LuckyDrawBox complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LuckyDrawBox">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LocalizationString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LuckyDrawSetCategoryId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuckyDrawBox", propOrder = {
        "isValid",
        "localizationString",
        "luckyDrawSetCategoryId"
})
public class LuckyDrawBox {

    @XmlElement(name = "IsValid")
    protected boolean isValid;
    @XmlElement(name = "LocalizationString")
    protected String localizationString;
    @XmlElement(name = "LuckyDrawSetCategoryId")
    protected int luckyDrawSetCategoryId;

    /**
     * Gets the value of the isValid property.
     */
    public boolean isIsValid() {
        return isValid;
    }

    /**
     * Sets the value of the isValid property.
     */
    public void setIsValid(boolean value) {
        this.isValid = value;
    }

    /**
     * Gets the value of the localizationString property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLocalizationString() {
        return localizationString;
    }

    /**
     * Sets the value of the localizationString property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocalizationString(String value) {
        this.localizationString = value;
    }

    /**
     * Gets the value of the luckyDrawSetCategoryId property.
     */
    public int getLuckyDrawSetCategoryId() {
        return luckyDrawSetCategoryId;
    }

    /**
     * Sets the value of the luckyDrawSetCategoryId property.
     */
    public void setLuckyDrawSetCategoryId(int value) {
        this.luckyDrawSetCategoryId = value;
    }

}
