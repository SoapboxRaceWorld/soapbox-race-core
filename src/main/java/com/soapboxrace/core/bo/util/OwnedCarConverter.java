package com.soapboxrace.core.bo.util;

import java.util.ArrayList;
import java.util.List;

import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PaintEntity;
import com.soapboxrace.core.jpa.PerformancePartEntity;
import com.soapboxrace.core.jpa.SkillModPartEntity;
import com.soapboxrace.core.jpa.VinylEntity;
import com.soapboxrace.core.jpa.VisualPartEntity;
import com.soapboxrace.jaxb.http.ArrayOfCustomPaintTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomVinylTrans;
import com.soapboxrace.jaxb.http.ArrayOfPerformancePartTrans;
import com.soapboxrace.jaxb.http.ArrayOfSkillModPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfVisualPartTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.CustomPaintTrans;
import com.soapboxrace.jaxb.http.CustomVinylTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PerformancePartTrans;
import com.soapboxrace.jaxb.http.SkillModPartTrans;
import com.soapboxrace.jaxb.http.VisualPartTrans;

public class OwnedCarConverter {

	public static OwnedCarTrans entity2Trans(OwnedCarEntity ownedCarEntity) {
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
		customCarTrans.setSkillModSlotCount(customCarEntity.getSkillModSlotCount());
		customCarTrans.setVersion(customCarEntity.getVersion());

		ArrayOfCustomPaintTrans arrayOfCustomPaintTrans = new ArrayOfCustomPaintTrans();
		List<CustomPaintTrans> customPaintTransList = arrayOfCustomPaintTrans.getCustomPaintTrans();
		List<PaintEntity> paints = customCarEntity.getPaints();
		for (PaintEntity paintEntity : paints) {
			CustomPaintTrans customPaintTransTmp = new CustomPaintTrans();
			customPaintTransTmp.setGroup(paintEntity.getGroup());
			customPaintTransTmp.setHue(paintEntity.getHue());
			customPaintTransTmp.setSat(paintEntity.getSat());
			customPaintTransTmp.setSlot(paintEntity.getSlot());
			customPaintTransTmp.setVar(paintEntity.getVar());
			customPaintTransList.add(customPaintTransTmp);
		}
		customCarTrans.setPaints(arrayOfCustomPaintTrans);

		ArrayOfPerformancePartTrans arrayOfPerformancePartTrans = new ArrayOfPerformancePartTrans();
		List<PerformancePartTrans> performancePartTransList = arrayOfPerformancePartTrans.getPerformancePartTrans();
		List<PerformancePartEntity> performanceParts = customCarEntity.getPerformanceParts();
		for (PerformancePartEntity performancePartEntity : performanceParts) {
			PerformancePartTrans performancePartTrans = new PerformancePartTrans();
			performancePartTrans.setPerformancePartAttribHash(performancePartEntity.getPerformancePartAttribHash());
			performancePartTransList.add(performancePartTrans);
		}
		customCarTrans.setPerformanceParts(arrayOfPerformancePartTrans);

		ArrayOfSkillModPartTrans arrayOfSkillModPartTrans = new ArrayOfSkillModPartTrans();
		List<SkillModPartTrans> skillModPartTransList = arrayOfSkillModPartTrans.getSkillModPartTrans();
		List<SkillModPartEntity> skillModParts = customCarEntity.getSkillModParts();
		for (SkillModPartEntity skillModPartEntity : skillModParts) {
			SkillModPartTrans skillModPartTrans = new SkillModPartTrans();
			skillModPartTrans.setIsFixed(skillModPartEntity.isFixed());
			skillModPartTrans.setSkillModPartAttribHash(skillModPartEntity.getSkillModPartAttribHash());
			skillModPartTransList.add(skillModPartTrans);
		}
		customCarTrans.setSkillModParts(arrayOfSkillModPartTrans);

		ArrayOfCustomVinylTrans arrayOfCustomVinylTrans = new ArrayOfCustomVinylTrans();
		List<CustomVinylTrans> customVinylTransList = arrayOfCustomVinylTrans.getCustomVinylTrans();
		List<VinylEntity> vinyls = customCarEntity.getVinyls();
		for (VinylEntity vinylEntity : vinyls) {
			CustomVinylTrans customVinylTransTmp = new CustomVinylTrans();
			customVinylTransTmp.setHash(vinylEntity.getHash());
			customVinylTransTmp.setHue1(vinylEntity.getHue1());
			customVinylTransTmp.setHue2(vinylEntity.getHue2());
			customVinylTransTmp.setHue3(vinylEntity.getHue3());
			customVinylTransTmp.setHue4(vinylEntity.getHue4());
			customVinylTransTmp.setLayer(vinylEntity.getLayer());
			customVinylTransTmp.setMir(vinylEntity.isMir());
			customVinylTransTmp.setRot(vinylEntity.getRot());
			customVinylTransTmp.setSat1(vinylEntity.getSat1());
			customVinylTransTmp.setSat2(vinylEntity.getSat2());
			customVinylTransTmp.setSat3(vinylEntity.getSat3());
			customVinylTransTmp.setSat4(vinylEntity.getSat4());
			customVinylTransTmp.setScaleX(vinylEntity.getScalex());
			customVinylTransTmp.setScaleY(vinylEntity.getScaley());
			customVinylTransTmp.setShear(vinylEntity.getShear());
			customVinylTransTmp.setTranX(vinylEntity.getTranx());
			customVinylTransTmp.setTranY(vinylEntity.getTrany());
			customVinylTransTmp.setVar1(vinylEntity.getVar1());
			customVinylTransTmp.setVar2(vinylEntity.getVar2());
			customVinylTransTmp.setVar3(vinylEntity.getVar3());
			customVinylTransTmp.setVar4(vinylEntity.getVar4());
			customVinylTransList.add(customVinylTransTmp);
		}
		customCarTrans.setVinyls(arrayOfCustomVinylTrans);

		ArrayOfVisualPartTrans arrayOfVisualPartTrans = new ArrayOfVisualPartTrans();
		List<VisualPartTrans> visualPartTransList = arrayOfVisualPartTrans.getVisualPartTrans();
		List<VisualPartEntity> visualParts = customCarEntity.getVisualParts();
		for (VisualPartEntity visualPartEntity : visualParts) {
			VisualPartTrans visualPartTrans = new VisualPartTrans();
			visualPartTrans.setPartHash(visualPartEntity.getPartHash());
			visualPartTrans.setSlotHash(visualPartEntity.getSlotHash());
			visualPartTransList.add(visualPartTrans);
		}
		customCarTrans.setVisualParts(arrayOfVisualPartTrans);

		ownedCarTrans.setCustomCar(customCarTrans);
		return ownedCarTrans;
	}

	public static void trans2Entity(OwnedCarTrans ownedCarTrans, OwnedCarEntity ownedCarEntity) {
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
		customCarEntity.setSkillModSlotCount(customCarTrans.getSkillModSlotCount());
		customCarEntity.setVersion(customCarTrans.getVersion());
	}

	public static void paints2NewEntity(CustomCarTrans customCarTrans, CustomCarEntity customCarEntity) {
		ArrayList<PaintEntity> paintEntityList = new ArrayList<>();
		List<CustomPaintTrans> customPaintTrans = customCarTrans.getPaints().getCustomPaintTrans();
		for (CustomPaintTrans customPaintTransTmp : customPaintTrans) {
			PaintEntity paintEntity = new PaintEntity();
			paintEntity.setCustomCar(customCarEntity);
			paintEntity.setGroup(customPaintTransTmp.getGroup());
			paintEntity.setHue(customPaintTransTmp.getHue());
			paintEntity.setSat(customPaintTransTmp.getSat());
			paintEntity.setSlot(customPaintTransTmp.getSlot());
			paintEntity.setVar(customPaintTransTmp.getVar());
			paintEntityList.add(paintEntity);
		}
		customCarEntity.setPaints(paintEntityList);
	}

	public static void performanceParts2NewEntity(CustomCarTrans customCarTrans, CustomCarEntity customCarEntity) {
		ArrayList<PerformancePartEntity> performancePartEntityList = new ArrayList<>();
		List<PerformancePartTrans> performancePartTransList = customCarTrans.getPerformanceParts().getPerformancePartTrans();
		for (PerformancePartTrans performancePartTransTmp : performancePartTransList) {
			PerformancePartEntity performancePartEntity = new PerformancePartEntity();
			performancePartEntity.setCustomCar(customCarEntity);
			performancePartEntity.setPerformancePartAttribHash(performancePartTransTmp.getPerformancePartAttribHash());
			performancePartEntityList.add(performancePartEntity);
		}
		customCarEntity.setPerformanceParts(performancePartEntityList);
	}

	public static void skillModParts2NewEntity(CustomCarTrans customCarTrans, CustomCarEntity customCarEntity) {
		ArrayList<SkillModPartEntity> skillModPartEntityList = new ArrayList<>();
		List<SkillModPartTrans> skillModPartTransList = customCarTrans.getSkillModParts().getSkillModPartTrans();
		for (SkillModPartTrans skillModPartTransTmp : skillModPartTransList) {
			SkillModPartEntity skillModPartEntity = new SkillModPartEntity();
			skillModPartEntity.setCustomCar(customCarEntity);
			skillModPartEntity.setFixed(skillModPartTransTmp.isIsFixed());
			skillModPartEntity.setSkillModPartAttribHash(skillModPartTransTmp.getSkillModPartAttribHash());
			skillModPartEntityList.add(skillModPartEntity);
		}
		customCarEntity.setSkillModParts(skillModPartEntityList);
	}

	public static void vinyls2NewEntity(CustomCarTrans customCarTrans, CustomCarEntity customCarEntity) {
		ArrayList<VinylEntity> vinylEntityList = new ArrayList<>();
		List<CustomVinylTrans> customVinylTrans = customCarTrans.getVinyls().getCustomVinylTrans();
		for (CustomVinylTrans customVinylTransTmp : customVinylTrans) {
			VinylEntity vinylEntity = new VinylEntity();
			vinylEntity.setCustomCar(customCarEntity);
			vinylEntity.setHash(customVinylTransTmp.getHash());
			vinylEntity.setHue1(customVinylTransTmp.getHue1());
			vinylEntity.setHue2(customVinylTransTmp.getHue2());
			vinylEntity.setHue3(customVinylTransTmp.getHue3());
			vinylEntity.setHue4(customVinylTransTmp.getHue4());
			vinylEntity.setLayer(customVinylTransTmp.getLayer());
			vinylEntity.setMir(customVinylTransTmp.isMir());
			vinylEntity.setRot(customVinylTransTmp.getRot());
			vinylEntity.setSat1(customVinylTransTmp.getSat1());
			vinylEntity.setSat2(customVinylTransTmp.getSat2());
			vinylEntity.setSat3(customVinylTransTmp.getSat3());
			vinylEntity.setSat4(customVinylTransTmp.getSat4());
			vinylEntity.setScalex(customVinylTransTmp.getScaleX());
			vinylEntity.setScaley(customVinylTransTmp.getScaleY());
			vinylEntity.setShear(customVinylTransTmp.getShear());
			vinylEntity.setTranx(customVinylTransTmp.getTranX());
			vinylEntity.setTrany(customVinylTransTmp.getTranY());
			vinylEntity.setVar1(customVinylTransTmp.getVar1());
			vinylEntity.setVar2(customVinylTransTmp.getVar2());
			vinylEntity.setVar3(customVinylTransTmp.getVar3());
			vinylEntity.setVar4(customVinylTransTmp.getVar4());
			vinylEntityList.add(vinylEntity);
		}
		customCarEntity.setVinyls(vinylEntityList);
	}

	public static void visuallParts2NewEntity(CustomCarTrans customCarTrans, CustomCarEntity customCarEntity) {
		ArrayList<VisualPartEntity> visualPartEntityList = new ArrayList<>();
		List<VisualPartTrans> visualPartTransList = customCarTrans.getVisualParts().getVisualPartTrans();
		for (VisualPartTrans visualPartTransTmp : visualPartTransList) {
			VisualPartEntity visualPartEntity = new VisualPartEntity();
			visualPartEntity.setCustomCar(customCarEntity);
			visualPartEntity.setPartHash(visualPartTransTmp.getPartHash());
			visualPartEntity.setSlotHash(visualPartTransTmp.getSlotHash());
			visualPartEntityList.add(visualPartEntity);
		}
		customCarEntity.setVisualParts(visualPartEntityList);
	}

	public static void details2NewEntity(OwnedCarTrans ownedCarTrans, OwnedCarEntity ownedCarEntity) {
		CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();
		CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();

		paints2NewEntity(customCarTrans, customCarEntity);
		performanceParts2NewEntity(customCarTrans, customCarEntity);
		skillModParts2NewEntity(customCarTrans, customCarEntity);
		vinyls2NewEntity(customCarTrans, customCarEntity);
		visuallParts2NewEntity(customCarTrans, customCarEntity);
	}

}
