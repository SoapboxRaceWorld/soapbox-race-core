package com.soapboxrace.core.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.CommerceOp;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PaintDAO;
import com.soapboxrace.core.dao.PerformancePartDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.SkillModPartDAO;
import com.soapboxrace.core.dao.VinylDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.dao.VisualPartDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.VinylProductEntity;
import com.soapboxrace.jaxb.http.BasketItemTrans;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.CustomPaintTrans;
import com.soapboxrace.jaxb.http.EntitlementItemTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PerformancePartTrans;
import com.soapboxrace.jaxb.http.SkillModPartTrans;
import com.soapboxrace.jaxb.http.VisualPartTrans;

@Stateless
public class CommerceBO {
	@EJB
	private PersonaBO personaBO;

	@EJB
	private InventoryBO inventoryBO;

	@EJB
	private CarSlotDAO carSlotDAO;

	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private ProductDAO productDAO;

	@EJB
	private VinylProductDAO vinylProductDao;

	@EJB
	private InventoryDAO inventoryDAO;

	@EJB
	private InventoryItemDAO inventoryItemDAO;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private PaintDAO paintDAO;

	@EJB
	private PerformancePartDAO performancePartDAO;

	@EJB
	private SkillModPartDAO skillModPartDAO;

	@EJB
	private VinylDAO vinylDAO;

	@EJB
	private VisualPartDAO visualPartDAO;

	public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans) {
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
		ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
		ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
		ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
		ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
		return ownedCarTrans;
	}

	public CommerceOp detectCommerceOperation(CommerceSessionTrans commerceSessionTrans, CarSlotEntity defaultCarEntity) {
		OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar());
		CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
		List<CustomPaintTrans> customPaintTransDB = customCarTransDB.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTransDB = customCarTransDB.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTransDB = customCarTransDB.getSkillModParts().getSkillModPartTrans();
		List<VisualPartTrans> visualPartTransDB = customCarTransDB.getVisualParts().getVisualPartTrans();

		CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
		List<CustomPaintTrans> customPaintTrans = customCarTrans.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTrans = customCarTrans.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTrans = customCarTrans.getSkillModParts().getSkillModPartTrans();
		List<VisualPartTrans> visualPartTrans = customCarTrans.getVisualParts().getVisualPartTrans();

		if (skillModPartTrans.size() != skillModPartTransDB.size() || !skillModPartTrans.containsAll(skillModPartTransDB)
				|| !skillModPartTransDB.containsAll(skillModPartTrans)) {
			return CommerceOp.SKILL;
		}
		if (!performancePartTrans.containsAll(performancePartTransDB) || !performancePartTransDB.containsAll(performancePartTrans)) {
			return CommerceOp.PERFORMANCE;
		}
		if (!visualPartTrans.containsAll(visualPartTransDB) || !visualPartTransDB.containsAll(visualPartTrans)) {
			return CommerceOp.VISUAL;
		}
		if (!customPaintTrans.containsAll(customPaintTransDB) || !customPaintTransDB.containsAll(customPaintTrans)) {
			return CommerceOp.PAINTS;
		}
		return CommerceOp.VINYL;
	}

	public void updateCar(CommerceOp commerceOp, CommerceSessionTrans commerceSessionTrans, CarSlotEntity defaultCarEntity) {
		CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
		CustomCarEntity customCarEntity = defaultCarEntity.getOwnedCar().getCustomCar();
		switch (commerceOp) {
		case PAINTS:
			paintDAO.deleteByCustomCar(customCarEntity);
			OwnedCarConverter.paints2NewEntity(customCarTrans, customCarEntity);
			break;
		case PERFORMANCE:
			performancePartDAO.deleteByCustomCar(customCarEntity);
			OwnedCarConverter.performanceParts2NewEntity(customCarTrans, customCarEntity);
			break;
		case SKILL:
			skillModPartDAO.deleteByCustomCar(customCarEntity);
			OwnedCarConverter.skillModParts2NewEntity(customCarTrans, customCarEntity);
			break;
		case VINYL:
			vinylDAO.deleteByCustomCar(customCarEntity);
			OwnedCarConverter.vinyls2NewEntity(customCarTrans, customCarEntity);
			break;
		case VISUAL:
			visualPartDAO.deleteByCustomCar(customCarEntity);
			OwnedCarConverter.visuallParts2NewEntity(customCarTrans, customCarEntity);
			break;
		default:
			break;
		}
		carSlotDAO.update(defaultCarEntity);
	}

	public void updateEconomy(CommerceOp commerceOp, List<BasketItemTrans> basketItemTransList, CommerceSessionTrans commerceSessionTrans,
			CarSlotEntity defaultCarEntity) {
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar());
			CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
			CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
			Float basketTotalValue = 0f;
			if (CommerceOp.VINYL.equals(commerceOp)) {
				basketTotalValue = getVinylTotalValue(basketItemTransList);
			} else {
				basketTotalValue = getBasketTotalValue(basketItemTransList);
			}
			Float resellTotalValue = 0F;
			switch (commerceOp) {
			case PERFORMANCE:
				List<PerformancePartTrans> performancePartTransDB = customCarTransDB.getPerformanceParts().getPerformancePartTrans();
				List<PerformancePartTrans> performancePartTrans = customCarTrans.getPerformanceParts().getPerformancePartTrans();
				ArrayList<PerformancePartTrans> performancePartTransListTmp = new ArrayList<>(performancePartTransDB);
				List<PerformancePartTrans> performancePartsFromBasket = inventoryBO.getPerformancePartsFromBasket(basketItemTransList);
				performancePartTransListTmp.removeAll(performancePartsFromBasket);
				performancePartTransListTmp.removeAll(performancePartTrans);
				for (PerformancePartTrans performancePartTransTmp : performancePartTransListTmp) {
					ProductEntity productEntity = productDAO.findByHash(performancePartTransTmp.getPerformancePartAttribHash());
					if (productEntity != null) {
						resellTotalValue = Float.sum(resellTotalValue, productEntity.getResalePrice());
					} else {
						System.err.println("INVALID HASH: [" + performancePartTransTmp.getPerformancePartAttribHash() + "]");
					}
				}
				break;
			case SKILL:
				List<SkillModPartTrans> skillModPartTransDB = customCarTransDB.getSkillModParts().getSkillModPartTrans();
				List<SkillModPartTrans> skillModPartTrans = customCarTrans.getSkillModParts().getSkillModPartTrans();
				List<SkillModPartTrans> skillModPartTransListTmp = new ArrayList<>(skillModPartTransDB);
				List<SkillModPartTrans> skillModPartsFromBasket = inventoryBO.getSkillModPartsFromBasket(basketItemTransList);
				skillModPartTransListTmp.removeAll(skillModPartsFromBasket);
				skillModPartTransListTmp.removeAll(skillModPartTrans);
				for (SkillModPartTrans skillModPartTransTmp : skillModPartTransListTmp) {
					ProductEntity productEntity = productDAO.findByHash(skillModPartTransTmp.getSkillModPartAttribHash());
					if (productEntity != null) {
						resellTotalValue = Float.sum(resellTotalValue, productEntity.getResalePrice());
					} else {
						System.err.println("INVALID HASH: [" + skillModPartTransTmp.getSkillModPartAttribHash() + "]");
					}
				}
				break;
			default:
				break;
			}
			List<EntitlementItemTrans> entitlementItemTransList = commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans();
			if (entitlementItemTransList != null && !entitlementItemTransList.isEmpty()) {
				for (EntitlementItemTrans entitlementItemTransTmp : entitlementItemTransList) {
					String entitlementId = entitlementItemTransTmp.getEntitlementId();
					Long personaId = defaultCarEntity.getPersona().getPersonaId();
					InventoryItemEntity inventoryItem = inventoryItemDAO.findByEntitlementTagAndPersona(personaId, entitlementId);
					Integer hash = inventoryItem.getHash();
					ProductEntity productEntity = productDAO.findByHash(hash);
					if (productEntity != null) {
						resellTotalValue = Float.sum(resellTotalValue, productEntity.getResalePrice());
					}
				}
			}
			Float result = Float.sum(basketTotalValue, (resellTotalValue * -1)) * -1;
			System.out.println("basket: [" + basketTotalValue + "]");
			System.out.println("resell: [" + resellTotalValue + "]");
			System.out.println("result: [" + result + "]");
			PersonaEntity persona = defaultCarEntity.getPersona();
			float cash = (float) persona.getCash();
			persona.setCash(Float.sum(cash, result));
			personaDAO.update(persona);
		}
	}

	private Float getVinylTotalValue(List<BasketItemTrans> basketItemTransList) {
		Float price = 0F;
		for (BasketItemTrans basketItemTrans : basketItemTransList) {
			VinylProductEntity vinylProductEntity = vinylProductDao.findByProductId(basketItemTrans.getProductId());
			if (vinylProductEntity != null) {
				price = Float.sum(price, vinylProductEntity.getPrice());
			} else {
				System.err.println("product [" + basketItemTrans.getProductId() + "] not found");
			}
		}
		return price;
	}

	private Float getBasketTotalValue(List<BasketItemTrans> basketItemTransList) {
		Float price = 0F;
		for (BasketItemTrans basketItemTrans : basketItemTransList) {
			ProductEntity productEntity = productDAO.findByProductId(basketItemTrans.getProductId());
			if (productEntity != null) {
				price = Float.sum(price, productEntity.getPrice());
			} else {
				System.err.println("product [" + basketItemTrans.getProductId() + "] not found");
			}
		}
		return price;
	}

}
