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
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Stateless
public class DriverPersonaBO {

    @EJB
    private UserDAO userDao;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LobbyEntrantDAO lobbyEntrantDAO;

    @EJB
    private LevelRepDAO levelRepDAO;

    @EJB
    private CarDAO carDAO;

    @EJB
    private TreasureHuntDAO treasureHuntDAO;

    @EJB
    private InventoryDAO inventoryDAO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    @EJB
    private PersonaBadgeDAO personaBadgeDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private InventoryBO inventoryBO;

    @EJB
    private PresenceBO presenceBO;

    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;

    @EJB
    private PersonaAchievementDAO personaAchievementDAO;

    @EJB
    private SocialRelationshipDAO socialRelationshipDAO;

    public ProfileData createPersona(Long userId, PersonaEntity personaEntity) {
        UserEntity userEntity = userDao.find(userId);

        if (userEntity.getPersonas().size() >= 3) {
            throw new EngineException(EngineExceptionCode.MaximumNumberOfPersonasForUserReached, false);
        }

        if (personaEntity.getIconIndex() < 0
                || personaEntity.getIconIndex() > parameterBO.getIntParam("MAX_ICON_INDEX", 26)) {
            throw new EngineException(EngineExceptionCode.InvalidOperation, false);
        }

        personaEntity.setUser(userEntity);
        personaEntity.setCash(parameterBO.getIntParam("STARTING_CASH_AMOUNT"));
        personaEntity.setLevel(parameterBO.getIntParam("STARTING_LEVEL_NUMBER"));
        personaEntity.setCreated(LocalDateTime.now());
        personaEntity.setFirstLogin(personaEntity.getCreated());
        personaEntity.setLastLogin(personaEntity.getCreated());
        personaDao.insert(personaEntity);

        inventoryBO.createInventory(personaEntity);
        createThInformation(personaEntity);

        return castPersonaEntity(personaEntity);
    }

    private ProfileData castPersonaEntity(PersonaEntity personaEntity) {
        ProfileData profileData = new ProfileData();
        // switch to apache beanutils copy
        profileData.setName(personaEntity.getName());
        profileData.setCash(personaEntity.getCash());
        profileData.setBoost(personaEntity.getBoost());
        profileData.setIconIndex(personaEntity.getIconIndex());
        profileData.setPersonaId(personaEntity.getPersonaId());
        profileData.setLevel(personaEntity.getLevel());
        return profileData;
    }

    public ArrayOfInt getExpLevelPointsMap() {
        ArrayOfInt arrayOfInt = new ArrayOfInt();
        int rep = 0;

        for (LevelRepEntity levelRepEntity : levelRepDAO.findAll()) {
            rep += levelRepEntity.getExpPoint().intValue();
            arrayOfInt.getInt().add(rep);
        }

        return arrayOfInt;
    }

    public ProfileData getPersonaInfo(Long personaId) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        ProfileData profileData = castPersonaEntity(personaEntity);

        ArrayOfBadgePacket arrayOfBadgePacket = this.getBadges(personaId);

        profileData.setBadges(arrayOfBadgePacket);
        profileData.setMotto(personaEntity.getMotto());
        profileData.setPercentToLevel(personaEntity.getPercentToLevel());
        profileData.setRating(personaEntity.getRating());
        profileData.setRep(personaEntity.getRep());
        profileData.setRepAtCurrentLevel(personaEntity.getRepAtCurrentLevel());
        profileData.setScore(personaEntity.getScore());
        return profileData;
    }

    public ArrayOfBadgePacket getBadges(Long personaId) {
        ArrayOfBadgePacket arrayOfBadgePacket = new ArrayOfBadgePacket();

        for (PersonaBadgeEntity personaBadgeEntity : personaBadgeDAO.findAllBadgesForPersona(personaId)) {
            PersonaAchievementRankEntity personaAchievementRankEntity =
                    personaAchievementRankDAO.findHighestCompletedRankOfAchievementByPersona(
                            personaId, personaBadgeEntity.getBadgeDefinitionEntity().getAchievementEntity().getId());
            if (personaAchievementRankEntity != null) {
                BadgePacket badgePacket = new BadgePacket();
                badgePacket.setAchievementRankId(personaAchievementRankEntity.getAchievementRankEntity().getId().intValue());
                badgePacket.setBadgeDefinitionId(personaBadgeEntity.getBadgeDefinitionEntity().getId().intValue());
                badgePacket.setIsRare(personaAchievementRankEntity.getAchievementRankEntity().isRare());
                badgePacket.setRarity(personaAchievementRankEntity.getAchievementRankEntity().getRarity());
                badgePacket.setSlotId(personaBadgeEntity.getSlot().shortValue());
                arrayOfBadgePacket.getBadgePacket().add(badgePacket);
            }
        }

        return arrayOfBadgePacket;
    }

    public ArrayOfPersonaBase getPersonaBaseFromList(List<Long> personaIdList) {
        ArrayOfPersonaBase arrayOfPersonaBase = new ArrayOfPersonaBase();
        for (Long personaId : personaIdList) {
            PersonaEntity personaEntity = personaDao.find(personaId);
            if (personaEntity == null) {
                return arrayOfPersonaBase;
            }
            arrayOfPersonaBase.getPersonaBase().add(getPersonaBase(personaEntity));
        }
        return arrayOfPersonaBase;
    }

    public PersonaBase getPersonaBase(PersonaEntity personaEntity) {
        PersonaBase personaBase = new PersonaBase();
        ArrayOfBadgePacket arrayOfBadgePacket = getBadges(personaEntity.getPersonaId());

        personaBase.setBadges(arrayOfBadgePacket);
        personaBase.setIconIndex(personaEntity.getIconIndex());
        personaBase.setLevel(personaEntity.getLevel());
        personaBase.setMotto(personaEntity.getMotto());
        personaBase.setName(personaEntity.getName());
        personaBase.setPresence(presenceBO.getPresence(personaEntity.getPersonaId()));
        personaBase.setPersonaId(personaEntity.getPersonaId());
        personaBase.setScore(personaEntity.getScore());
        personaBase.setUserId(personaEntity.getUser().getId());
        return personaBase;
    }

    public void deletePersona(Long personaId) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        UserEntity user = personaEntity.getUser();
        personaDao.delete(personaEntity);
        user.setSelectedPersonaIndex(Math.max(0, user.getSelectedPersonaIndex() - 1));
        userDao.update(user);
    }

    public PersonaPresence getPersonaPresenceByName(String name) {
        PersonaEntity personaEntity = personaDao.findByName(name);
        if (personaEntity != null) {
            PersonaPresence personaPresence = new PersonaPresence();
            personaPresence.setPersonaId(personaEntity.getPersonaId());
            personaPresence.setPresence(presenceBO.getPresence(personaEntity.getPersonaId()));
            personaPresence.setUserId(personaEntity.getUser().getId());
            return personaPresence;
        }
        throw new EngineException(EngineExceptionCode.PersonaNotFound, false);
    }

    public void updateStatusMessage(String message, Long personaId) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        personaEntity.setMotto(message);
        personaDao.update(personaEntity);
    }

    public void updateCash(PersonaEntity personaEntity, Double newCash) {
        if (personaEntity != null) {
            double fixedCash = Math.max(0, Math.min(parameterBO.getMaxCash(personaEntity.getUser()), newCash));
            personaEntity.setCash(fixedCash);
            personaDao.update(personaEntity);
        }
    }

    public ArrayOfString reserveName(String name) {
        ArrayOfString arrayOfString = new ArrayOfString();
        if (personaDao.findByName(name) != null) {
            arrayOfString.getString().add("NONE");
        }
        return arrayOfString;
    }

    public void createThInformation(PersonaEntity personaEntity) {
        TreasureHuntEntity treasureHuntEntity = new TreasureHuntEntity();
        treasureHuntEntity.setCoinsCollected(0);
        treasureHuntEntity.setIsStreakBroken(false);
        treasureHuntEntity.setNumCoins(parameterBO.getIntParam("TREASURE_HUNT_COINS", 15));
        treasureHuntEntity.setPersonaId(personaEntity.getPersonaId());
        treasureHuntEntity.setSeed(new Random().nextInt());
        treasureHuntEntity.setStreak(1);
        treasureHuntEntity.setThDate(LocalDate.now());
        treasureHuntDAO.insert(treasureHuntEntity);
    }

}
