package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.SocialSettings;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getsocialsettings" })
public class GetSocialSettings extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9135516689501603029L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		SocialSettings socialSettings = new SocialSettings();
		socialSettings.setAppearOffline(false);
		socialSettings.setDeclineGroupInvite(0);
		socialSettings.setDeclineIncommingFriendRequests(false);
		socialSettings.setDeclinePrivateInvite(0);
		socialSettings.setHideOfflineFriends(false);
		socialSettings.setShowNewsOnSignIn(false);
		socialSettings.setShowOnlyPlayersInSameChatChannel(false);
		JAXBElement<SocialSettings> createSocialSettings = new ObjectFactory().createSocialSettings(socialSettings);
		String marshal = MarshalXML.marshal(createSocialSettings);
		answer(request, response, marshal);
	}
}
