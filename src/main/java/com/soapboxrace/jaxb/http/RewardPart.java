
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RewardPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RewardPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RepPart" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RewardCategory" type="{}enumRewardCategory"/>
 *         &lt;element name="RewardType" type="{}enumRewardType"/>
 *         &lt;element name="TokenPart" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RewardPart", propOrder = {
    "repPart",
    "rewardCategory",
    "rewardType",
    "tokenPart"
})
public class RewardPart {

    @XmlElement(name = "RepPart")
    protected int repPart;
    @XmlElement(name = "RewardCategory", required = true)
    @XmlSchemaType(name = "string")
    protected EnumRewardCategory rewardCategory;
    @XmlElement(name = "RewardType", required = true)
    @XmlSchemaType(name = "string")
    protected EnumRewardType rewardType;
    @XmlElement(name = "TokenPart")
    protected int tokenPart;

    /**
     * Gets the value of the repPart property.
     * 
     */
    public int getRepPart() {
        return repPart;
    }

    /**
     * Sets the value of the repPart property.
     * 
     */
    public void setRepPart(int value) {
        this.repPart = value;
    }

    /**
     * Gets the value of the rewardCategory property.
     * 
     * @return
     *     possible object is
     *     {@link EnumRewardCategory }
     *     
     */
    public EnumRewardCategory getRewardCategory() {
        return rewardCategory;
    }

    /**
     * Sets the value of the rewardCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumRewardCategory }
     *     
     */
    public void setRewardCategory(EnumRewardCategory value) {
        this.rewardCategory = value;
    }

    /**
     * Gets the value of the rewardType property.
     * 
     * @return
     *     possible object is
     *     {@link EnumRewardType }
     *     
     */
    public EnumRewardType getRewardType() {
        return rewardType;
    }

    /**
     * Sets the value of the rewardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumRewardType }
     *     
     */
    public void setRewardType(EnumRewardType value) {
        this.rewardType = value;
    }

    /**
     * Gets the value of the tokenPart property.
     * 
     */
    public int getTokenPart() {
        return tokenPart;
    }

    /**
     * Sets the value of the tokenPart property.
     * 
     */
    public void setTokenPart(int value) {
        this.tokenPart = value;
    }

}
