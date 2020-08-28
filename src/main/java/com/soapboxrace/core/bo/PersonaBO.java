/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.BadgeBundle;
import com.soapboxrace.jaxb.http.BadgeInput;
import com.soapboxrace.jaxb.http.OwnedCarTrans;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PersonaBO {

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private CarSlotDAO carSlotDAO;

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private PersonaBadgeDAO personaBadgeDAO;

    @EJB
    private BadgeDefinitionDAO badgeDefinitionDAO;

    @EJB
    private CarDamageBO carDamageBO;

    public void updateBadges(Long personaId, BadgeBundle badgeBundle) {
        PersonaEntity personaEntity = personaDAO.find(personaId);

        for (BadgeInput badgeInput : badgeBundle.getBadgeInputs()) {
            if (badgeInput.getSlotId() > 3) {
                throw new RuntimeException("Invalid SlotId: " + badgeInput.getSlotId());
            }

            BadgeDefinitionEntity badgeDefinitionEntity =
                    badgeDefinitionDAO.find((long) badgeInput.getBadgeDefinitionId());
            if (badgeDefinitionEntity == null) {
                throw new RuntimeException("Invalid BadgeDefinitionId: " + badgeInput.getBadgeDefinitionId());
            }

            PersonaBadgeEntity personaBadgeEntity = personaBadgeDAO.findBadgeInSlotForPersona(personaId,
                    (int) badgeInput.getSlotId());
            if (personaBadgeEntity == null) {
                personaBadgeEntity = new PersonaBadgeEntity();
                personaBadgeEntity.setBadgeDefinitionEntity(badgeDefinitionEntity);
                personaBadgeEntity.setPersonaEntity(personaEntity);
                personaBadgeEntity.setSlot((int) badgeInput.getSlotId());
                personaBadgeDAO.insert(personaBadgeEntity);
            } else {
                personaBadgeEntity.setBadgeDefinitionEntity(badgeDefinitionEntity);
                personaBadgeDAO.update(personaBadgeEntity);
            }
        }
    }

    public void changeDefaultCar(Long personaId, Long defaultCarId) {
        PersonaEntity personaEntity = personaDAO.find(personaId);
        changeDefaultCar(personaEntity, defaultCarId);
        personaDAO.update(personaEntity);
    }

    public void changeDefaultCar(PersonaEntity personaEntity, Long defaultCarId) {
        List<CarSlotEntity> carSlotList = carSlotDAO.findByPersonaId(personaEntity.getPersonaId());
        int i = 0;
        for (CarSlotEntity carSlotEntity : carSlotList) {
            if (carSlotEntity.getOwnedCar().getId().equals(defaultCarId)) {
                break;
            }
            i++;
        }
        personaEntity.setCurCarIndex(i);
    }

    public PersonaEntity getPersonaById(Long personaId) {
        return personaDAO.find(personaId);
    }

    public CarSlotEntity getDefaultCarEntity(Long personaId) {
        PersonaEntity personaEntity = personaDAO.find(personaId);
        List<CarSlotEntity> carSlotList = getPersonasCar(personaId);
        int curCarIndex = personaEntity.getCurCarIndex();
        if (!carSlotList.isEmpty()) {
            if (curCarIndex >= carSlotList.size()) {
                curCarIndex = carSlotList.size() - 1;
                CarSlotEntity ownedCarEntity = carSlotList.get(curCarIndex);
                changeDefaultCar(personaEntity, ownedCarEntity.getId());
            }
            return carSlotList.get(curCarIndex);
        }
        return null;
    }

    public OwnedCarTrans getDefaultCar(Long personaId) {
        CarSlotEntity carSlotEntity = getDefaultCarEntity(personaId);
        if (carSlotEntity == null) {
            return new OwnedCarTrans();
        }
        return OwnedCarConverter.entity2Trans(carSlotEntity.getOwnedCar());
    }

    public void repairAllCars(PersonaEntity personaEntity) {
        List<CarSlotEntity> carSlotEntities = getPersonasCar(personaEntity.getPersonaId());

        for (CarSlotEntity carSlotEntity : carSlotEntities) {
            OwnedCarEntity ownedCarEntity = carSlotEntity.getOwnedCar();

            carDamageBO.updateDurability(ownedCarEntity, 100);
        }
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId) {
        return carSlotDAO.findByPersonaId(personaId);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public OwnedCarEntity getCarByOwnedCarId(Long ownedCarId) {
        OwnedCarEntity ownedCarEntity = ownedCarDAO.find(ownedCarId);
        CustomCarEntity customCar = ownedCarEntity.getCustomCar();

        // Load customcar data since we can't do it in the query
        customCar.getPaints().size();
        customCar.getPerformanceParts().size();
        customCar.getSkillModParts().size();
        customCar.getVisualParts().size();
        customCar.getVinyls().size();
        return ownedCarEntity;
    }

}
