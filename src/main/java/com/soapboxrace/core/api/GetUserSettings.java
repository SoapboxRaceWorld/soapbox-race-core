package com.soapboxrace.core.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.GetServerInformationBO;
import com.soapboxrace.core.bo.SceneryBO;
import com.soapboxrace.core.jpa.ServerInfoEntity;
import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.UserSettings;

@Path("/getusersettings")
public class GetUserSettings {
	@EJB
	private GetServerInformationBO serverInformationBO;

	@EJB
	private SceneryBO sceneryBO;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public UserSettings getUserSettingsGet(@HeaderParam("userId") Long userId) {
		ServerInfoEntity serverInformation = serverInformationBO.getServerInformation();
		List<String> activatedSceneryGroups = serverInformation.getActivatedHolidaySceneryGroups();
		List<String> disactivatedSceneryGroups = serverInformation.getDisactivatedHolidaySceneryGroups();
		List<Long> sceneryIds = activatedSceneryGroups.stream()
				.map(sceneryBO::getSceneryId)
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
						.filter(sceneryBO::isValid)
						.collect(Collectors.toList()));
		userSettings.setActivatedHolidaySceneryGroups(arrayOfString);
		ArrayOfLong arrayOfLong = new ArrayOfLong();
		arrayOfLong.getLong().addAll(sceneryIds);
		userSettings.setActiveHolidayIds(arrayOfLong);
		ArrayOfString arrayOfString2 = new ArrayOfString();
		arrayOfString2.getString().addAll(
				disactivatedSceneryGroups.stream()
						.filter(s -> sceneryBO.isValid(s.replace("_DISABLE", "")))
						.collect(Collectors.toList()));
		userSettings.setDisactivatedHolidaySceneryGroups(arrayOfString2);
		userSettings.setFirstTimeLogin(false);
		userSettings.setMaxLevel(60);
		userSettings.setStarterPackApplied(false);
		userSettings.setUserId(userId);
		return userSettings;
	}
}
