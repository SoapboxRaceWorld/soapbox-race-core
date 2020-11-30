/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The InventoryBO manages player inventories.
 * It provides facilities to manage inventory items, set up inventories,
 * and fetch information to be given to the client.
 */
@Stateless
public class InventoryBO {

    //region Dependencies
    @EJB
    private InventoryDAO inventoryDAO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;
    //endregion

    @Schedule(minute = "*", hour = "*")
    public void scheduledDeleteExpiredItems() {
        inventoryItemDAO.deleteAllExpiredItems();
    }

    //region Exposed methods (API)

    /**
     * Finds the {@link InventoryEntity} associated with the given persona ID.
     * Creates a new inventory if one cannot be found.
     *
     * @param personaEntity The persona to obtain the inventory record for.
     * @return The obtained or created inventory record.
     * @throws com.soapboxrace.core.engine.EngineException if the given persona ID is invalid OR
     *                                                     inventory creation fails
     */
    public InventoryEntity getInventory(PersonaEntity personaEntity) {
        // Do the important work!
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        if (inventoryEntity == null)
            inventoryEntity = this.createInventory(personaEntity);

        return inventoryEntity;
    }

    /**
     * Creates a {@link InventoryTrans} instance based on the given {@link InventoryEntity}.
     *
     * @param inventoryEntity The {@link InventoryEntity} to obtain data from
     * @return The new {@link InventoryTrans} instance to be returned to the client.
     */
    public InventoryTrans getClientInventory(InventoryEntity inventoryEntity) {
        InventoryTrans inventoryTrans = new InventoryTrans();
        inventoryTrans.setPerformancePartsCapacity(inventoryEntity.getPerformancePartsCapacity());
        inventoryTrans.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount());
        inventoryTrans.setVisualPartsCapacity(inventoryEntity.getVisualPartsCapacity());
        inventoryTrans.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount());
        inventoryTrans.setSkillModPartsCapacity(inventoryEntity.getSkillModPartsCapacity());
        inventoryTrans.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount());
        inventoryTrans.setInventoryItems(new ArrayOfInventoryItemTrans());

        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getInventoryItems()) {
            InventoryItemTrans inventoryItemTrans = convertItemToItemTrans(inventoryItemEntity);
            inventoryTrans.getInventoryItems().getInventoryItemTrans().add(inventoryItemTrans);
        }

        return inventoryTrans;
    }

    public InventoryItemTrans convertItemToItemTrans(InventoryItemEntity inventoryItemEntity) {
        ProductEntity productEntity = inventoryItemEntity.getProductEntity();
        InventoryEntity inventoryEntity = inventoryItemEntity.getInventoryEntity();
        InventoryItemTrans inventoryItemTrans = new InventoryItemTrans();
        inventoryItemTrans.setEntitlementTag(productEntity.getEntitlementTag());
        if (inventoryItemEntity.getExpirationDate() != null) {
            inventoryItemTrans.setExpirationDate(inventoryItemEntity.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        }
        inventoryItemTrans.setHash(productEntity.getHash());
        inventoryItemTrans.setInventoryId(inventoryEntity.getId());
        inventoryItemTrans.setProductId(productEntity.getProductId());
        inventoryItemTrans.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount());
        inventoryItemTrans.setResellPrice(inventoryItemEntity.getResellPrice());
        inventoryItemTrans.setStatus(inventoryItemEntity.getStatus());
        inventoryItemTrans.setStringHash("0x" + String.format("%08X", productEntity.getHash()));
        inventoryItemTrans.setVirtualItemType(productEntity.getProductType().toLowerCase());
        return inventoryItemTrans;
    }

    /**
     * Determines if the given {@link InventoryEntity} can hold the given {@link ProductEntity}.
     * In other words, does the inventory have enough capacity to hold items of the type of the given product?
     *
     * @param inventoryEntity The {@link InventoryEntity} to check capacity for.
     * @param productEntity   The {@link ProductEntity} to query for information when checking capacity.
     * @return {@code true} if the inventory can hold the given product, {@code false} otherwise
     */
    public boolean canInventoryHold(InventoryEntity inventoryEntity, ProductEntity productEntity) {
        String productType = productEntity.getProductType().toUpperCase();
        int capacity = 0; // the specific capacity for the given type of item for the given inventory
        int quantity = 0; // the number of items of the given type in the given inventory

        switch (productType) {
            case "PERFORMANCEPART":
                capacity = inventoryEntity.getPerformancePartsCapacity();
                quantity = inventoryEntity.getPerformancePartsUsedSlotCount();
                break;
            case "SKILLMODPART":
                capacity = inventoryEntity.getSkillModPartsCapacity();
                quantity = inventoryEntity.getSkillModPartsUsedSlotCount();
                break;
            case "VISUALPART":
                capacity = inventoryEntity.getVisualPartsCapacity();
                quantity = inventoryEntity.getVisualPartsUsedSlotCount();
                break;
        }

        return capacity == 0 || (quantity + 1 <= capacity);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId) {
        return addInventoryItem(inventoryEntity, productId, -1, null, false);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param ignoreLimits    Whether inventory limits should be ignored.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId, boolean ignoreLimits) {
        return addInventoryItem(inventoryEntity, productId, -1, null, ignoreLimits);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId, int quantity) {
        return addInventoryItem(inventoryEntity, productId, quantity, null, false);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @param ignoreLimits    Whether inventory limits should be ignored.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId, int quantity, boolean ignoreLimits) {
        return addInventoryItem(inventoryEntity, productId, quantity, null, ignoreLimits);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @param expirationDate  The expiration date of the inventory item as a {@link LocalDateTime}. Nullable.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId, int quantity,
                                                LocalDateTime expirationDate) {
        return addInventoryItem(inventoryEntity, productId, quantity, expirationDate, false);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance.
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @param expirationDate  The expiration date of the inventory item as a {@link LocalDateTime}. Nullable.
     * @param ignoreLimits    Whether inventory limits should be ignored.
     * @return The new {@link InventoryItemEntity} instance.
     */
    public InventoryItemEntity addInventoryItem(InventoryEntity inventoryEntity, String productId, int quantity,
                                                LocalDateTime expirationDate, boolean ignoreLimits) {
        ProductEntity productEntity = productDAO.findByProductId(productId);

        // Validation
        if (productEntity == null)
            throw new EngineException("Could not find product " + productId + " to be added",
                    EngineExceptionCode.NoSuchEntitlementExists, true);
        if (quantity < -1)
            throw new EngineException("Invalid product quantity: " + quantity,
                    EngineExceptionCode.EntitlementInvalidCount, true);
        if (!this.canInventoryHold(inventoryEntity, productEntity) && !ignoreLimits)
            throw new EngineException("Cannot add item to inventory. ID=" + productId + " IID=" + inventoryEntity.getId(), EngineExceptionCode.NotEnoughSpace, true);

        // Setup
        if (expirationDate == null && productEntity.getDurationMinute() != 0)
            expirationDate = LocalDateTime.now().plusMinutes(productEntity.getDurationMinute());
        if (quantity == -1)
            quantity = productEntity.getUseCount();

        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setProductEntity(productEntity);
        inventoryItemEntity.setRemainingUseCount(quantity);
        inventoryItemEntity.setExpirationDate(expirationDate);
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setResellPrice(Math.round(productEntity.getResalePrice() * parameterBO.getFloatParam(
                "INVENTORY_ITEM_RESALE_MULTIPLIER", 1.0f)));
        inventoryItemEntity.setInventoryEntity(inventoryEntity);

        // finish up
        updateInventorySlots(inventoryEntity, productEntity, true);
        inventoryItemDAO.insert(inventoryItemEntity);
        inventoryEntity.getInventoryItems().add(inventoryItemEntity);
        return inventoryItemEntity;
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance, contributing to a stack if necessary.
     * Does not persist the inventory to the database!
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addStackedInventoryItem(InventoryEntity inventoryEntity, String productId,
                                                       int quantity) {
        return addStackedInventoryItem(inventoryEntity, productId, quantity, false);
    }

    /**
     * Adds a new item to the given {@link InventoryEntity} instance, contributing to a stack if necessary.
     * Does not persist the inventory to the database!
     *
     * @param inventoryEntity The {@link InventoryEntity} instance to manipulate.
     * @param productId       The ID of the product that should be added.
     * @param quantity        The quantity of the product to be added.
     * @param ignoreLimits    Whether inventory limits should be ignored.
     * @return The new {@link InventoryItemEntity} instance.
     */
    @SuppressWarnings("UnusedReturnValue")
    public InventoryItemEntity addStackedInventoryItem(InventoryEntity inventoryEntity, String productId,
                                                       int quantity, boolean ignoreLimits) {
        ProductEntity productEntity = productDAO.findByProductId(productId);

        // Validation
        if (productEntity == null)
            throw new EngineException("Could not find product " + productId + " to be added",
                    EngineExceptionCode.NoSuchEntitlementExists, true);
        if (quantity < -1)
            throw new EngineException("Invalid product quantity for addStackedInventoryItem: " + quantity,
                    EngineExceptionCode.EntitlementInvalidCount, true);
        if (quantity == -1)
            quantity = productEntity.getUseCount();
        InventoryItemEntity existingItem =
                inventoryItemDAO.findByInventoryIdAndEntitlementTag(inventoryEntity.getId(),
                        productEntity.getEntitlementTag());
        if (existingItem != null) {
            // update RemainingUseCount
            existingItem.setRemainingUseCount(existingItem.getRemainingUseCount() + quantity);
            inventoryItemDAO.update(existingItem);
            return existingItem;
        } else {
            // create a new item
            return addInventoryItem(inventoryEntity, productId, quantity, ignoreLimits);
        }
    }

    /**
     * Decreases the remaining-use-count of a stacked inventory item with the given hash
     *
     * @param inventoryEntity The {@link InventoryEntity} to find the item in.
     * @param hash            The hash of the item.
     * @throws EngineException if no item with the entitlement tag can be found.
     */
    public InventoryItemEntity decreaseItemCount(InventoryEntity inventoryEntity, Integer hash) {
        return decreaseItemCount(inventoryEntity, productDAO.findByHash(hash).getEntitlementTag());
    }

    /**
     * Decreases the remaining-use-count of a stacked inventory item with the given entitlement tag.
     *
     * @param inventoryEntity The {@link InventoryEntity} to find the item in.
     * @param entitlementTag  The entitlement tag of the item.
     * @throws EngineException if no item with the entitlement tag can be found.
     */
    public InventoryItemEntity decreaseItemCount(InventoryEntity inventoryEntity, String entitlementTag) {
        InventoryItemEntity existingItem =
                inventoryItemDAO.findByInventoryIdAndEntitlementTag(inventoryEntity.getId(),
                        entitlementTag);
        if (existingItem == null)
            throw new EngineException("Could not find entitlement '" + entitlementTag + "' in IID " + inventoryEntity.getId(), EngineExceptionCode.NoSuchEntitlementExists, true);

        return decreaseItemCount(inventoryEntity, existingItem);
    }

    public InventoryItemEntity decreaseItemCount(InventoryEntity inventoryEntity, InventoryItemEntity existingItem) {
        existingItem.setRemainingUseCount(existingItem.getRemainingUseCount() - 1);

        if (existingItem.getRemainingUseCount() <= 0) {
            // the <= should just be a == but you never know what could happen
            updateInventorySlots(inventoryEntity, existingItem.getProductEntity(), false);
            inventoryEntity.getInventoryItems().remove(existingItem);
            inventoryItemDAO.delete(existingItem);
            inventoryDAO.update(inventoryEntity);
        }

        return existingItem;
    }

    /**
     * Creates and persists a new {@link InventoryEntity} for the given {@link PersonaEntity}.
     *
     * @param personaEntity The {@link PersonaEntity} to create an inventory for.
     * @return The created {@link InventoryEntity}.
     */
    public InventoryEntity createInventory(PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setPersonaEntity(personaEntity);
        inventoryEntity.setPerformancePartsCapacity(parameterBO.getIntParam("STARTING_INVENTORY_PERF_SLOTS", 100));
        inventoryEntity.setSkillModPartsCapacity(parameterBO.getIntParam("STARTING_INVENTORY_SKILL_SLOTS", 100));
        inventoryEntity.setVisualPartsCapacity(parameterBO.getIntParam("STARTING_INVENTORY_VISUAL_SLOTS", 100));

        inventoryDAO.insert(inventoryEntity);
        addDefaultInventoryItems(inventoryEntity);

        return inventoryEntity;
    }

    /**
     * Removes the FIRST item with the given entitlement tag from the inventory belonging to the persona with the given ID.
     * Changes are persisted!
     *
     * @param personaId      The ID of the persona whose inventory should be updated
     * @param entitlementTag The entitlement tag to search for in the inventory.
     * @throws EngineException if no item with the given entitlement
     */
    public void removeItem(Long personaId, String entitlementTag) {
        PersonaEntity personaEntity = personaDAO.find(personaId);
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        removeItem(personaEntity, inventoryEntity, entitlementTag, -1);
    }

    /**
     * Removes the FIRST item with the given entitlement tag from the inventory belonging to the persona with the given ID.
     * Changes are persisted!
     *
     * @param personaId      The ID of the persona whose inventory should be updated
     * @param entitlementTag The entitlement tag to search for in the inventory.
     * @throws EngineException if no item with the given entitlement
     */
    public void removeItem(Long personaId, String entitlementTag, Integer quantity) {
        PersonaEntity personaEntity = personaDAO.find(personaId);
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        removeItem(personaEntity, inventoryEntity, entitlementTag, quantity);
    }

    /**
     * Removes the FIRST item with the given entitlement tag from the inventory belonging to the persona with the given ID.
     * Changes are persisted!
     *
     * @param personaEntity  The ID of the persona whose inventory should be updated
     * @param entitlementTag The entitlement tag to search for in the inventory.
     * @throws EngineException if no item with the given entitlement
     */
    public void removeItem(PersonaEntity personaEntity, String entitlementTag, Integer quantity) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        removeItem(personaEntity, inventoryEntity, entitlementTag, quantity);
    }

    /**
     * Removes the FIRST item with the given entitlement tag from the given {@link InventoryEntity}.
     * Changes are persisted!
     *
     * @param inventoryEntity The {@link InventoryEntity} to remove an item from.
     * @param entitlementTag  The entitlement tag to search for in the inventory.
     * @throws EngineException if no item with the given entitlement
     */
    private void removeItem(PersonaEntity personaEntity, InventoryEntity inventoryEntity, String entitlementTag) {
        removeItem(personaEntity, inventoryEntity, entitlementTag, -1);
    }

    /**
     * Removes the given quality (or the entire stack) of the FIRST item with the given entitlement tag from the given
     * {@link InventoryEntity}.
     * Changes are persisted!
     *
     * @param inventoryEntity The {@link InventoryEntity} to remove an item from.
     * @param entitlementTag  The entitlement tag to search for in the inventory.
     * @param quantity        The number of stacked items to remove. If set to -1 or the current stack size, the
     *                        entire stack is removed.
     * @throws EngineException if no item with the given entitlement
     */
    private void removeItem(PersonaEntity personaEntity, InventoryEntity inventoryEntity, String entitlementTag, Integer quantity) {
        InventoryItemEntity inventoryItemEntity =
                inventoryItemDAO.findByInventoryIdAndEntitlementTag(inventoryEntity.getId(), entitlementTag);

        if (inventoryItemEntity == null)
            throw new EngineException("An item stack with the entitlement tag '" + entitlementTag + "' could not be " +
                    "found. " +
                    "IID: " + inventoryEntity.getId(),
                    EngineExceptionCode.EntitlementNoSuchGroup, true);

        if (quantity == -1 || quantity == inventoryItemEntity.getRemainingUseCount()) {
            updateInventorySlots(inventoryEntity, inventoryItemEntity.getProductEntity(), false);
            inventoryEntity.getInventoryItems().remove(inventoryItemEntity);
            inventoryItemDAO.delete(inventoryItemEntity);
            inventoryDAO.update(inventoryEntity);
            driverPersonaBO.updateCash(personaEntity, personaEntity.getCash() + inventoryItemEntity.getResellPrice());
        } else {
            if (quantity < 1)
                throw new EngineException("An invalid removal operation was requested. Cannot remove " + quantity +
                        " items from a stack.", EngineExceptionCode.EntitlementInvalidCount, true);
            if (quantity > inventoryItemEntity.getRemainingUseCount())
                throw new EngineException("An invalid removal operation was requested. Cannot remove " + quantity +
                        " items from the stack, as there are only " + inventoryItemEntity.getRemainingUseCount(),
                        EngineExceptionCode.EntitlementInvalidCount, true);
            inventoryItemEntity.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount() - quantity);
            inventoryItemDAO.update(inventoryItemEntity);
        }
    }

    //endregion


    //region Private methods (implementation)

    private void updateInventorySlots(InventoryEntity inventoryEntity, ProductEntity productEntity,
                                      boolean isItemAdded) {
        switch (productEntity.getProductType()) {
            case "PERFORMANCEPART":
                inventoryEntity.setPerformancePartsUsedSlotCount(isItemAdded ?
                        inventoryEntity.getPerformancePartsUsedSlotCount() + 1 :
                        inventoryEntity.getPerformancePartsUsedSlotCount() - 1);
                break;
            case "SKILLMODPART":
                inventoryEntity.setSkillModPartsUsedSlotCount(isItemAdded ?
                        inventoryEntity.getSkillModPartsUsedSlotCount() + 1 :
                        inventoryEntity.getSkillModPartsUsedSlotCount() - 1);
                break;
            case "VISUALPART":
                inventoryEntity.setVisualPartsUsedSlotCount(isItemAdded ?
                        inventoryEntity.getVisualPartsUsedSlotCount() + 1 :
                        inventoryEntity.getVisualPartsUsedSlotCount() - 1);
                break;
        }
    }

    private void addDefaultInventoryItems(InventoryEntity inventoryEntity) {
        String itemDesc = parameterBO.getStrParam("STARTING_INVENTORY_ITEMS");

        if (itemDesc != null && !itemDesc.isEmpty()) {
            String[] items = itemDesc.trim().split(";");

            for (String itemInfo : items) {
                String[] parts = itemInfo.split("\\|");
                if (parts.length != 2) {
                    throw new EngineException("Failed to parse default inventory items",
                            EngineExceptionCode.UnspecifiedError, true);
                }

                String productId = parts[0];
                int quantity = Integer.parseInt(parts[1]);

                addInventoryItem(inventoryEntity, productId, quantity, null);
            }
        }
    }
    //endregion
}
