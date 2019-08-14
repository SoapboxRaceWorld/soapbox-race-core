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
        modInfoVO.setIndexPath(parameterBO.getStrParam("MODDING_INDEX_PATH"));

        return modInfoVO;
    }
}
