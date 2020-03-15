
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
 * <p>Java class for LoginAnnouncementsDefinition complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LoginAnnouncementsDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Announcements" type="{}ArrayOfLoginAnnouncementDefinition" minOccurs="0"/>
 *         &lt;element name="ImagesPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginAnnouncementsDefinition", propOrder = {
        "announcements",
        "imagesPath"
})
public class LoginAnnouncementsDefinition {

    @XmlElement(name = "Announcements")
    protected ArrayOfLoginAnnouncementDefinition announcements;
    @XmlElement(name = "ImagesPath")
    protected String imagesPath;

    /**
     * Gets the value of the announcements property.
     *
     * @return possible object is
     * {@link ArrayOfLoginAnnouncementDefinition }
     */
    public ArrayOfLoginAnnouncementDefinition getAnnouncements() {
        return announcements;
    }

    /**
     * Sets the value of the announcements property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLoginAnnouncementDefinition }
     */
    public void setAnnouncements(ArrayOfLoginAnnouncementDefinition value) {
        this.announcements = value;
    }

    /**
     * Gets the value of the imagesPath property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getImagesPath() {
        return imagesPath;
    }

    /**
     * Sets the value of the imagesPath property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImagesPath(String value) {
        this.imagesPath = value;
    }

}
