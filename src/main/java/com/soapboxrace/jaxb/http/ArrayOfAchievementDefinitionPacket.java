
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAchievementDefinitionPacket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAchievementDefinitionPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievementDefinitionPacket" type="{}AchievementDefinitionPacket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAchievementDefinitionPacket", propOrder = {
    "achievementDefinitionPacket"
})
public class ArrayOfAchievementDefinitionPacket {

    @XmlElement(name = "AchievementDefinitionPacket", nillable = true)
    protected List<AchievementDefinitionPacket> achievementDefinitionPacket;

    /**
     * Gets the value of the achievementDefinitionPacket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the achievementDefinitionPacket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAchievementDefinitionPacket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AchievementDefinitionPacket }
     * 
     * 
     */
    public List<AchievementDefinitionPacket> getAchievementDefinitionPacket() {
        if (achievementDefinitionPacket == null) {
            achievementDefinitionPacket = new ArrayList<AchievementDefinitionPacket>();
        }
        return this.achievementDefinitionPacket;
    }

}
