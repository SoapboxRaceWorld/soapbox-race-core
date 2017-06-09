package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SERVER_INFO")
@NamedQueries({ //
		@NamedQuery(name = "ServerInfoEntity.findAll", query = "SELECT obj FROM ServerInfoEntity obj"), //
		@NamedQuery(name = "ServerInfoEntity.updateNumberOfRegistered", query = "UPDATE ServerInfoEntity obj SET obj.numberOfRegistered=obj.numberOfRegistered+1") //
})
public class ServerInfoEntity {

	@Id
	@Column(length = 1000)
	private String messageSrv;

	private String homePageUrl;
	private String facebookUrl;
	private String discordUrl;
	private String serverName;
	private String Country;
	private Integer timezone;
	private String bannerUrl;
	private String adminList;
	private String ownerList;
	private Integer numberOfRegistered;

	@Transient
	private Integer onlineNumber;

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
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
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

}
