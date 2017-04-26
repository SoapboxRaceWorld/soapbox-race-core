package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.interfaces.IGetUserSettingsBO;
import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.UserSettings;
import com.soapboxrace.jaxb.util.MarshalXML;

public class GetUserSettingsBO implements IGetUserSettingsBO {

	@Override
	public String getUserSettings(Long userId, String token) {
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
		userSettings.setUserId(userId);

		return MarshalXML.marshal(new ObjectFactory().createUserSettings(userSettings));
	}

}
