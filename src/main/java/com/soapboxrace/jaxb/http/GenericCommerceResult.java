package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericCommerceResult", propOrder = {
        "commerceItems",
        "invalidBasket",
        "inventoryItems",
        "purchasedCars",
        "status",
        "wallets"
})
public abstract class GenericCommerceResult {
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

    /**
     * Gets the value of the commerceItems property.
     *
     * @return possible object is
     * {@link ArrayOfCommerceItemTrans }
     */
    public ArrayOfCommerceItemTrans getCommerceItems() {
        return commerceItems;
    }

    /**
     * Sets the value of the commerceItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfCommerceItemTrans }
     */
    public void setCommerceItems(ArrayOfCommerceItemTrans value) {
        this.commerceItems = value;
    }

    /**
     * Gets the value of the invalidBasket property.
     *
     * @return possible object is
     * {@link InvalidBasketTrans }
     */
    public InvalidBasketTrans getInvalidBasket() {
        return invalidBasket;
    }

    /**
     * Sets the value of the invalidBasket property.
     *
     * @param value allowed object is
     *              {@link InvalidBasketTrans }
     */
    public void setInvalidBasket(InvalidBasketTrans value) {
        this.invalidBasket = value;
    }

    /**
     * Gets the value of the inventoryItems property.
     *
     * @return possible object is
     * {@link ArrayOfInventoryItemTrans }
     */
    public ArrayOfInventoryItemTrans getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets the value of the inventoryItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfInventoryItemTrans }
     */
    public void setInventoryItems(ArrayOfInventoryItemTrans value) {
        this.inventoryItems = value;
    }

    /**
     * Gets the value of the purchasedCars property.
     *
     * @return possible object is
     * {@link ArrayOfOwnedCarTrans }
     */
    public ArrayOfOwnedCarTrans getPurchasedCars() {
        return purchasedCars;
    }

    /**
     * Sets the value of the purchasedCars property.
     *
     * @param value allowed object is
     *              {@link ArrayOfOwnedCarTrans }
     */
    public void setPurchasedCars(ArrayOfOwnedCarTrans value) {
        this.purchasedCars = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link CommerceResultStatus }
     */
    public CommerceResultStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link CommerceResultStatus }
     */
    public void setStatus(CommerceResultStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the wallets property.
     *
     * @return possible object is
     * {@link ArrayOfWalletTrans }
     */
    public ArrayOfWalletTrans getWallets() {
        return wallets;
    }

    /**
     * Sets the value of the wallets property.
     *
     * @param value allowed object is
     *              {@link ArrayOfWalletTrans }
     */
    public void setWallets(ArrayOfWalletTrans value) {
        this.wallets = value;
    }
}
