package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.UserSettings;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getusersettings" })
public class GetUserSettings extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125697541381049590L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
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
		userSettings.setUserId(getUserId(request));

		JAXBElement<UserSettings> createUserSettings = new ObjectFactory().createUserSettings(userSettings);
		String marshal = MarshalXML.marshal(createUserSettings);
		response.getOutputStream().write(marshal.getBytes());
	}

}
