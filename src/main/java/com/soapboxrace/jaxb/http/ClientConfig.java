
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
 * <p>Java class for ClientConfig complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ClientConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientConfig", propOrder = {
        "group",
        "level"
})
public class ClientConfig {

    protected String group;
    protected long level;

    /**
     * Gets the value of the group property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGroup(String value) {
        this.group = value;
    }

    /**
     * Gets the value of the level property.
     */
    public long getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel(long value) {
        this.level = value;
    }

}
