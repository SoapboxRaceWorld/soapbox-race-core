
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAchievementRankPacket complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAchievementRankPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievementRankPacket" type="{}AchievementRankPacket" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAchievementRankPacket", propOrder = {
    "achievementRankPacket"
})
public class ArrayOfAchievementRankPacket {

    @XmlElement(name = "AchievementRankPacket", nillable = true)
    protected List<AchievementRankPacket> achievementRankPacket;

    /**
     * Gets the value of the achievementRankPacket property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the achievementRankPacket property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAchievementRankPacket().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AchievementRankPacket }
     * 
     * 
     */
    public List<AchievementRankPacket> getAchievementRankPacket() {
        if (achievementRankPacket == null) {
            achievementRankPacket = new ArrayList<AchievementRankPacket>();
        }
        return this.achievementRankPacket;
    }

}
