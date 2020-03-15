
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
 * <p>Java class for ArrayOfCommerceItemTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfCommerceItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CommerceItemTrans" type="{}CommerceItemTrans" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCommerceItemTrans", propOrder = {
        "commerceItemTrans"
})
public class ArrayOfCommerceItemTrans {

    @XmlElement(name = "CommerceItemTrans", nillable = true)
    protected List<CommerceItemTrans> commerceItemTrans;

    /**
     * Gets the value of the commerceItemTrans property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commerceItemTrans property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommerceItemTrans().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommerceItemTrans }
     */
    public List<CommerceItemTrans> getCommerceItemTrans() {
        if (commerceItemTrans == null) {
            commerceItemTrans = new ArrayList<CommerceItemTrans>();
        }
        return this.commerceItemTrans;
    }

}
