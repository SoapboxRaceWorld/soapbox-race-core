
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDragEntrantResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDragEntrantResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DragEntrantResult" type="{}DragEntrantResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDragEntrantResult", propOrder = {
    "dragEntrantResult"
})
public class ArrayOfDragEntrantResult {

    @XmlElement(name = "DragEntrantResult", nillable = true)
    protected List<DragEntrantResult> dragEntrantResult;

    /**
     * Gets the value of the dragEntrantResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dragEntrantResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDragEntrantResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DragEntrantResult }
     * 
     * 
     */
    public List<DragEntrantResult> getDragEntrantResult() {
        if (dragEntrantResult == null) {
            dragEntrantResult = new ArrayList<DragEntrantResult>();
        }
        return this.dragEntrantResult;
    }

}
