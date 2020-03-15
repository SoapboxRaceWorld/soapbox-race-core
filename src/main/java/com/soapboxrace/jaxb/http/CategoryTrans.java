
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategoryTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CategoryTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CatalogVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Categories" type="{}ArrayOfCategoryTrans" minOccurs="0"/>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FilterType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LongDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Products" type="{}ArrayOfProductTrans" minOccurs="0"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShowInNavigationPane" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ShowPromoPage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WebIcon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryTrans", propOrder = {
        "catalogVersion",
        "categories",
        "displayName",
        "filterType",
        "icon",
        "id",
        "longDescription",
        "name",
        "priority",
        "products",
        "shortDescription",
        "showInNavigationPane",
        "showPromoPage",
        "webIcon"
})
public class CategoryTrans {

    @XmlElement(name = "CatalogVersion")
    protected int catalogVersion;
    @XmlElement(name = "Categories")
    protected ArrayOfCategoryTrans categories;
    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "FilterType")
    protected int filterType;
    @XmlElement(name = "Icon")
    protected String icon;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "LongDescription")
    protected String longDescription;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Priority")
    protected int priority;
    @XmlElement(name = "Products")
    protected ArrayOfProductTrans products;
    @XmlElement(name = "ShortDescription")
    protected String shortDescription;
    @XmlElement(name = "ShowInNavigationPane")
    protected boolean showInNavigationPane;
    @XmlElement(name = "ShowPromoPage")
    protected boolean showPromoPage;
    @XmlElement(name = "WebIcon")
    protected String webIcon;

    /**
     * Gets the value of the catalogVersion property.
     */
    public int getCatalogVersion() {
        return catalogVersion;
    }

    /**
     * Sets the value of the catalogVersion property.
     */
    public void setCatalogVersion(int value) {
        this.catalogVersion = value;
    }

    /**
     * Gets the value of the categories property.
     *
     * @return possible object is
     * {@link ArrayOfCategoryTrans }
     */
    public ArrayOfCategoryTrans getCategories() {
        return categories;
    }

    /**
     * Sets the value of the categories property.
     *
     * @param value allowed object is
     *              {@link ArrayOfCategoryTrans }
     */
    public void setCategories(ArrayOfCategoryTrans value) {
        this.categories = value;
    }

    /**
     * Gets the value of the displayName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the filterType property.
     */
    public int getFilterType() {
        return filterType;
    }

    /**
     * Sets the value of the filterType property.
     */
    public void setFilterType(int value) {
        this.filterType = value;
    }

    /**
     * Gets the value of the icon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the value of the icon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIcon(String value) {
        this.icon = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the longDescription property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * Sets the value of the longDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongDescription(String value) {
        this.longDescription = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the priority property.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     */
    public void setPriority(int value) {
        this.priority = value;
    }

    /**
     * Gets the value of the products property.
     *
     * @return possible object is
     * {@link ArrayOfProductTrans }
     */
    public ArrayOfProductTrans getProducts() {
        return products;
    }

    /**
     * Sets the value of the products property.
     *
     * @param value allowed object is
     *              {@link ArrayOfProductTrans }
     */
    public void setProducts(ArrayOfProductTrans value) {
        this.products = value;
    }

    /**
     * Gets the value of the shortDescription property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the showInNavigationPane property.
     */
    public boolean isShowInNavigationPane() {
        return showInNavigationPane;
    }

    /**
     * Sets the value of the showInNavigationPane property.
     */
    public void setShowInNavigationPane(boolean value) {
        this.showInNavigationPane = value;
    }

    /**
     * Gets the value of the showPromoPage property.
     */
    public boolean isShowPromoPage() {
        return showPromoPage;
    }

    /**
     * Sets the value of the showPromoPage property.
     */
    public void setShowPromoPage(boolean value) {
        this.showPromoPage = value;
    }

    /**
     * Gets the value of the webIcon property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWebIcon() {
        return webIcon;
    }

    /**
     * Sets the value of the webIcon property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWebIcon(String value) {
        this.webIcon = value;
    }

}
