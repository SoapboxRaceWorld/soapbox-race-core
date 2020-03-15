
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
 * <p>Java class for BadgeDefinitionPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BadgeDefinitionPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Background" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BadgeDefinitionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Border" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BadgeDefinitionPacket", propOrder = {
        "background",
        "badgeDefinitionId",
        "border",
        "description",
        "icon",
        "name"
})
public class BadgeDefinitionPacket {

    @XmlElement(name = "Background")
    protected String background;
    @XmlElement(name = "BadgeDefinitionId")
    protected int badgeDefinitionId;
    @XmlElement(name = "Border")
    protected String border;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Icon")
    protected String icon;
    @XmlElement(name = "Name")
    protected String name;

    /**
     * Gets the value of the background property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBackground() {
        return background;
    }

    /**
     * Sets the value of the background property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * Gets the value of the badgeDefinitionId property.
     */
    public int getBadgeDefinitionId() {
        return badgeDefinitionId;
    }

    /**
     * Sets the value of the badgeDefinitionId property.
     */
    public void setBadgeDefinitionId(int value) {
        this.badgeDefinitionId = value;
    }

    /**
     * Gets the value of the border property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBorder() {
        return border;
    }

    /**
     * Sets the value of the border property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBorder(String value) {
        this.border = value;
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
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

}
