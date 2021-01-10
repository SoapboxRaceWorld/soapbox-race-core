/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.BadgeDefinitionDAO;
import com.soapboxrace.core.dao.CarDAO;
import com.soapboxrace.core.dao.PersonaBadgeDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.BadgeDefinitionEntity;
import com.soapboxrace.core.jpa.CarEntity;
import com.soapboxrace.core.jpa.PersonaBadgeEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
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
    private CarDAO carDAO;

    @EJB
    private PersonaBadgeDAO personaBadgeDAO;

    @EJB
    private BadgeDefinitionDAO badgeDefinitionDAO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private CarSlotBO carSlotBO;

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
        List<CarEntity> carSlotList = carDAO.findByPersonaId(personaEntity.getPersonaId());
        int i = 0;
        for (CarEntity carSlotEntity : carSlotList) {
            if (carSlotEntity.getId().equals(defaultCarId)) {
                break;
            }
            i++;
        }
        personaEntity.setCurCarIndex(i);
    }

    public PersonaEntity getPersonaById(Long personaId) {
        return personaDAO.find(personaId);
    }

    public CarEntity getDefaultCarEntity(Long personaId) {
        int carSlotCount = carSlotBO.countPersonasCar(personaId);

        if (carSlotCount > 0) {
            PersonaEntity personaEntity = personaDAO.find(personaId);
            int curCarIndex = personaEntity.getCurCarIndex();

            if (curCarIndex >= carSlotCount) {
                curCarIndex = carSlotCount - 1;
                personaEntity.setCurCarIndex(curCarIndex);
                personaDAO.update(personaEntity);
            }

            CarEntity carEntity = carDAO.findByPersonaIdEager(personaId, curCarIndex);
            carEntity.getPaints().size();
            carEntity.getPerformanceParts().size();
            carEntity.getSkillModParts().size();
            carEntity.getVinyls().size();
            carEntity.getVisualParts().size();

            return carEntity;
        }

        return null;
    }

    public OwnedCarTrans getDefaultCar(Long personaId) {
        CarEntity carEntity = getDefaultCarEntity(personaId);
        if (carEntity == null) {
            return new OwnedCarTrans();
        }

        return OwnedCarConverter.entity2Trans(carEntity);
    }

    public void repairAllCars(PersonaEntity personaEntity) {
        List<CarEntity> ownedCarEntities = carSlotBO.getPersonasCar(personaEntity.getPersonaId());

        for (CarEntity carEntity : ownedCarEntities) {
            carDamageBO.updateDurability(carEntity, 100);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public CarEntity getCarByOwnedCarId(Long ownedCarId) {
        CarEntity carEntity = carDAO.find(ownedCarId);

        // Load customcar data since we can't do it in the query
        carEntity.getPaints().size();
        carEntity.getPerformanceParts().size();
        carEntity.getSkillModParts().size();
        carEntity.getVisualParts().size();
        carEntity.getVinyls().size();
        return carEntity;
    }

}
