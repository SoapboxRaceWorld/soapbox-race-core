package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.InventoryItemEntity;

public class AchievementInventoryContext {
    private final InventoryItemEntity inventoryItemEntity;
    private final Event event;
    private final Integer eventVal;

    public AchievementInventoryContext(InventoryItemEntity inventoryItemEntity, Event event) {
        this.inventoryItemEntity = inventoryItemEntity;
        this.event = event;
        this.eventVal = event.ordinal();
    }

    public InventoryItemEntity getInventoryItemEntity() {
        return inventoryItemEntity;
    }

    public Event getEvent() {
        return event;
    }

    public Integer getEventVal() {
        return eventVal;
    }

    public enum Event {
        ADDED,
        QUANTITY_DECREASED,
        QUANTITY_INCREASED,
    }
}
