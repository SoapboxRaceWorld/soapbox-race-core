/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.CustomCarDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;

import javax.ejb.*;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Lock(LockType.READ)
public class CarSlotBO {

    @EJB
    private CarSlotDAO carSlotDAO;

    @EJB
    private CustomCarDAO customCarDAO;

    @EJB
    private PerformanceBO performanceBO;

    @EJB
    private BasketBO basketBO;

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void scheduledRemoval() {
        for (CarSlotEntity carSlotEntity : carSlotDAO.findAllExpired()) {
            if (carSlotEntity.getOwnedCar().getExpirationDate().isBefore(LocalDateTime.now())) {
                basketBO.removeCar(carSlotEntity.getPersona(), carSlotEntity.getOwnedCar().getId());
            }
        }
    }

    public List<CarSlotEntity> getPersonasCar(Long personaId) {
        List<CarSlotEntity> carSlotEntities = carSlotDAO.findByPersonaId(personaId);

        for (CarSlotEntity carSlotEntity : carSlotEntities) {
            CustomCarEntity customCarEntity = carSlotEntity.getOwnedCar().getCustomCar();
            customCarEntity.getPaints().size();
            customCarEntity.getPerformanceParts().size();
            customCarEntity.getSkillModParts().size();
            customCarEntity.getVinyls().size();
            customCarEntity.getVisualParts().size();

            if (customCarEntity.getCarClassHash() == 0) {
                // CarClassHash can be set to 0 to recalculate rating/class
                performanceBO.calcNewCarClass(customCarEntity);
                customCarDAO.update(customCarEntity);
            }
        }

        return carSlotEntities;
    }

    public int countPersonasCar(Long personaId) {
        return carSlotDAO.findNumByPersonaId(personaId);
    }
}
