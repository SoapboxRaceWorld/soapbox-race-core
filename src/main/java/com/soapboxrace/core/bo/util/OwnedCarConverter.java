package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.jaxb.http.ArrayOfCustomPaintTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomVinylTrans;
import com.soapboxrace.jaxb.http.ArrayOfPerformancePartTrans;
import com.soapboxrace.jaxb.http.ArrayOfSkillModPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfVisualPartTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;

public class OwnedCarConverter {

	public static OwnedCarTrans Entity2Trans(OwnedCarEntity ownedCarEntity) {
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setId(ownedCarEntity.getId());
		ownedCarTrans.setDurability(ownedCarEntity.getDurability());
		ownedCarTrans.setHeat(ownedCarEntity.getHeat());
		ownedCarTrans.setOwnershipType(ownedCarEntity.getOwnershipType());

		CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();
		CustomCarTrans customCarTrans = new CustomCarTrans();
		customCarTrans.setBaseCar(customCarEntity.getBaseCar());
		customCarTrans.setCarClassHash(customCarEntity.getCarClassHash());
		customCarTrans.setId(customCarEntity.getId().intValue());
		customCarTrans.setIsPreset(customCarEntity.isPreset());
		customCarTrans.setLevel(customCarEntity.getLevel());
		customCarTrans.setName(customCarEntity.getName());
		customCarTrans.setPhysicsProfileHash(customCarEntity.getPhysicsProfileHash());
		customCarTrans.setRating(customCarEntity.getRating());
		customCarTrans.setResalePrice(customCarEntity.getResalePrice());
		customCarTrans.setRideHeightDrop(customCarEntity.getRideHeightDrop());
		customCarTrans.setVersion(customCarEntity.getVersion());

		customCarTrans.setPaints(new ArrayOfCustomPaintTrans());
		customCarTrans.setPerformanceParts(new ArrayOfPerformancePartTrans());
		customCarTrans.setSkillModParts(new ArrayOfSkillModPartTrans());
		customCarTrans.setVinyls(new ArrayOfCustomVinylTrans());
		customCarTrans.setVisualParts(new ArrayOfVisualPartTrans());

		ownedCarTrans.setCustomCar(customCarTrans);
		return ownedCarTrans;
	}

	public static void Trans2Entity(OwnedCarTrans ownedCarTrans, OwnedCarEntity ownedCarEntity) {
		ownedCarEntity.setDurability(ownedCarTrans.getDurability());
		// ownedCarEntity.setExpirationDate(expirationDate);
		ownedCarEntity.setHeat(ownedCarTrans.getHeat());
		ownedCarEntity.setOwnershipType(ownedCarEntity.getOwnershipType());
		CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();
		CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();
		customCarEntity.setBaseCar(customCarTrans.getBaseCar());
		customCarEntity.setCarClassHash(customCarTrans.getCarClassHash());
		customCarEntity.setLevel(customCarTrans.getLevel());
		customCarEntity.setName(customCarTrans.getName());
		customCarEntity.setPhysicsProfileHash(customCarTrans.getPhysicsProfileHash());
		customCarEntity.setPreset(customCarTrans.isIsPreset());
		customCarEntity.setRating(customCarTrans.getRating());
		customCarEntity.setResalePrice(customCarTrans.getResalePrice());
		customCarEntity.setRideHeightDrop(customCarTrans.getRideHeightDrop());
		customCarEntity.setVersion(customCarTrans.getVersion());

		// customCarEntity.setPaints("");
		// customCarEntity.setPerformanceParts("");
		// customCarEntity.setSkillModParts(skillModParts);
		// customCarEntity.setVinyls("");
		// customCarEntity.setVisualParts(visualParts);
	}

}
