package com.soapboxrace.core.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.CommerceOp;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.CarClassesDAO;
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
import com.soapboxrace.core.jpa.CarClassesEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PerformancePartEntity;
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
	private VinylProductDAO vinylProductDAO;

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

	@EJB
	private CarClassesDAO carClassesDAO;

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
			calcNewCarClass(customCarEntity);
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

	private void calcNewCarClass(CustomCarEntity customCarEntity) {
		int physicsProfileHash = customCarEntity.getPhysicsProfileHash();
		CarClassesEntity carClassesEntity = carClassesDAO.findByHash(physicsProfileHash);
		if(carClassesEntity == null) {
			return;
		}
		List<PerformancePartEntity> performanceParts = customCarEntity.getPerformanceParts();
		int topSpeed = 0;
		int accel = 0;
		int handling = 0;
		for (PerformancePartEntity performancePartEntity : performanceParts) {
			int perfHash = performancePartEntity.getPerformancePartAttribHash();
			ProductEntity productEntity = productDAO.findByHash(perfHash);
			topSpeed = productEntity.getTopSpeed() + topSpeed;
			accel = productEntity.getAccel() + accel;
			handling = productEntity.getHandling() + handling;
		}
		float tt = (float) (topSpeed * 0.01);
		float ta = (float) (accel * 0.01);
		float th = (float) (handling * 0.01);
		float totalChanges = 1 / (((tt + ta + th) * 0.666666666666666f) + 1f);
		tt = tt * totalChanges;
		ta = ta * totalChanges;
		th = th * totalChanges;
		float finalConstant = 1 - tt - ta - th;

		Float finalTopSpeed1 = carClassesEntity.getTsVar1().floatValue() * th;
		Float finalTopSpeed2 = carClassesEntity.getTsVar2().floatValue() * ta;
		Float finalTopSpeed3 = carClassesEntity.getTsVar3().floatValue() * tt;
		Float finalTopSpeed = (finalConstant * carClassesEntity.getTsStock().floatValue()) + finalTopSpeed1 + finalTopSpeed2
				+ finalTopSpeed3;

		System.out.println(finalTopSpeed.intValue());

		Float finalAccel1 = carClassesEntity.getAcVar1().floatValue() * th;
		Float finalAccel2 = carClassesEntity.getAcVar2().floatValue() * ta;
		Float finalAccel3 = carClassesEntity.getAcVar3().floatValue() * tt;
		Float finalAccel = (finalConstant * carClassesEntity.getAcStock().floatValue()) + finalAccel1 + finalAccel2
				+ finalAccel3;

		System.out.println(finalAccel.intValue());

		Float finalHandling1 = carClassesEntity.getHaVar1().floatValue() * th;
		Float finalHandling2 = carClassesEntity.getHaVar2().floatValue() * ta;
		Float finalHandling3 = carClassesEntity.getHaVar3().floatValue() * tt;
		Float finalHandling = (finalConstant * carClassesEntity.getHaStock().floatValue()) + finalHandling1 + finalHandling2
				+ finalHandling3;

		System.out.println(finalHandling.intValue());

		Float finalClass = (finalTopSpeed.intValue() + finalAccel.intValue() + finalHandling.intValue()) / 3f;
		System.out.println(finalClass.intValue());
		int finalClassInt = finalClass.intValue();

		// move to new method
		int carclassHash = 872416321;
		if (finalClassInt >= 250 && finalClassInt < 400) {
			carclassHash = 415909161;
		} else if (finalClassInt >= 400 && finalClassInt < 500) {
			carclassHash = 1866825865;
		} else if (finalClassInt >= 500 && finalClassInt < 600) {
			carclassHash = -406473455;
		} else if (finalClassInt >= 600 && finalClassInt < 750) {
			carclassHash = -405837480;
		} else if (finalClassInt >= 750) {
			carclassHash = -2142411446;
		}

		customCarEntity.setCarClassHash(carclassHash);
	}

	private void disableItem(ProductEntity productEntity) {
		Boolean disableItemAfterBuy = parameterBO.getBoolParam("DISABLE_ITEM_AFTER_BUY");
		if (disableItemAfterBuy) {
			productEntity.setEnabled(false);
			productDAO.update(productEntity);
		}
	}

	private void disableItem(VinylProductEntity vinylProductEntity) {
		Boolean disableItemAfterBuy = parameterBO.getBoolParam("DISABLE_ITEM_AFTER_BUY");
		if (disableItemAfterBuy) {
			vinylProductEntity.setEnabled(false);
			vinylProductDAO.update(vinylProductEntity);
		}
	}

	public void updateEconomy(CommerceOp commerceOp, List<BasketItemTrans> basketItemTransList, CommerceSessionTrans commerceSessionTrans,
			CarSlotEntity defaultCarEntity) {
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar());
			CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
			CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
			Float basketTotalValue;
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
			VinylProductEntity vinylProductEntity = vinylProductDAO.findByProductId(basketItemTrans.getProductId());
			if (vinylProductEntity != null) {
				price = Float.sum(price, vinylProductEntity.getPrice());
				disableItem(vinylProductEntity);
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
				disableItem(productEntity);
			} else {
				System.err.println("product [" + basketItemTrans.getProductId() + "] not found");
			}
		}
		return price;
	}

}