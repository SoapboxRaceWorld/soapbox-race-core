/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.vo.ModInfoVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ModdingBO {

    @EJB
    private ParameterBO parameterBO;

    public ModInfoVO getModInfo() {
        if (!parameterBO.getBoolParam("MODDING_ENABLED")) {
            return null;
        }

        ModInfoVO modInfoVO = new ModInfoVO();
        modInfoVO.setServerID(parameterBO.getStrParam("MODDING_SERVER_ID"));
        modInfoVO.setBasePath(parameterBO.getStrParam("MODDING_BASE_PATH"));
        modInfoVO.setFeatures(parameterBO.getStrListParam("MODDING_FEATURES"));

        return modInfoVO;
    }
}
