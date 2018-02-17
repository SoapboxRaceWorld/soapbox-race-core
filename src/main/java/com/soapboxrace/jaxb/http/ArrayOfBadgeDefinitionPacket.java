
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfBadgeDefinitionPacket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfBadgeDefinitionPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BadgeDefinitionPacket" type="{}BadgeDefinitionPacket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfBadgeDefinitionPacket", propOrder = {
    "badgeDefinitionPacket"
})
public class ArrayOfBadgeDefinitionPacket {

    @XmlElement(name = "BadgeDefinitionPacket", nillable = true)
    protected List<BadgeDefinitionPacket> badgeDefinitionPacket;

    /**
     * Gets the value of the badgeDefinitionPacket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the badgeDefinitionPacket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBadgeDefinitionPacket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BadgeDefinitionPacket }
     * 
     * 
     */
    public List<BadgeDefinitionPacket> getBadgeDefinitionPacket() {
        if (badgeDefinitionPacket == null) {
            badgeDefinitionPacket = new ArrayList<BadgeDefinitionPacket>();
        }
        return this.badgeDefinitionPacket;
    }

}
