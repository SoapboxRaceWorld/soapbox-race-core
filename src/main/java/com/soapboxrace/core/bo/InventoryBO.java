package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class InventoryBO {

    @EJB
    private InventoryDAO inventoryDAO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private VirtualItemDAO virtualItemDAO;

    public InventoryItemEntity addFromCatalog(ProductEntity productEntity, PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        if (inventoryEntity != null) {
            VirtualItemEntity virtualItemEntity = virtualItemDAO.findByHash(productEntity.getHash());

            if (virtualItemEntity != null) {
                System.out.println("productHash: " + productEntity.getHash());
                System.out.println("virtualItemName: " + virtualItemEntity.getItemName());

                InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
                inventoryItemEntity.setInventoryEntity(inventoryEntity);
                inventoryItemEntity.setEntitlementTag(virtualItemEntity.getItemName());

                if (productEntity.getDurationMinute() > 0) {
                    inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusMinutes(productEntity.getDurationMinute()));
                }

                inventoryItemEntity.setHash(virtualItemEntity.getHash());
                inventoryItemEntity.setProductId("DO NOT USE ME");
                inventoryItemEntity.setRemainingUseCount(productEntity.getUseCount());
                inventoryItemEntity.setResellPrice((int) productEntity.getResalePrice());
                inventoryItemEntity.setStatus("ACTIVE");
                inventoryItemEntity.setVirtualItemType(virtualItemEntity.getType());

                updateInventoryCapacities(inventoryEntity, inventoryItemEntity, true);
                inventoryItemDAO.insert(inventoryItemEntity);

                return inventoryItemEntity;
            }
        }

        return null;
    }

    public InventoryItemEntity addFromCatalogOrUpdateUsage(ProductEntity productEntity, PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        if (inventoryEntity != null) {
            VirtualItemEntity virtualItemEntity = virtualItemDAO.findByHash(productEntity.getHash());

            if (virtualItemEntity != null) {
                InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(personaEntity.getPersonaId(), productEntity.getHash());

                if (inventoryItemEntity == null) {
                    System.out.println("productHash: " + productEntity.getHash());
                    System.out.println("virtualItemName: " + virtualItemEntity.getItemName());

                    inventoryItemEntity = new InventoryItemEntity();
                    inventoryItemEntity.setInventoryEntity(inventoryEntity);
                    inventoryItemEntity.setEntitlementTag(virtualItemEntity.getItemName());

                    if (productEntity.getDurationMinute() > 0) {
                        inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusMinutes(productEntity.getDurationMinute()));
                    }

                    inventoryItemEntity.setHash(virtualItemEntity.getHash());
                    inventoryItemEntity.setProductId("DO NOT USE ME");
                    inventoryItemEntity.setRemainingUseCount(productEntity.getUseCount());
                    inventoryItemEntity.setResellPrice((int) productEntity.getResalePrice());
                    inventoryItemEntity.setStatus("ACTIVE");
                    inventoryItemEntity.setVirtualItemType(virtualItemEntity.getType());

                    updateInventoryCapacities(inventoryEntity, inventoryItemEntity, true);
                    inventoryItemDAO.insert(inventoryItemEntity);
                } else {
                    inventoryItemEntity.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount() + productEntity.getUseCount());
                    inventoryItemDAO.update(inventoryItemEntity);
                }

                return inventoryItemEntity;
            }
        }

        return null;
    }

    /**
     * Add the product with the given ID to the inventory of the persona with the given ID.
     *
     * @param personaId The persona ID
     * @param productId The product ID
     */
    public void addPart(Long personaId, String productId) {
        ProductEntity productEntity = productDAO.findByProductId(productId);

        if (productEntity != null) {
            InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

            if (inventoryEntity != null) {
                VirtualItemEntity virtualItemEntity = virtualItemDAO.findByHash(productEntity.getHash());

                if (virtualItemEntity != null) {
                    addFromCatalog(productEntity, personaDAO.findById(personaId));
                }
            }
        }
    }

    /**
     * Delete an inventory item and update the capacity information of its associated inventory.
     *
     * @param personaId The ID of the persona whose inventory contains the item to be deleted.
     * @param hash      The hash of the item to be deleted.
     */
    public void deletePart(Long personaId, Integer hash) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        if (inventoryEntity != null) {
            InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(personaId, hash);

            if (inventoryItemEntity != null) {
                inventoryEntity.getInventoryItems().remove(inventoryItemEntity);
                updateInventoryCapacities(inventoryEntity, inventoryItemEntity, false);
            }
        }
    }

    /**
     * Delete an inventory item and update the capacity information of its associated inventory.
     *
     * @param personaId      The ID of the persona whose inventory contains the item to be deleted.
     * @param entitlementTag The entitlement tag of the item to be deleted.
     */
    public void deletePart(Long personaId, String entitlementTag) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        if (inventoryEntity != null) {
            InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId, entitlementTag);

            if (inventoryItemEntity != null) {
                inventoryEntity.getInventoryItems().remove(inventoryItemEntity);
                updateInventoryCapacities(inventoryEntity, inventoryItemEntity, false);
            }
        }
    }

    /**
     * Create a base inventory for the given persona.
     */
    public InventoryEntity createInventory(PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setPersonaEntity(personaEntity);

        inventoryEntity.setVisualPartsUsedSlotCount(0);
        inventoryEntity.setSkillModPartsUsedSlotCount(0);
        inventoryEntity.setPerformancePartsUsedSlotCount(0);
        inventoryEntity.setVisualPartsCapacity(250);
        inventoryEntity.setSkillModPartsCapacity(250);
        inventoryEntity.setPerformancePartsCapacity(250);

        inventoryEntity.getInventoryItems().add(getPowerupItem("nosshot", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("runflattires", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("instantcooldown", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("shield", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("slingshot", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("ready", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("juggernaut", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("emergencyevade", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("team_emergencyevade", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("onemorelap", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("team_slingshot", inventoryEntity));
        inventoryEntity.getInventoryItems().add(getPowerupItem("trafficmagnet", inventoryEntity));

        inventoryDAO.insert(inventoryEntity);

        return inventoryDAO.findByPersonaId(personaEntity.getPersonaId());
    }

    /**
     * Check the inventory of the persona by the given ID for an item with the given hash.
     *
     * @param activePersonaId
     * @param hash
     * @return
     */
    public boolean hasItem(Long activePersonaId, Integer hash) {
        InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(activePersonaId, hash);

        return inventoryItemEntity != null;
    }

    /**
     * Convert the inventory belonging to the persona with the given ID to the XML structure for the game.
     *
     * @param personaId The persona ID
     * @return The XML entity
     */
    public InventoryTrans getInventory(long personaId) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        if (inventoryEntity == null) {
            return getInventoryTrans(this.createInventory(personaDAO.findById(personaId)));
        }

        return getInventoryTrans(inventoryEntity);
    }

    public void decrementUsage(Long activePersonaId, Integer hash) {
        InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(activePersonaId, hash);

        if (inventoryItemEntity != null && inventoryItemEntity.getRemainingUseCount() > 0) {
            inventoryItemEntity.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount() - 1);
            inventoryItemDAO.update(inventoryItemEntity);
        }
    }

    public boolean isInventoryFull(ProductEntity productEntity, PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        if (inventoryEntity == null)
            return false;
        switch (productEntity.getProductType()) {
            case "PERFORMANCEPART":
                return inventoryEntity.getPerformancePartsUsedSlotCount() >= inventoryEntity.getPerformancePartsCapacity();
            case "SKILLMODPART":
                return inventoryEntity.getSkillModPartsUsedSlotCount() >= inventoryEntity.getSkillModPartsCapacity();
            case "VISUALPART":
                return inventoryEntity.getVisualPartsUsedSlotCount() >= inventoryEntity.getVisualPartsCapacity();
        }

        return false;
    }

    private void updateInventoryCapacities(InventoryEntity inventoryEntity, InventoryItemEntity item, boolean added) {
        switch (item.getVirtualItemType()) {
            case "performancepart":
                inventoryEntity.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount() + (added ? 1 : -1));
                break;
            case "skillmodpart":
                inventoryEntity.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount() + (added ? 1 : -1));
                break;
            case "visualpart":
                inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() + (added ? 1 : -1));
                break;
        }

        inventoryDAO.update(inventoryEntity);
    }

    private InventoryItemEntity getPowerupItem(String entitlementTag, InventoryEntity inventoryEntity) {
        VirtualItemEntity virtualItemEntity = virtualItemDAO.findByItemName(entitlementTag);

        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setResellPrice(0);
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setRemainingUseCount(250);
        inventoryItemEntity.setHash(virtualItemEntity.getHash());
        inventoryItemEntity.setEntitlementTag(entitlementTag);
        inventoryItemEntity.setExpirationDate(null);
        inventoryItemEntity.setVirtualItemType("powerup");
        inventoryItemEntity.setProductId("DO NOT USE ME");
        inventoryItemEntity.setInventoryEntity(inventoryEntity);
        return inventoryItemEntity;
    }

    private InventoryTrans getInventoryTrans(InventoryEntity inventoryEntity) {
        InventoryTrans inventoryTrans = new InventoryTrans();
        inventoryTrans.setInventoryItems(new ArrayOfInventoryItemTrans());
        inventoryTrans.setPerformancePartsCapacity(inventoryEntity.getPerformancePartsCapacity());
        inventoryTrans.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount());
        inventoryTrans.setSkillModPartsCapacity(inventoryEntity.getSkillModPartsCapacity());
        inventoryTrans.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount());
        inventoryTrans.setVisualPartsCapacity(inventoryEntity.getVisualPartsCapacity());
        inventoryTrans.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount());

        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getInventoryItems()) {
            InventoryItemTrans inventoryItemTrans = new InventoryItemTrans();
            inventoryItemTrans.setEntitlementTag(inventoryItemEntity.getEntitlementTag());
            inventoryItemTrans.setInventoryId(inventoryItemEntity.getId());
            inventoryItemTrans.setProductId(inventoryItemEntity.getProductId());
            inventoryItemTrans.setHash(inventoryItemEntity.getHash());
            inventoryItemTrans.setStringHash("0x" + String.format("%08X", inventoryItemEntity.getHash()));
            inventoryItemTrans.setVirtualItemType(inventoryItemEntity.getVirtualItemType());
            inventoryItemTrans.setStatus(inventoryItemEntity.getStatus());
            inventoryItemTrans.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount());
            inventoryItemTrans.setResellPrice(inventoryItemEntity.getResellPrice());

            if (inventoryItemEntity.getExpirationDate() != null) {
                inventoryItemTrans.setExpirationDate(inventoryItemEntity.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            }

            inventoryTrans.getInventoryItems().getInventoryItemTrans().add(inventoryItemTrans);
        }

        return inventoryTrans;
    }
}
