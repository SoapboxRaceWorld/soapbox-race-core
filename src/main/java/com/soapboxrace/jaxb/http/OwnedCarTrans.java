
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OwnedCarTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OwnedCarTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomCar" type="{}CustomCarTrans" minOccurs="0"/>
 *         &lt;element name="Durability" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Heat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OwnershipType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OwnedCarTrans", propOrder = {
        "customCar",
        "durability",
        "expirationDate",
        "heat",
        "id",
        "ownershipType"
})
public class OwnedCarTrans {

    @XmlElement(name = "CustomCar")
    protected CustomCarTrans customCar;
    @XmlElement(name = "Durability")
    protected int durability;
    @XmlElement(name = "ExpirationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "Heat")
    protected float heat;
    @XmlElement(name = "Id")
    protected long id;
    @XmlElement(name = "OwnershipType")
    protected String ownershipType;

    /**
     * Gets the value of the customCar property.
     *
     * @return possible object is
     * {@link CustomCarTrans }
     */
    public CustomCarTrans getCustomCar() {
        return customCar;
    }

    /**
     * Sets the value of the customCar property.
     *
     * @param value allowed object is
     *              {@link CustomCarTrans }
     */
    public void setCustomCar(CustomCarTrans value) {
        this.customCar = value;
    }

    /**
     * Gets the value of the durability property.
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Sets the value of the durability property.
     */
    public void setDurability(int value) {
        this.durability = value;
    }

    /**
     * Gets the value of the expirationDate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the heat property.
     */
    public float getHeat() {
        return heat;
    }

    /**
     * Sets the value of the heat property.
     */
    public void setHeat(float value) {
        this.heat = value;
    }

    /**
     * Gets the value of the id property.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the ownershipType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOwnershipType() {
        return ownershipType;
    }

    /**
     * Sets the value of the ownershipType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOwnershipType(String value) {
        this.ownershipType = value;
    }

}
