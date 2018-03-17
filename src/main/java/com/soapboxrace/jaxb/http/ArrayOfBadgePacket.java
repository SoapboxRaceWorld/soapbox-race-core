
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBadgePacket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfBadgePacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BadgePacket" type="{}BadgePacket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBadgePacket", propOrder = {
    "badgePacket"
})
public class ArrayOfBadgePacket {

    @XmlElement(name = "BadgePacket", nillable = true)
    protected List<BadgePacket> badgePacket;

    /**
     * Gets the value of the badgePacket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the badgePacket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBadgePacket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BadgePacket }
     * 
     * 
     */
    public List<BadgePacket> getBadgePacket() {
        if (badgePacket == null) {
            badgePacket = new ArrayList<BadgePacket>();
        }
        return this.badgePacket;
    }

}
