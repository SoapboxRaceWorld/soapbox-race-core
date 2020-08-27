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
import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    private CardPackDAO cardPackDAO;

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

    @EJB
    private ItemRewardBO itemRewardBO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private AmplifierDAO amplifierDAO;

    public ProductEntity findProduct(String productId) {
        return productDao.findByProductId(productId);
    }

    public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity) {
        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
        int price =
                (int) (productDao.findByProductId(productId).getPrice() * (100 - defaultCarEntity.getOwnedCar().getDurability()));
        ProductEntity repairProduct = productDao.findByProductId(productId);

        if (repairProduct == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }
        if (this.performPersonaTransaction(personaEntity, repairProduct, price)) {
            personaDao.update(personaEntity);
            OwnedCarEntity ownedCarEntity = defaultCarEntity.getOwnedCar();
            carDamageBO.updateDurability(ownedCarEntity, 100);
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

        if (performPersonaTransaction(personaEntity, powerupProduct)) {
            InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());
            inventoryBO.addStackedInventoryItem(inventoryEntity, productId, powerupProduct.getUseCount());
            inventoryDao.update(inventoryEntity);

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyCar(ProductEntity productEntity, PersonaEntity personaEntity, TokenSessionEntity tokenSessionEntity,
                                       CommerceResultTrans commerceResultTrans) {
        if (getPersonaCarCount(personaEntity.getPersonaId()) >= parameterBO.getCarLimit(tokenSessionEntity.getUserEntity())) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_CAR_SLOTS;
        }

        if (performPersonaTransaction(personaEntity, productEntity)) {
            try {
                CarSlotEntity carSlotEntity = addCar(productEntity, personaEntity);
                personaBo.changeDefaultCar(personaEntity, carSlotEntity.getOwnedCar().getId());
                personaDao.update(personaEntity);

                ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
                OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(carSlotEntity.getOwnedCar());
                commerceResultTrans.setPurchasedCars(arrayOfOwnedCarTrans);
                arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
            } catch (EngineException e) {
                this.performPersonaTransaction(personaEntity, productEntity, -1, true);

                return CommerceResultStatus.FAIL_MAX_STACK_OR_RENTAL_LIMIT;
            }

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyBundle(String productId, PersonaEntity personaEntity, CommerceResultTrans commerceResultTrans) {
        ProductEntity bundleProduct = productDao.findByProductId(productId);

        if (bundleProduct == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }

        if (performPersonaTransaction(personaEntity, bundleProduct)) {
            try {
                CardPackEntity cardPackEntity = cardPackDAO.findByEntitlementTag(bundleProduct.getEntitlementTag());

                if (cardPackEntity == null) {
                    throw new EngineException("Could not find card pack with name: " + bundleProduct.getEntitlementTag() + " (product ID: " + productId + ")", EngineExceptionCode.LuckyDrawCouldNotDrawProduct, true);
                }

                for (CardPackItemEntity cardPackItemEntity : cardPackEntity.getItems()) {
                    itemRewardBO.convertRewards(
                            itemRewardBO.getRewards(personaEntity, cardPackItemEntity.getScript()),
                            commerceResultTrans
                    );
                }

                return CommerceResultStatus.SUCCESS;
            } catch (EngineException e) {
                this.performPersonaTransaction(personaEntity, bundleProduct, -1, true);

                throw new EngineException("Error occurred in bundle purchase (product ID: " + productId + ")", e, e.getCode(), true);
            }
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus reviveTreasureHunt(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        if (!canPurchaseProduct(personaEntity, productEntity))
            return CommerceResultStatus.FAIL_LOCKED_PRODUCT_NOT_ACCESSIBLE_TO_THIS_USER;

        if (performPersonaTransaction(personaEntity, productEntity)) {
            TreasureHuntEntity treasureHuntEntity = treasureHuntDAO.findById(personaEntity.getPersonaId());
            treasureHuntEntity.setIsStreakBroken(false);

            treasureHuntDAO.update(treasureHuntEntity);

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyAmplifier(PersonaEntity personaEntity, String productId) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        if (!canPurchaseProduct(personaEntity, productEntity))
            return CommerceResultStatus.FAIL_LOCKED_PRODUCT_NOT_ACCESSIBLE_TO_THIS_USER;

        if (!canAddAmplifier(personaEntity.getPersonaId(), productEntity.getEntitlementTag())) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }

        if (performPersonaTransaction(personaEntity, productEntity)) {
            addAmplifier(personaEntity, productEntity);
            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CarSlotEntity addCar(ProductEntity productEntity, PersonaEntity personaEntity) {
        Objects.requireNonNull(productEntity, "productEntity is null");

        /*
        rentals cannot be purchased unless you have one other car
        rentals will not be considered when evaluating whether or not you can sell a car
        so if you own one regular car and one rental, you cannot sell the regular car no matter what
        until you get another regular car
         */

        boolean isRental = productEntity.getDurationMinute() > 0;
        if (isRental) {
            Long numNonRentals = carSlotDAO.findNumNonRentalsByPersonaId(personaEntity.getPersonaId());

            if (numNonRentals.equals(0L)) {
                throw new EngineException("Persona " + personaEntity.getName() + " has no non-rental cars", EngineExceptionCode.MissingRequiredEntitlements, true);
            }
        }

        OwnedCarTrans ownedCarTrans = getCar(productEntity);
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

        if (isRental && canAddAmplifier(personaEntity.getPersonaId(), "INSURANCE_AMPLIFIER")) {
            addAmplifier(personaEntity, productDao.findByEntitlementTag("INSURANCE_AMPLIFIER"));
        }

        CarClassesEntity carClassesEntity =
                carClassesDAO.findById(carSlotEntity.getOwnedCar().getCustomCar().getName());

        AchievementTransaction transaction = achievementBO.createTransaction(personaEntity.getPersonaId());

        if (carClassesEntity != null) {
            AchievementCommerceContext commerceContext = new AchievementCommerceContext(carClassesEntity,
                    AchievementCommerceContext.CommerceType.CAR_PURCHASE);
            transaction.add("COMMERCE", Map.of("persona", personaEntity, "carSlot", carSlotEntity, "commerceCtx", commerceContext));
            achievementBO.commitTransaction(personaEntity, transaction);
        }

        return carSlotEntity;
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId) {
        return carSlotDAO.findByPersonaId(personaId);
    }

    public boolean sellCar(TokenSessionEntity tokenSessionEntity, Long personaId, Long serialNumber) {
        this.tokenSessionBO.verifyPersonaOwnership(tokenSessionEntity, personaId);

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
        if (!carSlotEntity.getPersona().getPersonaId().equals(personaEntity.getPersonaId())) {
            throw new EngineException(EngineExceptionCode.CarNotOwnedByDriver, false);
        }
        Long nonRentalCarCount = carSlotDAO.findNumNonRentalsByPersonaId(personaEntity.getPersonaId());

        // If the car is not a rental, check the number of non-rentals
        if (!"RentalCar".equalsIgnoreCase(carSlotEntity.getOwnedCar().getOwnershipType())) {
            if (nonRentalCarCount <= 1) {
                return false;
            }
        } else if (nonRentalCarCount == 0) {
            return false;
        }

        int curCarIndex = personaEntity.getCurCarIndex();

        if (curCarIndex <= 0) {
            throw new EngineException("curCarIndex <= 0 when trying to sell car", EngineExceptionCode.CantDeleteLastOwnedCar, true);
        }

        personaEntity.setCurCarIndex(curCarIndex - 1);
        carSlotDAO.delete(carSlotEntity);
        personaDao.update(personaEntity);

        return true;
    }

    private boolean canAddAmplifier(Long personaId, String entitlementTag) {
        return inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaId, entitlementTag).isEmpty();
    }

    private void addAmplifier(PersonaEntity personaEntity, ProductEntity productEntity) {
        InventoryEntity inventoryEntity = inventoryBO.getInventory(personaEntity);
        inventoryBO.addInventoryItem(inventoryEntity, productEntity.getProductId());

        AmplifierEntity amplifierEntity = amplifierDAO.findAmplifierByHash(productEntity.getHash());

        if (amplifierEntity.getAmpType().equals("INSURANCE")) {
            personaBo.repairAllCars(personaEntity);
        }
    }

    private OwnedCarTrans getCar(ProductEntity productEntity) {
        Objects.requireNonNull(productEntity, "productEntity is null");
        String productId = productEntity.getProductId();
        BasketDefinitionEntity basketDefinitionEntity = basketDefinitionsDAO.findById(productId);
        if (basketDefinitionEntity == null) {
            throw new IllegalArgumentException(String.format("No basket definition for %s", productId));
        }
        String ownedCarTrans = basketDefinitionEntity.getOwnedCarTrans();
        OwnedCarTrans ownedCarTrans1 = JAXBUtility.unMarshal(ownedCarTrans, OwnedCarTrans.class);

        if (productEntity.getDurationMinute() != 0) {
            ownedCarTrans1.setOwnershipType("RentalCar");
        }

        // do this automatically in case we get any weird data in our table
        ownedCarTrans1.setHeat(1.0f);
        ownedCarTrans1.setDurability(100);
        ownedCarTrans1.getCustomCar().setResalePrice(productEntity.getResalePrice());

        return ownedCarTrans1;
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, ProductEntity productEntity) {
        return performPersonaTransaction(personaEntity, productEntity, -1);
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, ProductEntity productEntity, int priceOverride) {
        return performPersonaTransaction(personaEntity, productEntity, priceOverride, false);
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, ProductEntity productEntity, int priceOverride,
                                              boolean reverseTransaction) {
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean canPurchaseProduct(PersonaEntity personaEntity, ProductEntity productEntity) {
        if (productEntity.isEnabled()) {
            // non-premium products are available to all; user must be premium to purchase a premium product
            return !productEntity.isPremium() || personaEntity.getUser().isPremium();
        }

        return false;
    }
}
