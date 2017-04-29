package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.SocialSettings;

@Path("/getsocialsettings")
public class GetSocialSettings {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public SocialSettings getSocialSettings() {
		SocialSettings socialSettings = new SocialSettings();
		socialSettings.setAppearOffline(false);
		socialSettings.setDeclineGroupInvite(0);
		socialSettings.setDeclineIncommingFriendRequests(false);
		socialSettings.setDeclinePrivateInvite(0);
		socialSettings.setHideOfflineFriends(false);
		socialSettings.setShowNewsOnSignIn(false);
		socialSettings.setShowOnlyPlayersInSameChatChannel(false);
		return socialSettings;
	}
}
