/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarDAO;
import com.soapboxrace.core.jpa.CarEntity;
import org.slf4j.Logger;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

@Singleton
@Lock(LockType.READ)
public class CarSlotBO {

    @EJB
    private CarDAO carDAO;

    @EJB
    private PerformanceBO performanceBO;

    @Inject
    private Logger logger;

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void scheduledRemoval() {
        int numRemoved = carDAO.deleteAllExpired();
        if (numRemoved > 0) {
            logger.info("Removed {} expired cars", numRemoved);
        }
    }

    public List<CarEntity> getPersonasCar(Long personaId) {
        List<CarEntity> ownedCarEntities = carDAO.findByPersonaId(personaId);

        for (CarEntity carEntity : ownedCarEntities) {
            carEntity.getPaints().size();
            carEntity.getPerformanceParts().size();
            carEntity.getSkillModParts().size();
            carEntity.getVinyls().size();
            carEntity.getVisualParts().size();

            if (carEntity.getCarClassHash() == 0) {
                // CarClassHash can be set to 0 to recalculate rating/class
                performanceBO.calcNewCarClass(carEntity);
                carDAO.update(carEntity);
            }
        }

        return ownedCarEntities;
    }

    public int countPersonasCar(Long personaId) {
        return carDAO.findNumByPersonaId(personaId);
    }
}
