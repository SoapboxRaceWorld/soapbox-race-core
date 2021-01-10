/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OwnedCarConverter {

    public static OwnedCarTrans entity2Trans(CarEntity carEntity) {
        OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
        ownedCarTrans.setId(carEntity.getId());
        ownedCarTrans.setDurability(carEntity.getDurability());
        ownedCarTrans.setHeat(carEntity.getHeat());
        ownedCarTrans.setOwnershipType(carEntity.getOwnershipType());
        if (carEntity.getExpirationDate() != null)
            ownedCarTrans.setExpirationDate(TimeConverter.generateGregorianCalendar(GregorianCalendar.from(carEntity.getExpirationDate().atZone(ZoneId.systemDefault()))));

        CustomCarTrans customCarTrans = new CustomCarTrans();
        customCarTrans.setBaseCar(carEntity.getBaseCar());
        customCarTrans.setCarClassHash(carEntity.getCarClassHash());
        customCarTrans.setId(carEntity.getId().intValue());
        customCarTrans.setIsPreset(carEntity.isPreset());
        customCarTrans.setLevel(carEntity.getLevel());
        customCarTrans.setName(carEntity.getName());
        customCarTrans.setPhysicsProfileHash(carEntity.getPhysicsProfileHash());
        customCarTrans.setRating(carEntity.getRating());
        customCarTrans.setResalePrice(carEntity.getResalePrice());
        customCarTrans.setRideHeightDrop(carEntity.getRideHeightDrop());
        customCarTrans.setSkillModSlotCount(carEntity.getSkillModSlotCount());
        customCarTrans.setVersion(carEntity.getVersion());

        ArrayOfCustomPaintTrans arrayOfCustomPaintTrans = new ArrayOfCustomPaintTrans();
        List<CustomPaintTrans> customPaintTransList = arrayOfCustomPaintTrans.getCustomPaintTrans();
        Set<PaintEntity> paints = carEntity.getPaints();
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
        Set<PerformancePartEntity> performanceParts = carEntity.getPerformanceParts();
        for (PerformancePartEntity performancePartEntity : performanceParts) {
            PerformancePartTrans performancePartTrans = new PerformancePartTrans();
            performancePartTrans.setPerformancePartAttribHash(performancePartEntity.getPerformancePartAttribHash());
            performancePartTransList.add(performancePartTrans);
        }
        customCarTrans.setPerformanceParts(arrayOfPerformancePartTrans);

        ArrayOfSkillModPartTrans arrayOfSkillModPartTrans = new ArrayOfSkillModPartTrans();
        List<SkillModPartTrans> skillModPartTransList = arrayOfSkillModPartTrans.getSkillModPartTrans();
        Set<SkillModPartEntity> skillModParts = carEntity.getSkillModParts();
        for (SkillModPartEntity skillModPartEntity : skillModParts) {
            SkillModPartTrans skillModPartTrans = new SkillModPartTrans();
            skillModPartTrans.setIsFixed(skillModPartEntity.isFixed());
            skillModPartTrans.setSkillModPartAttribHash(skillModPartEntity.getSkillModPartAttribHash());
            skillModPartTransList.add(skillModPartTrans);
        }
        customCarTrans.setSkillModParts(arrayOfSkillModPartTrans);

        ArrayOfCustomVinylTrans arrayOfCustomVinylTrans = new ArrayOfCustomVinylTrans();
        List<CustomVinylTrans> customVinylTransList = arrayOfCustomVinylTrans.getCustomVinylTrans();
        Set<VinylEntity> vinyls = carEntity.getVinyls();
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
        Set<VisualPartEntity> visualParts = carEntity.getVisualParts();
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

    public static void trans2Entity(OwnedCarTrans ownedCarTrans, CarEntity carEntity) {
        carEntity.setDurability(ownedCarTrans.getDurability());
        // ownedCarEntity.setExpirationDate(expirationDate);
        carEntity.setHeat(ownedCarTrans.getHeat());
        carEntity.setOwnershipType(carEntity.getOwnershipType());
        CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();
        carEntity.setBaseCar(customCarTrans.getBaseCar());
        carEntity.setCarClassHash(customCarTrans.getCarClassHash());
        carEntity.setLevel(customCarTrans.getLevel());
        carEntity.setName(customCarTrans.getName());
        carEntity.setPhysicsProfileHash(customCarTrans.getPhysicsProfileHash());
        carEntity.setPreset(customCarTrans.isIsPreset());
        carEntity.setRating(customCarTrans.getRating());
        carEntity.setResalePrice(customCarTrans.getResalePrice());
        carEntity.setRideHeightDrop(customCarTrans.getRideHeightDrop());
        carEntity.setSkillModSlotCount(customCarTrans.getSkillModSlotCount());
        carEntity.setVersion(customCarTrans.getVersion());
    }

    public static void paints2NewEntity(CustomCarTrans customCarTrans, CarEntity carEntity) {
        Set<PaintEntity> paintEntityList = new HashSet<>();
        List<CustomPaintTrans> customPaintTrans = customCarTrans.getPaints().getCustomPaintTrans();
        for (CustomPaintTrans customPaintTransTmp : customPaintTrans) {
            PaintEntity paintEntity = new PaintEntity();
            paintEntity.setCar(carEntity);
            paintEntity.setGroup(customPaintTransTmp.getGroup());
            paintEntity.setHue(customPaintTransTmp.getHue());
            paintEntity.setSat(customPaintTransTmp.getSat());
            paintEntity.setSlot(customPaintTransTmp.getSlot());
            paintEntity.setVar(customPaintTransTmp.getVar());
            paintEntityList.add(paintEntity);
        }
        if (carEntity.getPaints() == null) {
            carEntity.setPaints(paintEntityList);
        } else {
            carEntity.getPaints().clear();
            carEntity.getPaints().addAll(paintEntityList);
        }
    }

    public static void performanceParts2NewEntity(CustomCarTrans customCarTrans, CarEntity carEntity) {
        Set<PerformancePartEntity> performancePartEntityList = new HashSet<>();
        List<PerformancePartTrans> performancePartTransList =
                customCarTrans.getPerformanceParts().getPerformancePartTrans();
        for (PerformancePartTrans performancePartTransTmp : performancePartTransList) {
            PerformancePartEntity performancePartEntity = new PerformancePartEntity();
            performancePartEntity.setCar(carEntity);
            performancePartEntity.setPerformancePartAttribHash(performancePartTransTmp.getPerformancePartAttribHash());
            performancePartEntityList.add(performancePartEntity);
        }
        if (carEntity.getPerformanceParts() == null) {
            carEntity.setPerformanceParts(performancePartEntityList);
        } else {
            carEntity.getPerformanceParts().clear();
            carEntity.getPerformanceParts().addAll(performancePartEntityList);
        }
    }

    public static void skillModParts2NewEntity(CustomCarTrans customCarTrans, CarEntity carEntity) {
        Set<SkillModPartEntity> skillModPartEntityList = new HashSet<>();
        List<SkillModPartTrans> skillModPartTransList = customCarTrans.getSkillModParts().getSkillModPartTrans();
        for (SkillModPartTrans skillModPartTransTmp : skillModPartTransList) {
            SkillModPartEntity skillModPartEntity = new SkillModPartEntity();
            skillModPartEntity.setCar(carEntity);
            skillModPartEntity.setFixed(skillModPartTransTmp.isIsFixed());
            skillModPartEntity.setSkillModPartAttribHash(skillModPartTransTmp.getSkillModPartAttribHash());
            skillModPartEntityList.add(skillModPartEntity);
        }
        if (carEntity.getSkillModParts() == null) {
            carEntity.setSkillModParts(skillModPartEntityList);
        } else {
            carEntity.getSkillModParts().clear();
            carEntity.getSkillModParts().addAll(skillModPartEntityList);
        }
    }

    public static void vinyls2NewEntity(CustomCarTrans customCarTrans, CarEntity carEntity) {
        Set<VinylEntity> vinylEntityList = new HashSet<>();
        List<CustomVinylTrans> customVinylTrans = customCarTrans.getVinyls().getCustomVinylTrans();
        for (CustomVinylTrans customVinylTransTmp : customVinylTrans) {
            VinylEntity vinylEntity = new VinylEntity();
            vinylEntity.setCar(carEntity);
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
        if (carEntity.getVinyls() == null) {
            carEntity.setVinyls(vinylEntityList);
        } else {
            carEntity.getVinyls().clear();
            carEntity.getVinyls().addAll(vinylEntityList);
        }
    }

    public static void visuallParts2NewEntity(CustomCarTrans customCarTrans, CarEntity carEntity) {
        Set<VisualPartEntity> visualPartEntityList = new HashSet<>();
        List<VisualPartTrans> visualPartTransList = customCarTrans.getVisualParts().getVisualPartTrans();
        for (VisualPartTrans visualPartTransTmp : visualPartTransList) {
            VisualPartEntity visualPartEntity = new VisualPartEntity();
            visualPartEntity.setCar(carEntity);
            visualPartEntity.setPartHash(visualPartTransTmp.getPartHash());
            visualPartEntity.setSlotHash(visualPartTransTmp.getSlotHash());
            visualPartEntityList.add(visualPartEntity);
        }
        if (carEntity.getVisualParts() == null) {
            carEntity.setVisualParts(visualPartEntityList);
        } else {
            carEntity.getVisualParts().clear();
            carEntity.getVisualParts().addAll(visualPartEntityList);
        }
    }

    public static void details2NewEntity(OwnedCarTrans ownedCarTrans, CarEntity carEntity) {
        CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();

        paints2NewEntity(customCarTrans, carEntity);
        performanceParts2NewEntity(customCarTrans, carEntity);
        skillModParts2NewEntity(customCarTrans, carEntity);
        vinyls2NewEntity(customCarTrans, carEntity);
        visuallParts2NewEntity(customCarTrans, carEntity);
    }

}
