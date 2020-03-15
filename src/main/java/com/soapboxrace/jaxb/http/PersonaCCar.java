
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
 * <p>Java class for PersonaCCar complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PersonaCCar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CCID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Durability" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Heat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="IsDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaCCar", propOrder = {
        "ccid",
        "durability",
        "heat",
        "isDefault",
        "personaId"
})
public class PersonaCCar {

    @XmlElement(name = "CCID")
    protected int ccid;
    @XmlElement(name = "Durability")
    protected int durability;
    @XmlElement(name = "Heat")
    protected float heat;
    @XmlElement(name = "IsDefault")
    protected boolean isDefault;
    @XmlElement(name = "PersonaId")
    protected long personaId;

    /**
     * Gets the value of the ccid property.
     */
    public int getCCID() {
        return ccid;
    }

    /**
     * Sets the value of the ccid property.
     */
    public void setCCID(int value) {
        this.ccid = value;
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
     * Gets the value of the isDefault property.
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     */
    public void setIsDefault(boolean value) {
        this.isDefault = value;
    }

    /**
     * Gets the value of the personaId property.
     */
    public long getPersonaId() {
        return personaId;
    }

    /**
     * Sets the value of the personaId property.
     */
    public void setPersonaId(long value) {
        this.personaId = value;
    }

}
