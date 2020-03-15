
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for LoginAnnouncementDefinition complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LoginAnnouncementDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Context" type="{}LoginAnnouncementContext"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImageChecksum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Target" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{}LoginAnnouncementType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginAnnouncementDefinition", propOrder = {
        "context",
        "id",
        "imageChecksum",
        "imageUrl",
        "target",
        "type"
})
public class LoginAnnouncementDefinition {

    @XmlElement(name = "Context", required = true)
    @XmlSchemaType(name = "string")
    protected LoginAnnouncementContext context;
    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "ImageChecksum")
    protected int imageChecksum;
    @XmlElement(name = "ImageUrl")
    protected String imageUrl;
    @XmlElement(name = "Target")
    protected String target;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected LoginAnnouncementType type;

    /**
     * Gets the value of the context property.
     *
     * @return possible object is
     * {@link LoginAnnouncementContext }
     */
    public LoginAnnouncementContext getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     *
     * @param value allowed object is
     *              {@link LoginAnnouncementContext }
     */
    public void setContext(LoginAnnouncementContext value) {
        this.context = value;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the imageChecksum property.
     */
    public int getImageChecksum() {
        return imageChecksum;
    }

    /**
     * Sets the value of the imageChecksum property.
     */
    public void setImageChecksum(int value) {
        this.imageChecksum = value;
    }

    /**
     * Gets the value of the imageUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the value of the imageUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     * {@link LoginAnnouncementType }
     */
    public LoginAnnouncementType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link LoginAnnouncementType }
     */
    public void setType(LoginAnnouncementType value) {
        this.type = value;
    }

}
