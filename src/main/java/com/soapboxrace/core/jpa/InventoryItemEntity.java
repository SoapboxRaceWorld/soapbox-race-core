package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVENTORY_ITEM")
@NamedQueries({
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaId",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndTag",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId AND obj.productEntity.entitlementTag = :entitlementTag"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByPersonaIdAndHash",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.personaEntity.id = " +
                        ":personaId AND obj.productEntity.hash = :hash"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryAndTag",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = " +
                        ":inventoryId AND obj.productEntity.entitlementTag = :entitlementTag"),
        @NamedQuery(
                name = "InventoryItemEntity.findAllByInventoryAndHash",
                query = "SELECT obj FROM InventoryItemEntity obj WHERE obj.inventoryEntity.id = " +
                        ":inventoryId AND obj.productEntity.hash = :hash"),
})
public class InventoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InventoryEntity.class, optional = false, cascade = CascadeType.PERSIST)
    private InventoryEntity inventoryEntity;

    @Column
    private LocalDateTime expirationDate;

    @ManyToOne(targetEntity = ProductEntity.class, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
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
