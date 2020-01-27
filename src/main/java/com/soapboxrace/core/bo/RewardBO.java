/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.*;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Set;

@Stateless
public class RewardBO {

    @EJB
    private PersonaBO personaBo;

    @EJB
    private LevelRepDAO levelRepDao;

    @EJB
    private DropBO dropBO;

    @EJB
    private InventoryBO inventoryBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private InventoryItemDAO inventoryItemDao;

    @EJB
    private ProductDAO productDAO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private ItemRewardBO itemRewardBO;

    @EJB
    private DriverPersonaBO driverPersonaBO;

    @EJB
    private AmplifierDAO amplifierDAO;

    public Float getPlayerLevelConst(int playerLevel, float levelCashRewardMultiplier) {
        return levelCashRewardMultiplier * playerLevel;
    }

    public Float getTimeConst(Long legitTime, Long routeTime) {
        float timeConst = legitTime.floatValue() / routeTime.floatValue();
        return Math.min(timeConst, 1f);
    }

    public int getBaseReward(float baseReward, float playerLevelConst, float timeConst) {
        float baseRewardResult = baseReward * playerLevelConst * timeConst;
        return (int) baseRewardResult;
    }

    public void setBaseReward(PersonaEntity personaEntity, EventEntity eventEntity,
                              ArbitrationPacket arbitrationPacket, RewardVO rewardVO) {
        float baseRep = (float) eventEntity.getBaseRepReward();
        float baseCash = (float) eventEntity.getBaseCashReward();
        Float playerLevelRepConst = getPlayerLevelConst(personaEntity.getLevel(),
                eventEntity.getLevelRepRewardMultiplier());
        Float playerLevelCashConst = getPlayerLevelConst(personaEntity.getLevel(),
                eventEntity.getLevelCashRewardMultiplier());
        Float timeConst = getTimeConst(eventEntity.getLegitTime(), arbitrationPacket.getEventDurationInMilliseconds());
        rewardVO.setBaseRep(getBaseReward(baseRep, playerLevelRepConst, timeConst));
        rewardVO.setBaseCash(getBaseReward(baseCash, playerLevelCashConst, timeConst));
    }

    public RewardVO getRewardVO(PersonaEntity personaEntity) {
        boolean enableEconomy =
                parameterBO.getBoolParam("ENABLE_ECONOMY") && personaEntity.getCash() < parameterBO.getMaxCash(personaEntity.getUser());
        boolean enableReputation =
                parameterBO.getBoolParam("ENABLE_REPUTATION") && personaEntity.getLevel() < parameterBO.getMaxLevel(personaEntity.getUser());
        return new RewardVO(enableEconomy, enableReputation);
    }

    public void applyRaceReward(Integer exp, Integer cash, PersonaEntity personaEntity) {
        applyRaceReward(exp, cash, personaEntity, true);
    }

    public void applyRaceReward(Integer exp, Integer cash, PersonaEntity personaEntity, boolean isInEvent) {
        int maxLevel = parameterBO.getMaxLevel(personaEntity.getUser());
        if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
            double newCash = personaEntity.getCash() + cash;
            driverPersonaBO.updateCash(personaEntity, newCash);
        }

        boolean hasLevelChanged = false;

        if (parameterBO.getBoolParam("ENABLE_REPUTATION") && personaEntity.getLevel() < maxLevel) {
            Long expToNextLevel = levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
            Long expMax = (long) (personaEntity.getRepAtCurrentLevel() + exp);
            if (expMax >= expToNextLevel) {
                boolean isLeveledUp = true;
                hasLevelChanged = true;
                while (isLeveledUp) {
                    personaEntity.setLevel(personaEntity.getLevel() + 1);
                    personaEntity.setRepAtCurrentLevel((int) (expMax - expToNextLevel));

                    expToNextLevel = levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
                    expMax = (long) (personaEntity.getRepAtCurrentLevel() + exp);

                    isLeveledUp = (expMax >= expToNextLevel);
                    if (personaEntity.getLevel() >= maxLevel) {
                        isLeveledUp = false;
                    }
                }
            } else {
                personaEntity.setRepAtCurrentLevel(expMax.intValue());
            }
            personaEntity.setRep(personaEntity.getRep() + exp);
        }
        personaDao.update(personaEntity);

        AchievementProgressionContext progressionContext = new AchievementProgressionContext(cash, exp,
                personaEntity.getLevel(), personaEntity.getScore(), hasLevelChanged, isInEvent);

        achievementBO.updateAchievements(personaEntity, "PROGRESSION", new HashMap<String, Object>() {{
            put("persona", personaEntity);
            put("progression", progressionContext);
        }});
    }

    public void setTopSpeedReward(EventEntity eventEntity, float topSpeed, RewardVO rewardVO) {
        float minTopSpeedTrigger = eventEntity.getMinTopSpeedTrigger();
        if (topSpeed >= minTopSpeedTrigger) {
            float baseRep = rewardVO.getBaseRep();
            float baseCash = rewardVO.getBaseCash();
            float topSpeedCashMultiplier = eventEntity.getTopSpeedCashMultiplier();
            float topSpeedRepMultiplier = eventEntity.getTopSpeedRepMultiplier();
            float highSpeedRep = baseRep * topSpeedRepMultiplier;
            float highSpeedCash = baseCash * topSpeedCashMultiplier;
            rewardVO.add((int) highSpeedRep, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
        }
    }

    public void setSkillMultiplierReward(PersonaEntity personaEntity, RewardVO rewardVO,
                                         SkillModRewardType skillModRewardType) {
        CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
        Set<SkillModPartEntity> skillModParts = defaultCarEntity.getOwnedCar().getCustomCar().getSkillModParts();
        float skillMultiplier = 0f;
        float maxSkillMultiplier = 30f;
        if (SkillModRewardType.EXPLORER.equals(skillModRewardType)) {
            maxSkillMultiplier = 50f;
        }
        for (SkillModPartEntity skillModPartEntity : skillModParts) {
            ProductEntity productEntity = productDAO.findByHash(skillModPartEntity.getSkillModPartAttribHash());
            if (productEntity != null && productEntity.getProductTitle().equals(skillModRewardType.toString())) {
                float skillValue = productEntity.getSkillValue();
                skillMultiplier += skillValue;
            }
        }
        float finalSkillMultiplier = Math.min(maxSkillMultiplier, skillMultiplier) / 100;
        float cash = rewardVO.getCash();
        float finalCash = cash * finalSkillMultiplier;
        rewardVO.add(0, (int) finalCash, EnumRewardCategory.SKILL_MOD, EnumRewardType.TOKEN_AMPLIFIER);
    }

    public Accolades getAccolades(PersonaEntity personaEntity, EventEntity eventEntity,
                                  ArbitrationPacket arbitrationPacket, RewardVO rewardVO) {
        Accolades accolades = new Accolades();
        accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
        accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
        accolades.setLuckyDrawInfo(getEventLuckyDraw(arbitrationPacket.getRank(),
                personaEntity, eventEntity));
        accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
        accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
        return accolades;
    }

    public Accolades getAccolades(PersonaEntity personaEntity, TreasureHuntEntity treasureHuntEntity,
                                  TreasureHuntConfigEntity treasureHuntConfigEntity,
                                  RewardVO rewardVO) {
        Accolades accolades = new Accolades();
        accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
        accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
        accolades.setLuckyDrawInfo(getTreasureHuntLuckyDraw(
                personaEntity, treasureHuntEntity, treasureHuntConfigEntity));
        accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
        accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
        return accolades;
    }

    public void setMultiplierReward(EventEntity eventEntity, RewardVO rewardVO) {
        float rep = rewardVO.getRep();
        float cash = rewardVO.getCash();
        float finalRepRewardMultiplier = eventEntity.getFinalRepRewardMultiplier();
        float finalCashRewardMultiplier = eventEntity.getFinalCashRewardMultiplier();
        float finalRep = rep * finalRepRewardMultiplier;
        float finalCash = cash * finalCashRewardMultiplier;
        rewardVO.add((int) finalRep, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
        rewardVO.add(0, (int) finalCash, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);
    }

    public void setPerfectStartReward(EventEntity eventEntity, int perfectStart, RewardVO rewardVO) {
        if (perfectStart == 1) {
            float baseRep = rewardVO.getBaseRep();
            float baseCash = rewardVO.getBaseCash();
            float perfectStartCashMultiplier = eventEntity.getPerfectStartCashMultiplier();
            float perfectStartRepMultiplier = eventEntity.getPerfectStartRepMultiplier();
            float perfectStartRep = baseRep * perfectStartRepMultiplier;
            float perfectStartCash = baseCash * perfectStartCashMultiplier;
            rewardVO.add((int) perfectStartRep, (int) perfectStartCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
        }
    }

    public void setAmplifierReward(PersonaEntity personaEntity, RewardVO rewardVO) {
        for (InventoryItemEntity inventoryItemEntity :
                inventoryItemDao.findAllByPersonaIdAndType(personaEntity.getPersonaId(), "AMPLIFIER")) {
            processAmplifier(inventoryItemEntity, rewardVO);
        }
    }

    public void setRankReward(EventEntity eventEntity, ArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
        float rankRepMultiplier = 0f;
        float rankCashMultiplier = 0f;
        switch (routeArbitrationPacket.getRank()) {
            case 1:
                rankRepMultiplier = eventEntity.getRank1RepMultiplier();
                rankCashMultiplier = eventEntity.getRank1CashMultiplier();
                break;
            case 2:
                rankRepMultiplier = eventEntity.getRank2RepMultiplier();
                rankCashMultiplier = eventEntity.getRank2CashMultiplier();
                break;
            case 3:
                rankRepMultiplier = eventEntity.getRank3RepMultiplier();
                rankCashMultiplier = eventEntity.getRank3CashMultiplier();
                break;
            case 4:
                rankRepMultiplier = eventEntity.getRank4RepMultiplier();
                rankCashMultiplier = eventEntity.getRank4CashMultiplier();
                break;
            case 5:
                rankRepMultiplier = eventEntity.getRank5RepMultiplier();
                rankCashMultiplier = eventEntity.getRank5CashMultiplier();
                break;
            case 6:
                rankRepMultiplier = eventEntity.getRank6RepMultiplier();
                rankCashMultiplier = eventEntity.getRank6CashMultiplier();
                break;
            case 7:
                rankRepMultiplier = eventEntity.getRank7RepMultiplier();
                rankCashMultiplier = eventEntity.getRank7CashMultiplier();
                break;
            case 8:
                rankRepMultiplier = eventEntity.getRank8RepMultiplier();
                rankCashMultiplier = eventEntity.getRank8CashMultiplier();
                break;
            default:
                break;
        }
        float baseRep = rewardVO.getBaseRep();
        float baseCash = rewardVO.getBaseCash();
        int rankRepResult = (int) (baseRep * rankRepMultiplier);
        int cashRepResult = (int) (baseCash * rankCashMultiplier);
        rewardVO.add(rankRepResult, cashRepResult, EnumRewardCategory.BONUS, EnumRewardType.NONE);
    }

    public void setPursitParamReward(float rewardValue, EnumRewardType enumRewardType, RewardVO rewardVO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PURSUIT_");
        stringBuilder.append(enumRewardType.toString());
        String rewardMultiplierStr = stringBuilder.toString().concat("_REP_MULTIPLIER");
        String cashMultiplierStr = stringBuilder.toString().concat("_CASH_MULTIPLIER");
        float rewardMultiplier = parameterBO.getFloatParam(rewardMultiplierStr);
        float cashMultiplier = parameterBO.getFloatParam(cashMultiplierStr);
        float baseRep = rewardVO.getBaseRep();
        float baseCash = rewardVO.getBaseCash();
        int repReward = (int) (baseRep * rewardValue * rewardMultiplier);
        int cashReward = (int) (baseCash * rewardValue * cashMultiplier);
        rewardVO.add(repReward, cashReward, EnumRewardCategory.PURSUIT, enumRewardType);
    }

    private void processAmplifier(InventoryItemEntity inventoryItemEntity, RewardVO rewardVO) {
        AmplifierEntity amplifierEntity =
                amplifierDAO.findAmplifierByHash(inventoryItemEntity.getProductEntity().getHash());
        switch (amplifierEntity.getAmpType()) {
            case "CASH":
                rewardVO.add(0, (int) ((rewardVO.getCash() * amplifierEntity.getCashMultiplier()) - rewardVO.getCash()),
                        EnumRewardCategory.AMPLIFIER,
                        EnumRewardType.TOKEN_AMPLIFIER);
                break;
            case "REP":
                rewardVO.add((int) ((rewardVO.getRep() * amplifierEntity.getRepMultiplier()) - rewardVO.getRep()), 0,
                        EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
                break;
            default:
                break;
        }
    }

    private LuckyDrawInfo getTreasureHuntLuckyDraw(PersonaEntity personaEntity,
                                                   TreasureHuntEntity treasureHuntEntity, TreasureHuntConfigEntity treasureHuntConfigEntity) {
        ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
        arrayOfLuckyDrawItem.getLuckyDrawItem().add(getTreasureHuntRewardItem(personaEntity, treasureHuntConfigEntity));

        ArrayOfLuckyDrawBox arrayOfLuckyDrawBox = new ArrayOfLuckyDrawBox();
        LuckyDrawBox luckyDrawBox = new LuckyDrawBox();
        luckyDrawBox.setIsValid(true);
        luckyDrawBox.setLocalizationString("LD_CARD_SILVER");
        luckyDrawBox.setLuckyDrawSetCategoryId(1);
        arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
        arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
        arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
        arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
        arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);

        LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
        luckyDrawInfo.setBoxes(arrayOfLuckyDrawBox);
        luckyDrawInfo.setCurrentStreak(treasureHuntEntity.getStreak() > 1 ? (treasureHuntEntity.getStreak() - 1) : 1);
        luckyDrawInfo.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
        luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
        luckyDrawInfo.setNumBoxAnimations(180);
        return luckyDrawInfo;
    }

    private LuckyDrawItem getEventRewardItem(PersonaEntity personaEntity, EventEntity eventEntity, Integer rank) {
        if (eventEntity == null) {
            return getRandomRewardItem(personaEntity);
        }

        RewardTableEntity rewardTableEntity;

        switch (rank) {
            case 1:
                rewardTableEntity = eventEntity.getRewardTableRank1();
                break;
            case 2:
                rewardTableEntity = eventEntity.getRewardTableRank2();
                break;
            case 3:
                rewardTableEntity = eventEntity.getRewardTableRank3();
                break;
            case 4:
                rewardTableEntity = eventEntity.getRewardTableRank4();
                break;
            case 5:
                rewardTableEntity = eventEntity.getRewardTableRank5();
                break;
            case 6:
                rewardTableEntity = eventEntity.getRewardTableRank6();
                break;
            case 7:
                rewardTableEntity = eventEntity.getRewardTableRank7();
                break;
            case 8:
                rewardTableEntity = eventEntity.getRewardTableRank8();
                break;
            default:
                throw new IllegalArgumentException("cannot handle rank " + rank);
        }

        if (rewardTableEntity == null) {
            return getRandomRewardItem(personaEntity);
        }

        return getRandomRewardItemFromTable(personaEntity, rewardTableEntity);
    }

    private Reward getFinalReward(Integer rep, Integer cash) {
        Reward finalReward = new Reward();
        finalReward.setRep(rep);
        finalReward.setTokens(cash);
        return finalReward;
    }

    private Boolean isLeveledUp(PersonaEntity personaEntity, Integer exp) {
        return (long) (personaEntity.getRepAtCurrentLevel() + exp) >= levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
    }

    private LuckyDrawInfo getEventLuckyDraw(Integer rank, PersonaEntity personaEntity,
                                            EventEntity eventEntity) {
        LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
        if (!parameterBO.getBoolParam("ENABLE_DROP_ITEM")) {
            return luckyDrawInfo;
        }
        ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
        LuckyDrawItem itemFromProduct = getEventRewardItem(personaEntity, eventEntity, rank);
        if (itemFromProduct == null) {
            return luckyDrawInfo;
        }
        arrayOfLuckyDrawItem.getLuckyDrawItem().add(itemFromProduct);
        luckyDrawInfo.setCardDeck(CardDecks.forRank(rank));
        luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
        return luckyDrawInfo;
    }

    private LuckyDrawItem getTreasureHuntRewardItem(PersonaEntity personaEntity,
                                                    TreasureHuntConfigEntity treasureHuntConfigEntity) {
        if (treasureHuntConfigEntity == null) {
            return getRandomRewardItem(personaEntity);
        }

        RewardTableEntity rewardTableEntity = treasureHuntConfigEntity.getRewardTableEntity();

        if (rewardTableEntity == null) {
            return getRandomRewardItem(personaEntity);
        }

        return getRandomRewardItemFromTable(personaEntity, rewardTableEntity);
    }

    private LuckyDrawItem getRandomRewardItem(PersonaEntity personaEntity) {
        ProductEntity productEntity = dropBO.getRandomProductItem();
        return getLuckyDrawItem(personaEntity, productEntity);
    }

    private LuckyDrawItem getRandomRewardItemFromTable(PersonaEntity personaEntity,
                                                       RewardTableEntity rewardTableEntity) {
        LuckyDrawItem luckyDrawItem = new LuckyDrawItem();
        ItemRewardBase rewardBase = itemRewardBO.getGenerator().table()
                .tableName(rewardTableEntity.getName())
                .weighted(true)
                .build();

        if (rewardBase instanceof ItemRewardProduct) {
            ItemRewardProduct rewardProduct = (ItemRewardProduct) rewardBase;
            ProductEntity productEntity = rewardProduct.getProducts().get(0);

            Integer quantity = -1;

            if (rewardBase instanceof ItemRewardQuantityProduct) {
                quantity = ((ItemRewardQuantityProduct) rewardBase).getUseCount();
            }
            luckyDrawItem = getLuckyDrawItem(personaEntity, productEntity, quantity);
        } else if (rewardBase instanceof ItemRewardCash) {
            ItemRewardCash rewardCash = (ItemRewardCash) rewardBase;
            luckyDrawItem.setWasSold(false);
            luckyDrawItem.setResellPrice(0.0f);
            luckyDrawItem.setRemainingUseCount(rewardCash.getCash());
            luckyDrawItem.setHash(-429893590);
            luckyDrawItem.setIcon("128_cash");
            luckyDrawItem.setVirtualItem("TOKEN_REWARD");
            luckyDrawItem.setVirtualItemType("REWARD");
            luckyDrawItem.setWasSold(false);
            // GM_CATALOG_00000190 is not the correct label to use. EA's server seemed to think it had formatting
            // arguments.
            // LB_CASH actually accepts a formatting argument!
            luckyDrawItem.setDescription("LB_CASH," + rewardCash.getCash());

            driverPersonaBO.updateCash(personaEntity, personaEntity.getCash() + rewardCash.getCash());
        } else {
            return null;
        }

        return luckyDrawItem;
    }

    private LuckyDrawItem getLuckyDrawItem(PersonaEntity personaEntity, ProductEntity productEntity) {
        return getLuckyDrawItem(personaEntity, productEntity, -1);
    }

    private LuckyDrawItem getLuckyDrawItem(PersonaEntity personaEntity, ProductEntity productEntity, int quantity) {
        LuckyDrawItem luckyDrawItem = dropBO.copyProduct2LuckyDraw(productEntity);
        InventoryEntity inventory = inventoryBO.getInventory(personaEntity.getPersonaId());
        boolean inventoryFull = !inventoryBO.canInventoryHold(inventory,
                productEntity);
        if (inventoryFull) {
            luckyDrawItem.setWasSold(true);
            if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
                float resalePrice = productEntity.getResalePrice();
                double cash = personaEntity.getCash();
                driverPersonaBO.updateCash(personaEntity, cash + resalePrice);
            }
        } else {
            if (productEntity.getProductType().equals("POWERUP")) {
                inventoryBO.addStackedInventoryItem(inventory, productEntity.getProductId(), quantity);
            } else {
                inventoryBO.addInventoryItem(inventory, productEntity.getProductId(), quantity);
            }
            luckyDrawItem.setRemainingUseCount(quantity == -1 ? productEntity.getUseCount() : quantity);
        }
        return luckyDrawItem;
    }
}
