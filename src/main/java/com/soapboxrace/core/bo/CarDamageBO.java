/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CarDamageBO {

    @EJB
    private CarSlotDAO carSlotDao;

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    public Integer updateDamageCar(Long personaId, ArbitrationPacket arbitrationPacket, Integer numberOfCollision) {
        if (!parameterBO.getBoolParam("ENABLE_CAR_DAMAGE")) {
            return 100;
        }

        InventoryItemEntity insurance = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId,
                "INSURANCE_AMPLIFIER");

        if (insurance != null) {
            return 100;
        }

        Long carId = arbitrationPacket.getCarId();
        Long eventDuration = arbitrationPacket.getEventDurationInMilliseconds();
        OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(carId);
        CarSlotEntity carSlotEntity = ownedCarEntity.getCarSlot();
        int durability = ownedCarEntity.getDurability();
        if (durability > 10) {
            Integer calcDamage = numberOfCollision + ((int) (eventDuration / 60000)) * 2;
            Integer newCarDamage = durability - calcDamage;
            ownedCarEntity.setDurability(newCarDamage < 10 ? 10 : newCarDamage);
            if (carSlotEntity != null) {
                carSlotDao.update(carSlotEntity);
            }
        }
        return ownedCarEntity.getDurability();
    }
}
