
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
 * <p>Java class for UserInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UserInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultPersonaIdx" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="personas" type="{}ArrayOfProfileData" minOccurs="0"/>
 *         &lt;element name="user" type="{}User" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInfo", propOrder = {
        "defaultPersonaIdx",
        "personas",
        "user"
})
public class UserInfo {

    protected int defaultPersonaIdx;
    protected ArrayOfProfileData personas;
    protected User user;

    /**
     * Gets the value of the defaultPersonaIdx property.
     */
    public int getDefaultPersonaIdx() {
        return defaultPersonaIdx;
    }

    /**
     * Sets the value of the defaultPersonaIdx property.
     */
    public void setDefaultPersonaIdx(int value) {
        this.defaultPersonaIdx = value;
    }

    /**
     * Gets the value of the personas property.
     *
     * @return possible object is
     * {@link ArrayOfProfileData }
     */
    public ArrayOfProfileData getPersonas() {
        return personas;
    }

    /**
     * Sets the value of the personas property.
     *
     * @param value allowed object is
     *              {@link ArrayOfProfileData }
     */
    public void setPersonas(ArrayOfProfileData value) {
        this.personas = value;
    }

    /**
     * Gets the value of the user property.
     *
     * @return possible object is
     * {@link User }
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     *
     * @param value allowed object is
     *              {@link User }
     */
    public void setUser(User value) {
        this.user = value;
    }

}
