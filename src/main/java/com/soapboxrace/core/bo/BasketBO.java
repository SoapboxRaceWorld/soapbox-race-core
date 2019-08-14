package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementCommerceContext;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
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
    private TreasureHuntDAO treasureHuntDAO;

    @EJB
    private CarClassesDAO carClassesDAO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    public ProductEntity findProduct(String productId) {
        return productDao.findByProductId(productId);
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

        return ownedCarTrans1;
    }

    public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity) {
        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
        int price = (int) (productDao.findByProductId(productId).getPrice() * (100 - defaultCarEntity.getOwnedCar().getDurability()));

        if (this.performPersonaTransaction(personaEntity, productId, price)) {
            personaDao.update(personaEntity);
            defaultCarEntity.getOwnedCar().setDurability(100);
            carSlotDAO.update(defaultCarEntity);
            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, String productId) {
        return performPersonaTransaction(personaEntity, productId, -1);
    }

    private boolean performPersonaTransaction(PersonaEntity personaEntity, String productId, int priceOverride) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        assert productEntity != null;

        float effectivePrice = priceOverride == -1 ? productEntity.getPrice() : priceOverride;
        switch (productEntity.getCurrency()) {
            case "CASH":
                if (personaEntity.getCash() >= effectivePrice) {
                    if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                        driverPersonaBO.updateCash(personaEntity, personaEntity.getCash() - effectivePrice);
                    }
                    return true;
                }
            case "_NS":
                if (personaEntity.getBoost() >= effectivePrice) {
                    if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                        personaEntity.setBoost(personaEntity.getBoost() - effectivePrice);
                        personaDao.update(personaEntity);
                    }
                    return true;
                }
        }

        return false;
    }

    public CommerceResultStatus buyPowerups(String productId, PersonaEntity personaEntity) {
        if (!parameterBO.getBoolParam("ENABLE_ECONOMY")) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        ProductEntity powerupProduct = productDao.findByProductId(productId);
        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

        if (powerupProduct == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }

        if (performPersonaTransaction(personaEntity, productId)) {
            InventoryItemEntity item = inventoryItemDao.findByPersonaIdAndHash(personaEntity.getPersonaId(), powerupProduct.getHash());

            if (item == null) {
                return CommerceResultStatus.FAIL_INVALID_BASKET;
            }

            int newUsageCount = item.getRemainingUseCount() + powerupProduct.getUseCount();
            item.setRemainingUseCount(newUsageCount);
            inventoryItemDao.update(item);

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
            CarSlotEntity carSlotEntity = addCar(productId, personaEntity);
            CarClassesEntity carClassesEntity = carClassesDAO.findById(carSlotEntity.getOwnedCar().getCustomCar().getName());

            if (carClassesEntity != null) {
                AchievementCommerceContext commerceContext = new AchievementCommerceContext(carClassesEntity, AchievementCommerceContext.CommerceType.CAR_PURCHASE);
                achievementBO.updateAchievements(personaEntity.getPersonaId(), "COMMERCE", new HashMap<String, Object>() {{
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

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CommerceResultStatus buyInsurance(String productId, PersonaEntity personaEntity) {
        return buyAmplifier(personaEntity, productId, "INSURANCE_AMPLIFIER");
    }

    public CommerceResultStatus buyCashAmplifier(String productId, PersonaEntity personaEntity) {
        return buyAmplifier(personaEntity, productId, "CASH_AMPLIFIER_2X");
    }

    public CommerceResultStatus buyRepAmplifier(String productId, PersonaEntity personaEntity) {
        return buyAmplifier(personaEntity, productId, "REP_AMPLIFIER_2X");
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

    public CommerceResultStatus buyAmplifier(PersonaEntity personaEntity, String productId, String entitlementTag) {
        ProductEntity productEntity = productDao.findByProductId(productId);

        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

        List<InventoryItemEntity> existing = inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaEntity.getPersonaId(), productId);

        if (!existing.isEmpty()) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }

        if (performPersonaTransaction(personaEntity, productId)) {
            InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
            inventoryItemEntity.setInventoryEntity(inventoryEntity);
            inventoryItemEntity.setRemainingUseCount(0);
            inventoryItemEntity.setEntitlementTag(entitlementTag);
            if (productEntity.getDurationMinute() != 0) {
                inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusMinutes(productEntity.getDurationMinute()));
            }
            inventoryItemEntity.setStatus("ACTIVE");
            inventoryItemEntity.setVirtualItemType("amplifier");
            inventoryItemEntity.setProductId("DO NOT USE ME");
            inventoryItemEntity.setHash(productEntity.getHash());

            inventoryItemDao.insert(inventoryItemEntity);

            return CommerceResultStatus.SUCCESS;
        }

        return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
    }

    public CarSlotEntity addCar(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);
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

        if (productEntity.getDurationMinute() != 0) {
            ownedCarEntity.setExpirationDate(LocalDateTime.now().plusMinutes(productEntity.getDurationMinute()));
            ownedCarEntity.setOwnershipType("RentalCar");
        }

        carSlotDAO.insert(carSlotEntity);

        return carSlotEntity;
    }

    public int getPersonaCarCount(Long personaId) {
        return getPersonasCar(personaId).size();
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId) {
        List<CarSlotEntity> findByPersonaId = carSlotDAO.findByPersonaId(personaId);
        for (CarSlotEntity carSlotEntity : findByPersonaId) {
            CustomCarEntity customCar = carSlotEntity.getOwnedCar().getCustomCar();
            customCar.getPaints().size();
            customCar.getPerformanceParts().size();
            customCar.getSkillModParts().size();
            customCar.getVisualParts().size();
            customCar.getVinyls().size();
        }
        return findByPersonaId;
    }

    public boolean sellCar(String securityToken, Long personaId, Long serialNumber) {
        this.tokenSessionBO.verifyPersona(securityToken, personaId);

        OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(serialNumber);
        if (ownedCarEntity == null) {
            return false;
        }
        CarSlotEntity carSlotEntity = ownedCarEntity.getCarSlot();
        if (carSlotEntity == null) {
            return false;
        }
        int personaCarCount = getPersonaCarCount(personaId);
        if (personaCarCount <= 1) {
            return false;
        }

        PersonaEntity personaEntity = personaDao.findById(personaId);

        double cashTotal = personaEntity.getCash() + ownedCarEntity.getCustomCar().getResalePrice();
        driverPersonaBO.updateCash(personaEntity, cashTotal);

        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaId);

        int curCarIndex = personaEntity.getCurCarIndex();
        if (defaultCarEntity.getId().equals(carSlotEntity.getId())) {
            curCarIndex = 0;
        } else {
            List<CarSlotEntity> personasCar = personaBo.getPersonasCar(personaId);
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

}
