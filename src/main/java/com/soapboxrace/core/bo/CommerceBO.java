package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.util.ListDiffer;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CommerceBO
{
    @EJB
    private PersonaBO personaBO;

    @EJB
    private CarSlotDAO carSlotDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private ProductDAO productDao;

    @EJB
    private VinylProductDAO vinylProductDao;

    @EJB
    private InventoryDAO inventoryDAO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    public CommerceResultStatus updateCar(CommerceSessionTrans commerceSessionTrans, PersonaEntity personaEntity)
    {
        CarSlotEntity carSlotEntity = carSlotDAO.findById(commerceSessionTrans.getUpdatedCar().getId());

        if (carSlotEntity == null)
            return CommerceResultStatus.FAIL_INVALID_BASKET;

        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());
        OwnedCarTrans currentCar = UnmarshalXML.unMarshal(carSlotEntity.getOwnedCarTrans(), OwnedCarTrans.class);
        CustomCarTrans currentCustomCar = currentCar.getCustomCar();

        OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaEntity.getPersonaId());
        OwnedCarTrans updatedCar = commerceSessionTrans.getUpdatedCar();
        CustomCarTrans updatedCustomCar = updatedCar.getCustomCar();

        int purchaseCost = 0;
        int saleValue = 0;

        ListDiffer.ListDifferences<ListDiffer.ProxyItem<CustomVinylTrans>> vinylsDiff = ListDiffer.getDifferencesByKey(
                currentCustomCar.getVinyls().getCustomVinylTrans(), updatedCustomCar.getVinyls().getCustomVinylTrans(),
                CustomVinylTrans::getHash);
        ListDiffer.ListDifferences<ListDiffer.ProxyItem<PerformancePartTrans>> perfPartsDiff = ListDiffer.getDifferencesByKey(
                currentCustomCar.getPerformanceParts().getPerformancePartTrans(), updatedCustomCar.getPerformanceParts().getPerformancePartTrans(),
                PerformancePartTrans::getPerformancePartAttribHash);
        ListDiffer.ListDifferences<ListDiffer.ProxyItem<VisualPartTrans>> visualPartsDiff = ListDiffer.getDifferencesByKey(
                currentCustomCar.getVisualParts().getVisualPartTrans(), updatedCustomCar.getVisualParts().getVisualPartTrans(),
                VisualPartTrans::getPartHash);
        ListDiffer.ListDifferences<ListDiffer.ProxyItem<SkillModPartTrans>> skillPartsDiff = ListDiffer.getDifferencesByKey(
                currentCustomCar.getSkillModParts().getSkillModPartTrans(), updatedCustomCar.getSkillModParts().getSkillModPartTrans(),
                SkillModPartTrans::getSkillModPartAttribHash);

        // region Performance Parts
        {
            for (PerformancePartTrans performancePartTrans : currentCustomCar.getPerformanceParts().getPerformancePartTrans())
            {
                System.out.println(String.format("[Commerce] CURRENT PERF PART: %d", performancePartTrans.getPerformancePartAttribHash()));
            }

            for (ListDiffer.ProxyItem<PerformancePartTrans> addedPart : perfPartsDiff.getAdded())
            {
                final PerformancePartTrans performancePartTrans = addedPart.getItem();
                System.out.println(String.format("[Commerce] PERF PART ADDED: %d", performancePartTrans.getPerformancePartAttribHash()));

                InventoryItemEntity itemInInventory = findAvailableInInventory(personaEntity, performancePartTrans.getPerformancePartAttribHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", performancePartTrans.getPerformancePartAttribHash()));

                    itemInInventory.setRemainingUseCount(0);
                    itemInInventory.setStatus("INSTALLED");
                    inventoryItemDAO.update(itemInInventory);

                    inventoryEntity.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount() - 1);
                    inventoryDAO.update(inventoryEntity);
                }
            }

            for (ListDiffer.ProxyItem<PerformancePartTrans> keptPart : perfPartsDiff.getKept())
            {
                System.out.println(String.format("[Commerce] PERF PART KEPT: %d", keptPart.getItem().getPerformancePartAttribHash()));
            }

            for (ListDiffer.ProxyItem<PerformancePartTrans> removedPart : perfPartsDiff.getRemoved())
            {
                final PerformancePartTrans performancePartTrans = removedPart.getItem();
                System.out.println(String.format("[Commerce] PERF PART REMOVED: %d", performancePartTrans.getPerformancePartAttribHash()));

                InventoryItemEntity itemInInventory = findUsedInInventory(personaEntity, performancePartTrans.getPerformancePartAttribHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", performancePartTrans.getPerformancePartAttribHash()));

                    saleValue += itemInInventory.getResalePrice();
                    inventoryItemDAO.delete(itemInInventory);
                }
            }
        }
        // endregion

        // region Visual Parts
        {
            for (ListDiffer.ProxyItem<VisualPartTrans> addedPart : visualPartsDiff.getAdded())
            {
                final VisualPartTrans visualPartTrans = addedPart.getItem();
                System.out.println(String.format("[Commerce] VISUAL PART ADDED: %d", addedPart.getItem().getPartHash()));

                InventoryItemEntity itemInInventory = findAvailableInInventory(personaEntity, visualPartTrans.getPartHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", visualPartTrans.getPartHash()));

                    itemInInventory.setRemainingUseCount(0);
                    itemInInventory.setStatus("INSTALLED");
                    inventoryItemDAO.update(itemInInventory);

                    inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() - 1);
                    inventoryDAO.update(inventoryEntity);
                }
            }

            for (ListDiffer.ProxyItem<VisualPartTrans> keptPart : visualPartsDiff.getKept())
            {
                System.out.println(String.format("[Commerce] VISUAL PART KEPT: %d", keptPart.getItem().getPartHash()));
            }

            for (ListDiffer.ProxyItem<VisualPartTrans> removedPart : visualPartsDiff.getRemoved())
            {
                final VisualPartTrans visualPartTrans = removedPart.getItem();
                System.out.println(String.format("[Commerce] VISUAL PART REMOVED: %d", removedPart.getItem().getPartHash()));

                InventoryItemEntity itemInInventory = findUsedInInventory(personaEntity, visualPartTrans.getPartHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", visualPartTrans.getPartHash()));

                    saleValue += itemInInventory.getResalePrice();
                    inventoryItemDAO.delete(itemInInventory);
                }
            }
        }
        // endregion

        // region Skill Parts
        {
            for (ListDiffer.ProxyItem<SkillModPartTrans> addedPart : skillPartsDiff.getAdded())
            {
                final SkillModPartTrans skillModPartTrans = addedPart.getItem();
                System.out.println(String.format("[Commerce] SKILLMOD PART ADDED: %d", addedPart.getItem().getSkillModPartAttribHash()));

                InventoryItemEntity itemInInventory = findAvailableInInventory(personaEntity, skillModPartTrans.getSkillModPartAttribHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", skillModPartTrans.getSkillModPartAttribHash()));

                    itemInInventory.setRemainingUseCount(0);
                    itemInInventory.setStatus("INSTALLED");
                    inventoryItemDAO.update(itemInInventory);

                    inventoryEntity.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount() - 1);
                    inventoryDAO.update(inventoryEntity);
                }
            }

            for (ListDiffer.ProxyItem<SkillModPartTrans> keptPart : skillPartsDiff.getKept())
            {
                System.out.println(String.format("[Commerce] SKILLMOD PART KEPT: %d", keptPart.getItem().getSkillModPartAttribHash()));
            }

            for (ListDiffer.ProxyItem<SkillModPartTrans> removedPart : skillPartsDiff.getRemoved())
            {
                final SkillModPartTrans skillModPartTrans = removedPart.getItem();
                System.out.println(String.format("[Commerce] SKILLMOD PART REMOVED: %d", removedPart.getItem().getSkillModPartAttribHash()));

                InventoryItemEntity itemInInventory = findUsedInInventory(personaEntity, skillModPartTrans.getSkillModPartAttribHash());

                if (itemInInventory != null)
                {
                    System.out.println(String.format("[Commerce] Found %d in inventory", skillModPartTrans.getSkillModPartAttribHash()));

                    saleValue += itemInInventory.getResalePrice();
                    inventoryItemDAO.delete(itemInInventory);
                }
            }
        }
        // endregion

        for (BasketItemTrans basketItemTrans : commerceSessionTrans.getBasket().getItems().getBasketItemTrans())
        {
            if (basketItemTrans.getProductId().contains("SRV-VINYL"))
            {
                VinylProductEntity vinylProductEntity = vinylProductDao.findByProductId(basketItemTrans.getProductId());

                if (vinylProductEntity != null)
                {
                    purchaseCost += vinylProductEntity.getPrice();
                }
            } else
            {
                ProductEntity productEntity = productDao.findByProductId(basketItemTrans.getProductId());
                
                if (productEntity != null)
                {
                    purchaseCost += productEntity.getPrice();
                }
            }

//            if (basketItemTrans.getProductId().contains("SRV-PWHEEL") || basketItemTrans.getProductId().contains("SRV-PBODY"))
//            {
//                ProductEntity productEntity = productDao.findByProductId(basketItemTrans.getProductId());
//
//                if (productEntity != null)
//                {
//                    purchaseCost += productEntity.getPrice();
//                }
//            }
        }

        if (personaEntity.getCash() < purchaseCost)
        {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }

        int newCash = (int) (personaEntity.getCash() - purchaseCost + saleValue);

        if (newCash > 9999999)
            newCash = 9999999;

        if (newCash < 1)
            newCash = 1;

        personaEntity.setCash(newCash);
        personaDAO.update(personaEntity);

        ownedCarTrans.getCustomCar().setCarClassHash(commerceSessionTrans.getUpdatedCar().getCustomCar().getCarClassHash());
        ownedCarTrans.getCustomCar().setVinyls(commerceSessionTrans.getUpdatedCar().getCustomCar().getVinyls());
        ownedCarTrans.getCustomCar().setPaints(commerceSessionTrans.getUpdatedCar().getCustomCar().getPaints());
        ownedCarTrans.getCustomCar().setSkillModParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getSkillModParts());
        ownedCarTrans.getCustomCar().setPerformanceParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getPerformanceParts());
        ownedCarTrans.getCustomCar().setVisualParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getVisualParts());
        ownedCarTrans.setOwnershipType("CustomizedCar");
        ownedCarTrans.setHeat(1);

        carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
        carSlotDAO.update(carSlotEntity);
        return CommerceResultStatus.SUCCESS;

//
//		CarSlotEntity carSlotEntity = carSlotDAO.findById(commerceSessionTrans.getUpdatedCar().getId());
//		if (carSlotEntity != null) {
//			List<BasketItemTrans> listBasketItemTrans = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
//			if(!listBasketItemTrans.isEmpty()) { // if buy or install perf part
//				int maxBuyPrice = 0;
//				for(BasketItemTrans basketItemTrans : listBasketItemTrans) {
//					if(basketItemTrans.getProductId().contains("SRV-VINYL")) {
//						maxBuyPrice += (vinylProductDao.findByProductId(basketItemTrans.getProductId()).getPrice() * basketItemTrans.getQuantity());
//					} else {
//						maxBuyPrice += (productDao.findByProductId(basketItemTrans.getProductId()).getPrice() * basketItemTrans.getQuantity());
//					}
//				}
//				if(maxBuyPrice > 0) {
//					if(personaEntity.getCash() < maxBuyPrice) {
//						return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
//					}
//					
//					personaEntity.setCash(personaEntity.getCash() - maxBuyPrice);
//					personaDAO.update(personaEntity);
//				}
//			} else { // else i sell some part from my inventory
//				
//			}
//			
//			carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
//			carSlotDAO.update(carSlotEntity);
//			return CommerceResultStatus.SUCCESS;
//		}
//		return CommerceResultStatus.FAIL_INVALID_BASKET;
    }

    private InventoryItemEntity findAvailableInInventory(PersonaEntity personaEntity, long hash)
    {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems())
        {
            if ("ACTIVE".equals(inventoryItemEntity.getStatus()) && inventoryItemEntity.getHash() == hash)
                return inventoryItemEntity;
        }

        return null;
    }

    private InventoryItemEntity findUsedInInventory(PersonaEntity personaEntity, long hash)
    {
        InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

        for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems())
        {
            if ("INSTALLED".equals(inventoryItemEntity.getStatus()) && inventoryItemEntity.getHash() == hash && inventoryItemEntity.getRemainingUseCount() == 0)
                return inventoryItemEntity;
        }

        return null;
    }

    private ProductEntity findProduct(long hash)
    {
        return productDao.findByHash(hash);
    }

    private VinylProductEntity findVProduct(long hash)
    {
        return vinylProductDao.findByHash(hash);
    }

    public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans)
    {
        OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
        ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
        ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
        ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
        ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
        ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
        return ownedCarTrans;
    }

}
