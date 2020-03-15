
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
 * <p>Java class for PersonaIdArray complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PersonaIdArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonaIds" type="{}ArrayOfLong" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaIdArray", propOrder = {
        "personaIds"
})
public class PersonaIdArray {

    @XmlElement(name = "PersonaIds")
    protected ArrayOfLong personaIds;

    /**
     * Gets the value of the personaIds property.
     *
     * @return possible object is
     * {@link ArrayOfLong }
     */
    public ArrayOfLong getPersonaIds() {
        return personaIds;
    }

    /**
     * Sets the value of the personaIds property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLong }
     */
    public void setPersonaIds(ArrayOfLong value) {
        this.personaIds = value;
    }

}
