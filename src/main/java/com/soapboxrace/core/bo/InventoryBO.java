package com.soapboxrace.core.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import com.soapboxrace.core.bo.util.CommerceOp;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.ProductType;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.BasketItemTrans;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.EntitlementItemTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PerformancePartTrans;
import com.soapboxrace.jaxb.http.SkillModPartTrans;
import com.soapboxrace.jaxb.http.VisualPartTrans;

@Stateless
public class InventoryBO {
	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private InventoryDAO inventoryDAO;

	@EJB
	private InventoryItemDAO inventoryItemDAO;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private ProductDAO productDAO;

	public InventoryTrans getInventory(Long personaId) {
		InventoryTrans inventoryTrans = new InventoryTrans();
		PersonaEntity personaEntity = personaDAO.findById(personaId);
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

		if (personaEntity == null) {
			return new InventoryTrans();
		}

		if (inventoryEntity == null) {
			inventoryEntity = createInventory(personaEntity);
		}

		inventoryTrans.setPerformancePartsCapacity(inventoryEntity.getPerformancePartsCapacity());
		inventoryTrans.setSkillModPartsCapacity(inventoryEntity.getSkillModPartsCapacity());
		inventoryTrans.setVisualPartsCapacity(inventoryEntity.getVisualPartsCapacity());

		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();

		for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems()) {
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

	public boolean hasItem(long personaId, long hash) {
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);

		for (InventoryItemEntity inventoryItemEntity : inventoryEntity.getItems()) {
			if (inventoryItemEntity.getHash() == hash && inventoryItemEntity.getRemainingUseCount() > 0) {
				return true;
			}
		}

		return false;
	}

	public void decrementUsage(Long personaId, Integer hash) {
		InventoryItemEntity item = inventoryItemDAO.findByHashAndPersona(personaId, hash);
		item.setRemainingUseCount(item.getRemainingUseCount() - 1);
		inventoryItemDAO.update(item);
	}

	public InventoryEntity createInventory(PersonaEntity personaEntity) {
		InventoryEntity inventoryEntity = new InventoryEntity();
		inventoryEntity.setPersona(personaEntity);
		inventoryEntity.setSkillModPartsCapacity(parameterBO.getStartingInventorySkillSlots());
		inventoryEntity.setPerformancePartsCapacity(parameterBO.getStartingInventoryPerfSlots());
		inventoryEntity.setVisualPartsCapacity(parameterBO.getStartingInventoryVisualSlots());
		inventoryDAO.insert(inventoryEntity);

		inventoryItemDAO.insert(getPowerUpInventory("nosshot", -1681514783, "0x9bc61ee1", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("runflattires", -537557654, "0xdff5856a", inventoryEntity, personaEntity));
		inventoryItemDAO.insert(getPowerUpInventory("instantcooldown", -1692359144, "0x9b20a618", inventoryEntity, personaEntity));
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

	private ProductType detectProductType(Integer hash) {
		ProductEntity productEntity = productDAO.findByHash(hash);
		return ProductType.valueOf(productEntity.getProductType());
	}

	private void decreaseInventory(InventoryEntity inventoryEntity, Integer hash) {
		ProductType productType = detectProductType(hash);
		switch (productType) {
		case PERFORMANCEPART:
			inventoryEntity.setPerformancePartsUsedSlotCount(inventoryEntity.getPerformancePartsUsedSlotCount() - 1);
			break;
		case SKILLMODPART:
			inventoryEntity.setSkillModPartsUsedSlotCount(inventoryEntity.getSkillModPartsUsedSlotCount() - 1);
			break;
		case VISUALPART:
			inventoryEntity.setVisualPartsUsedSlotCount(inventoryEntity.getVisualPartsUsedSlotCount() - 1);
			break;
		case POWERUP:
			break;
		default:
			break;
		}
		inventoryDAO.update(inventoryEntity);
	}

	public void deletePart(Long personaId, String entitlementId) {
		InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByEntitlementTagAndPersona(personaId, entitlementId);
		if (inventoryItemEntity != null) {
			InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);
			decreaseInventory(inventoryEntity, inventoryItemEntity.getHash());
			inventoryItemDAO.delete(inventoryItemEntity);
		} else {
			System.err.println("INVALID entitlementId: [" + entitlementId + "]");
		}
	}

	public void deletePart(Long personaId, Integer hash) {
		InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByHashAndPersona(personaId, hash);
		if (inventoryItemEntity != null) {
			InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaId);
			decreaseInventory(inventoryEntity, inventoryItemEntity.getHash());
			inventoryItemDAO.delete(inventoryItemEntity);
		} else {
			System.err.println("INVALID hash: [" + hash + "]");
		}
	}

	public List<SkillModPartTrans> getSkillModPartsFromBasket(List<BasketItemTrans> basketItemTransList) {
		List<SkillModPartTrans> skillModPartTransList = new ArrayList<>();
		if (basketItemTransList != null) {
			for (BasketItemTrans basketItemTransTmp : basketItemTransList) {
				ProductEntity productEntity = productDAO.findByProductId(basketItemTransTmp.getProductId());
				SkillModPartTrans skillModPartTrans = new SkillModPartTrans();
				skillModPartTrans.setSkillModPartAttribHash(productEntity.getHash().intValue());
				skillModPartTransList.add(skillModPartTrans);
			}
		}
		return skillModPartTransList;
	}

	public List<PerformancePartTrans> getPerformancePartsFromBasket(List<BasketItemTrans> basketItemTransList) {
		List<PerformancePartTrans> performancePartTransList = new ArrayList<>();
		if (basketItemTransList != null) {
			for (BasketItemTrans basketItemTransTmp : basketItemTransList) {
				ProductEntity productEntity = productDAO.findByProductId(basketItemTransTmp.getProductId());
				PerformancePartTrans performancePartTrans = new PerformancePartTrans();
				performancePartTrans.setPerformancePartAttribHash(productEntity.getHash().intValue());
				performancePartTransList.add(performancePartTrans);
			}
		}
		return performancePartTransList;
	}

	public List<VisualPartTrans> getVisualPartsFromBasket(List<BasketItemTrans> basketItemTransList) {
		List<VisualPartTrans> visualPartTransList = new ArrayList<>();
		if (basketItemTransList != null) {
			for (BasketItemTrans basketItemTransTmp : basketItemTransList) {
				ProductEntity productEntity = productDAO.findByProductId(basketItemTransTmp.getProductId());
				VisualPartTrans visualPartTrans = new VisualPartTrans();
				visualPartTrans.setPartHash(productEntity.getHash().intValue());
				visualPartTransList.add(visualPartTrans);
			}
		}
		return visualPartTransList;
	}

	public void updateInventory(CommerceOp commerceOp, List<BasketItemTrans> basketItemTransList, CommerceSessionTrans commerceSessionTrans,
			CarSlotEntity defaultCarEntity) {
		OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar());
		CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
		CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
		switch (commerceOp) {
		case PERFORMANCE:
			List<PerformancePartTrans> performancePartTransDB = customCarTransDB.getPerformanceParts().getPerformancePartTrans();
			List<PerformancePartTrans> performancePartTrans = customCarTrans.getPerformanceParts().getPerformancePartTrans();
			ArrayList<PerformancePartTrans> performancePartTransListTmp = new ArrayList<>(performancePartTrans);
			List<PerformancePartTrans> performancePartsFromBasket = getPerformancePartsFromBasket(basketItemTransList);
			performancePartTransListTmp.removeAll(performancePartTransDB);
			performancePartTransListTmp.removeAll(performancePartsFromBasket);
			for (PerformancePartTrans performancePartTransTmp : performancePartTransListTmp) {
				System.out.println("added from inventory: " + performancePartTransTmp.getPerformancePartAttribHash());
				deletePart(defaultCarEntity.getPersona().getPersonaId(), performancePartTransTmp.getPerformancePartAttribHash());
			}
			break;
		case SKILL:
			List<SkillModPartTrans> skillModPartTransDB = customCarTransDB.getSkillModParts().getSkillModPartTrans();
			List<SkillModPartTrans> skillModPartTrans = customCarTrans.getSkillModParts().getSkillModPartTrans();
			List<SkillModPartTrans> skillModPartTransListTmp = new ArrayList<>(skillModPartTrans);
			List<SkillModPartTrans> skillModPartsFromBasket = getSkillModPartsFromBasket(basketItemTransList);
			skillModPartTransListTmp.removeAll(skillModPartTransDB);
			skillModPartTransListTmp.removeAll(skillModPartsFromBasket);
			for (SkillModPartTrans skillModPartTransTmp : skillModPartTransListTmp) {
				System.out.println("added from inventory: " + skillModPartTransTmp.getSkillModPartAttribHash());
				deletePart(defaultCarEntity.getPersona().getPersonaId(), skillModPartTransTmp.getSkillModPartAttribHash());
			}
			break;
		case VISUAL:
			List<VisualPartTrans> visualPartTransDB = customCarTransDB.getVisualParts().getVisualPartTrans();
			List<VisualPartTrans> visualPartTrans = customCarTrans.getVisualParts().getVisualPartTrans();
			ArrayList<VisualPartTrans> visualPartTransListTmp = new ArrayList<>(visualPartTrans);
			List<VisualPartTrans> visualPartsFromBasket = getVisualPartsFromBasket(basketItemTransList);
			visualPartTransListTmp.removeAll(visualPartTransDB);
			visualPartTransListTmp.removeAll(visualPartsFromBasket);
			for (VisualPartTrans visualPartTransTmp : visualPartTransListTmp) {
				System.out.println("added from inventory: " + visualPartTransTmp.getPartHash());
				deletePart(defaultCarEntity.getPersona().getPersonaId(), visualPartTransTmp.getPartHash());
			}
			break;
		default:
			break;
		}
		List<EntitlementItemTrans> entitlementItemTransList = commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans();
		if (entitlementItemTransList != null && !entitlementItemTransList.isEmpty()) {
			for (EntitlementItemTrans entitlementItemTransTmp : entitlementItemTransList) {
				String entitlementId = entitlementItemTransTmp.getEntitlementId();
				deletePart(defaultCarEntity.getPersona().getPersonaId(), entitlementId);
			}
		}
	}

	public void addDroppedItem(ProductEntity productEntity, PersonaEntity personaEntity) {
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());

		String entitlementTag = DigestUtils.md5Hex(String.valueOf(productEntity.getHash()));
		InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
		inventoryItemEntity.setPersona(personaEntity);
		inventoryItemEntity.setInventory(inventoryEntity);
		inventoryItemEntity.setEntitlementTag(entitlementTag);
		inventoryItemEntity.setHash(productEntity.getHash());
		inventoryItemEntity.setRemainingUseCount(productEntity.getUseCount());
		inventoryItemEntity.setResalePrice(productEntity.getResalePrice());
		inventoryItemEntity.setStatus("ACTIVE");
		inventoryItemEntity.setStringHash("0x" + Integer.toHexString(productEntity.getHash()));
		inventoryItemEntity.setVirtualItemType(productEntity.getProductType());
		inventoryItemEntity.setProductId(productEntity.getProductId());

		ProductType productTypeEnum = detectProductType(productEntity);
		switch (productTypeEnum) {
		case PERFORMANCEPART:
			int performancePartsUsedSlotCount = inventoryEntity.getPerformancePartsUsedSlotCount() + 1;
			inventoryEntity.setPerformancePartsUsedSlotCount(performancePartsUsedSlotCount);
			inventoryItemDAO.insert(inventoryItemEntity);
			inventoryDAO.update(inventoryEntity);
			break;
		case SKILLMODPART:
			int skillModPartsUsedSlotCount = inventoryEntity.getSkillModPartsUsedSlotCount() + 1;
			inventoryEntity.setSkillModPartsUsedSlotCount(skillModPartsUsedSlotCount);
			inventoryItemDAO.insert(inventoryItemEntity);
			inventoryDAO.update(inventoryEntity);
			break;
		case VISUALPART:
			int visualPartsUsedSlotCount = inventoryEntity.getVisualPartsUsedSlotCount() + 1;
			inventoryEntity.setVisualPartsUsedSlotCount(visualPartsUsedSlotCount);
			inventoryItemDAO.insert(inventoryItemEntity);
			inventoryDAO.update(inventoryEntity);
			break;
		case POWERUP:
			inventoryItemEntity = inventoryItemDAO.findByHashAndPersona(personaEntity.getPersonaId(), productEntity.getHash());
			inventoryItemEntity.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount() + productEntity.getUseCount());
			inventoryItemDAO.update(inventoryItemEntity);
			break;
		default:
			break;
		}
	}

	public ProductType detectProductType(ProductEntity productEntity) {
		return ProductType.valueOf(productEntity.getProductType());
	}

	public boolean isInventoryFull(ProductEntity productEntity, PersonaEntity personaEntity) {
		InventoryEntity inventoryEntity = inventoryDAO.findByPersonaId(personaEntity.getPersonaId());
		ProductType productTypeEnum = detectProductType(productEntity);
		int capacity = 0;
		int usedSlotCount = 0;
		switch (productTypeEnum) {
		case PERFORMANCEPART:
			capacity = inventoryEntity.getPerformancePartsCapacity();
			usedSlotCount = inventoryEntity.getPerformancePartsUsedSlotCount();
			break;
		case SKILLMODPART:
			capacity = inventoryEntity.getSkillModPartsCapacity();
			usedSlotCount = inventoryEntity.getSkillModPartsUsedSlotCount();
			break;
		case VISUALPART:
			capacity = inventoryEntity.getVisualPartsCapacity();
			usedSlotCount = inventoryEntity.getVisualPartsUsedSlotCount();
			break;
		case POWERUP:
			return false;
		default:
			break;
		}
		if (usedSlotCount < capacity) {
			return false;
		}
		return true;
	}
}
