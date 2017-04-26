
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ProductTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ProductTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BundleItems" type="{}ArrayOfProductTrans" minOccurs="0"/>
 *         &lt;element name="CategoryId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DurationMinute" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LongDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecondaryIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VisualStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductTrans", propOrder = {
    "bundleItems",
    "categoryId",
    "currency",
    "description",
    "durationMinute",
    "hash",
    "icon",
    "level",
    "longDescription",
    "price",
    "priority",
    "productId",
    "productTitle",
    "productType",
    "secondaryIcon",
    "useCount",
    "visualStyle",
    "webIcon",
    "webLocation"
})
public class ProductTrans {

    @XmlElement(name = "BundleItems")
    protected ArrayOfProductTrans bundleItems;
    @XmlElement(name = "CategoryId")
    protected String categoryId;
    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "DurationMinute")
    protected int durationMinute;
    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "Icon")
    protected String icon;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "LongDescription")
    protected String longDescription;
    @XmlElement(name = "Price")
    protected double price;
    @XmlElement(name = "Priority")
    protected int priority;
    @XmlElement(name = "ProductId")
    protected String productId;
    @XmlElement(name = "ProductTitle")
    protected String productTitle;
    @XmlElement(name = "ProductType")
    protected String productType;
    @XmlElement(name = "SecondaryIcon")
    protected String secondaryIcon;
    @XmlElement(name = "UseCount")
    protected int useCount;
    @XmlElement(name = "VisualStyle")
    protected String visualStyle;
    @XmlElement(name = "WebIcon")
    protected String webIcon;
    @XmlElement(name = "WebLocation")
    protected String webLocation;

    /**
     * Obtém o valor da propriedade bundleItems.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProductTrans }
     *     
     */
    public ArrayOfProductTrans getBundleItems() {
        return bundleItems;
    }

    /**
     * Define o valor da propriedade bundleItems.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProductTrans }
     *     
     */
    public void setBundleItems(ArrayOfProductTrans value) {
        this.bundleItems = value;
    }

    /**
     * Obtém o valor da propriedade categoryId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Define o valor da propriedade categoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryId(String value) {
        this.categoryId = value;
    }

    /**
     * Obtém o valor da propriedade currency.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Define o valor da propriedade currency.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Obtém o valor da propriedade description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define o valor da propriedade description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtém o valor da propriedade durationMinute.
     * 
     */
    public int getDurationMinute() {
        return durationMinute;
    }

    /**
     * Define o valor da propriedade durationMinute.
     * 
     */
    public void setDurationMinute(int value) {
        this.durationMinute = value;
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
     * Obtém o valor da propriedade icon.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Define o valor da propriedade icon.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcon(String value) {
        this.icon = value;
    }

    /**
     * Obtém o valor da propriedade level.
     * 
     */
    public int getLevel() {
        return level;
    }

    /**
     * Define o valor da propriedade level.
     * 
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Obtém o valor da propriedade longDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Define o valor da propriedade longDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Obtém o valor da propriedade price.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Define o valor da propriedade price.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Obtém o valor da propriedade priority.
     * 
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Define o valor da propriedade priority.
     * 
     */
    public void setPriority(int value) {
        this.priority = value;
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
     * Obtém o valor da propriedade productTitle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductTitle() {
        return productTitle;
    }

    /**
     * Define o valor da propriedade productTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductTitle(String value) {
        this.productTitle = value;
    }

    /**
     * Obtém o valor da propriedade productType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Define o valor da propriedade productType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * Obtém o valor da propriedade secondaryIcon.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryIcon() {
        return secondaryIcon;
    }

    /**
     * Define o valor da propriedade secondaryIcon.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryIcon(String value) {
        this.secondaryIcon = value;
    }

    /**
     * Obtém o valor da propriedade useCount.
     * 
     */
    public int getUseCount() {
        return useCount;
    }

    /**
     * Define o valor da propriedade useCount.
     * 
     */
    public void setUseCount(int value) {
        this.useCount = value;
    }

    /**
     * Obtém o valor da propriedade visualStyle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisualStyle() {
        return visualStyle;
    }

    /**
     * Define o valor da propriedade visualStyle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisualStyle(String value) {
        this.visualStyle = value;
    }

    /**
     * Obtém o valor da propriedade webIcon.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebIcon() {
        return webIcon;
    }

    /**
     * Define o valor da propriedade webIcon.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebIcon(String value) {
        this.webIcon = value;
    }

    /**
     * Obtém o valor da propriedade webLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebLocation() {
        return webLocation;
    }

    /**
     * Define o valor da propriedade webLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebLocation(String value) {
        this.webLocation = value;
    }

}
