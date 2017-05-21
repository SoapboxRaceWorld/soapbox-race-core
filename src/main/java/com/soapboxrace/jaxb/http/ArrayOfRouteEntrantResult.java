
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRouteEntrantResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRouteEntrantResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RouteEntrantResult" type="{}RouteEntrantResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRouteEntrantResult", propOrder = {
    "routeEntrantResult"
})
public class ArrayOfRouteEntrantResult {

    @XmlElement(name = "RouteEntrantResult", nillable = true)
    protected List<RouteEntrantResult> routeEntrantResult;

    /**
     * Gets the value of the routeEntrantResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the routeEntrantResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRouteEntrantResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RouteEntrantResult }
     * 
     * 
     */
    public List<RouteEntrantResult> getRouteEntrantResult() {
        if (routeEntrantResult == null) {
            routeEntrantResult = new ArrayList<RouteEntrantResult>();
        }
        return this.routeEntrantResult;
    }

}
