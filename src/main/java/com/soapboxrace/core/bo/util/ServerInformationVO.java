package com.soapboxrace.core.bo.util;

import java.util.List;

public class ServerInformationVO {
    private String messageSrv;

    private String homePageUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String discordUrl;

    private String serverName;
    private String country;
    private Integer timezone;
    private String bannerUrl;
    private String adminList;
    private String ownerList;
    private Long numberOfRegistered;
    private Integer secondsToShutDown = 7200;
    private String allowedCountries;

    private List<String> activatedHolidaySceneryGroups;
    private List<String> disactivatedHolidaySceneryGroups;

    private Long onlineNumber;
    private boolean requireTicket = false;
    private float playerCountRewardMultiplier;

    private String webSignupUrl;
    private String webRecoveryUrl;
    private String webPanelUrl;

    private float cashRewardMultiplier;
    private float repRewardMultiplier;

    private boolean happyHourEnabled = false;
    private float happyHourMultipler = 1f;
    private String serverVersion;

    public String getMessageSrv() {
        return messageSrv;
    }

    public void setMessageSrv(String messageSrv) {
        this.messageSrv = messageSrv;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getDiscordUrl() {
        return discordUrl;
    }

    public void setDiscordUrl(String discordUrl) {
        this.discordUrl = discordUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getAdminList() {
        return adminList;
    }

    public void setAdminList(String adminList) {
        this.adminList = adminList;
    }

    public String getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(String ownerList) {
        this.ownerList = ownerList;
    }

    public Long getNumberOfRegistered() {
        return numberOfRegistered;
    }

    public void setNumberOfRegistered(Long numberOfRegistered) {
        this.numberOfRegistered = numberOfRegistered;
    }

    public Integer getSecondsToShutDown() {
        return secondsToShutDown;
    }

    public void setSecondsToShutDown(Integer secondsToShutDown) {
        this.secondsToShutDown = secondsToShutDown;
    }

    public String getAllowedCountries() {
        return allowedCountries;
    }

    public void setAllowedCountries(String allowedCountries) {
        this.allowedCountries = allowedCountries;
    }

    public List<String> getActivatedHolidaySceneryGroups() {
        return activatedHolidaySceneryGroups;
    }

    public void setActivatedHolidaySceneryGroups(List<String> activatedHolidaySceneryGroups) {
        this.activatedHolidaySceneryGroups = activatedHolidaySceneryGroups;
    }

    public List<String> getDisactivatedHolidaySceneryGroups() {
        return disactivatedHolidaySceneryGroups;
    }

    public void setDisactivatedHolidaySceneryGroups(List<String> disactivatedHolidaySceneryGroups) {
        this.disactivatedHolidaySceneryGroups = disactivatedHolidaySceneryGroups;
    }

    public Long getOnlineNumber() {
        return onlineNumber;
    }

    public void setOnlineNumber(Long onlineNumber) {
        this.onlineNumber = onlineNumber;
    }

    public boolean isRequireTicket() {
        return requireTicket;
    }

    public void setRequireTicket(boolean requireTicket) {
        this.requireTicket = requireTicket;
    }

    public String getWebSignupUrl() {
        return webSignupUrl;
    }

    public void setWebSignupUrl(String webSignupUrl) {
        this.webSignupUrl = webSignupUrl;
    }

    
    public String getWebRecoveryUrl() {
        return webRecoveryUrl;
    }

    public void setWebRecoveryUrl(String webRecoveryUrl) {
        this.webRecoveryUrl = webRecoveryUrl;
    }

    public String getWebPanelUrl() {
        return webPanelUrl;
    }

    public void setWebPanelUrl(String webPanelUrl) {
        this.webPanelUrl = webPanelUrl;
    }
    
    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

	public float getPlayerCountRewardMultiplier() {
		return playerCountRewardMultiplier;
	}

	public void setPlayerCountRewardMultiplier(float playerCountRewardMultiplier) {
		this.playerCountRewardMultiplier = playerCountRewardMultiplier;
    }

	public float getCashRewardMultiplier() {
		return cashRewardMultiplier;
	}

	public void setCashRewardMultiplier(float cashRewardMultiplier) {
		this.cashRewardMultiplier = cashRewardMultiplier;
    }

	public float getRepRewardMultiplier() {
		return repRewardMultiplier;
	}

	public void setRepRewardMultiplier(float repRewardMultiplier) {
		this.repRewardMultiplier = repRewardMultiplier;
    }
    
	public boolean getHappyHourEnabled() {
		return happyHourEnabled;
	}

	public void setHappyHourEnabled(boolean happyHourEnabled) {
		this.happyHourEnabled = happyHourEnabled;
    }

	public float getHappyHourMultipler() {
		return happyHourMultipler;
	}

	public void setHappyHourMultipler(float happyHourMultipler) {
		this.happyHourMultipler = happyHourMultipler;
	}
}
