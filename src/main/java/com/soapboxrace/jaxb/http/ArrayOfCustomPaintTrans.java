
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
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfCustomPaintTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfCustomPaintTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomPaintTrans" type="{}CustomPaintTrans" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCustomPaintTrans", propOrder = {
        "customPaintTrans"
})
public class ArrayOfCustomPaintTrans {

    @XmlElement(name = "CustomPaintTrans", nillable = true)
    protected List<CustomPaintTrans> customPaintTrans;

    /**
     * Gets the value of the customPaintTrans property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customPaintTrans property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomPaintTrans().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomPaintTrans }
     */
    public List<CustomPaintTrans> getCustomPaintTrans() {
        if (customPaintTrans == null) {
            customPaintTrans = new ArrayList<CustomPaintTrans>();
        }
        return this.customPaintTrans;
    }

}
