package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

public class CommerceSessionItem {
    public enum ItemType {
        INVENTORY, BASKET
    }

    public enum ItemStatus {
        ADDED, REMOVED
    }

    private final ProductEntity productEntity;

    private final ItemType itemType;

    private final ItemStatus itemStatus;

    public CommerceSessionItem(ProductEntity productEntity, ItemType itemType, ItemStatus itemStatus) {
        this.productEntity = productEntity;
        this.itemType = itemType;
        this.itemStatus = itemStatus;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }
}
