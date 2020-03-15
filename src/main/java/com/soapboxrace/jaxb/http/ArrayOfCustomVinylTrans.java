
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
 * <p>Java class for ArrayOfCustomVinylTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ArrayOfCustomVinylTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomVinylTrans" type="{}CustomVinylTrans" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCustomVinylTrans", propOrder = {
        "customVinylTrans"
})
public class ArrayOfCustomVinylTrans {

    @XmlElement(name = "CustomVinylTrans", nillable = true)
    protected List<CustomVinylTrans> customVinylTrans;

    /**
     * Gets the value of the customVinylTrans property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customVinylTrans property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomVinylTrans().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomVinylTrans }
     */
    public List<CustomVinylTrans> getCustomVinylTrans() {
        if (customVinylTrans == null) {
            customVinylTrans = new ArrayList<CustomVinylTrans>();
        }
        return this.customVinylTrans;
    }

}
