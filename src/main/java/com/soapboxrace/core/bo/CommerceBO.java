package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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

	public void updateCar(CommerceSessionTrans commerceSessionTrans, CarSlotEntity defaultCarEntity) {
		OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar());
		CustomCarTrans customCarTransDB = ownedCarTrans.getCustomCar();
		List<CustomPaintTrans> customPaintTransDB = customCarTransDB.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTransDB = customCarTransDB.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTransDB = customCarTransDB.getSkillModParts().getSkillModPartTrans();
		List<CustomVinylTrans> customVinylTransDB = customCarTransDB.getVinyls().getCustomVinylTrans();
		List<VisualPartTrans> visualPartTransDB = customCarTransDB.getVisualParts().getVisualPartTrans();

		CustomCarTrans customCarTrans = commerceSessionTrans.getUpdatedCar().getCustomCar();
		List<CustomPaintTrans> customPaintTrans = customCarTrans.getPaints().getCustomPaintTrans();
		List<PerformancePartTrans> performancePartTrans = customCarTrans.getPerformanceParts().getPerformancePartTrans();
		List<SkillModPartTrans> skillModPartTrans = customCarTrans.getSkillModParts().getSkillModPartTrans();
		List<CustomVinylTrans> customVinylTrans = customCarTrans.getVinyls().getCustomVinylTrans();
		List<VisualPartTrans> visualPartTrans = customCarTrans.getVisualParts().getVisualPartTrans();

		CustomCarEntity customCarDB = defaultCarEntity.getOwnedCar().getCustomCar();

		if (skillModPartTrans.size() != skillModPartTransDB.size() || !skillModPartTrans.containsAll(skillModPartTransDB)
				|| !skillModPartTransDB.containsAll(skillModPartTrans)) {
			System.out.println("skill logic");
			skillModPartDAO.deleteByCustomCar(customCarDB);

			OwnedCarConverter.skillModParts2NewEntity(customCarTrans, defaultCarEntity.getOwnedCar().getCustomCar());
			carSlotDAO.update(defaultCarEntity);
			return;
		}
		if (!performancePartTrans.containsAll(performancePartTransDB) || !performancePartTransDB.containsAll(performancePartTrans)) {
			System.out.println("performance logic");
			performancePartDAO.deleteByCustomCar(customCarDB);
			OwnedCarConverter.performanceParts2NewEntity(customCarTrans, defaultCarEntity.getOwnedCar().getCustomCar());
			carSlotDAO.update(defaultCarEntity);
			return;
		}
		if (!visualPartTrans.containsAll(visualPartTransDB) || !visualPartTransDB.containsAll(visualPartTrans)) {
			System.out.println("visual logic");
			visualPartDAO.deleteByCustomCar(customCarDB);
			OwnedCarConverter.visuallParts2NewEntity(customCarTrans, defaultCarEntity.getOwnedCar().getCustomCar());
			carSlotDAO.update(defaultCarEntity);
			return;
		}
		if (!customPaintTrans.containsAll(customPaintTransDB) || !customPaintTransDB.containsAll(customPaintTrans)) {
			System.out.println("paint logic");
			paintDAO.deleteByCustomCar(customCarDB);
			OwnedCarConverter.paints2NewEntity(customCarTrans, defaultCarEntity.getOwnedCar().getCustomCar());
			carSlotDAO.update(defaultCarEntity);
			return;
		}
		if (!customVinylTrans.containsAll(customVinylTransDB) || !customVinylTransDB.containsAll(customVinylTrans)) {
			System.out.println("vinyl logic");
			OwnedCarConverter.vinyls2NewEntity(customCarTrans, defaultCarEntity.getOwnedCar().getCustomCar());
			vinylDAO.deleteByCustomCar(customCarDB);
			carSlotDAO.update(defaultCarEntity);
		}
	}

}
