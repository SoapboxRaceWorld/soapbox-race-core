/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVENTORY_ITEM")
@NamedQueries({
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaId",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.personaId = " +
                        ":personaId"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndTag",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.personaId = " +
                        ":personaId AND obj.productEntity.entitlementTag = :entitlementTag"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndHash",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.personaId = " +
                        ":personaId AND obj.productEntity.hash = :hash"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndType",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.personaId = " +
                        ":personaId AND obj.productEntity.productType = :productType"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryAndTag",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = " +
                        ":inventoryId AND obj.productEntity.entitlementTag = :entitlementTag"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryAndHash",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = " +
                        ":inventoryId AND obj.productEntity.hash = :hash"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryAndType",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = " +
                        ":inventoryId AND obj.productEntity.productType = :productType"),
        @NamedQuery(
                name = "InventoryItemEntity.deleteAllExpiredItems",
                query = "DELETE FROM InventoryItemEntity obj WHERE obj.expirationDate IS NOT NULL and obj.expirationDate < current_timestamp")
})
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InventoryEntity.class, optional = false)
    @JoinColumn(name = "inventoryEntity_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INVENTORY_ITEM_INVENTORY_inventoryEntity_id"))
    private InventoryEntity inventoryEntity;

    @Column
    private LocalDateTime expirationDate;

    @ManyToOne(targetEntity = ProductEntity.class, /* TODO: why is this here? */ cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "productId", referencedColumnName = "productId", foreignKey = @ForeignKey(name = "FK_INVENTORY_ITEM_PRODUCT_productId"))
    private ProductEntity productEntity;

    @Column
    private int remainingUseCount;

    @Column
    private int resellPrice;

    @Column(nullable = false)
    private String status;

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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
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
}
