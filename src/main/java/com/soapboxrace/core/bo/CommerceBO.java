/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

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
    private PersonaDAO personaDAO;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private VinylProductDAO vinylProductDAO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    @EJB
    private CustomCarDAO customCarDAO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    @EJB
    private PerformanceBO performanceBO;

    public CommerceSessionResultTrans doCommerce(CommerceSessionTrans commerceSessionTrans, Long personaId) {
        List<BasketItemTrans> basketItems = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
        PersonaEntity personaEntity = personaDAO.findById(personaId);
        OwnedCarEntity ownedCarEntity = personaBO.getDefaultCarEntity(personaId).getOwnedCar();
        OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaId);
        CustomCarTrans customCarTrans = ownedCarTrans.getCustomCar();
        CustomCarTrans commerceCustomCar = commerceSessionTrans.getUpdatedCar().getCustomCar();

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

        InventoryEntity inventoryEntity = inventoryBO.getInventory(personaId);

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
                        inventoryBO.decreaseItemCount(inventoryEntity, productEntity.getEntitlementTag());
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

                if (inventoryItemEntity != null) {
                    inventoryBO.removeItem(inventoryEntity, e.getEntitlementId(), e.getQuantity());
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

        performanceBO.calcNewCarClass(customCar);

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

        return commerceSessionResultTrans;
    }
}