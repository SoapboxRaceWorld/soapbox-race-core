/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.BuildInfo;
import com.soapboxrace.core.bo.util.ServerInformationVO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GetServerInformationBO {

    @EJB
    private OnlineUsersBO onlineUsersBO;

    @EJB
    private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

    public ServerInformationVO getServerInformation() {
        OnlineUsersEntity onlineUsersEntity = onlineUsersBO.getOnlineUsersStats();

        ServerInformationVO serverInformationVO = new ServerInformationVO();
        serverInformationVO.setMessageSrv(parameterBO.getStrParam("SERVER_INFO_MESSAGE"));
        serverInformationVO.setHomePageUrl(parameterBO.getStrParam("SERVER_INFO_HOMEPAGE_URL"));
        serverInformationVO.setFacebookUrl(parameterBO.getStrParam("SERVER_INFO_FACEBOOK_URL"));
        serverInformationVO.setTwitterUrl(parameterBO.getStrParam("SERVER_INFO_TWITTER_URL"));
        serverInformationVO.setDiscordUrl(parameterBO.getStrParam("SERVER_INFO_DISCORD_URL"));
        serverInformationVO.setServerName(parameterBO.getStrParam("SERVER_INFO_NAME"));
        serverInformationVO.setCountry(parameterBO.getStrParam("SERVER_INFO_COUNTRY"));
        serverInformationVO.setTimezone(parameterBO.getIntParam("SERVER_INFO_TIMEZONE"));
        serverInformationVO.setBannerUrl(parameterBO.getStrParam("SERVER_INFO_BANNER_URL"));
        serverInformationVO.setAdminList(parameterBO.getStrParam("SERVER_INFO_ADMINS"));
        serverInformationVO.setOwnerList(parameterBO.getStrParam("SERVER_INFO_OWNERS"));
        serverInformationVO.setSecondsToShutDown(parameterBO.getIntParam("SERVER_INFO_SHUTDOWN_TIMER", 7200));
        serverInformationVO.setAllowedCountries(parameterBO.getStrParam("SERVER_INFO_ALLOWED_COUNTRIES"));
        serverInformationVO.setActivatedHolidaySceneryGroups(parameterBO.getStrListParam("SERVER_INFO_ENABLED_SCENERY"));
        serverInformationVO.setDisactivatedHolidaySceneryGroups(parameterBO.getStrListParam("SERVER_INFO_DISABLED_SCENERY"));
        serverInformationVO.setRequireTicket(parameterBO.getStrParam("TICKET_TOKEN") != null);
        serverInformationVO.setPlayerCountRewardMultiplier(rewardBO.getPlayerCountConst());
        serverInformationVO.setHappyHourEnabled(parameterBO.getBoolParam("happyHourEnabled"));
        serverInformationVO.setHappyHourMultipler(parameterBO.getFloatParam("happyHourMultipler"));
        serverInformationVO.setWebSignupUrl(parameterBO.getStrParam("SERVER_INFO_SIGNUPURL"));
        serverInformationVO.setWebRecoveryUrl(parameterBO.getStrParam("SERVER_INFO_RESETURL"));
        serverInformationVO.setWebPanelUrl(parameterBO.getStrParam("SERVER_INFO_WEBPANEL"));
        serverInformationVO.setCashRewardMultiplier(parameterBO.getFloatParam("CASH_REWARD_MULTIPLIER"));
        serverInformationVO.setRepRewardMultiplier(parameterBO.getFloatParam("REP_REWARD_MULTIPLIER"));
        serverInformationVO.setDiscordApplicationID(parameterBO.getStrParam("SERVER_INFO_DISCORDAPPLICATIONID"));
        serverInformationVO.setServerVersion(BuildInfo.getVersion() + " - " + BuildInfo.getCommitID());
        serverInformationVO.setOnlineNumber(onlineUsersEntity.getNumberOfOnline());
        serverInformationVO.setNumberOfRegistered(onlineUsersEntity.getNumberOfRegistered());
        serverInformationVO.setModernAuthSupport(parameterBO.getBoolParam("MODERN_AUTH_ENABLED"));

        return serverInformationVO;
    }
}
