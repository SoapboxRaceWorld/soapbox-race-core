//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.22 às 07:20:05 AM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de InventoryItemTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InventoryItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EntitlementTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="InventoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingUseCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResellPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StringHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VirtualItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryItemTrans", propOrder = {
    "entitlementTag",
    "expirationDate",
    "hash",
    "inventoryId",
    "productId",
    "remainingUseCount",
    "resellPrice",
    "status",
    "stringHash",
    "virtualItemType"
})
public class InventoryItemTrans {

    @XmlElement(name = "EntitlementTag")
    protected String entitlementTag;
    @XmlElement(name = "ExpirationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "InventoryId")
    protected long inventoryId;
    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "RemainingUseCount")
    protected long remainingUseCount;
    @XmlElement(name = "ResellPrice")
    protected double resellPrice;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StringHash")
    protected String stringHash;
    @XmlElement(name = "VirtualItemType")
    protected String virtualItemType;

    /**
     * Obtém o valor da propriedade entitlementTag.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitlementTag() {
        return entitlementTag;
    }

    /**
     * Define o valor da propriedade entitlementTag.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitlementTag(String value) {
        this.entitlementTag = value;
    }

    /**
     * Obtém o valor da propriedade expirationDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Define o valor da propriedade expirationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Obtém o valor da propriedade hash.
     * 
     */
    public int getHash() {
        return hash;
    }

    /**
     * Define o valor da propriedade hash.
     * 
     */
    public void setHash(int value) {
        this.hash = value;
    }

    /**
     * Obtém o valor da propriedade inventoryId.
     * 
     */
    public long getInventoryId() {
        return inventoryId;
    }

    /**
     * Define o valor da propriedade inventoryId.
     * 
     */
    public void setInventoryId(long value) {
        this.inventoryId = value;
    }

    /**
     * Obtém o valor da propriedade productId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Define o valor da propriedade productId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Obtém o valor da propriedade remainingUseCount.
     * 
     */
    public long getRemainingUseCount() {
        return remainingUseCount;
    }

    /**
     * Define o valor da propriedade remainingUseCount.
     * 
     */
    public void setRemainingUseCount(long value) {
        this.remainingUseCount = value;
    }

    /**
     * Obtém o valor da propriedade resellPrice.
     * 
     */
    public double getResellPrice() {
        return resellPrice;
    }

    /**
     * Define o valor da propriedade resellPrice.
     * 
     */
    public void setResellPrice(double value) {
        this.resellPrice = value;
    }

    /**
     * Obtém o valor da propriedade status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtém o valor da propriedade stringHash.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStringHash() {
        return stringHash;
    }

    /**
     * Define o valor da propriedade stringHash.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStringHash(String value) {
        this.stringHash = value;
    }

    /**
     * Obtém o valor da propriedade virtualItemType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualItemType() {
        return virtualItemType;
    }

    /**
     * Define o valor da propriedade virtualItemType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualItemType(String value) {
        this.virtualItemType = value;
    }

}
