package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
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
    private CarSlotDAO carSlotDAO;

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
    private OpenFireRestApiCli restApiCli;

    @EJB
    private PresenceManager presenceManager;

    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;

    @EJB
    private PersonaAchievementDAO personaAchievementDAO;

    public ProfileData createPersona(Long userId, PersonaEntity personaEntity) {
        UserEntity userEntity = userDao.findById(userId);

        if (userEntity.getListOfProfile().size() >= 3) {
            return null;
        }

        personaEntity.setUser(userEntity);
        personaEntity.setCash(parameterBO.getIntParam("STARTING_CASH_AMOUNT"));
        personaEntity.setLevel(parameterBO.getIntParam("STARTING_LEVEL_NUMBER"));
        personaEntity.setCreated(LocalDateTime.now());
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
        PersonaEntity personaEntity = personaDao.findById(personaId);
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

    private ArrayOfBadgePacket getBadges(Long personaId) {
        ArrayOfBadgePacket arrayOfBadgePacket = new ArrayOfBadgePacket();

        for (PersonaBadgeEntity personaBadgeEntity : personaBadgeDAO.findAllBadgesForPersona(personaId)) {
            PersonaAchievementRankEntity personaAchievementRankEntity = personaAchievementRankDAO.findHighestCompletedRankOfAchievementByPersona(
                    personaId, personaBadgeEntity.getBadgeDefinitionEntity().getAchievementEntity().getId());
            if (personaAchievementRankEntity != null) {
                BadgePacket badgePacket = new BadgePacket();
                badgePacket.setAchievementRankId(personaAchievementRankEntity.getAchievementRankEntity().getId().intValue());
                badgePacket.setBadgeDefinitionId(personaBadgeEntity.getBadgeDefinitionEntity().getId().intValue());
                badgePacket.setIsRare(personaAchievementRankEntity.getAchievementRankEntity().getRarity() < 0.5f);
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
            PersonaEntity personaEntity = personaDao.findById(personaId);
            if (personaEntity == null) {
                return arrayOfPersonaBase;
            }
            PersonaBase personaBase = new PersonaBase();
            ArrayOfBadgePacket arrayOfBadgePacket = getBadges(personaId);

            personaBase.setBadges(arrayOfBadgePacket);
            personaBase.setIconIndex(personaEntity.getIconIndex());
            personaBase.setLevel(personaEntity.getLevel());
            personaBase.setMotto(personaEntity.getMotto());
            personaBase.setName(personaEntity.getName());
            personaBase.setPresence(presenceManager.getPresence(personaEntity.getPersonaId()));
            personaBase.setPersonaId(personaEntity.getPersonaId());
            personaBase.setScore(personaEntity.getScore());
            personaBase.setUserId(personaEntity.getUser().getId());
            arrayOfPersonaBase.getPersonaBase().add(personaBase);
        }
        return arrayOfPersonaBase;
    }

    public void deletePersona(Long personaId) {
        PersonaEntity personaEntity = personaDao.findById(personaId);
        List<CarSlotEntity> carSlots = carSlotDAO.findByPersonaId(personaId);
        for (CarSlotEntity carSlotEntity : carSlots) {
            carSlotDAO.delete(carSlotEntity);
        }
        carSlotDAO.deleteByPersona(personaEntity);
        lobbyEntrantDAO.deleteByPersona(personaEntity);
        treasureHuntDAO.deleteByPersona(personaEntity);
        inventoryItemDAO.deleteByPersona(personaEntity);
        inventoryDAO.deleteByPersona(personaEntity);
        personaAchievementRankDAO.deleteByPersona(personaEntity);
        personaAchievementDAO.deleteByPersona(personaEntity);

        personaDao.delete(personaEntity);
    }

    public PersonaPresence getPersonaPresenceByName(String name) {
        PersonaEntity personaEntity = personaDao.findByName(name);
        if (personaEntity != null) {
            PersonaPresence personaPresence = new PersonaPresence();
            personaPresence.setPersonaId(personaEntity.getPersonaId());
            personaPresence.setPresence(presenceManager.getPresence(personaEntity.getPersonaId()));
            personaPresence.setUserId(personaEntity.getUser().getId());
            return personaPresence;
        }
        PersonaPresence personaPresence = new PersonaPresence();
        personaPresence.setPersonaId(0);
        personaPresence.setPresence(0);
        personaPresence.setUserId(0);
        return personaPresence;
    }

    public void updateStatusMessage(String message, Long personaId) {
        PersonaEntity personaEntity = personaDao.findById(personaId);
        personaEntity.setMotto(message);
        personaDao.update(personaEntity);
    }

    public void updateCash(Long personaId, Double newCash) {
        PersonaEntity personaEntity = personaDao.findById(personaId);

        if (personaEntity != null) {
            personaEntity.setCash(Math.max(0, Math.min(parameterBO.getMaxCash(personaEntity.getUser()), newCash)));
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