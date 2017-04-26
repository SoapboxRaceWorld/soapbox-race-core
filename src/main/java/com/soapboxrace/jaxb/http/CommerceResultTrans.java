
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CommerceResultTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
@XmlType(name = "CommerceResultTrans", propOrder = {
    "commerceItems",
    "invalidBasket",
    "inventoryItems",
    "purchasedCars",
    "status",
    "wallets"
})
public class CommerceResultTrans {

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
     * Obtém o valor da propriedade commerceItems.
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
     * Define o valor da propriedade commerceItems.
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
     * Obtém o valor da propriedade invalidBasket.
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
     * Define o valor da propriedade invalidBasket.
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
     * Obtém o valor da propriedade inventoryItems.
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
     * Define o valor da propriedade inventoryItems.
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
     * Obtém o valor da propriedade purchasedCars.
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
     * Define o valor da propriedade purchasedCars.
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
     * Obtém o valor da propriedade status.
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
     * Define o valor da propriedade status.
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
     * Obtém o valor da propriedade wallets.
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
     * Define o valor da propriedade wallets.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWalletTrans }
     *     
     */
    public void setWallets(ArrayOfWalletTrans value) {
        this.wallets = value;
    }

}
