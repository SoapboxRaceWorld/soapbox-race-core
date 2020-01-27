/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.AchievementAwarded;
import com.soapboxrace.jaxb.xmpp.AchievementProgress;
import com.soapboxrace.jaxb.xmpp.AchievementsAwarded;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeAchievementsAwarded;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.script.Bindings;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.OptionalInt;

@Stateless
public class AchievementBO {
    private final ThreadLocal<NashornScriptEngine> scriptEngine =
            ThreadLocal.withInitial(() -> (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn"));
    @EJB
    private ItemRewardBO itemRewardBO;
    @EJB
    private PersonaAchievementDAO personaAchievementDAO;
    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;
    @EJB
    private AchievementDAO achievementDAO;
    @EJB
    private AchievementRankDAO achievementRankDAO;
    @EJB
    private AchievementRewardDAO achievementRewardDAO;
    @EJB
    private BadgeDefinitionDAO badgeDefinitionDAO;
    @EJB
    private PersonaDAO personaDAO;
    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @Schedule(minute = "*/30", hour = "*")
    public void updateRankRarities() {
        Long countPersonas = personaDAO.countPersonas();

        if (countPersonas == 0L) return;

        for (AchievementEntity achievementEntity : achievementDAO.findAll()) {
            for (AchievementRankEntity achievementRankEntity : achievementEntity.getRanks()) {
                achievementRankEntity.setRarity(((float) personaAchievementRankDAO.countPersonasWithRank(achievementRankEntity.getId())) / countPersonas);
                achievementRankDAO.update(achievementRankEntity);
            }
        }
    }

    public AchievementRewards redeemReward(Long personaId, Long achievementRankId) {
        PersonaAchievementRankEntity personaAchievementRankEntity =
                personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaId, achievementRankId);

        if (personaAchievementRankEntity == null) {
            throw new IllegalArgumentException(personaId + " does not have " + achievementRankId);
        }

        if (!"RewardWaiting".equals(personaAchievementRankEntity.getState())) {
            throw new IllegalArgumentException(personaId + " has no reward for " + achievementRankId);
        }

        AchievementRewards achievementRewards = new AchievementRewards();
        achievementRewards.setAchievementRankId(achievementRankId);
        achievementRewards.setVisualStyle(personaAchievementRankEntity.getAchievementRankEntity().getRewardVisualStyle());
        achievementRewards.setStatus(CommerceResultStatus.SUCCESS);
        achievementRewards.setCommerceItems(
                itemRewardBO.getRewards(
                        personaId,
                        achievementRewardDAO.findByDescription(personaAchievementRankEntity.getAchievementRankEntity().getRewardDescription())
                                .getRewardScript()));
        achievementRewards.setInventoryItems(new ArrayOfInventoryItemTrans());
        achievementRewards.setPurchasedCars(new ArrayOfOwnedCarTrans());
        achievementRewards.setWallets(new ArrayOfWalletTrans());

        achievementRewards.getWallets().getWalletTrans().add(new WalletTrans() {{
            setBalance(personaDAO.findById(personaId).getCash());
            setCurrency("CASH");
        }});

        personaAchievementRankEntity.setState("Completed");
        personaAchievementRankDAO.update(personaAchievementRankEntity);

        return achievementRewards;
    }

    public AchievementsPacket loadAll(Long personaId) {
        if (personaId.equals(0L)) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        AchievementsPacket achievementsPacket = new AchievementsPacket();
        achievementsPacket.setPersonaId(personaId);
        achievementsPacket.setBadges(new ArrayOfBadgeDefinitionPacket());
        achievementsPacket.setDefinitions(new ArrayOfAchievementDefinitionPacket());

        for (AchievementEntity achievementEntity : achievementDAO.findAll()) {
            AchievementDefinitionPacket achievementDefinitionPacket = new AchievementDefinitionPacket();

            achievementDefinitionPacket.setAchievementDefinitionId(achievementEntity.getId().intValue());
            achievementDefinitionPacket.setAchievementRanks(new ArrayOfAchievementRankPacket());
            achievementDefinitionPacket.setBadgeDefinitionId(achievementEntity.getBadgeDefinitionEntity().getId().intValue());
            achievementDefinitionPacket.setIsVisible(achievementEntity.getVisible());
            achievementDefinitionPacket.setProgressText(achievementEntity.getProgressText());
            achievementDefinitionPacket.setStatConversion(StatConversion.fromValue(achievementEntity.getStatConversion()));

            PersonaAchievementEntity personaAchievementEntity =
                    personaAchievementDAO.findByPersonaIdAndAchievementId(personaId, achievementEntity.getId());

            if (personaAchievementEntity != null) {
                achievementDefinitionPacket.setCanProgress(personaAchievementEntity.isCanProgress());
                achievementDefinitionPacket.setCurrentValue(personaAchievementEntity.getCurrentValue());
            } else {
                achievementDefinitionPacket.setCanProgress(true);
            }

            for (AchievementRankEntity achievementRankEntity : achievementEntity.getRanks()) {
                AchievementRankPacket rankPacket = new AchievementRankPacket();
                rankPacket.setAchievedOn("0001-01-01T00:00:00");
                rankPacket.setIsRare(false);
                rankPacket.setRarity(achievementRankEntity.getRarity());
                rankPacket.setRank(achievementRankEntity.getRank().shortValue());
                rankPacket.setRewardDescription(achievementRankEntity.getRewardDescription());
                rankPacket.setRewardType(achievementRankEntity.getRewardType());
                rankPacket.setRewardVisualStyle(achievementRankEntity.getRewardVisualStyle());
                rankPacket.setPoints(achievementRankEntity.getPoints().shortValue());
                rankPacket.setAchievementRankId(achievementRankEntity.getId().intValue());
                rankPacket.setState(AchievementState.LOCKED);
                rankPacket.setThresholdValue(achievementRankEntity.getThresholdValue());

                PersonaAchievementRankEntity personaAchievementRankEntity =
                        personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaId,
                                achievementRankEntity.getId());

                if (personaAchievementRankEntity != null) {
                    rankPacket.setState(AchievementState.fromValue(personaAchievementRankEntity.getState()));

                    if (personaAchievementRankEntity.getAchievedOn() != null) {
                        rankPacket.setAchievedOn(personaAchievementRankEntity.getAchievedOn().format(DateTimeFormatter.ofPattern("yyyyy-MM-dd'T'hh:mm:ss")));
                    }
                }

                achievementDefinitionPacket.getAchievementRanks().getAchievementRankPacket().add(rankPacket);
            }

            achievementsPacket.getDefinitions().getAchievementDefinitionPacket().add(achievementDefinitionPacket);
        }

        for (BadgeDefinitionEntity badgeDefinitionEntity : badgeDefinitionDAO.findAll()) {
            BadgeDefinitionPacket badgeDefinitionPacket = new BadgeDefinitionPacket();
            badgeDefinitionPacket.setBackground(badgeDefinitionEntity.getBackground());
            badgeDefinitionPacket.setBorder(badgeDefinitionEntity.getBorder());
            badgeDefinitionPacket.setDescription(badgeDefinitionEntity.getDescription());
            badgeDefinitionPacket.setIcon(badgeDefinitionEntity.getIcon());
            badgeDefinitionPacket.setName(badgeDefinitionEntity.getName());
            badgeDefinitionPacket.setBadgeDefinitionId(badgeDefinitionEntity.getId().intValue());
            achievementsPacket.getBadges().getBadgeDefinitionPacket().add(badgeDefinitionPacket);
        }

        return achievementsPacket;
    }

    /**
     * Update all appropriate achievements in the given category for the persona by the given ID
     *
     * @param personaEntity       The {@link PersonaEntity} instance to be updated
     * @param achievementCategory The category of achievements to evaluate
     * @param properties          Relevant contextual information for achievements.
     */
    public void updateAchievements(PersonaEntity personaEntity, String achievementCategory,
                                   Map<String, Object> properties) {
        final Bindings bindings = scriptEngine.get().createBindings();
        bindings.putAll(properties);

        AchievementsAwarded achievementsAwarded = new AchievementsAwarded();
        achievementsAwarded.setAchievements(new ArrayList<>());
        achievementsAwarded.setBadges(new ArrayList<>());
        achievementsAwarded.setProgressed(new ArrayList<>());

        for (AchievementEntity achievementEntity : achievementDAO.findAllByCategory(achievementCategory)) {
            if (!achievementEntity.getAutoUpdate()) continue;

            if (achievementEntity.getUpdateTrigger() == null || achievementEntity.getUpdateTrigger().trim().isEmpty()) {
                continue;
            }

            // Locate persona-specific achievement data. Create it if it does not exist
            PersonaAchievementEntity personaAchievementEntity = personaAchievementDAO.findByPersonaIdAndAchievementId(
                    personaEntity.getPersonaId(), achievementEntity.getId());
            boolean insert = false;

            if (personaAchievementEntity == null) {
                personaAchievementEntity = new PersonaAchievementEntity();
                personaAchievementEntity.setCanProgress(true);
                personaAchievementEntity.setCurrentValue(0L);
                personaAchievementEntity.setAchievementEntity(achievementEntity);
                personaAchievementEntity.setPersonaEntity(personaEntity);
                insert = true;
            }

            bindings.put("personaAchievement", personaAchievementEntity);

            try {
                Boolean shouldUpdate = (Boolean) scriptEngine.get().eval(achievementEntity.getUpdateTrigger(),
                        bindings);
                if (shouldUpdate) {
                    if (insert)
                        personaAchievementDAO.insert(personaAchievementEntity);
                    updateAchievement(personaEntity, achievementEntity, bindings, achievementsAwarded,
                            personaAchievementEntity);
                }
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        }


        achievementsAwarded.setPersonaId(personaEntity.getPersonaId());

        XMPP_ResponseTypeAchievementsAwarded responseTypeAchievementsAwarded =
                new XMPP_ResponseTypeAchievementsAwarded();
        responseTypeAchievementsAwarded.setAchievementsAwarded(achievementsAwarded);

        openFireSoapBoxCli.send(responseTypeAchievementsAwarded, personaEntity.getPersonaId());
    }

    private void updateAchievement(PersonaEntity personaEntity, AchievementEntity achievementEntity, Bindings bindings,
                                   AchievementsAwarded achievementsAwarded,
                                   PersonaAchievementEntity personaAchievementEntity) {
        // If no progression can be made, there's nothing to do.
        if (!personaAchievementEntity.isCanProgress()) {
            return;
        }

        Integer newScore = personaEntity.getScore();

//        bindings.put("personaAchievement", personaAchievementEntity);

        // Determine the value to add to the achievement progress.
        try {
            Long cleanVal = 0L;

            if (achievementEntity.getUpdateValue() == null || achievementEntity.getUpdateValue().trim().isEmpty()) {
                return;
            }

            Object rawVal = scriptEngine.get().eval(achievementEntity.getUpdateValue(), bindings);

            if (rawVal instanceof Integer) {
                cleanVal = ((Integer) rawVal).longValue();
            } else if (rawVal instanceof Long) {
                cleanVal = (Long) rawVal;
            } else if (rawVal instanceof Float) {
                if (Float.isNaN((Float) rawVal)) {
                    throw new RuntimeException("Float return value is NaN! Script: " + achievementEntity.getUpdateValue());
                }

                cleanVal = (long) Math.round((Float) rawVal);
            } else if (rawVal instanceof Double) {
                if (Double.isNaN((Double) rawVal)) {
                    throw new RuntimeException("Double return value is NaN! Script: " + achievementEntity.getUpdateValue());
                }

                cleanVal = Math.round((Double) rawVal);
            }


            OptionalInt maxVal = achievementEntity.getRanks().stream()
                    .mapToInt(AchievementRankEntity::getThresholdValue)
                    .max();
            if (maxVal.isPresent()) {
                long newVal = Math.max(0, Math.min(maxVal.getAsInt(),
                        achievementEntity.getShouldOverwriteProgress() ? cleanVal :
                                (personaAchievementEntity.getCurrentValue() + cleanVal)));

                for (int i = 0; i < achievementEntity.getRanks().size(); i++) {
                    AchievementRankEntity previous = null;
                    AchievementRankEntity current = achievementEntity.getRanks().get(i);
                    PersonaAchievementRankEntity previousRank = null;
                    PersonaAchievementRankEntity currentRank =
                            personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaEntity.getPersonaId(),
                                    current.getId());

                    if (i > 0) {
                        previous = achievementEntity.getRanks().get(i - 1);
                        previousRank =
                                personaAchievementRankDAO.findByPersonaIdAndAchievementRankId(personaEntity.getPersonaId(),
                                        previous.getId());

                        if (previousRank == null) {
                            previousRank = createPersonaAchievementRank(personaAchievementEntity, previous);
                        }
                    }

                    if (currentRank == null) {
                        currentRank = createPersonaAchievementRank(personaAchievementEntity, current);
                    }

                    long threshold = current.getThresholdValue();

                    if (currentRank.getState().equals("Completed") || currentRank.getState().equals("RewardWaiting"))
                        continue;

                    if (newVal >= threshold && newVal != personaAchievementEntity.getCurrentValue()) {
                        currentRank.setState("RewardWaiting");
                        currentRank.setAchievedOn(LocalDateTime.now());
                        personaAchievementRankDAO.update(currentRank);

                        AchievementAwarded achievementAwarded = new AchievementAwarded();
                        achievementAwarded.setAchievedOn(currentRank.getAchievedOn().format(DateTimeFormatter.ofPattern("yyyyy-MM-dd'T'hh:mm:ss")));
                        achievementAwarded.setAchievementDefinitionId(achievementEntity.getId());
                        achievementAwarded.setAchievementRankId(current.getId());
                        achievementAwarded.setDescription(achievementEntity.getBadgeDefinitionEntity().getDescription());
                        achievementAwarded.setIcon(achievementEntity.getBadgeDefinitionEntity().getIcon());
                        achievementAwarded.setName(achievementEntity.getBadgeDefinitionEntity().getName());
                        achievementAwarded.setRarity(current.getRarity());
                        achievementAwarded.setPoints(current.getPoints());

                        achievementsAwarded.getAchievements().add(achievementAwarded);

                        newScore += current.getPoints();
                    } else if (previous != null && previousRank.getState().equals("InProgress")) {
                        currentRank.setState("Locked");
                        personaAchievementRankDAO.update(currentRank);
                        break;
                    } else if (newVal > 0 && newVal != personaAchievementEntity.getCurrentValue() && newVal < threshold) {
                        currentRank.setState("InProgress");
                        personaAchievementRankDAO.update(currentRank);
                        AchievementProgress progress = new AchievementProgress();
                        progress.setAchievementDefinitionId(achievementEntity.getId());
                        progress.setCurrentValue(newVal);
                        achievementsAwarded.getProgressed().add(progress);
                        break;
                    }
                }

                personaAchievementEntity.setCurrentValue(newVal);
                personaAchievementEntity.setCanProgress(newVal < maxVal.getAsInt());
                personaAchievementDAO.update(personaAchievementEntity);
            }
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }

        achievementsAwarded.setScore(newScore);
        personaEntity.setScore(newScore);

        personaDAO.update(personaEntity);
    }

    private PersonaAchievementRankEntity createPersonaAchievementRank(PersonaAchievementEntity personaAchievementEntity, AchievementRankEntity achievementRankEntity) {
        PersonaAchievementRankEntity rankEntity = new PersonaAchievementRankEntity();
        rankEntity.setState("Locked");
        rankEntity.setPersonaAchievementEntity(personaAchievementEntity);
        rankEntity.setAchievementRankEntity(achievementRankEntity);
        personaAchievementRankDAO.insert(rankEntity);
        return rankEntity;
    }
}
