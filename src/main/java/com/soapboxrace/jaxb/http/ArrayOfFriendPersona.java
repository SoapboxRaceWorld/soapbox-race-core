
package com.soapboxrace.jaxb.http;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFriendPersona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFriendPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FriendPersona" type="{}FriendPersona" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFriendPersona", propOrder = {
    "friendPersona"
})
public class ArrayOfFriendPersona {

    @XmlElement(name = "FriendPersona", nillable = true)
    protected List<FriendPersona> friendPersona;

    /**
     * Gets the value of the friendPersona property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the friendPersona property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFriendPersona().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FriendPersona }
     * 
     * 
     */
    public List<FriendPersona> getFriendPersona() {
        if (friendPersona == null) {
            friendPersona = new ArrayList<FriendPersona>();
        }
        return this.friendPersona;
    }

}
