
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfLuckyDrawItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfLuckyDrawItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LuckyDrawItem" type="{}LuckyDrawItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfLuckyDrawItem", propOrder = {
    "luckyDrawItem"
})
public class ArrayOfLuckyDrawItem {

    @XmlElement(name = "LuckyDrawItem", nillable = true)
    protected List<LuckyDrawItem> luckyDrawItem;

    /**
     * Gets the value of the luckyDrawItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the luckyDrawItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLuckyDrawItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LuckyDrawItem }
     * 
     * 
     */
    public List<LuckyDrawItem> getLuckyDrawItem() {
        if (luckyDrawItem == null) {
            luckyDrawItem = new ArrayList<LuckyDrawItem>();
        }
        return this.luckyDrawItem;
    }

}
