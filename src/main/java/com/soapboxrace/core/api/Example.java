package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.PersonaFriendsList;
import com.soapboxrace.jaxb.http.UserSettings;

@Path("/getusersettings")
public class Example {

	@GET
	@Path("/some/{user}/path")
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	@XsiSchemaLocation(schemaLocation = "http://schemas.datacontract.org/2004/07/Victory.TransferObjects.DriverPersona")
	public PersonaFriendsList uga(@PathParam("user") Long userId) {
		return new PersonaFriendsList();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public UserSettings getUserSettingsGet() {
		UserSettings userSettings = new UserSettings();
		userSettings.setCarCacheAgeLimit(600);
		userSettings.setIsRaceNowEnabled(true);
		userSettings.setMaxCarCacheSize(250);
		userSettings.setMinRaceNowLevel(2);
		userSettings.setVoipAvailable(false);
		ArrayOfString arrayOfString = new ArrayOfString();
		arrayOfString.getString().add("SCENERY_GROUP_NORMAL");
		arrayOfString.getString().add("PLACEHOLDER");
		userSettings.setActivatedHolidaySceneryGroups(arrayOfString);
		ArrayOfLong arrayOfLong = new ArrayOfLong();
		arrayOfLong.getLong().add(0L);
		userSettings.setActiveHolidayIds(arrayOfLong);
		ArrayOfString arrayOfString2 = new ArrayOfString();
		arrayOfString2.getString().add("SCENERY_GROUP_NORMAL_DISABLE");
		arrayOfString2.getString().add("PLACEHOLDER");
		userSettings.setDisactivatedHolidaySceneryGroups(arrayOfString2);
		userSettings.setFirstTimeLogin(false);
		userSettings.setMaxLevel(60);
		userSettings.setStarterPackApplied(false);
		userSettings.setUserId(10L);
		return userSettings;
	}

}
