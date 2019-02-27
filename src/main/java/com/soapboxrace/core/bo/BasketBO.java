package com.soapboxrace.core.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Stateless
public class BasketBO
{

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
    private CustomCarDAO customCarDAO;

    @EJB
    private TokenSessionDAO tokenDAO;

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
    private AchievementDAO achievementDAO;

    @EJB
    private AchievementsBO achievementsBO;

    @EJB
    private CarBO carBO;
    
    @EJB
    private CarClassesDAO carClassesDAO;
    
    @EJB
    private TreasureHuntDAO treasureHuntDAO;

    public ProductEntity findProduct(String productId) {
        return productDao.findByProductId(productId);
    }

    private OwnedCarTrans getCar(String productId)
    {
        BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
        if (basketDefinitonEntity == null) {
            throw new IllegalArgumentException(String.format("No basket definition for %s", productId));
        }
        String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
        return UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
    }

    public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity)
    {
        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
        int price = (int) (productDao.findByProductId(productId).getPrice() * (100 - defaultCarEntity.getOwnedCar().getDurability()));
        if (personaEntity.getCash() < price) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
            personaEntity.setCash(personaEntity.getCash() - price);
        }
        personaDao.update(personaEntity);

        defaultCarEntity.getOwnedCar().setDurability(100);

        carSlotDAO.update(defaultCarEntity);
        return CommerceResultStatus.SUCCESS;
    }

    public CommerceResultStatus buyPowerups(String productId, PersonaEntity personaEntity)
    {
        if (!parameterBO.getBoolParam("ENABLE_ECONOMY")) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        ProductEntity powerupProduct = productDao.findByProductId(productId);
        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

        if (powerupProduct == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }

        if (personaEntity.getCash() < powerupProduct.getPrice()) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }

        InventoryItemEntity item = null;

        for (InventoryItemEntity i : inventoryEntity.getInventoryItems()) {
            if (i.getHash().equals(powerupProduct.getHash())) {
                item = i;
                break;
            }
        }

        if (item == null) {
            return CommerceResultStatus.FAIL_INVALID_BASKET;
        }

        boolean upgradedAmount = false;

        int newUsageCount = item.getRemainingUseCount() + 15;

        if (newUsageCount > 250)
            newUsageCount = 250;

        if (item.getRemainingUseCount() != newUsageCount)
            upgradedAmount = true;

        item.setRemainingUseCount(newUsageCount);
        inventoryItemDao.update(item);

        if (upgradedAmount) {
            personaEntity.setCash(personaEntity.getCash() - powerupProduct.getPrice());
            personaDao.update(personaEntity);
        }

        return CommerceResultStatus.SUCCESS;
    }

    public CommerceResultStatus buyCar(String productId, PersonaEntity personaEntity, String securityToken)
    {
        if (getPersonaCarCount(personaEntity.getPersonaId()) >= parameterBO.getCarLimit(securityToken))
        {
            return CommerceResultStatus.FAIL_INSUFFICIENT_CAR_SLOTS;
        }

        ProductEntity productEntity = productDao.findByProductId(productId);
        if (productEntity == null || personaEntity.getCash() < productEntity.getPrice())
        {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }

        CarSlotEntity carSlotEntity = addCar(productId, personaEntity);

        if (parameterBO.getBoolParam("ENABLE_ECONOMY"))
        {
            personaEntity.setCash(personaEntity.getCash() - productEntity.getPrice());
        }
        personaDao.update(personaEntity);

        personaBo.changeDefaultCar(personaEntity.getPersonaId(), carSlotEntity.getOwnedCar().getId());
        return CommerceResultStatus.SUCCESS;
    }
    
    public CommerceResultStatus buyInsurance(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);
        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());
    
        if (personaEntity.getBoost() < productEntity.getPrice()) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        
        List<InventoryItemEntity> existing = inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaEntity.getPersonaId(), "INSURANCE_AMPLIFIER");
        
        if (!existing.isEmpty()) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }
        
        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setInventoryEntity(inventoryEntity);
        inventoryItemEntity.setRemainingUseCount(0);
        inventoryItemEntity.setEntitlementTag("INSURANCE_AMPLIFIER");
        inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusDays(7));
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setVirtualItemType("amplifier");
        inventoryItemEntity.setProductId("DO NOT USE ME");
        inventoryItemEntity.setHash(8483711); // binhash("INSURANCE_AMPLIFIER")

        inventoryItemDao.insert(inventoryItemEntity);
        
        personaEntity.setBoost(personaEntity.getBoost() - productEntity.getPrice());
        personaDao.update(personaEntity);
        
        inventoryDao.findByPersonaId(personaEntity.getPersonaId()).getInventoryItems();
        
        return CommerceResultStatus.SUCCESS;
    }

    public CommerceResultStatus buyCashAmplifier(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);
        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

        if (personaEntity.getBoost() < productEntity.getPrice()) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }

        List<InventoryItemEntity> existing = inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaEntity.getPersonaId(), "CASH_AMPLIFIER_2X");

        if (!existing.isEmpty()) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }

        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setInventoryEntity(inventoryEntity);
        inventoryItemEntity.setRemainingUseCount(0);
        inventoryItemEntity.setEntitlementTag("CASH_AMPLIFIER_2X");
        inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusDays(10));
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setVirtualItemType("amplifier");
        inventoryItemEntity.setProductId("DO NOT USE ME");
        inventoryItemEntity.setHash(525953935);

        inventoryItemDao.insert(inventoryItemEntity);

        personaEntity.setBoost(personaEntity.getBoost() - productEntity.getPrice());
        personaDao.update(personaEntity);

        return CommerceResultStatus.SUCCESS;
    }

    public CommerceResultStatus buyRepAmplifier(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);
        InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

        if (personaEntity.getBoost() < productEntity.getPrice()) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }

        List<InventoryItemEntity> existing = inventoryItemDao.findAllByPersonaIdAndEntitlementTag(personaEntity.getPersonaId(), "REP_AMPLIFIER_2X");

        if (!existing.isEmpty()) {
            return CommerceResultStatus.FAIL_MAX_ALLOWED_PURCHASES_FOR_THIS_PRODUCT;
        }

        InventoryItemEntity inventoryItemEntity = new InventoryItemEntity();
        inventoryItemEntity.setInventoryEntity(inventoryEntity);
        inventoryItemEntity.setRemainingUseCount(0);
        inventoryItemEntity.setEntitlementTag("REP_AMPLIFIER_2X");
        inventoryItemEntity.setExpirationDate(LocalDateTime.now().plusDays(10));
        inventoryItemEntity.setStatus("ACTIVE");
        inventoryItemEntity.setVirtualItemType("amplifier");
        inventoryItemEntity.setProductId("DO NOT USE ME");
        inventoryItemEntity.setHash(835624850);

        inventoryItemDao.insert(inventoryItemEntity);

        personaEntity.setBoost(personaEntity.getBoost() - productEntity.getPrice());
        personaDao.update(personaEntity);

        return CommerceResultStatus.SUCCESS;
    }
    
    public CommerceResultStatus reviveTreasureHunt(String productId, PersonaEntity personaEntity) {
        ProductEntity productEntity = productDao.findByProductId(productId);
        
        if (personaEntity.getBoost() < productEntity.getPrice()) {
            return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
        }
        
        TreasureHuntEntity treasureHuntEntity = treasureHuntDAO.findById(personaEntity.getPersonaId());
        treasureHuntEntity.setIsStreakBroken(false);
        treasureHuntEntity.setStreak(treasureHuntEntity.getStreak() + 1);
        treasureHuntEntity.setThDate(LocalDate.now());

        treasureHuntDAO.update(treasureHuntEntity);
        
        personaEntity.setBoost(personaEntity.getBoost() - productEntity.getPrice());
        personaDao.update(personaEntity);
        
        return CommerceResultStatus.SUCCESS;
    }
    
    public CarSlotEntity addCar(String productId, PersonaEntity personaEntity)
    {
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

        carSlotDAO.insert(carSlotEntity);

        if (productEntity.getLongDescription() != null && !productEntity.getLongDescription().isEmpty()) {
            String longDesc = productEntity.getLongDescription();
            String carId = longDesc.split("_")[1];
            String brand = carBO.getBrand(carId);

            AchievementDefinitionEntity achievement = achievementDAO.findByName("achievement_ACH_OWN_" + brand);

            if (achievement != null) {
                achievementsBO.update(personaEntity, achievement, 1L);
            } else {
                System.out.println("Could not find achievement for brand " + brand + " (" + longDesc + ")");
            }
        }

        return carSlotEntity;
    }
    
    public int getPersonaCarCount(Long personaId)
    {
        return getPersonasCar(personaId).size();
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId)
    {
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

    public boolean sellCar(String securityToken, Long personaId, Long serialNumber)
    {
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

        final int maxCash = parameterBO.getMaxCash(securityToken);
        if (personaEntity.getCash() < maxCash) {
            int cashTotal = (int) (personaEntity.getCash() + ownedCarEntity.getCustomCar().getResalePrice());
            if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                personaEntity.setCash(Math.max(0, Math.min(maxCash, cashTotal)));
            }
        }

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
