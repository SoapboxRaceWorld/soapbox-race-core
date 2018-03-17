package com.soapboxrace.core.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.soapboxrace.core.jpa.convert.SceneryGroupConverter;

@Entity
@Table(name = "SERVER_INFO")
@NamedQueries({ //
		@NamedQuery(name = "ServerInfoEntity.findAll", query = "SELECT obj FROM ServerInfoEntity obj"), //
		@NamedQuery(name = "ServerInfoEntity.updateNumberOfRegistered", query = "UPDATE ServerInfoEntity obj SET obj.numberOfRegistered=obj.numberOfRegistered+1") //
})
public class ServerInfoEntity {
	@Column(length = 1000)
	private String messageSrv;

	private String homePageUrl;
	private String facebookUrl;
	private String discordUrl;
	@Id
	private String serverName;
	private String country;
	private Integer timezone;
	private String bannerUrl;
	private String adminList;
	private String ownerList;
	private Integer numberOfRegistered;

	@Convert(converter = SceneryGroupConverter.class)
	private List<String> activatedHolidaySceneryGroups;

	@Convert(converter = SceneryGroupConverter.class)
	private List<String> disactivatedHolidaySceneryGroups;

	@Transient
	private Integer onlineNumber;
	@Transient
	private boolean requireTicket = false;
	@Transient
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

	public Integer getNumberOfRegistered() {
		return numberOfRegistered;
	}

	public void setNumberOfRegistered(Integer numberOfRegistered) {
		this.numberOfRegistered = numberOfRegistered;
	}

	public Integer getOnlineNumber() {
		return onlineNumber;
	}

	public void setOnlineNumber(Integer onlineNumber) {
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

	public void setActivatedHolidaySceneryGroups(List<String> activatedHolidaySceneryGroups) {
		this.activatedHolidaySceneryGroups = activatedHolidaySceneryGroups;
	}

	public List<String> getActivatedHolidaySceneryGroups() {
		return this.activatedHolidaySceneryGroups;
	}

	public void setDisactivatedHolidaySceneryGroups(List<String> disactivatedHolidaySceneryGroups) {
		this.disactivatedHolidaySceneryGroups = disactivatedHolidaySceneryGroups;
	}

	public List<String> getDisactivatedHolidaySceneryGroups() {
		return this.disactivatedHolidaySceneryGroups;
	}
}
