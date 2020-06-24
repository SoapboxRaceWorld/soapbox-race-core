package com.soapboxrace.core.bo.util;

import java.util.List;

public class ServerInformationVO {
    private String messageSrv;

    private String homePageUrl;
    private String facebookUrl;
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

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }
}
