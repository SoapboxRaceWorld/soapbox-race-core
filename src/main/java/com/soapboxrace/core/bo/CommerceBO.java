package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.ListDifferences;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    @EJB
    private CustomCarDAO customCarDAO;

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private VirtualItemDAO virtualItemDAO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans) {
        OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
        ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
        ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
        ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
        ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
        ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
        return ownedCarTrans;
    }

    public CommerceSessionResultTrans doCommerce(CommerceSessionTrans commerceSessionTrans, Long personaId) {
        List<BasketItemTrans> basketItems = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        OwnedCarEntity ownedCarEntity = personaBO.getDefaultCarEntity(personaId).getOwnedCar();
        OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaId);
        CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();
        CustomCarTrans commerceCustomCar = commerceSessionTrans.getUpdatedCar().getCustomCar();

//        for (CustomVinylTrans cvt : commerceCustomCar.getVinyls().getCustomVinylTrans()) {
//            if (cvt.getScaleX()==3277)
//                cvt.setScaleX(0);
//            if (cvt.getScaleY()==3277)
//                cvt.setScaleY(0);
//        }

        ArrayOfCustomVinylTrans arrayOfCustomVinylTrans = new ArrayOfCustomVinylTrans();

        for (CustomVinylTrans cvt : commerceCustomCar.getVinyls().getCustomVinylTrans()) {
            if (cvt.getScaleX() == 3277)
                cvt.setScaleX(0);
            if (cvt.getScaleY() == 3277)
                cvt.setScaleY(0);
            arrayOfCustomVinylTrans.getCustomVinylTrans().add(cvt);
        }

        commerceCustomCar.setVinyls(arrayOfCustomVinylTrans);

        ListDifferences<CustomPaintTrans> paintDifferences = ListDifferences.getDifferences(
                customCarTrans.getPaints().getCustomPaintTrans(), commerceCustomCar.getPaints().getCustomPaintTrans());
        ListDifferences<CustomVinylTrans> vinylDifferences = ListDifferences.getDifferences(
                customCarTrans.getVinyls().getCustomVinylTrans(), commerceCustomCar.getVinyls().getCustomVinylTrans());
        ListDifferences<PerformancePartTrans> performancePartDifferences = ListDifferences.getDifferences(
                customCarTrans.getPerformanceParts().getPerformancePartTrans(),
                commerceCustomCar.getPerformanceParts().getPerformancePartTrans());
        ListDifferences<VisualPartTrans> visualPartDifferences = ListDifferences.getDifferences(
                customCarTrans.getVisualParts().getVisualPartTrans(),
                commerceCustomCar.getVisualParts().getVisualPartTrans());
        ListDifferences<SkillModPartTrans> skillModPartDifferences = ListDifferences.getDifferences(
                customCarTrans.getSkillModParts().getSkillModPartTrans(),
                commerceCustomCar.getSkillModParts().getSkillModPartTrans());

        Map<Integer, Object> addedItems = new HashMap<>();
        Map<Integer, Object> removedItems = new HashMap<>();

        paintDifferences.getAdded().forEach(p -> addedItems.put(p.getGroup(), p));
        paintDifferences.getRemoved().forEach(p -> removedItems.put(p.getGroup(), p));
        performancePartDifferences.getAdded().forEach(p -> addedItems.put(p.getPerformancePartAttribHash(), p));
        performancePartDifferences.getRemoved().forEach(p -> removedItems.put(p.getPerformancePartAttribHash(), p));
        skillModPartDifferences.getAdded().forEach(p -> addedItems.put(p.getSkillModPartAttribHash(), p));
        skillModPartDifferences.getRemoved().forEach(p -> removedItems.put(p.getSkillModPartAttribHash(), p));
        visualPartDifferences.getAdded().forEach(p -> addedItems.put(p.getPartHash(), p));
        visualPartDifferences.getRemoved().forEach(p -> removedItems.put(p.getPartHash(), p));
        vinylDifferences.getAdded().forEach(p -> addedItems.put(p.getHash(), p));
        vinylDifferences.getRemoved().forEach(p -> removedItems.put(p.getHash(), p));

        CommerceSessionResultTrans commerceSessionResultTrans = new CommerceSessionResultTrans();
        AtomicInteger addCash = new AtomicInteger();
        int removeCash = 0;
        int addBoost = 0;
        int removeBoost = 0;

        for (Map.Entry<Integer, Object> addedItem : addedItems.entrySet()) {
            if (addedItem.getValue() instanceof CustomVinylTrans) {
                VinylProductEntity vinylProductEntity = vinylProductDAO.findByHash(addedItem.getKey());

                if (vinylProductEntity != null) {
                    if (vinylProductEntity.getCurrency().equals("CASH"))
                        removeCash += (int) vinylProductEntity.getPrice();
                    else
                        removeBoost += (int) vinylProductEntity.getPrice();
                } else {
                    commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INVALID_BASKET);
                    return commerceSessionResultTrans;
                }
            } else {
                ProductEntity productEntity = productDAO.findByHash(addedItem.getKey());

                if (productEntity != null) {
                    if (basketItems.stream().anyMatch(p -> p.getProductId().equalsIgnoreCase(productEntity.getProductId()))) {
                        if (productEntity.getCurrency().equals("CASH"))
                            removeCash += (int) productEntity.getPrice();
                        else
                            removeBoost += (int) productEntity.getPrice();
                    } else {
                        InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(personaId,
                                addedItem.getKey());

                        if (inventoryItemEntity != null) {
                            inventoryBO.decrementUsage(personaId, addedItem.getKey());
                        } else {
                            commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INVALID_BASKET);
                            return commerceSessionResultTrans;
                        }
                    }
                } else {
                    commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INVALID_BASKET);
                    return commerceSessionResultTrans;
                }
            }
        }

        for (Map.Entry<Integer, Object> removedItem : removedItems.entrySet()) {
            if (!(removedItem.getValue() instanceof CustomVinylTrans)) {
                ProductEntity productEntity = productDAO.findByHash(removedItem.getKey());

                if (productEntity != null) {
                    if (productEntity.getCurrency().equals("CASH"))
                        addCash.addAndGet((int) productEntity.getResalePrice());
                    else
                        addBoost += (int) productEntity.getResalePrice();
                }
            }
        }

        if (commerceSessionTrans.getEntitlementsToSell().getItems() != null) {
            commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans().forEach(e -> {
                InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId
                        , e.getEntitlementId());

                if (inventoryItemEntity != null && inventoryItemEntity.getRemainingUseCount() >= e.getQuantity()) {
                    inventoryItemEntity.setRemainingUseCount(inventoryItemEntity.getRemainingUseCount() - e.getQuantity());
                    inventoryItemDAO.update(inventoryItemEntity);
                    addCash.addAndGet(inventoryItemEntity.getResellPrice());
                }
            });
        }

        double finalCash = personaEntity.getCash() - removeCash + addCash.get();
        double finalBoost = personaEntity.getBoost() - removeBoost + addBoost;

        if (finalCash < 0 || finalBoost < 0) {
            commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
            commerceSessionResultTrans.setUpdatedCar(personaBO.getDefaultCar(personaId));
            return commerceSessionResultTrans;
        }

        CustomCarEntity customCar = ownedCarEntity.getCustomCar();
        OwnedCarConverter.paints2NewEntity(commerceCustomCar, customCar);
        OwnedCarConverter.vinyls2NewEntity(commerceCustomCar, customCar);
        OwnedCarConverter.skillModParts2NewEntity(commerceCustomCar, customCar);
        OwnedCarConverter.performanceParts2NewEntity(commerceCustomCar, customCar);
        OwnedCarConverter.visuallParts2NewEntity(commerceCustomCar, customCar);

        calcNewCarClass(customCar);

        customCarDAO.update(customCar);

        personaEntity.setBoost(finalBoost);
        driverPersonaBO.updateCash(personaEntity, finalCash);

        commerceSessionResultTrans.setUpdatedCar(personaBO.getDefaultCar(personaId));
        commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
        commerceSessionResultTrans.setStatus(CommerceResultStatus.SUCCESS);
        commerceSessionResultTrans.setInventoryItems(new ArrayOfInventoryItemTrans());

        ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
        WalletTrans cashWallet = new WalletTrans();
        cashWallet.setCurrency("CASH");
        cashWallet.setBalance(personaEntity.getCash());
        arrayOfWalletTrans.getWalletTrans().add(cashWallet);

        WalletTrans boostWallet = new WalletTrans();
        boostWallet.setCurrency("BOOST");
        boostWallet.setBalance(personaEntity.getBoost());
        arrayOfWalletTrans.getWalletTrans().add(boostWallet);

        commerceSessionResultTrans.setWallets(arrayOfWalletTrans);

//        achievementBO.updateAchievements(personaEntity.getPersonaId(), "COMMERCE", new HashMap<String, Object>(){{
//            put("persona", personaEntity);
//
//        }});

        return commerceSessionResultTrans;
    }

    private void addPaint(CustomCarEntity customCarEntity, Object customizationObject, Integer hash) {
        if (customizationObject instanceof CustomPaintTrans) {
            CustomPaintTrans customPaintTrans = (CustomPaintTrans) customizationObject;
            PaintEntity paintEntity = new PaintEntity();
            paintEntity.setCustomCar(customCarEntity);
            paintEntity.setGroup(customPaintTrans.getGroup());
            paintEntity.setHue(customPaintTrans.getHue());
            paintEntity.setSat(customPaintTrans.getSat());
            paintEntity.setSlot(customPaintTrans.getSlot());
            paintEntity.setVar(customPaintTrans.getVar());
            customCarEntity.getPaints().add(paintEntity);
        }
    }

    private void addVinyl(CustomCarEntity customCarEntity, Object customizationObject, Integer hash) {
        if (customizationObject instanceof CustomVinylTrans) {
            CustomVinylTrans customVinylTrans = (CustomVinylTrans) customizationObject;
            VinylEntity vinylEntity = new VinylEntity();
            vinylEntity.setHash(customVinylTrans.getHash());
            vinylEntity.setHue1(customVinylTrans.getHue1());
            vinylEntity.setHue2(customVinylTrans.getHue2());
            vinylEntity.setHue3(customVinylTrans.getHue3());
            vinylEntity.setHue4(customVinylTrans.getHue4());
            vinylEntity.setLayer(customVinylTrans.getLayer());
            vinylEntity.setMir(customVinylTrans.isMir());
            vinylEntity.setRot(customVinylTrans.getRot());
            vinylEntity.setScalex(customVinylTrans.getScaleX());
            vinylEntity.setScaley(customVinylTrans.getScaleY());
            vinylEntity.setShear(customVinylTrans.getShear());
            vinylEntity.setTranx(customVinylTrans.getTranX());
            vinylEntity.setTrany(customVinylTrans.getTranY());
            vinylEntity.setVar1(customVinylTrans.getVar1());
            vinylEntity.setVar2(customVinylTrans.getVar2());
            vinylEntity.setVar3(customVinylTrans.getVar3());
            vinylEntity.setVar4(customVinylTrans.getVar4());
            vinylEntity.setSat1(customVinylTrans.getSat1());
            vinylEntity.setSat2(customVinylTrans.getSat2());
            vinylEntity.setSat3(customVinylTrans.getSat3());
            vinylEntity.setSat4(customVinylTrans.getSat4());
            vinylEntity.setCustomCar(customCarEntity);
            customCarEntity.getVinyls().add(vinylEntity);
        }
    }

    private void addPerformancePart(CustomCarEntity customCarEntity, Object customizationObject, Integer hash) {
        if (customizationObject instanceof PerformancePartTrans) {
            PerformancePartEntity performancePartEntity = new PerformancePartEntity();
            performancePartEntity.setPerformancePartAttribHash(hash);
            performancePartEntity.setCustomCar(customCarEntity);
            customCarEntity.getPerformanceParts().add(performancePartEntity);
        }
    }

    private void addSkillPart(CustomCarEntity customCarEntity, Object customizationObject, Integer hash) {
        if (customizationObject instanceof SkillModPartTrans) {
            SkillModPartEntity skillModPartEntity = new SkillModPartEntity();
            skillModPartEntity.setSkillModPartAttribHash(hash);
            skillModPartEntity.setCustomCar(customCarEntity);
            customCarEntity.getSkillModParts().add(skillModPartEntity);
        }
    }

    private void addVisualPart(CustomCarEntity customCarEntity, Object customizationObject, Integer hash) {
        if (customizationObject instanceof VisualPartTrans) {
            VisualPartEntity visualPartEntity = new VisualPartEntity();
            visualPartEntity.setPartHash(hash);
            visualPartEntity.setSlotHash(((VisualPartTrans) customizationObject).getSlotHash());
            visualPartEntity.setCustomCar(customCarEntity);
            customCarEntity.getVisualParts().add(visualPartEntity);
        }
    }

    private Integer calcProductSellPrice(ProductEntity productEntity) {
        return (int) productEntity.getResalePrice();
    }

    private void calcNewCarClass(CustomCarEntity customCarEntity) {
        int physicsProfileHash = customCarEntity.getPhysicsProfileHash();
        CarClassesEntity carClassesEntity = carClassesDAO.findByHash(physicsProfileHash);
        if (carClassesEntity == null) {
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
        Float finalTopSpeed =
                (finalConstant * carClassesEntity.getTsStock().floatValue()) + finalTopSpeed1 + finalTopSpeed2
                + finalTopSpeed3;

        Float finalAccel1 = carClassesEntity.getAcVar1().floatValue() * th;
        Float finalAccel2 = carClassesEntity.getAcVar2().floatValue() * ta;
        Float finalAccel3 = carClassesEntity.getAcVar3().floatValue() * tt;
        Float finalAccel = (finalConstant * carClassesEntity.getAcStock().floatValue()) + finalAccel1 + finalAccel2
                + finalAccel3;

        Float finalHandling1 = carClassesEntity.getHaVar1().floatValue() * th;
        Float finalHandling2 = carClassesEntity.getHaVar2().floatValue() * ta;
        Float finalHandling3 = carClassesEntity.getHaVar3().floatValue() * tt;
        Float finalHandling =
                (finalConstant * carClassesEntity.getHaStock().floatValue()) + finalHandling1 + finalHandling2
                + finalHandling3;

        Float finalClass = (finalTopSpeed.intValue() + finalAccel.intValue() + finalHandling.intValue()) / 3f;
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
}