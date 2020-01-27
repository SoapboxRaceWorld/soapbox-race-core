/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarClassesDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.CarClassesEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.PerformancePartEntity;
import com.soapboxrace.core.jpa.ProductEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Set;

@Stateless
public class PerformanceBO {

    @EJB
    private CarClassesDAO carClassesDAO;

    @EJB
    private ProductDAO productDAO;

    public void calcNewCarClass(CustomCarEntity customCarEntity) {
        int physicsProfileHash = customCarEntity.getPhysicsProfileHash();
        CarClassesEntity carClassesEntity = carClassesDAO.findByHash(physicsProfileHash);
        if (carClassesEntity == null) {
            return;
        }
        Set<PerformancePartEntity> performanceParts = customCarEntity.getPerformanceParts();
        int topSpeed = 0;
        int accel = 0;
        int handling = 0;
        for (PerformancePartEntity performancePartEntity : performanceParts) {
            int perfHash = performancePartEntity.getPerformancePartAttribHash();
            ProductEntity productEntity = productDAO.findByHash(perfHash);
            topSpeed = productEntity.getTopSpeed() + topSpeed;
            accel = productEntity.getAccel() + accel;
            handling = productEntity.getHandling() + handling;
        }
        float tt = (float) (topSpeed * 0.01);
        float ta = (float) (accel * 0.01);
        float th = (float) (handling * 0.01);
        float totalChanges = 1 / (((tt + ta + th) * 0.666666666666666f) + 1f);
        tt = tt * totalChanges;
        ta = ta * totalChanges;
        th = th * totalChanges;
        float finalConstant = 1 - tt - ta - th;

        Float finalTopSpeed1 = carClassesEntity.getTsVar1().floatValue() * th;
        Float finalTopSpeed2 = carClassesEntity.getTsVar2().floatValue() * ta;
        Float finalTopSpeed3 = carClassesEntity.getTsVar3().floatValue() * tt;
        Float finalTopSpeed =
                (finalConstant * carClassesEntity.getTsStock().floatValue()) + finalTopSpeed1 + finalTopSpeed2
                        + finalTopSpeed3;

        Float finalAccel1 = carClassesEntity.getAcVar1().floatValue() * th;
        Float finalAccel2 = carClassesEntity.getAcVar2().floatValue() * ta;
        Float finalAccel3 = carClassesEntity.getAcVar3().floatValue() * tt;
        Float finalAccel = (finalConstant * carClassesEntity.getAcStock().floatValue()) + finalAccel1 + finalAccel2
                + finalAccel3;

        Float finalHandling1 = carClassesEntity.getHaVar1().floatValue() * th;
        Float finalHandling2 = carClassesEntity.getHaVar2().floatValue() * ta;
        Float finalHandling3 = carClassesEntity.getHaVar3().floatValue() * tt;
        Float finalHandling =
                (finalConstant * carClassesEntity.getHaStock().floatValue()) + finalHandling1 + finalHandling2
                        + finalHandling3;

        Float finalClass = (finalTopSpeed.intValue() + finalAccel.intValue() + finalHandling.intValue()) / 3f;
        int finalClassInt = finalClass.intValue();

        // move to new method
        int carclassHash = 872416321;
        if (finalClassInt >= 250 && finalClassInt < 400) {
            carclassHash = 415909161;
        } else if (finalClassInt >= 400 && finalClassInt < 500) {
            carclassHash = 1866825865;
        } else if (finalClassInt >= 500 && finalClassInt < 600) {
            carclassHash = -406473455;
        } else if (finalClassInt >= 600 && finalClassInt < 750) {
            carclassHash = -405837480;
        } else if (finalClassInt >= 750) {
            carclassHash = -2142411446;
        }

        customCarEntity.setCarClassHash(carclassHash);
    }
}
