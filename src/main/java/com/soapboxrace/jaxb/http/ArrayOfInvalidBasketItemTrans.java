
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInvalidBasketItemTrans complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInvalidBasketItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InvalidBasketItemTrans" type="{}InvalidBasketItemTrans" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInvalidBasketItemTrans", propOrder = {
    "invalidBasketItemTrans"
})
public class ArrayOfInvalidBasketItemTrans {

    @XmlElement(name = "InvalidBasketItemTrans", nillable = true)
    protected List<InvalidBasketItemTrans> invalidBasketItemTrans;

    /**
     * Gets the value of the invalidBasketItemTrans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invalidBasketItemTrans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvalidBasketItemTrans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvalidBasketItemTrans }
     * 
     * 
     */
    public List<InvalidBasketItemTrans> getInvalidBasketItemTrans() {
        if (invalidBasketItemTrans == null) {
            invalidBasketItemTrans = new ArrayList<InvalidBasketItemTrans>();
        }
        return this.invalidBasketItemTrans;
    }

}
