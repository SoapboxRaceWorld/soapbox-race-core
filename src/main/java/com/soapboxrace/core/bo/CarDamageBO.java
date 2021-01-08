/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.jpa.CarEntity;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CarDamageBO {

    @EJB
    private CarDAO carDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PerformanceBO performanceBO;

    @EJB
    private InventoryItemDAO inventoryItemDAO;

    public Integer induceCarDamage(Long personaId, ArbitrationPacket arbitrationPacket, EventEntity eventEntity) {
        if (!parameterBO.getBoolParam("ENABLE_CAR_DAMAGE")) {
            return 100;
        }

        InventoryItemEntity insurance = inventoryItemDAO.findByPersonaIdAndEntitlementTag(personaId,
                "INSURANCE_AMPLIFIER");

        if (insurance != null) {
            return 100;
        }

        Long carId = arbitrationPacket.getCarId();
        CarEntity carEntity = carDAO.find(carId);
        int durability = carEntity.getDurability();
        if (durability > 0) {
            int calcDamage = eventEntity.getEventModeId() == 19 ? 2 : 5; // 5% for non-drags, 2% for drags
            int newCarDamage = Math.max(durability - calcDamage, 0);

            updateDurability(carEntity, newCarDamage);
        }
        return carEntity.getDurability();
    }

    public void updateDurability(CarEntity carEntity, int newDurability) {
        int oldDurability = carEntity.getDurability();
        carEntity.setDurability(newDurability);
        carDAO.update(carEntity);

        if (newDurability == 0 && oldDurability != 0) {
            // recalculate excluding parts
            performanceBO.calcNewCarClass(carEntity, true);
            carDAO.update(carEntity);
        } else if (newDurability != 0 && oldDurability == 0) {
            // recalculate with parts
            performanceBO.calcNewCarClass(carEntity, false);
            carDAO.update(carEntity);
        }
    }
}
