
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaFriendsList complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PersonaFriendsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="friendPersona" type="{}ArrayOfFriendPersona" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaFriendsList", propOrder = {
        "friendPersona"
})
public class PersonaFriendsList {

    protected ArrayOfFriendPersona friendPersona;

    /**
     * Gets the value of the friendPersona property.
     *
     * @return possible object is
     * {@link ArrayOfFriendPersona }
     */
    public ArrayOfFriendPersona getFriendPersona() {
        return friendPersona;
    }

    /**
     * Sets the value of the friendPersona property.
     *
     * @param value allowed object is
     *              {@link ArrayOfFriendPersona }
     */
    public void setFriendPersona(ArrayOfFriendPersona value) {
        this.friendPersona = value;
    }

}
