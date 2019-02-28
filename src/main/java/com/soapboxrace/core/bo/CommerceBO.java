package com.soapboxrace.core.bo;

import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.ListDifferences;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;

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
    private AchievementsBO achievementsBO;

    @EJB
    private AchievementDAO achievementDAO;

    @EJB
    private VirtualItemDAO virtualItemDAO;

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
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        CommerceSessionResultTrans commerceSessionResultTrans = new CommerceSessionResultTrans();
        CustomCarTrans commerceCustomCar = commerceSessionTrans.getUpdatedCar().getCustomCar();

        List<BasketItemTrans> basketItems = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
        List<ProductEntity> productsFromBasket = new ArrayList<>();
        List<VinylProductEntity> vinylProducts = new ArrayList<>();
        String basketProductType = null;

        int deductCash = 0;
        int addCash = 0;
        int deductBoost = 0;
        int addBoost = 0;

        OwnedCarEntity ownedCarEntity = personaBO.getDefaultCarEntity(personaId).getOwnedCar();
        CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();
        OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(ownedCarEntity);
        CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();

        for (BasketItemTrans basketItemTrans : basketItems) {
            ProductEntity findById = productDAO.findByProductId(basketItemTrans.getProductId());

            if (findById == null) {
                VinylProductEntity vinylProductEntity = vinylProductDAO.findByProductId(basketItemTrans.getProductId());

                if (vinylProductEntity == null) {
                    continue;
                } else {
                    vinylProducts.add(vinylProductEntity);
                    continue;
                }
            }

            if (basketProductType != null && !basketProductType.equals(findById.getProductType())) {
                continue;
            }

            basketProductType = findById.getProductType();
            productsFromBasket.add(findById);

            System.out.println("[Commerce] Item in basket -> product: " + findById.getProductTitle() + " (" + findById.getProductId() + ", " + findById.getProductType() + ")");
        }

        if (!ownedCarEntity.getId().equals(commerceSessionTrans.getUpdatedCar().getId())) {
            System.out.println("car IDs don't match: " + ownedCarEntity.getId() + " vs " + commerceSessionTrans.getUpdatedCar().getId());
            commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INVALID_BASKET);
            commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
            commerceSessionResultTrans.setInventoryItems(new ArrayOfInventoryItemTrans());
            commerceSessionResultTrans.setUpdatedCar(OwnedCarConverter.entity2Trans(ownedCarEntity));
            return commerceSessionResultTrans;
        }

        Map<Object, ProductEntity> addedFromCatalog = new HashMap<>();
        Map<Object, VinylProductEntity> addedVinylsFromCatalog = new HashMap<>();
        Map<Object, InventoryItemEntity> addedFromInventory = new HashMap<>();
        List<ProductEntity> removedProducts = new ArrayList<>();
        List<VinylProductEntity> removedVinylProducts = new ArrayList<>();

        Map<Object, Integer> addedHashes = new HashMap<>();
        List<Integer> removedHashes = new ArrayList<>();

        ListDifferences<PerformancePartTrans> performancePartDiff = ListDifferences.getDifferences(customCarTrans.getPerformanceParts().getPerformancePartTrans(), commerceCustomCar.getPerformanceParts().getPerformancePartTrans());
        ListDifferences<SkillModPartTrans> skillModPartDiff = ListDifferences.getDifferences(customCarTrans.getSkillModParts().getSkillModPartTrans(), commerceCustomCar.getSkillModParts().getSkillModPartTrans());
        ListDifferences<VisualPartTrans> visualPartDiff = ListDifferences.getDifferences(customCarTrans.getVisualParts().getVisualPartTrans(), commerceCustomCar.getVisualParts().getVisualPartTrans());
        ListDifferences<CustomVinylTrans> vinylDiff = ListDifferences.getDifferences(customCarTrans.getVinyls().getCustomVinylTrans(), commerceCustomCar.getVinyls().getCustomVinylTrans());
        ListDifferences<CustomPaintTrans> paintDiff = ListDifferences.getDifferences(customCarTrans.getPaints().getCustomPaintTrans(), commerceCustomCar.getPaints().getCustomPaintTrans());

        if (performancePartDiff.hasDifferences()) {
            for (PerformancePartTrans addedPart : performancePartDiff.getAdded()) {
                System.out.println("[Commerce] Added performance part: " + addedPart.getPerformancePartAttribHash());
                addedHashes.put(addedPart, addedPart.getPerformancePartAttribHash());
            }

            for (PerformancePartTrans removedPart : performancePartDiff.getRemoved()) {
                System.out.println("[Commerce] Removed performance part: " + removedPart.getPerformancePartAttribHash());
                removedHashes.add(removedPart.getPerformancePartAttribHash());
            }
        }

        if (skillModPartDiff.hasDifferences()) {
            for (SkillModPartTrans addedPart : skillModPartDiff.getAdded()) {
                System.out.println("[Commerce] Added skill mod part: " + addedPart.getSkillModPartAttribHash());
                addedHashes.put(addedPart, addedPart.getSkillModPartAttribHash());
            }

            for (SkillModPartTrans removedPart : skillModPartDiff.getRemoved()) {
                System.out.println("[Commerce] Removed skill mod part: " + removedPart.getSkillModPartAttribHash());
                removedHashes.add(removedPart.getSkillModPartAttribHash());
            }
        }

        if (visualPartDiff.hasDifferences()) {
            for (VisualPartTrans addedPart : visualPartDiff.getAdded()) {
                System.out.println("[Commerce] Added visual part: " + addedPart.getPartHash());
                addedHashes.put(addedPart, addedPart.getPartHash());
            }

            for (VisualPartTrans removedPart : visualPartDiff.getRemoved()) {
                System.out.println("[Commerce] Removed visual part: " + removedPart.getPartHash());
                removedHashes.add(removedPart.getPartHash());
            }
        }

        if (vinylDiff.hasDifferences()) {
            for (CustomVinylTrans addedVinyl : vinylDiff.getAdded()) {
                System.out.println("[Commerce] Added vinyl: " + addedVinyl.getHash());
                addedHashes.put(addedVinyl, addedVinyl.getHash());
            }

            for (CustomVinylTrans removedVinyl : vinylDiff.getRemoved()) {
                System.out.println("[Commerce] Removed vinyl: " + removedVinyl.getHash());
                removedHashes.add(removedVinyl.getHash());
            }
        }

        if (paintDiff.hasDifferences()) {
            for (CustomPaintTrans addedPaint : paintDiff.getAdded()) {
                System.out.println("[Commerce] Added paint: " + addedPaint.getGroup());
                addedHashes.put(addedPaint, addedPaint.getGroup());
            }

            for (CustomPaintTrans removedPaint : paintDiff.getRemoved()) {
                System.out.println("[Commerce] Removed paint: " + removedPaint.getGroup());
                removedHashes.add(removedPaint.getGroup());
            }
        }

        for (Map.Entry<Object, Integer> addedItem : addedHashes.entrySet()) {
            System.out.println("addedItem: " + addedItem.getValue());

            VinylProductEntity vinylProductEntity = vinylProductDAO.findByHash(addedItem.getValue());

            if (vinylProductEntity == null) {
                Optional<ProductEntity> basketProduct = productsFromBasket.stream().filter(p -> p.getHash().equals(addedItem.getKey())).findFirst();

                if (basketProduct.isPresent()) {
                    System.out.println("[Commerce] Part is in basket");
                    ProductEntity productEntity = basketProduct.get();

                    addedFromCatalog.put(addedItem.getKey(), productEntity);

                    switch (productEntity.getCurrency()) {
                        case "CASH":
                            deductCash += productEntity.getPrice();
                            break;
                        case "_NS":
                            deductBoost += productEntity.getPrice();
                            break;
                        default:
                            System.out.println("I don't know what you did... but it's not good");
                    }
                } else {
                    System.out.println("[Commerce] Not in basket, check inventory");
                    InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndHash(personaId, addedItem.getValue());

                    if (inventoryItemEntity != null) {
                        addedFromInventory.put(addedItem.getKey(), inventoryItemEntity);
                    } else {
                        System.out.println("[Commerce] Not in inventory, go to catalog");
                        ProductEntity productEntity = productDAO.findByHash(addedItem.getValue());

                        if (productEntity == null) {
                            System.out.println("[Commerce] Not in catalog, let's just skip it");
                        } else {
                            addedFromCatalog.put(addedItem.getKey(), productEntity);

                            switch (productEntity.getCurrency()) {
                                case "CASH":
                                    deductCash += productEntity.getPrice();
                                    break;
                                case "_NS":
                                    deductBoost += productEntity.getPrice();
                                    break;
                                default:
                                    System.out.println("I don't know what you did... but it's not good");
                            }
                        }
                    }
                }
            } else {
                addedVinylsFromCatalog.put(addedItem.getKey(), vinylProductEntity);

                switch (vinylProductEntity.getCurrency()) {
                    case "CASH":
                        deductCash += vinylProductEntity.getPrice();
                        break;
                    case "_NS":
                        deductBoost += vinylProductEntity.getPrice();
                        break;
                    default:
                        System.out.println("I don't know what you did... but it's not good");
                }
            }
        }

        for (Integer removedHash : removedHashes) {
            System.out.println("removedHash: " + removedHash);

            ProductEntity productEntity = productDAO.findByHash(removedHash);
            VinylProductEntity vinylProductEntity;

            if (productEntity == null) {
                vinylProductEntity = vinylProductDAO.findByHash(removedHash);

                if (vinylProductEntity == null) {
                    System.out.println("[Commerce] Vinyl doesn't exist, let's skip this");
                } else {
                    removedVinylProducts.add(vinylProductEntity);
                }
            } else {
                switch (productEntity.getProductType()) {
                    case "PERFORMANCEPART":
                    case "SKILLMODPART":
                    case "VISUALPART":
                        switch (productEntity.getCurrency()) {
                            case "CASH":
                                addCash += calcProductSellPrice(productEntity);
                                break;
                            case "_NS":
                                addBoost += calcProductSellPrice(productEntity);
                                break;
                        }

                        break;
                    default:
                        System.out.println("[Commerce] Can't return currency for " + productEntity.getProductType());
                }

                removedProducts.add(productEntity);
            }
        }

        // sell entitlements
        if (commerceSessionTrans.getEntitlementsToSell() != null
                && commerceSessionTrans.getEntitlementsToSell().getItems() != null
                && !commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans().isEmpty()) {
            for (EntitlementItemTrans entitlementItemTrans : commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans()) {
                for (int i = 0; i < entitlementItemTrans.getQuantity(); i++) {
                    InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId, entitlementItemTrans.getEntitlementId());

                    if (inventoryItemEntity != null) {
                        ProductEntity productEntity = productDAO.findByHash(inventoryItemEntity.getHash());

                        if (productEntity != null) {
                            switch (productEntity.getCurrency()) {
                                case "CASH":
                                    addCash += calcProductSellPrice(productEntity);
                                    break;
                                case "_NS":
                                    addBoost += calcProductSellPrice(productEntity);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("[Commerce] Addition values: boost = " + addBoost + " cash = " + addCash);
        System.out.println("[Commerce] Deduction values: boost = " + deductBoost + " cash = " + deductCash);

        int finalCash = (int) (personaEntity.getCash() - deductCash + addCash);
        int finalBoost = (int) (personaEntity.getBoost() - deductBoost + addBoost);

        System.out.println("[Commerce] finalCash = " + finalCash + ", finalBoost = " + finalBoost);

        if (deductCash > finalCash) {
            commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
            commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
            commerceSessionResultTrans.setInventoryItems(new ArrayOfInventoryItemTrans());
            commerceSessionResultTrans.setUpdatedCar(OwnedCarConverter.entity2Trans(ownedCarEntity));
            return commerceSessionResultTrans;
        }

        if (deductBoost > finalBoost) {
            commerceSessionResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
            commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
            commerceSessionResultTrans.setInventoryItems(new ArrayOfInventoryItemTrans());
            commerceSessionResultTrans.setUpdatedCar(OwnedCarConverter.entity2Trans(ownedCarEntity));
            return commerceSessionResultTrans;
        }

        // remove removed things
        for (ProductEntity productEntity : removedProducts) {
            switch (productEntity.getProductType()) {
                case "PERFORMANCEPART":
                    Iterator<PerformancePartEntity> performancePartEntityIterator = customCarEntity.getPerformanceParts().iterator();

                    while (performancePartEntityIterator.hasNext()) {
                        PerformancePartEntity performancePartEntity = performancePartEntityIterator.next();

                        if (performancePartEntity.getPerformancePartAttribHash() == productEntity.getHash()) {
                            System.out.println("[Commerce] Remove performance part: " + productEntity.getHash());
                            performancePartDAO.delete(performancePartEntity);
                            performancePartEntityIterator.remove();
                            break;
                        }
                    }

                    break;
                case "SKILLMODPART":
                    Iterator<SkillModPartEntity> skillModPartEntityIterator = customCarEntity.getSkillModParts().iterator();

                    while (skillModPartEntityIterator.hasNext()) {
                        SkillModPartEntity skillModPartEntity = skillModPartEntityIterator.next();

                        if (skillModPartEntity.getSkillModPartAttribHash() == productEntity.getHash()) {
                            System.out.println("[Commerce] Remove skill mod part: " + productEntity.getHash());
                            skillModPartDAO.delete(skillModPartEntity);
                            skillModPartEntityIterator.remove();
                            break;
                        }
                    }

                    break;
                case "VISUALPART":
                    Iterator<VisualPartEntity> visualPartEntityIterator = customCarEntity.getVisualParts().iterator();

                    while (visualPartEntityIterator.hasNext()) {
                        VisualPartEntity visualPartEntity = visualPartEntityIterator.next();

                        if (visualPartEntity.getPartHash() == productEntity.getHash()) {
                            System.out.println("[Commerce] Remove visual part: " + productEntity.getHash());
                            visualPartDAO.delete(visualPartEntity);
                            visualPartEntityIterator.remove();
                            break;
                        }
                    }

                    break;
                case "PAINT_BODY":
                case "PAINT_WHEEL":
                    Iterator<PaintEntity> paintEntityIterator = customCarEntity.getPaints().iterator();

                    while (paintEntityIterator.hasNext()) {
                        PaintEntity paintEntity = paintEntityIterator.next();

                        if (paintEntity.getGroup() == productEntity.getHash()) {
                            System.out.println("[Commerce] Remove paint: " + productEntity.getHash());
                            paintDAO.delete(paintEntity);
                            paintEntityIterator.remove();
                            break;
                        }
                    }

                    break;
            }
        }

        for (VinylProductEntity vinylProductEntity : removedVinylProducts) {
            Iterator<VinylEntity> vinylEntityIterator = customCarEntity.getVinyls().iterator();

            while (vinylEntityIterator.hasNext()) {
                VinylEntity vinylEntity = vinylEntityIterator.next();

                if (vinylEntity.getHash() == vinylProductEntity.getHash()) {
                    System.out.println("[Commerce] Remove vinyl: " + vinylEntity.getHash());
                    vinylDAO.delete(vinylEntity);
                    vinylEntityIterator.remove();
                    break;
                }
            }
        }

        customCarDAO.update(customCarEntity);

        for (Map.Entry<Object, ProductEntity> addedProduct : addedFromCatalog.entrySet()) {
            ProductEntity productEntity = addedProduct.getValue();
            Object customizationObject = addedProduct.getKey();

            switch (productEntity.getProductType()) {
                case "PERFORMANCEPART":
                    addPerformancePart(customCarEntity, customizationObject, productEntity.getHash());
                    achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_PERFORMANCEPART"), 1L);
                    break;
                case "SKILLMODPART":
                    addSkillPart(customCarEntity, customizationObject, productEntity.getHash());
                    VirtualItemEntity skillVirtualItem = virtualItemDAO.findByHash(productEntity.getHash());

                    if (skillVirtualItem != null
                            && skillVirtualItem.getType().equalsIgnoreCase("skillmodpart")
                            && (skillVirtualItem.getItemName().contains("_03_")
                                || skillVirtualItem.getItemName().contains("_04_")
                                || skillVirtualItem.getItemName().contains("_05_"))) {
                        switch (skillVirtualItem.getSubType()) {
                            case "skillmod_race":
                                achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_RACE_SKILLS"), 1L);
                                break;
                            case "skillmod_explore":
                                achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_EXPLORE_SKILLS"), 1L);
                                break;
                            case "skillmod_pursuit":
                                achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_PURSUIT_SKILLS"), 1L);
                                break;
                        }
                    }

                    break;
                case "VISUALPART":
                    addVisualPart(customCarEntity, customizationObject, productEntity.getHash());
                    achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_AFTERMARKETPART"), 1L);
                    break;
                case "PAINT_BODY":
                case "PAINT_WHEEL":
                    addPaint(customCarEntity, customizationObject, productEntity.getHash());
                    achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_PAINTS"), 1L);
                    break;
                default:
                    System.out.println("[Commerce] I don't know how to handle this: " + productEntity.getProductType());
            }
        }

        for (Map.Entry<Object, InventoryItemEntity> addedInventoryItem : addedFromInventory.entrySet()) {
            InventoryItemEntity inventoryItemEntity = addedInventoryItem.getValue();
            Object customizationObject = addedInventoryItem.getKey();
            switch (inventoryItemEntity.getVirtualItemType().toUpperCase()) {
                case "PERFORMANCEPART":
                    addPerformancePart(customCarEntity, customizationObject, inventoryItemEntity.getHash());
                    break;
                case "SKILLMODPART":
                    addSkillPart(customCarEntity, customizationObject, inventoryItemEntity.getHash());
                    break;
                case "VISUALPART":
                    addVisualPart(customCarEntity, customizationObject, inventoryItemEntity.getHash());
                    break;
            }

            inventoryBO.deletePart(personaId, inventoryItemEntity.getHash());
        }

        // sell entitlements
        if (commerceSessionTrans.getEntitlementsToSell() != null
                && commerceSessionTrans.getEntitlementsToSell().getItems() != null
                && !commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans().isEmpty()) {
            for (EntitlementItemTrans entitlementItemTrans : commerceSessionTrans.getEntitlementsToSell().getItems().getEntitlementItemTrans()) {
                for (int i = 0; i < entitlementItemTrans.getQuantity(); i++) {
                    InventoryItemEntity inventoryItemEntity = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId, entitlementItemTrans.getEntitlementId());

                    if (inventoryItemEntity != null) {
                        inventoryBO.deletePart(personaId, entitlementItemTrans.getEntitlementId());
                    }
                }
            }
        }

        for (Map.Entry<Object, VinylProductEntity> addedVinylProduct : addedVinylsFromCatalog.entrySet()) {
            VinylProductEntity productEntity = addedVinylProduct.getValue();
            Object customizationObject = addedVinylProduct.getKey();

            addVinyl(customCarEntity, customizationObject, productEntity.getHash());
            achievementsBO.update(personaEntity, achievementDAO.findByName("achievement_ACH_INSTALL_VINYLS"), 1L);
        }

        personaEntity.setCash(finalCash);
        personaEntity.setBoost(finalBoost);
        personaDAO.update(personaEntity);

        calcNewCarClass(customCarEntity);
        customCarDAO.update(customCarEntity);
        ownedCarEntity.setCustomCar(customCarEntity);
        ownedCarDAO.update(ownedCarEntity);

        commerceSessionResultTrans.setStatus(CommerceResultStatus.SUCCESS);
        commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
        commerceSessionResultTrans.setInventoryItems(new ArrayOfInventoryItemTrans());
        commerceSessionResultTrans.setUpdatedCar(OwnedCarConverter.entity2Trans(ownedCarEntity));

        WalletTrans cashWallet = new WalletTrans();
        cashWallet.setBalance(personaEntity.getCash());
        cashWallet.setCurrency("CASH");

        WalletTrans boostWallet = new WalletTrans();
        boostWallet.setBalance(personaEntity.getBoost());
        boostWallet.setCurrency("BOOST");

        ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
        arrayOfWalletTrans.getWalletTrans().add(cashWallet);
        arrayOfWalletTrans.getWalletTrans().add(boostWallet);

        commerceSessionResultTrans.setWallets(arrayOfWalletTrans);

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
        Float finalTopSpeed = (finalConstant * carClassesEntity.getTsStock().floatValue()) + finalTopSpeed1 + finalTopSpeed2
                + finalTopSpeed3;

        Float finalAccel1 = carClassesEntity.getAcVar1().floatValue() * th;
        Float finalAccel2 = carClassesEntity.getAcVar2().floatValue() * ta;
        Float finalAccel3 = carClassesEntity.getAcVar3().floatValue() * tt;
        Float finalAccel = (finalConstant * carClassesEntity.getAcStock().floatValue()) + finalAccel1 + finalAccel2
                + finalAccel3;

        Float finalHandling1 = carClassesEntity.getHaVar1().floatValue() * th;
        Float finalHandling2 = carClassesEntity.getHaVar2().floatValue() * ta;
        Float finalHandling3 = carClassesEntity.getHaVar3().floatValue() * tt;
        Float finalHandling = (finalConstant * carClassesEntity.getHaStock().floatValue()) + finalHandling1 + finalHandling2
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