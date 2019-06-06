package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "VIRTUALITEM")
@NamedQueries({
        @NamedQuery(name = "VirtualItemEntity.findByHash", query = "SELECT obj FROM VirtualItemEntity obj WHERE obj.hash = :hash"),
        @NamedQuery(name = "VirtualItemEntity.findByItemName", query = "SELECT obj FROM VirtualItemEntity obj WHERE obj.itemName = :itemName"),
        @NamedQuery(name = "VirtualItemEntity.findByItemType", query = "SELECT obj FROM VirtualItemEntity obj WHERE obj.type = :itemType"),
        @NamedQuery(name = "VirtualItemEntity.findByItemSubType", query = "SELECT obj FROM VirtualItemEntity obj WHERE obj.subType = :itemSubType"),
})
public class VirtualItemEntity {
    @Column(name = "longdescription")
    private String longDescription;

    @Column(name = "shortdescription")
    private String shortDescription;

    @Column(name = "type")
    private String type;

    @Column(name = "itemName")
    @Id
    private String itemName;

    @Column
    private String title;

    @Column
    private Integer hash;

    @Column
    private String icon;

    @Column
    private String subType;

    @Column
    private String brand;

    @Column(name = "resellprice")
    private Integer resellPrice;

    @Column(name = "tier")
    private Integer tier;

    @Column(name = "warnondelete")
    private Boolean warnOnDelete;

    @Column(name = "rarity")
    private Integer rarity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(Integer resellPrice) {
        this.resellPrice = resellPrice;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Boolean getWarnOnDelete() {
        return warnOnDelete;
    }

    public void setWarnOnDelete(Boolean warnOnDelete) {
        this.warnOnDelete = warnOnDelete;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }
}
