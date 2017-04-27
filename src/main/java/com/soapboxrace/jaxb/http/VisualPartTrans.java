
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VisualPartTrans complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VisualPartTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SlotHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VisualPartTrans", propOrder = {
    "partHash",
    "slotHash"
})
public class VisualPartTrans {

    @XmlElement(name = "PartHash")
    protected int partHash;
    @XmlElement(name = "SlotHash")
    protected int slotHash;

    /**
     * Gets the value of the partHash property.
     * 
     */
    public int getPartHash() {
        return partHash;
    }

    /**
     * Sets the value of the partHash property.
     * 
     */
    public void setPartHash(int value) {
        this.partHash = value;
    }

    /**
     * Gets the value of the slotHash property.
     * 
     */
    public int getSlotHash() {
        return slotHash;
    }

    /**
     * Sets the value of the slotHash property.
     * 
     */
    public void setSlotHash(int value) {
        this.slotHash = value;
    }

}
