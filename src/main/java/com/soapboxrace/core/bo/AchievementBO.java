/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.soapboxrace.core.bo.util.AchievementProgressionContext;
import com.soapboxrace.core.bo.util.AchievementUpdateInfo;
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

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Lock(LockType.READ)
public class AchievementBO {
    public static final DateTimeFormatter RANK_COMPLETED_AT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");
    @EJB
    private ItemRewardBO itemRewardBO;
    @EJB
    private DriverPersonaBO driverPersonaBO;
    @EJB
    private ScriptingBO scriptingBO;
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

    private List<AchievementEntity> achievementEntities;
    private List<BadgeDefinitionEntity> badgeDefinitionEntities;
    private Multimap<String, AchievementEntity> achievementCategoryMap;

    @PostConstruct
    public void loadData() {
        this.achievementEntities = achievementDAO.findAll();
        this.badgeDefinitionEntities = badgeDefinitionDAO.findAll();
        this.achievementCategoryMap = ArrayListMultimap.create();
        this.achievementEntities.forEach(a -> this.achievementCategoryMap.put(a.getCategory(), a));
    }

    @Schedule(minute = "*/30", hour = "*")
    public void updateRankRarities() {
        Long countPersonas = personaDAO.countPersonas();

        if (countPersonas > 0) {
            for (AchievementEntity achievementEntity : this.achievementEntities) {
                for (AchievementRankEntity achievementRankEntity : achievementEntity.getRanks()) {
                    this.achievementRankDAO.updateRankRarity(achievementRankEntity.getId(), ((float) personaAchievementRankDAO.countPersonasWithRank(achievementRankEntity.getId())) / countPersonas);
                }
            }
        }
    }

    /**
     * Creates a new achievement transaction for the given persona ID
     *
     * @param personaId the persona ID
     * @return new {@link AchievementTransaction} instance
     */
    public AchievementTransaction createTransaction(Long personaId) {
        return new AchievementTransaction(personaId);
    }

    /**
     * Synchronously commits the changes for the given {@link AchievementTransaction} instance.
     *
     * @param personaEntity the {@link PersonaEntity} instance
     * @param transaction   the {@link AchievementTransaction} instance
     */
    public void commitTransaction(PersonaEntity personaEntity, AchievementTransaction transaction) {
        List<AchievementUpdateInfo> achievementUpdateInfoList = new ArrayList<>();
        transaction.getEntries().forEach((k, v) -> v.forEach(m -> achievementUpdateInfoList.addAll(updateAchievements(personaEntity, getPersonaAchievementEntityMap(personaEntity.getPersonaId()), k, m))));
        personaDAO.update(personaEntity);
        List<BadgePacket> badgePacketList = driverPersonaBO.getBadges(personaEntity.getPersonaId()).getBadgePacket();

        sendUpdateMessage(personaEntity, achievementUpdateInfoList, badgePacketList);

        transaction.markCommitted();
        transaction.clear();
        achievementUpdateInfoList.clear();
    }

    public AchievementsPacket loadAll(Long personaId) {
        if (personaId.equals(0L)) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy, true);
        }

        AchievementsPacket achievementsPacket = new AchievementsPacket();
        achievementsPacket.setPersonaId(personaId);
        achievementsPacket.setBadges(new ArrayOfBadgeDefinitionPacket());
        achievementsPacket.setDefinitions(new ArrayOfAchievementDefinitionPacket());

        Map<Long, PersonaAchievementEntity> personaAchievementEntityMap = getPersonaAchievementEntityMap(personaId);
        for (AchievementEntity achievementEntity : /*achievementDAO.findAll()*/ achievementEntities) {
            AchievementDefinitionPacket achievementDefinitionPacket = new AchievementDefinitionPacket();

            achievementDefinitionPacket.setAchievementDefinitionId(achievementEntity.getId().intValue());
            achievementDefinitionPacket.setAchievementRanks(new ArrayOfAchievementRankPacket());
            achievementDefinitionPacket.setBadgeDefinitionId(achievementEntity.getBadgeDefinitionEntity().getId().intValue());
            achievementDefinitionPacket.setIsVisible(achievementEntity.getVisible());
            achievementDefinitionPacket.setProgressText(achievementEntity.getProgressText());
            achievementDefinitionPacket.setStatConversion(StatConversion.fromValue(achievementEntity.getStatConversion()));
            achievementDefinitionPacket.setCanProgress(true);

            PersonaAchievementEntity personaAchievementEntity = personaAchievementEntityMap.get(achievementEntity.getId());

            if (personaAchievementEntity != null) {
                achievementDefinitionPacket.setCanProgress(personaAchievementEntity.isCanProgress());
                achievementDefinitionPacket.setCurrentValue(personaAchievementEntity.getCurrentValue());
            }

            for (AchievementRankEntity achievementRankEntity : achievementEntity.getRanks()) {
                AchievementRankPacket rankPacket = new AchievementRankPacket();
                rankPacket.setAchievedOn("0001-01-01T00:00:00");
                rankPacket.setIsRare(achievementRankEntity.isRare());
                rankPacket.setRarity(achievementRankEntity.getRarity());
                rankPacket.setRank(achievementRankEntity.getRank().shortValue());
                rankPacket.setRewardDescription(achievementRankEntity.getRewardDescription());
                rankPacket.setRewardType(achievementRankEntity.getRewardType());
                rankPacket.setRewardVisualStyle(achievementRankEntity.getRewardVisualStyle());
                rankPacket.setPoints(achievementRankEntity.getPoints().shortValue());
                rankPacket.setAchievementRankId(achievementRankEntity.getId().intValue());
                if (achievementRankEntity.getRank().equals(1)) {
                    // NOTE 17.05.2020: this is apparently necessary, game ignores updates otherwise
                    rankPacket.setState(AchievementState.IN_PROGRESS);
                } else {
                    rankPacket.setState(AchievementState.LOCKED);
                }
                rankPacket.setThresholdValue(achievementRankEntity.getThresholdValue());

                if (personaAchievementEntity != null && personaAchievementEntity.getRanks().size() >= achievementRankEntity.getRank()) {
                    PersonaAchievementRankEntity personaAchievementRankEntity = personaAchievementEntity.getRanks().get(achievementRankEntity.getRank() - 1);
                    rankPacket.setState(AchievementState.fromValue(personaAchievementRankEntity.getState()));

                    if (personaAchievementRankEntity.getAchievedOn() != null) {
                        rankPacket.setAchievedOn(personaAchievementRankEntity.getAchievedOn().format(RANK_COMPLETED_AT_FORMATTER));
                    }
                }

                achievementDefinitionPacket.getAchievementRanks().getAchievementRankPacket().add(rankPacket);
            }

            achievementsPacket.getDefinitions().getAchievementDefinitionPacket().add(achievementDefinitionPacket);
        }

        for (BadgeDefinitionEntity badgeDefinitionEntity : /*badgeDefinitionDAO.findAll()*/ badgeDefinitionEntities) {
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

    public AchievementRewards redeemReward(Long personaId, Long achievementRankId) {
        PersonaEntity personaEntity = personaDAO.find(personaId);
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
        ItemRewardBO.RewardedItemsContainer rewards = itemRewardBO.getRewards(
                personaEntity,
                achievementRewardDAO.findByDescription(personaAchievementRankEntity.getAchievementRankEntity().getRewardDescription())
                        .getRewardScript());
        itemRewardBO.convertRewards(rewards, achievementRewards);
        achievementRewards.setWallets(new ArrayOfWalletTrans());

        achievementRewards.getWallets().getWalletTrans().add(new WalletTrans() {{
            setBalance(personaEntity.getCash());
            setCurrency("CASH");
        }});

        personaAchievementRankEntity.setState("Completed");
        personaAchievementRankDAO.update(personaAchievementRankEntity);

        return achievementRewards;
    }

    private void sendUpdateMessage(PersonaEntity personaEntity, List<AchievementUpdateInfo> achievementUpdateInfoList, List<BadgePacket> badgePacketList) {
        AchievementsAwarded achievementsAwarded = new AchievementsAwarded();
        achievementsAwarded.setScore(personaEntity.getScore());
        achievementsAwarded.setPersonaId(personaEntity.getPersonaId());
        achievementsAwarded.setProgressed(new ArrayList<>());
        achievementsAwarded.setAchievements(new ArrayList<>());
        achievementsAwarded.setBadges(badgePacketList);

        for (AchievementUpdateInfo achievementUpdateInfo : achievementUpdateInfoList) {
            AchievementEntity achievementEntity = achievementUpdateInfo.getAchievementEntity();
            for (AchievementUpdateInfo.CompletedAchievementRank completedAchievementRank : achievementUpdateInfo.getCompletedAchievementRanks()) {
                AchievementRankEntity achievementRankEntity = completedAchievementRank.getAchievementRankEntity();
                AchievementAwarded achievementAwarded = new AchievementAwarded();
                achievementAwarded.setAchievementDefinitionId(achievementEntity.getId());
                achievementAwarded.setAchievedOn(completedAchievementRank.getAchievedOn().format(RANK_COMPLETED_AT_FORMATTER));
                achievementAwarded.setAchievementRankId(achievementRankEntity.getId());
                achievementAwarded.setDescription(achievementEntity.getBadgeDefinitionEntity().getDescription());
                achievementAwarded.setIcon(achievementEntity.getBadgeDefinitionEntity().getIcon());
                achievementAwarded.setRare(achievementRankEntity.isRare());
                achievementAwarded.setName(achievementEntity.getBadgeDefinitionEntity().getName());
                achievementAwarded.setPoints(achievementRankEntity.getPoints());
                achievementAwarded.setRarity(achievementRankEntity.getRarity());
                achievementsAwarded.getAchievements().add(achievementAwarded);
            }

            AchievementUpdateInfo.ProgressedAchievement progressedAchievement = achievementUpdateInfo.getProgressedAchievement();

            if (progressedAchievement != null) {
                AchievementProgress achievementProgress = new AchievementProgress();
                achievementProgress.setAchievementDefinitionId(progressedAchievement.getAchievementDefinitionId());
                achievementProgress.setCurrentValue(progressedAchievement.getValue());
                achievementsAwarded.getProgressed().add(achievementProgress);
            }
        }

        XMPP_ResponseTypeAchievementsAwarded responseTypeAchievementsAwarded = new XMPP_ResponseTypeAchievementsAwarded();
        responseTypeAchievementsAwarded.setAchievementsAwarded(achievementsAwarded);

        openFireSoapBoxCli.send(responseTypeAchievementsAwarded, personaEntity.getPersonaId());
    }

    private Map<Long, PersonaAchievementEntity> getPersonaAchievementEntityMap(Long personaId) {
        return personaAchievementDAO.findAllByPersonaId(personaId)
                .stream()
                .collect(Collectors.toMap(p -> p.getAchievementEntity().getId(), p -> p));
    }

    /**
     * Update all appropriate achievements in the given category for the persona by the given ID
     *
     * @param personaEntity       The {@link PersonaEntity} instance to be updated
     * @param achievementCategory The category of achievements to evaluate
     * @param properties          Relevant contextual information for achievements.
     */
    private List<AchievementUpdateInfo> updateAchievements(PersonaEntity personaEntity, Map<Long, PersonaAchievementEntity> personaAchievementEntityMap, String achievementCategory,
                                                           Map<String, Object> properties) {
        int originalScore = personaEntity.getScore();
        int newScore = originalScore;
        properties = new HashMap<>(properties);
        List<AchievementUpdateInfo> achievementUpdateInfoList = new ArrayList<>();

        for (AchievementEntity achievementEntity : achievementCategoryMap.get(achievementCategory)) {
            if (!achievementEntity.getAutoUpdate()) continue;

            if (achievementEntity.getUpdateTrigger() == null || achievementEntity.getUpdateTrigger().trim().isEmpty()) {
                continue;
            }

            // Locate persona-specific achievement data. Create it if it does not exist
            PersonaAchievementEntity personaAchievementEntity = personaAchievementEntityMap.get(achievementEntity.getId());
            boolean insert = false;

            if (personaAchievementEntity == null) {
                personaAchievementEntity = new PersonaAchievementEntity();
                personaAchievementEntity.setCanProgress(true);
                personaAchievementEntity.setCurrentValue(0L);
                personaAchievementEntity.setAchievementEntity(achievementEntity);
                personaAchievementEntity.setPersonaEntity(personaEntity);
                personaAchievementEntityMap.put(achievementEntity.getId(), personaAchievementEntity);
                insert = true;
            }

            properties.put("personaAchievement", personaAchievementEntity);

            try {
                Boolean shouldUpdate = (Boolean) scriptingBO.eval(achievementEntity.getUpdateTrigger(),
                        properties);
                if (shouldUpdate) {
                    if (insert)
                        personaAchievementDAO.insert(personaAchievementEntity);
                    AchievementUpdateInfo achievementUpdateInfo = updateAchievement(achievementEntity, properties, personaAchievementEntity);
                    newScore += achievementUpdateInfo.getPointsGiven();
                    achievementUpdateInfoList.add(achievementUpdateInfo);
                }
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        }

        personaEntity.setScore(newScore);

        if (newScore != originalScore) {
            AchievementProgressionContext progressionContext = new AchievementProgressionContext(0, 0,
                    personaEntity.getLevel(), personaEntity.getScore(), 0, false, true,
                    false, false);

            achievementUpdateInfoList.addAll(updateAchievements(personaEntity, personaAchievementEntityMap, "PROGRESSION",
                    Map.of("persona", personaEntity, "progression", progressionContext)));
        }

        return achievementUpdateInfoList;
    }

    private AchievementUpdateInfo updateAchievement(AchievementEntity achievementEntity, Map<String, Object> bindings,
                                                    PersonaAchievementEntity personaAchievementEntity) {
        // If no progression can be made, there's nothing to do.
        if (!personaAchievementEntity.isCanProgress()) {
            return new AchievementUpdateInfo(achievementEntity);
        }

        Integer pointsAdded = 0;

        // Determine the value to add to the achievement progress.
        try {
            Long cleanVal = 0L;

            if (achievementEntity.getUpdateValue() == null || achievementEntity.getUpdateValue().trim().isEmpty()) {
                return new AchievementUpdateInfo(achievementEntity);
            }

            AchievementUpdateInfo achievementUpdateInfo = new AchievementUpdateInfo(achievementEntity);
            Object rawVal = scriptingBO.eval(achievementEntity.getUpdateValue(), bindings);

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
                if (newVal == 0L) {
                    achievementUpdateInfo.setProgressedAchievement(new AchievementUpdateInfo.ProgressedAchievement(achievementEntity.getId(), newVal));
                } else {
                    for (int i = 0; i < achievementEntity.getRanks().size(); i++) {
                        AchievementRankEntity previous = null;
                        AchievementRankEntity current = achievementEntity.getRanks().get(i);
                        PersonaAchievementRankEntity previousRank = null;
                        PersonaAchievementRankEntity currentRank = findPersonaAchievementRank(personaAchievementEntity, current);

                        if (i > 0) {
                            previous = achievementEntity.getRanks().get(i - 1);
                            previousRank = findPersonaAchievementRank(personaAchievementEntity, previous);

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
                            pointsAdded += current.getPoints();

                            achievementUpdateInfo.getCompletedAchievementRanks().add(new AchievementUpdateInfo.CompletedAchievementRank(
                                    achievementEntity, current, currentRank.getAchievedOn()));
                        } else if (previous != null && previousRank.getState().equals("InProgress")) {
                            currentRank.setState("Locked");
                            break;
                        } else if (newVal > 0 && newVal != personaAchievementEntity.getCurrentValue() && newVal < threshold) {
                            currentRank.setState("InProgress");
                            achievementUpdateInfo.setProgressedAchievement(new AchievementUpdateInfo.ProgressedAchievement(achievementEntity.getId(), newVal));
                            break;
                        }
                    }
                }

                personaAchievementEntity.setCurrentValue(newVal);
                personaAchievementEntity.setCanProgress(newVal < maxVal.getAsInt());
                personaAchievementDAO.update(personaAchievementEntity);
            }

            achievementUpdateInfo.setPointsGiven(pointsAdded);
            return achievementUpdateInfo;
        } catch (ScriptException ex) {
            throw new EngineException(ex, EngineExceptionCode.UnspecifiedError, true);
        }
    }

    private PersonaAchievementRankEntity findPersonaAchievementRank(PersonaAchievementEntity personaAchievementEntity, AchievementRankEntity achievementRankEntity) {
        List<PersonaAchievementRankEntity> personaAchievementRankEntities = personaAchievementEntity.getRanks();
        Integer rankNum = achievementRankEntity.getRank();

        if (personaAchievementRankEntities.size() >= rankNum) {
            return personaAchievementRankEntities.get(rankNum - 1);
        }

        return null;
    }

    private PersonaAchievementRankEntity createPersonaAchievementRank(PersonaAchievementEntity personaAchievementEntity, AchievementRankEntity achievementRankEntity) {
        PersonaAchievementRankEntity rankEntity = new PersonaAchievementRankEntity();
        rankEntity.setState("Locked");
        rankEntity.setPersonaAchievementEntity(personaAchievementEntity);
        rankEntity.setAchievementRankEntity(achievementRankEntity);
        personaAchievementRankDAO.insert(rankEntity);
        personaAchievementEntity.getRanks().add(rankEntity);
        return rankEntity;
    }
}
