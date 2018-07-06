
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CommerceResultTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommerceResultTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CommerceItems" type="{}ArrayOfCommerceItemTrans" minOccurs="0"/>
 *         &lt;element name="InvalidBasket" type="{}InvalidBasketTrans" minOccurs="0"/>
 *         &lt;element name="InventoryItems" type="{}ArrayOfInventoryItemTrans" minOccurs="0"/>
 *         &lt;element name="PurchasedCars" type="{}ArrayOfOwnedCarTrans" minOccurs="0"/>
 *         &lt;element name="Status" type="{}CommerceResultStatus"/>
 *         &lt;element name="Wallets" type="{}ArrayOfWalletTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AchievementRewards", propOrder = {
        "commerceItems",
        "invalidBasket",
        "inventoryItems",
        "purchasedCars",
        "status",
        "wallets",
        "achievementRankId",
        "visualStyle"
})
@XmlRootElement(name = "AchievementRewards")
public class AchievementRewards {

    @XmlElement(name = "CommerceItems")
    protected ArrayOfCommerceItemTrans commerceItems;
    @XmlElement(name = "InvalidBasket")
    protected InvalidBasketTrans invalidBasket;
    @XmlElement(name = "InventoryItems")
    protected ArrayOfInventoryItemTrans inventoryItems;
    @XmlElement(name = "PurchasedCars")
    protected ArrayOfOwnedCarTrans purchasedCars;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected CommerceResultStatus status;
    @XmlElement(name = "Wallets")
    protected ArrayOfWalletTrans wallets;
    @XmlElement(name = "AchievementRankId")
    protected Long achievementRankId;
    @XmlElement(name = "VisualStyle")
    protected String visualStyle;

    /**
     * Gets the value of the commerceItems property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfCommerceItemTrans }
     *
     */
    public ArrayOfCommerceItemTrans getCommerceItems() {
        return commerceItems;
    }

    /**
     * Sets the value of the commerceItems property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfCommerceItemTrans }
     *
     */
    public void setCommerceItems(ArrayOfCommerceItemTrans value) {
        this.commerceItems = value;
    }

    /**
     * Gets the value of the invalidBasket property.
     *
     * @return
     *     possible object is
     *     {@link InvalidBasketTrans }
     *
     */
    public InvalidBasketTrans getInvalidBasket() {
        return invalidBasket;
    }

    /**
     * Sets the value of the invalidBasket property.
     *
     * @param value
     *     allowed object is
     *     {@link InvalidBasketTrans }
     *
     */
    public void setInvalidBasket(InvalidBasketTrans value) {
        this.invalidBasket = value;
    }

    /**
     * Gets the value of the inventoryItems property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryItemTrans }
     *
     */
    public ArrayOfInventoryItemTrans getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets the value of the inventoryItems property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryItemTrans }
     *
     */
    public void setInventoryItems(ArrayOfInventoryItemTrans value) {
        this.inventoryItems = value;
    }

    /**
     * Gets the value of the purchasedCars property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfOwnedCarTrans }
     *
     */
    public ArrayOfOwnedCarTrans getPurchasedCars() {
        return purchasedCars;
    }

    /**
     * Sets the value of the purchasedCars property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfOwnedCarTrans }
     *
     */
    public void setPurchasedCars(ArrayOfOwnedCarTrans value) {
        this.purchasedCars = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link CommerceResultStatus }
     *
     */
    public CommerceResultStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link CommerceResultStatus }
     *
     */
    public void setStatus(CommerceResultStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the wallets property.
     *
     * @return
     *     possible object is
     *     {@link ArrayOfWalletTrans }
     *
     */
    public ArrayOfWalletTrans getWallets() {
        return wallets;
    }

    /**
     * Sets the value of the wallets property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayOfWalletTrans }
     *
     */
    public void setWallets(ArrayOfWalletTrans value) {
        this.wallets = value;
    }

    public Long getAchievementRankId()
    {
        return achievementRankId;
    }

    public void setAchievementRankId(Long achievementRankId)
    {
        this.achievementRankId = achievementRankId;
    }

    public String getVisualStyle()
    {
        return visualStyle;
    }

    public void setVisualStyle(String visualStyle)
    {
        this.visualStyle = visualStyle;
    }
}
