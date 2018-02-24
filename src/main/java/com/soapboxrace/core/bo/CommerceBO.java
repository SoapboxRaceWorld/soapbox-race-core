package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.CustomPaintTrans;
import com.soapboxrace.jaxb.http.CustomVinylTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PerformancePartTrans;
import com.soapboxrace.jaxb.http.SkillModPartTrans;
import com.soapboxrace.jaxb.http.VisualPartTrans;

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

	public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans) {
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
		ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
		ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
		ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
		ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
		return ownedCarTrans;
	}

	public void updateCar(CommerceSessionTrans commerceSessionTrans, CarSlotEntity defaultCarEntity) {
		OwnedCarTrans ownedCarTrans = OwnedCarConverter.Entity2Trans(defaultCarEntity.getOwnedCar());
		CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
		List<CustomPaintTrans> customPaintTransDB = customCarTransDB.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTransDB = customCarTransDB.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTransDB = customCarTransDB.getSkillModParts().getSkillModPartTrans();
		List<CustomVinylTrans> customVinylTransDB = customCarTransDB.getVinyls().getCustomVinylTrans();
		List<VisualPartTrans> visualPartTransDB = customCarTransDB.getVisualParts().getVisualPartTrans();

		CustomCarTrans customCar = commerceSessionTrans.getUpdatedCar().getCustomCar();
		List<CustomPaintTrans> customPaintTrans = customCar.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTrans = customCar.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTrans = customCar.getSkillModParts().getSkillModPartTrans();
		List<CustomVinylTrans> customVinylTrans = customCar.getVinyls().getCustomVinylTrans();
		List<VisualPartTrans> visualPartTrans = customCar.getVisualParts().getVisualPartTrans();

		if (skillModPartTrans.size() != skillModPartTransDB.size() || !skillModPartTrans.containsAll(skillModPartTransDB)
				|| !skillModPartTransDB.containsAll(skillModPartTrans)) {
			System.out.println("skill logic");
			return;
		}
		if (!performancePartTrans.containsAll(performancePartTransDB) || !performancePartTransDB.containsAll(performancePartTrans)) {
			System.out.println("performance logic");
			return;
		}
		if (!visualPartTrans.containsAll(visualPartTransDB) || !visualPartTransDB.containsAll(visualPartTrans)) {
			System.out.println("visual logic");
			return;
		}
		if (!customPaintTrans.containsAll(customPaintTransDB) || !customPaintTransDB.containsAll(customPaintTrans)) {
			System.out.println("paint logic");
			return;
		}
		if (!customVinylTrans.containsAll(customVinylTransDB) || !customVinylTransDB.containsAll(customVinylTrans)) {
			System.out.println("vinyl logic");
		}
	}

}
