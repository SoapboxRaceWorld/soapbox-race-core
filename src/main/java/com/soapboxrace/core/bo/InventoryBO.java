package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class InventoryBO
{
    @EJB
    private PersonaDAO personaDAO;
    
    @EJB
    private InventoryDAO inventoryDAO;
    
    @EJB
    private InventoryItemDAO inventoryItemDAO;
    
    @EJB
    private ParameterBO parameterBO;

    public InventoryTrans getInventory(Long personaId)
    {
        InventoryTrans inventoryTrans = new InventoryTrans();
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        if (personaEntity == null)
        {
            return new InventoryTrans();
        }
        
        if (inventoryEntity == null)
        {
            inventoryEntity = createInventory(personaEntity);
        }
        
        inventoryTrans.setPerformancePartsCapacity(inventoryEntity.getPerformancePartsCapacity());
        inventoryTrans.setSkillModPartsCapacity(inventoryEntity.getSkillModPartsCapacity());
        inventoryTrans.setVisualPartsCapacity(inventoryEntity.getVisualPartsCapacity());

        ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
        
        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems())
        {
            InventoryItemTrans inventoryItemTrans = new InventoryItemTrans();
            inventoryItemTrans.setEntitlementTag(inventoryItemEntity.getEntitlementTag());
            inventoryItemTrans.setHash(inventoryItemEntity.getHash());
            inventoryItemTrans.setInventoryId(inventoryItemEntity.getId());
            inventoryItemTrans.setProductId("DO NOT USE ME");
            inventoryItemTrans.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount());
            inventoryItemTrans.setResellPrice(inventoryItemEntity.getResalePrice());
            inventoryItemTrans.setStringHash(inventoryItemEntity.getStringHash());
            inventoryItemTrans.setStatus(inventoryItemEntity.getStatus());
            inventoryItemTrans.setVirtualItemType(inventoryItemEntity.getVirtualItemType());
            
            arrayOfInventoryItemTrans.getInventoryItemTrans().add(inventoryItemTrans);
        }
        
        inventoryTrans.setInventoryItems(arrayOfInventoryItemTrans);
        
        inventoryTrans.setVisualPartsCapacity(inventoryEntity.getVisualPartsCapacity());
        inventoryTrans.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount());
        
        inventoryTrans.setSkillModPartsCapacity(inventoryEntity.getSkillModPartsCapacity());
        inventoryTrans.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount());
        
        inventoryTrans.setPerformancePartsCapacity(inventoryEntity.getPerformancePartsCapacity());
        inventoryTrans.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount());

        return inventoryTrans;
    }
    
    public void sellEntitlement(long personaId, String entitlementTag)
    {
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);
        InventoryItemEntity inventoryItemEntity = null;

        for (InventoryItemEntity item : inventoryEntity.getItems())
        {
            if (entitlementTag.equals(item.getEntitlementTag()) && "ACTIVE".equals(item.getStatus()))
            {
                inventoryItemEntity = item;
                break;
            }
        }
        
        if (inventoryItemEntity != null)
        {
            int newCash = (int) (personaEntity.getCash() + inventoryItemEntity.getResalePrice());

            if (newCash > 9999999)
                newCash = 9999999;

            if (newCash < 1)
                newCash = 1;

            personaEntity.setCash(newCash);
            personaDAO.update(personaEntity);
            
            inventoryItemDAO.delete(inventoryItemEntity);
            
            switch (inventoryItemEntity.getVirtualItemType())
            {
                case "PERFORMANCEPART":
                    inventoryEntity.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount() - 1);
                    break;
                case "VISUALPART":
                    inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() - 1);
                    break;
                case "SKILLMODPART":
                    inventoryEntity.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount() - 1);
                    break;
            }
            
            inventoryDAO.update(inventoryEntity);
        }
    }
    
    public boolean hasItem(long personaId, long hash)
    {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);
        
        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems())
        {
            if (inventoryItemEntity.getHash() == hash && inventoryItemEntity.getRemainingUseCount() > 0)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public void decrementUsage(long personaId, long hash)
    {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

        for (InventoryItemEntity item : inventoryEntity.getItems())
        {
            if (item.getHash() == hash)
            {
                item.setRemainingUseCount(item.getRemainingUseCount() - 1);
                inventoryItemDAO.update(item);
                
                break;
            }
        }
    }
    
    public InventoryEntity createInventory(PersonaEntity personaEntity)
    {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setPersona(personaEntity);
        inventoryEntity.setSkillModPartsCapacity(parameterBO.getStartingInventorySkillSlots());
        inventoryEntity.setPerformancePartsCapacity(parameterBO.getStartingInventoryPerfSlots());
        inventoryEntity.setVisualPartsCapacity(parameterBO.getStartingInventoryVisualSlots());
        inventoryDAO.insert(inventoryEntity);

		inventoryItemDAO.insert(getPowerUpInventory("nosshot", -1681514783, "0x9bc61ee1", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("runflattires", -537557654, "0xdff5856a", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("instantcooldown", -1692359144,"0x9b20a618", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("shield", -364944936, "0xea3f61d8", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("slingshot", 2236629, "0x2220d5", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("ready", 957701799, "0x39155ea7", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("juggernaut", 1805681994, "0x6ba0854a", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("emergencyevade", -611661916, "0xdb8ac7a4", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("team_emergencyevade", -1564932069, "0xa2b9081b", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("onemorelap", 1627606782, "0x61034efe", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("team_slingshot", 1113720384, "0x42620640", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("trafficmagnet", 125509666, "0x77b2022", inventoryEntity, personaEntity));        
        return inventoryDAO.findByPersonaId(personaEntity.getPersonaId());
    }

    private InventoryItemEntity getPowerUpInventory(String tag, int hash, String strHash, InventoryEntity inventoryEntity, PersonaEntity personaEntity) {
        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setEntitlementTag(tag);
        inventoryItemEntity.setHash(hash);
        inventoryItemEntity.setProductId("DO NOT USE ME");
        inventoryItemEntity.setRemainingUseCount(250);
        inventoryItemEntity.setResalePrice(0.00f);
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setStringHash(strHash);
        inventoryItemEntity.setVirtualItemType("powerup");
        inventoryItemEntity.setInventory(inventoryEntity);
        inventoryItemEntity.setPersona(personaEntity);
        return inventoryItemEntity;
    }
}
