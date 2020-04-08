/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CustomCarDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CarDamageBO {

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private CustomCarDAO customCarDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PerformanceBO performanceBO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    public Integer induceCarDamage(Long personaId, ArbitrationPacket arbitrationPacket, Integer numberOfCollision) {
        if (!parameterBO.getBoolParam("ENABLE_CAR_DAMAGE")) {
            return 100;
        }

        InventoryItemEntity insurance = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId,
                "INSURANCE_AMPLIFIER");

        if (insurance != null) {
            return 100;
        }

        Long carId = arbitrationPacket.getCarId();
        long eventDuration = arbitrationPacket.getEventDurationInMilliseconds();
        OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(carId);
        int durability = ownedCarEntity.getDurability();
        if (durability > 0) {
            int calcDamage = numberOfCollision + ((int) (eventDuration / 60000)) * 2;
            int newCarDamage = Math.max(durability - calcDamage, 0);

            updateDurability(ownedCarEntity, newCarDamage);
        }
        return ownedCarEntity.getDurability();
    }

    public void updateDurability(OwnedCarEntity ownedCarEntity, int newDurability) {
        CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();
        ownedCarEntity.setDurability(newDurability);
        performanceBO.calcNewCarClass(customCarEntity, newDurability == 0);

        ownedCarDAO.update(ownedCarEntity);
        customCarDAO.update(customCarEntity);
    }
}
