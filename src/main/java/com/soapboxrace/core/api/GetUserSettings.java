/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.GetServerInformationBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.bo.SceneryUtil;
import com.soapboxrace.core.bo.util.ServerInformationVO;
import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.UserSettings;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/getusersettings")
public class GetUserSettings {
    @EJB
    private GetServerInformationBO serverInformationBO;

    @EJB
    private ParameterBO parameterBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public UserSettings getUserSettingsGet() {
        ServerInformationVO serverInformation = serverInformationBO.getServerInformation();
        List<String> activatedSceneryGroups = serverInformation.getActivatedHolidaySceneryGroups();
        List<String> disactivatedSceneryGroups = serverInformation.getDisactivatedHolidaySceneryGroups();
        List<Long> sceneryIds = activatedSceneryGroups.stream()
                .map(SceneryUtil::getSceneryId)
                .collect(Collectors.toList());

        UserSettings userSettings = new UserSettings();
        userSettings.setCarCacheAgeLimit(600);
        userSettings.setIsRaceNowEnabled(true);
        userSettings.setMaxCarCacheSize(250);
        userSettings.setMinRaceNowLevel(2);
        userSettings.setVoipAvailable(false);
        ArrayOfString arrayOfString = new ArrayOfString();
        arrayOfString.getString().addAll(
                activatedSceneryGroups.stream()
                        .filter(SceneryUtil::isValid)
                        .collect(Collectors.toList()));
        userSettings.setActivatedHolidaySceneryGroups(arrayOfString);
        ArrayOfLong arrayOfLong = new ArrayOfLong();
        arrayOfLong.getLong().addAll(sceneryIds);
        userSettings.setActiveHolidayIds(arrayOfLong);
        ArrayOfString arrayOfString2 = new ArrayOfString();
        arrayOfString2.getString().addAll(
                disactivatedSceneryGroups.stream()
                        .filter(s -> SceneryUtil.isValid(s.replace("_DISABLE", "")))
                        .collect(Collectors.toList()));
        userSettings.setDisactivatedHolidaySceneryGroups(arrayOfString2);
        userSettings.setFirstTimeLogin(false);
        userSettings.setMaxLevel(parameterBO.getMaxLevel(requestSessionInfo.getUser()));
        userSettings.setStarterPackApplied(false);
        userSettings.setUserId(requestSessionInfo.getUser().getId());
        return userSettings;
    }
}
