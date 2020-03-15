
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
 * <p>Java class for ArrayOfVisualPartTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfVisualPartTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VisualPartTrans" type="{}VisualPartTrans" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVisualPartTrans", propOrder = {
        "visualPartTrans"
})
public class ArrayOfVisualPartTrans {

    @XmlElement(name = "VisualPartTrans", nillable = true)
    protected List<VisualPartTrans> visualPartTrans;

    /**
     * Gets the value of the visualPartTrans property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the visualPartTrans property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVisualPartTrans().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VisualPartTrans }
     */
    public List<VisualPartTrans> getVisualPartTrans() {
        if (visualPartTrans == null) {
            visualPartTrans = new ArrayList<VisualPartTrans>();
        }
        return this.visualPartTrans;
    }

}
