
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for NewsArticleTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="NewsArticleTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExpiryTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Filters" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IconType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LongText_HALId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewsId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Parameters" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ShortText_HALId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sticky" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NewsArticleTrans", propOrder = {
        "expiryTime",
        "filters",
        "iconType",
        "longTextHALId",
        "newsId",
        "parameters",
        "personaId",
        "shortTextHALId",
        "sticky",
        "timestamp",
        "type"
})
public class NewsArticleTrans {

    @XmlElement(name = "ExpiryTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiryTime;
    @XmlElement(name = "Filters")
    protected int filters;
    @XmlElement(name = "IconType")
    protected int iconType;
    @XmlElement(name = "LongText_HALId")
    protected String longTextHALId;
    @XmlElement(name = "NewsId")
    protected long newsId;
    @XmlElement(name = "Parameters")
    protected String parameters;
    @XmlElement(name = "PersonaId")
    protected long personaId;
    @XmlElement(name = "ShortText_HALId")
    protected String shortTextHALId;
    @XmlElement(name = "Sticky")
    protected int sticky;
    @XmlElement(name = "Timestamp")
    protected long timestamp;
    @XmlElement(name = "Type")
    protected int type;

    /**
     * Gets the value of the expiryTime property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getExpiryTime() {
        return expiryTime;
    }

    /**
     * Sets the value of the expiryTime property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setExpiryTime(XMLGregorianCalendar value) {
        this.expiryTime = value;
    }

    /**
     * Gets the value of the filters property.
     */
    public int getFilters() {
        return filters;
    }

    /**
     * Sets the value of the filters property.
     */
    public void setFilters(int value) {
        this.filters = value;
    }

    /**
     * Gets the value of the iconType property.
     */
    public int getIconType() {
        return iconType;
    }

    /**
     * Sets the value of the iconType property.
     */
    public void setIconType(int value) {
        this.iconType = value;
    }

    /**
     * Gets the value of the longTextHALId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLongTextHALId() {
        return longTextHALId;
    }

    /**
     * Sets the value of the longTextHALId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongTextHALId(String value) {
        this.longTextHALId = value;
    }

    /**
     * Gets the value of the newsId property.
     */
    public long getNewsId() {
        return newsId;
    }

    /**
     * Sets the value of the newsId property.
     */
    public void setNewsId(long value) {
        this.newsId = value;
    }

    /**
     * Gets the value of the parameters property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setParameters(String value) {
        this.parameters = value;
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

    /**
     * Gets the value of the shortTextHALId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getShortTextHALId() {
        return shortTextHALId;
    }

    /**
     * Sets the value of the shortTextHALId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShortTextHALId(String value) {
        this.shortTextHALId = value;
    }

    /**
     * Gets the value of the sticky property.
     */
    public int getSticky() {
        return sticky;
    }

    /**
     * Sets the value of the sticky property.
     */
    public void setSticky(int value) {
        this.sticky = value;
    }

    /**
     * Gets the value of the timestamp property.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the type property.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     */
    public void setType(int value) {
        this.type = value;
    }

}
