/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementCommerceContext;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ArrayOfOwnedCarTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceResultTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Stateless
public class BasketBO {

    @EJB
    private PersonaBO personaBo;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private BasketDefinitionDAO basketDefinitionsDAO;

    @EJB
    private CarSlotDAO carSlotDAO;

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private ProductDAO productDao;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private InventoryDAO inventoryDao;

    @EJB
    private InventoryItemDAO inventoryItemDao;

    @EJB
    private InventoryBO inventoryBO;

    @EJB
    private TreasureHuntDAO treasureHuntDAO;

    @EJB
    private CarClassesDAO carClassesDAO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    @EJB
    private PerformanceBO performanceBO;

    public ProductEntity findProduct(String productId) {
        return productDao.findByProductId(productId);
    }

    public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity) {
        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
        int price =
                (int) (productDao.findByProductId(productId).getPrice() * (100 - defaultCarEntity.getOwnedCar().getDurability()));

        if (this.performPersonaTransaction(personaEntity, productId, price)) {
            personaDao.update(personaEntity);
            defaultCarEntity.getOwnedCar().setDurability(100);
            carSlotDAO.update(defaultCarEntity);
            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyPowerups(String productId, PersonaEntity personaEntity) {
        if (!parameterBO.getBoolParam("ENABLE_ECONOMY")) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        ProductEntity powerupProduct = productDao.findByProductId(productId);

        if (powerupProduct == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }

        if (performPersonaTransaction(personaEntity, productId)) {
            InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());
            inventoryBO.addStackedInventoryItem(inventoryEntity, productId, powerupProduct.getUseCount());
            inventoryDao.update(inventoryEntity);

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyCar(String productId, PersonaEntity personaEntity, String securityToken,
                                       CommerceResultTrans commerceResultTrans) {
        if (getPersonaCarCount(personaEntity.getPersonaId()) >= parameterBO.getCarLimit(securityToken)) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_CAR_SLOTS;
        }

        if (performPersonaTransaction(personaEntity, productId)) {
            try {
                CarSlotEntity carSlotEntity = addCar(productId, personaEntity);

                CarClassesEntity carClassesEntity =
                        carClassesDAO.findById(carSlotEntity.getOwnedCar().getCustomCar().getName());

                if (carClassesEntity != null) {
                    AchievementCommerceContext commerceContext = new AchievementCommerceContext(carClassesEntity,
                            AchievementCommerceContext.CommerceType.CAR_PURCHASE);
                    achievementBO.updateAchievements(personaEntity, "COMMERCE", new HashMap<String,
                            Object>() {{
                        put("persona", personaEntity);
                        put("carSlot", carSlotEntity);
                        put("commerceCtx", commerceContext);
                    }});
                }

                personaDao.update(personaEntity);

                personaBo.changeDefaultCar(personaEntity.getPersonaId(), carSlotEntity.getOwnedCar().getId());

                ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
                OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(carSlotEntity.getOwnedCar());
                commerceResultTrans.setPurchasedCars(arrayOfOwnedCarTrans);
                arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
            } catch (EngineException e) {
                this.performPersonaTransaction(personaEntity, productId, -1, true);

                return CommerceResultStatus.FAIL_MAX_STACK_OR_RENTAL_LIMIT;
            }

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus reviveTreasureHunt(String productId, PersonaEntity personaEntity) {
        if (performPersonaTransaction(personaEntity, productId)) {
            TreasureHuntEntity treasureHuntEntity = treasureHuntDAO.findById(personaEntity.getPersonaId());
            treasureHuntEntity.setIsStreakBroken(false);
            treasureHuntEntity.setStreak(treasureHuntEntity.getStreak() + 1);
            treasureHuntEntity.setThDate(LocalDate.now());

            treasureHuntDAO.update(treasureHuntEntity);

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyAmplifier(PersonaEntity personaEntity, String productId) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        if (!canPurchaseProduct(personaEntity, productEntity))
            return CommerceResultStatus.FAIL_LOCKED_PRODUCT_NOT_ACCESSIBLE_TO_THIS_USER;

        List<InventoryItemEntity> existing =
                inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaEntity.getPersonaId(),
                        productEntity.getEntitlementTag());

        if (!existing.isEmpty()) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }

        if (performPersonaTransaction(personaEntity, productId)) {
            InventoryEntity inventoryEntity = inventoryBO.getInventory(personaEntity.getPersonaId());
            inventoryBO.addInventoryItem(inventoryEntity, productId);
            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CarSlotEntity addCar(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        Objects.requireNonNull(productEntity, "productEntity is null");

        /*
        rentals cannot be purchased unless you have one other car
        rentals will not be considered when evaluating whether or not you can sell a car
        so if you own one regular car and one rental, you cannot sell the regular car no matter what
        until you get another regular car
         */

        boolean isRental = productEntity.getDurationMinute() > 0;
        if (isRental) {
            List<CarSlotEntity> nonRentals = carSlotDAO.findNonRentalsByPersonaId(personaEntity.getPersonaId());

            if (nonRentals.isEmpty()) {
                throw new EngineException("Persona " + personaEntity.getName() + " has no non-rental cars", EngineExceptionCode.MissingRequiredEntitlements);
            }
        }

        OwnedCarTrans ownedCarTrans = getCar(productId);
        ownedCarTrans.setId(0L);
        ownedCarTrans.getCustomCar().setId(0);

        CarSlotEntity carSlotEntity = new CarSlotEntity();
        carSlotEntity.setPersona(personaEntity);

        OwnedCarEntity ownedCarEntity = new OwnedCarEntity();
        ownedCarEntity.setCarSlot(carSlotEntity);

        CustomCarEntity customCarEntity = new CustomCarEntity();
        customCarEntity.setOwnedCar(ownedCarEntity);
        ownedCarEntity.setCustomCar(customCarEntity);
        carSlotEntity.setOwnedCar(ownedCarEntity);
        OwnedCarConverter.trans2Entity(ownedCarTrans, ownedCarEntity);
        OwnedCarConverter.details2NewEntity(ownedCarTrans, ownedCarEntity);

        if (isRental) {
            ownedCarEntity.setExpirationDate(LocalDateTime.now().plusMinutes(productEntity.getDurationMinute()));
            ownedCarEntity.setOwnershipType("RentalCar");
        }

        carSlotDAO.insert(carSlotEntity);

        performanceBO.calcNewCarClass(carSlotEntity.getOwnedCar().getCustomCar());

        return carSlotEntity;
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId) {
        return carSlotDAO.findByPersonaId(personaId);
    }

    public boolean sellCar(String securityToken, Long personaId, Long serialNumber) {
        this.tokenSessionBO.verifyPersonaOwnership(securityToken, personaId);

        OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(serialNumber);
        if (ownedCarEntity == null) {
            return false;
        }

        if ("RentalCar".equalsIgnoreCase(ownedCarEntity.getOwnershipType())) {
            return false;
        }

        PersonaEntity personaEntity = personaDao.findById(personaId);

        if (!removeCar(personaEntity, serialNumber)) {
            return false;
        }

        double cashTotal = personaEntity.getCash() + ownedCarEntity.getCustomCar().getResalePrice();
        driverPersonaBO.updateCash(personaEntity, cashTotal);

        return true;
    }

    public boolean removeCar(PersonaEntity personaEntity, Long serialNumber) {
        OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(serialNumber);
        if (ownedCarEntity == null) {
            return false;
        }
        CarSlotEntity carSlotEntity = ownedCarEntity.getCarSlot();
        if (carSlotEntity == null) {
            return false;
        }
        int nonRentalCarCount = carSlotDAO.findNonRentalsByPersonaId(personaEntity.getPersonaId()).size();

        // If the car is not a rental, check the number of non-rentals
        if (!"RentalCar".equalsIgnoreCase(carSlotEntity.getOwnedCar().getOwnershipType())) {
            if (nonRentalCarCount <= 1) {
                return false;
            }
        } else if (nonRentalCarCount == 0) {
            return false;
        }

        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());

        int curCarIndex = personaEntity.getCurCarIndex();
        if (defaultCarEntity.getId().equals(carSlotEntity.getId())) {
            curCarIndex = 0;
        } else {
            List<CarSlotEntity> personasCar = personaBo.getPersonasCar(personaEntity.getPersonaId());
            int curCarIndexTmp = curCarIndex;
            for (int i = 0; i < curCarIndexTmp; i++) {
                if (personasCar.get(i).getId().equals(carSlotEntity.getId())) {
                    curCarIndex--;
                    break;
                }
            }
        }
        carSlotDAO.delete(carSlotEntity);
        personaEntity.setCurCarIndex(curCarIndex);
        personaDao.update(personaEntity);

        return true;
    }

    private OwnedCarTrans getCar(String productId) {
        ProductEntity productEntity = findProduct(productId);
        if (productEntity == null) {
            throw new IllegalArgumentException(String.format("No product definition for %s", productId));
        }

        BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
        if (basketDefinitonEntity == null) {
            throw new IllegalArgumentException(String.format("No basket definition for %s", productId));
        }
        String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
        OwnedCarTrans ownedCarTrans1 = UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);

        if (productEntity.getDurationMinute() != 0) {
            ownedCarTrans1.setOwnershipType("RentalCar");
        }

        // do this automatically in case we get any weird data in our table
        ownedCarTrans1.setHeat(1.0f);
        ownedCarTrans1.setDurability(100);
        ownedCarTrans1.getCustomCar().setResalePrice(productEntity.getResalePrice());

        return ownedCarTrans1;
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, String productId) {
        return performPersonaTransaction(personaEntity, productId, -1);
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, String productId, int priceOverride) {
        return performPersonaTransaction(personaEntity, productId, priceOverride, false);
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, String productId, int priceOverride,
                                              boolean reverseTransaction) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        assert productEntity != null;

        if (!canPurchaseProduct(personaEntity, productEntity))
            return false;

        float effectivePrice = priceOverride == -1 ? productEntity.getPrice() : priceOverride;
        switch (productEntity.getCurrency()) {
            case "CASH":
                if (personaEntity.getCash() >= effectivePrice) {
                    if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                        driverPersonaBO.updateCash(personaEntity,
                                personaEntity.getCash() + (effectivePrice * (reverseTransaction ? 1 : -1)));
                    }
                    return true;
                }
            case "_NS":
                if (personaEntity.getBoost() >= effectivePrice) {
                    if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                        personaEntity.setBoost(personaEntity.getBoost() + (effectivePrice * (reverseTransaction ? 1 :
                                -1)));
                        personaDao.update(personaEntity);
                    }
                    return true;
                }
        }

        return false;
    }

    private int getPersonaCarCount(Long personaId) {
        return getPersonasCar(personaId).size();
    }

    private boolean canPurchaseProduct(PersonaEntity personaEntity, ProductEntity productEntity) {
        if (productEntity.isEnabled()) {
            // non-premium products are available to all; user must be premium to purchase a premium product
            return !productEntity.isPremium() || personaEntity.getUser().isPremium();
        }

        return false;
    }
}
