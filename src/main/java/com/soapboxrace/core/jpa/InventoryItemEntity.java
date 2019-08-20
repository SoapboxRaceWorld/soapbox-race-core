package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVENTORY_ITEM")
@NamedQueries({
        @NamedQuery(
                name = "InventoryItemEntity.findAllWithExpirationDate",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.expirationDate IS NOT NULL"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryId",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = :inventoryId"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaId",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryIdAndType",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = :inventoryId AND obj" +
                        ".virtualItemType = :virtualItemType"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndTag",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId AND obj.entitlementTag = :entitlementTag"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndHash",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId AND obj.hash = :hash")
})
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InventoryEntity.class, optional = false)
    private InventoryEntity inventoryEntity;

    @Column(nullable = false)
    private String entitlementTag;

    @Column
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private Integer hash; // bStringHash(entitlementTag.toUpperCase())

    @Column(nullable = false)
    private String productId;

    @Column
    private int remainingUseCount;

    @Column
    private int resellPrice;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String virtualItemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryEntity getInventoryEntity() {
        return inventoryEntity;
    }

    public void setInventoryEntity(InventoryEntity inventoryEntity) {
        this.inventoryEntity = inventoryEntity;
    }

    public String getEntitlementTag() {
        return entitlementTag;
    }

    public void setEntitlementTag(String entitlementTag) {
        this.entitlementTag = entitlementTag;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getRemainingUseCount() {
        return remainingUseCount;
    }

    public void setRemainingUseCount(int remainingUseCount) {
        this.remainingUseCount = remainingUseCount;
    }

    public int getResellPrice() {
        return resellPrice;
    }

    public void setResellPrice(int resellPrice) {
        this.resellPrice = resellPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVirtualItemType() {
        return virtualItemType;
    }

    public void setVirtualItemType(String virtualItemType) {
        this.virtualItemType = virtualItemType;
    }
}
