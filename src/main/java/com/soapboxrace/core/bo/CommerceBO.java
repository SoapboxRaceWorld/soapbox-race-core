package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.VinylProductEntity;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.CustomVinylTrans;
import com.soapboxrace.jaxb.http.EntitlementItemTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PerformancePartTrans;
import com.soapboxrace.jaxb.http.SkillModPartTrans;
import com.soapboxrace.jaxb.http.VisualPartTrans;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.util.ListDiffer;

@Stateless
public class CommerceBO {
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

	@EJB
	private ParameterBO parameterBO;

	public CommerceResultStatus updateCar(CommerceSessionTrans commerceSessionTrans, PersonaEntity personaEntity) {
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
				currentCustomCar.getVinyls().getCustomVinylTrans(), updatedCustomCar.getVinyls().getCustomVinylTrans(), CustomVinylTrans::getHash);
		ListDiffer.ListDifferences<ListDiffer.ProxyItem<PerformancePartTrans>> perfPartsDiff = ListDiffer.getDifferencesByKey(
				currentCustomCar.getPerformanceParts().getPerformancePartTrans(), updatedCustomCar.getPerformanceParts().getPerformancePartTrans(),
				PerformancePartTrans::getPerformancePartAttribHash);
		ListDiffer.ListDifferences<ListDiffer.ProxyItem<VisualPartTrans>> visualPartsDiff = ListDiffer.getDifferencesByKey(
				currentCustomCar.getVisualParts().getVisualPartTrans(), updatedCustomCar.getVisualParts().getVisualPartTrans(), VisualPartTrans::getPartHash);
		ListDiffer.ListDifferences<ListDiffer.ProxyItem<SkillModPartTrans>> skillPartsDiff = ListDiffer.getDifferencesByKey(
				currentCustomCar.getSkillModParts().getSkillModPartTrans(), updatedCustomCar.getSkillModParts().getSkillModPartTrans(),
				SkillModPartTrans::getSkillModPartAttribHash);

		for (EntitlementItemTrans entitlementItemTrans : commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans()) {
			String tag = entitlementItemTrans.getEntitlementId();
			int quantity = entitlementItemTrans.getQuantity();

			List<InventoryItemEntity> inventoryItemEntityList = inventoryItemDAO.findListByEntitlementTagAndPersona(tag, personaEntity);

			for (InventoryItemEntity item : inventoryItemEntityList) {
				if ("INSTALLED".equals(item.getStatus())) {
					inventoryItemEntityList.remove(item);
				}
			}

			if (inventoryItemEntityList.isEmpty()) {
				continue;
			}

			if (inventoryItemEntityList.size() < quantity) {
				quantity = inventoryItemEntityList.size();
			}

			saleValue += inventoryItemEntityList.get(0).getResalePrice() * quantity;

			inventoryItemEntityList.forEach(inventoryItemDAO::delete);

			switch (inventoryItemEntityList.get(0).getVirtualItemType()) {
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
		}

		inventoryDAO.update(inventoryEntity);

		// region Vinyls
		for (ListDiffer.ProxyItem<CustomVinylTrans> addedVinyl : vinylsDiff.getAdded()) {
			final CustomVinylTrans customVinylTrans = addedVinyl.getItem();
			final int vinylTransHash = customVinylTrans.getHash();

			VinylProductEntity vinylProductEntity = vinylProductDao.findByHash(vinylTransHash);

			if (vinylProductEntity != null) {
				purchaseCost += vinylProductEntity.getPrice();
				System.out.println(String.format("[Commerce: Vinyls] Added - %d", vinylTransHash));
			} else {
				System.out.println(String.format("[Commerce: Vinyls] Unknown - %d", vinylTransHash));
			}
		}
		// endregion

		// region Performance Parts
		for (ListDiffer.ProxyItem<PerformancePartTrans> addedPerfPart : perfPartsDiff.getAdded()) {
			final PerformancePartTrans performancePartTrans = addedPerfPart.getItem();

			InventoryItemEntity inventoryItemEntity = findAvailableInInventory(personaEntity, performancePartTrans.getPerformancePartAttribHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: PerfParts] Found %d to add in inventory: %d/%s",
						performancePartTrans.getPerformancePartAttribHash(), inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				inventoryItemEntity.setStatus("INSTALLED");
				inventoryItemEntity.setRemainingUseCount(0);

				inventoryEntity.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount() - 1);

				inventoryItemDAO.update(inventoryItemEntity);
				inventoryDAO.update(inventoryEntity);
			} else {
				ProductEntity productEntity = productDao.findByHash(performancePartTrans.getPerformancePartAttribHash());

				if (productEntity != null) {
					purchaseCost += productEntity.getPrice();
				}
			}
		}

		for (ListDiffer.ProxyItem<PerformancePartTrans> removedPerfPart : perfPartsDiff.getRemoved()) {
			final PerformancePartTrans performancePartTrans = removedPerfPart.getItem();

			InventoryItemEntity inventoryItemEntity = findUsedInInventory(personaEntity, performancePartTrans.getPerformancePartAttribHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: PerfParts] Found %d to remove in inventory: %d/%s",
						performancePartTrans.getPerformancePartAttribHash(), inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				saleValue += inventoryItemEntity.getResalePrice();

				inventoryItemDAO.delete(inventoryItemEntity);
			} else {
				System.out.println(String.format("[Commerce: PerfParts] Couldn't find %d to remove in inventory; looking in catalog",
						performancePartTrans.getPerformancePartAttribHash()));

				ProductEntity productEntity = productDao.findByHash(performancePartTrans.getPerformancePartAttribHash());

				if (productEntity != null) {
					System.out.println(String.format("[Commerce: PerfParts] Found %d to remove in catalog: %s for $%.0f IGC",
							performancePartTrans.getPerformancePartAttribHash(), productEntity.getProductId(), productEntity.getPrice()));
					saleValue += productEntity.getPrice() / 4;
				}
			}
		}
		// endregion

		// region Visual Parts
		for (ListDiffer.ProxyItem<VisualPartTrans> addedVisualPart : visualPartsDiff.getAdded()) {
			final VisualPartTrans visualPartTrans = addedVisualPart.getItem();

			InventoryItemEntity inventoryItemEntity = findAvailableInInventory(personaEntity, visualPartTrans.getPartHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: VisParts] Found %d to add in inventory: %d/%s", visualPartTrans.getPartHash(),
						inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				inventoryItemEntity.setStatus("INSTALLED");
				inventoryItemEntity.setRemainingUseCount(0);

				inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() - 1);

				inventoryItemDAO.update(inventoryItemEntity);
				inventoryDAO.update(inventoryEntity);
			} else {
				ProductEntity productEntity = productDao.findByHash(visualPartTrans.getPartHash());

				if (productEntity != null) {
					purchaseCost += productEntity.getPrice();
				}
			}
		}

		for (ListDiffer.ProxyItem<VisualPartTrans> removedVisualPart : visualPartsDiff.getRemoved()) {
			final VisualPartTrans visualPartTrans = removedVisualPart.getItem();

			InventoryItemEntity inventoryItemEntity = findUsedInInventory(personaEntity, visualPartTrans.getPartHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: VisParts] Found %d to remove in inventory: %d/%s", visualPartTrans.getPartHash(),
						inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				saleValue += inventoryItemEntity.getResalePrice();

				inventoryItemDAO.delete(inventoryItemEntity);
			} else {
				System.out.println(String.format("[Commerce: VisParts] Couldn't find %d to remove in inventory", visualPartTrans.getPartHash()));

				ProductEntity productEntity = productDao.findByHash(visualPartTrans.getPartHash());

				if (productEntity != null) {
					System.out.println(String.format("[Commerce: VisParts] Found %d to remove in catalog: %s for $%.0f IGC", visualPartTrans.getPartHash(),
							productEntity.getProductId(), productEntity.getPrice()));
					saleValue += productEntity.getPrice() / 4;
				}
			}
		}
		// endregion

		// region Skill Mod Parts
		for (ListDiffer.ProxyItem<SkillModPartTrans> addedSkillMod : skillPartsDiff.getAdded()) {
			final SkillModPartTrans skillModPartTrans = addedSkillMod.getItem();

			InventoryItemEntity inventoryItemEntity = findAvailableInInventory(personaEntity, skillModPartTrans.getSkillModPartAttribHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: SkillMods] Found %d to add in inventory: %d/%s", skillModPartTrans.getSkillModPartAttribHash(),
						inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				inventoryItemEntity.setStatus("INSTALLED");
				inventoryItemEntity.setRemainingUseCount(0);

				inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() - 1);

				inventoryItemDAO.update(inventoryItemEntity);
				inventoryDAO.update(inventoryEntity);
			} else {
				ProductEntity productEntity = productDao.findByHash(skillModPartTrans.getSkillModPartAttribHash());

				if (productEntity != null) {
					purchaseCost += productEntity.getPrice();
				}
			}
		}

		for (ListDiffer.ProxyItem<SkillModPartTrans> removedSkillMod : skillPartsDiff.getRemoved()) {
			final SkillModPartTrans skillModPartTrans = removedSkillMod.getItem();

			InventoryItemEntity inventoryItemEntity = findUsedInInventory(personaEntity, skillModPartTrans.getSkillModPartAttribHash());

			if (inventoryItemEntity != null) {
				System.out.println(String.format("[Commerce: SkillMods] Found %d to remove in inventory: %d/%s", skillModPartTrans.getSkillModPartAttribHash(),
						inventoryItemEntity.getId(), inventoryItemEntity.getEntitlementTag()));

				saleValue += inventoryItemEntity.getResalePrice();

				inventoryItemDAO.delete(inventoryItemEntity);
			} else {
				System.out
						.println(String.format("[Commerce: SkillMods] Couldn't find %d to remove in inventory", skillModPartTrans.getSkillModPartAttribHash()));

				ProductEntity productEntity = productDao.findByHash(skillModPartTrans.getSkillModPartAttribHash());

				if (productEntity != null) {
					System.out.println(String.format("[Commerce: SkillMods] Found %d to remove in catalog: %s for $%.0f IGC",
							skillModPartTrans.getSkillModPartAttribHash(), productEntity.getProductId(), productEntity.getPrice()));
					saleValue += productEntity.getPrice() / 4;
				}
			}
		}
		// endregion

		if (personaEntity.getCash() < purchaseCost) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}

		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			System.out.println(String.format("[Commerce] Purchase cost: $%d IGC ------- Sale value: $%d IGC", purchaseCost, saleValue));
			int newCash = (int) (personaEntity.getCash() - purchaseCost + saleValue);
			if (newCash > 9999999) {
				newCash = 9999999;
			}
			if (newCash < 1) {
				newCash = 1;
			}
			personaEntity.setCash(newCash);
		}

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
		// CarSlotEntity carSlotEntity =
		// carSlotDAO.findById(commerceSessionTrans.getUpdatedCar().getId());
		// if (carSlotEntity != null) {
		// List<BasketItemTrans> listBasketItemTrans =
		// commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
		// if(!listBasketItemTrans.isEmpty()) { // if buy or install perf part
		// int maxBuyPrice = 0;
		// for(BasketItemTrans basketItemTrans : listBasketItemTrans) {
		// if(basketItemTrans.getProductId().contains("SRV-VINYL")) {
		// maxBuyPrice +=
		// (vinylProductDao.findByProductId(basketItemTrans.getProductId()).getPrice() *
		// basketItemTrans.getQuantity());
		// } else {
		// maxBuyPrice +=
		// (productDao.findByProductId(basketItemTrans.getProductId()).getPrice() *
		// basketItemTrans.getQuantity());
		// }
		// }
		// if(maxBuyPrice > 0) {
		// if(personaEntity.getCash() < maxBuyPrice) {
		// return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		// }
		//
		// personaEntity.setCash(personaEntity.getCash() - maxBuyPrice);
		// personaDAO.update(personaEntity);
		// }
		// } else { // else i sell some part from my inventory
		//
		// }
		//
		// carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
		// carSlotDAO.update(carSlotEntity);
		// return CommerceResultStatus.SUCCESS;
		// }
		// return CommerceResultStatus.FAIL_INVALID_BASKET;
	}

	private InventoryItemEntity findAvailableInInventory(PersonaEntity personaEntity, long hash) {
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

		for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems()) {
			if ("ACTIVE".equals(inventoryItemEntity.getStatus()) && inventoryItemEntity.getHash() == hash)
				return inventoryItemEntity;
		}

		return null;
	}

	private InventoryItemEntity findUsedInInventory(PersonaEntity personaEntity, long hash) {
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

		for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems()) {
			if ("INSTALLED".equals(inventoryItemEntity.getStatus()) && inventoryItemEntity.getHash() == hash && inventoryItemEntity.getRemainingUseCount() == 0)
				return inventoryItemEntity;
		}

		return null;
	}

	private ProductEntity findProduct(long hash) {
		return productDao.findByHash(hash);
	}

	private VinylProductEntity findVProduct(long hash) {
		return vinylProductDao.findByHash(hash);
	}

	public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans) {
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
		ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
		ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
		ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
		ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
		return ownedCarTrans;
	}

}
