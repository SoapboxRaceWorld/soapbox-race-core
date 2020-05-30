/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.HardwareInfoDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
import com.soapboxrace.jaxb.http.HardwareInfo;
import com.soapboxrace.jaxb.util.JAXBUtility;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class HardwareInfoBO {
    @EJB
    private HardwareInfoDAO hardwareInfoDAO;

    public String calcHardwareInfoHash(String hardwareInfoXml) {
        if (hardwareInfoXml != null && !hardwareInfoXml.isEmpty()) {
            return DigestUtils.sha1Hex(hardwareInfoXml);
        }
        return "empty";
    }

    public HardwareInfoEntity save(HardwareInfo hardwareInfo) {
        long userId = hardwareInfo.getUserID();

        hardwareInfo.setAvailableMem(0);
        hardwareInfo.setCpuid10(0);
        hardwareInfo.setCpuid11(0);
        hardwareInfo.setCpuid12(0);
        hardwareInfo.setCpuid13(0);
        hardwareInfo.setUserID(0);
        String hardwareInfoXml = JAXBUtility.marshal(hardwareInfo);
        String calcHardwareInfoHash = calcHardwareInfoHash(hardwareInfoXml);
        HardwareInfoEntity hardwareInfoEntityTmp = hardwareInfoDAO.findByHardwareHash(calcHardwareInfoHash);
        if (hardwareInfoEntityTmp == null) {
            hardwareInfoEntityTmp = new HardwareInfoEntity();
            hardwareInfoEntityTmp.setUserId(userId);
            hardwareInfoEntityTmp.setHardwareInfo(hardwareInfoXml);
            hardwareInfoEntityTmp.setHardwareHash(calcHardwareInfoHash);
            hardwareInfoDAO.insert(hardwareInfoEntityTmp);
        } else {
            hardwareInfoEntityTmp.setUserId(userId);
            hardwareInfoDAO.update(hardwareInfoEntityTmp);
        }
        return hardwareInfoEntityTmp;
    }

    public boolean isHardwareHashBanned(String hardwareHash) {
        HardwareInfoEntity hardwareInfoEntity = hardwareInfoDAO.findByHardwareHash(hardwareHash);
        if (hardwareInfoEntity != null) {
            return hardwareInfoEntity.isBanned();
        }
        return false;
    }

}
