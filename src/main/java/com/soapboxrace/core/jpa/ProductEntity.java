/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "PRODUCT", indexes = {
        @Index(name = "prod_id_index", columnList = "productId"),
        @Index(name = "parent_prod_id_index", columnList = "parentProductId")
})
@NamedQueries({ //
        @NamedQuery(name = "ProductEntity.findByLevelEnabled", //
                query = "SELECT obj FROM ProductEntity obj WHERE " //
                        + "obj.enabled = :enabled AND "//
                        + "obj.minLevel <= :minLevel AND " //
                        + "(obj.premium = false or obj.premium = :premium )AND " //
                        + "obj.categoryName = :categoryName AND "//
                        + "obj.productType = :productType"), //
        @NamedQuery(name = "ProductEntity.findForEndRace", //
                query = "SELECT obj FROM ProductEntity obj WHERE " //
                        + "obj.enabled = true AND " //
                        + "obj.level <= :level AND " //
                        + "obj.categoryName = :categoryName AND " //
                        + "obj.isDropable = true AND " //
                        + "obj.productType = :productType"), //
        @NamedQuery(name = "ProductEntity.findByProductId", query = "SELECT obj FROM ProductEntity obj WHERE obj" +
                ".productId = :productId"), //
        @NamedQuery(name = "ProductEntity.findByEntitlementTag", query = "SELECT obj FROM ProductEntity obj WHERE obj" +
                ".entitlementTag = :entitlementTag"), //
        @NamedQuery(name = "ProductEntity.findByHash", query = "SELECT obj FROM ProductEntity obj WHERE obj.hash = " +
                ":hash"), //
        @NamedQuery(name = "ProductEntity.findByType", query = "SELECT obj FROM ProductEntity obj WHERE obj" +
                ".productType = :type AND obj.enabled=true"), //
        @NamedQuery(name = "ProductEntity.findDropsByType", query = "SELECT obj FROM ProductEntity obj WHERE obj" +
                ".productType = :type AND obj.isDropable = true AND obj.enabled = true AND obj.dropWeight IS NOT NULL" +
                " AND obj.dropWeight > 0.0"), //
        @NamedQuery(name = "ProductEntity.findDropsBySubTypeAndRarity", query = "SELECT obj FROM ProductEntity obj " +
                "WHERE obj.subType = :subType AND obj.enabled=true AND obj.rarity = :rarity AND obj.isDropable = true" +
                " AND obj.dropWeight >" +
                " 0.0"),
        @NamedQuery(name = "ProductEntity.findDropsByProdTypeAndRarity", query = "SELECT obj FROM ProductEntity obj " +
                "WHERE obj.productType = :prodType AND obj.enabled=true AND obj.rarity = :rarity AND obj.isDropable =" +
                " true AND obj" +
                ".dropWeight > 0.0"),
})
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "parentProductId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PRODUCT_PRODUCT_parentProductId"))
    private ProductEntity parentProduct;
    private String categoryId;
    @Column(nullable = false)
    private String currency;
    private String description;
    private int durationMinute;
    private Integer hash;
    @Column(nullable = false)
    private String icon;
    private int level;
    private String longDescription;
    private float price;
    private float resalePrice;
    private int priority;
    @Column(nullable = false)
    private String productId;
    private String productTitle;
    @Column(nullable = false)
    private String productType;
    private String secondaryIcon;
    private int useCount;
    private String visualStyle;
    private String webIcon;
    private String webLocation;
    private String categoryName;
    private boolean enabled;
    private int minLevel;
    private boolean premium = false;
    private boolean isDropable;
    private Integer topSpeed = 0;
    private Integer accel = 0;
    private Integer handling = 0;
    private Float skillValue;
    @Column(nullable = false)
    private String entitlementTag;
    private String subType;
    private String brand;
    private Integer rarity;
    private Double dropWeight;

    @OneToMany(mappedBy = "parentProduct", targetEntity = ProductEntity.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ProductEntity> bundleItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSecondaryIcon() {
        return secondaryIcon;
    }

    public void setSecondaryIcon(String secondaryIcon) {
        this.secondaryIcon = secondaryIcon;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public String getVisualStyle() {
        return visualStyle;
    }

    public void setVisualStyle(String visualStyle) {
        this.visualStyle = visualStyle;
    }

    public String getWebIcon() {
        return webIcon;
    }

    public void setWebIcon(String webIcon) {
        this.webIcon = webIcon;
    }

    public String getWebLocation() {
        return webLocation;
    }

    public void setWebLocation(String webLocation) {
        this.webLocation = webLocation;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isDropable() {
        return isDropable;
    }

    public void setDropable(boolean isDropable) {
        this.isDropable = isDropable;
    }

    public float getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(float resalePrice) {
        this.resalePrice = resalePrice;
    }

    public Integer getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(Integer topSpeed) {
        this.topSpeed = topSpeed;
    }

    public Integer getAccel() {
        return accel;
    }

    public void setAccel(Integer accel) {
        this.accel = accel;
    }

    public Integer getHandling() {
        return handling;
    }

    public void setHandling(Integer handling) {
        this.handling = handling;
    }

    public Float getSkillValue() {
        return skillValue;
    }

    public void setSkillValue(Float skillValue) {
        this.skillValue = skillValue;
    }

    public ProductEntity getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(ProductEntity parentProduct) {
        this.parentProduct = parentProduct;
    }

    public Set<ProductEntity> getBundleItems() {
        return bundleItems;
    }

    public void setBundleItems(Set<ProductEntity> bundleItems) {
        this.bundleItems = bundleItems;
    }

    public String getEntitlementTag() {
        return entitlementTag;
    }

    public void setEntitlementTag(String entitlementTag) {
        this.entitlementTag = entitlementTag;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

    public Double getDropWeight() {
        return dropWeight;
    }

    public void setDropWeight(Double dropRate) {
        this.dropWeight = dropRate;
    }
}
