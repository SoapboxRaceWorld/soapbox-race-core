/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.SocialSettings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getsocialsettings")
public class GetSocialSettings {

    @GET
    @Secured
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
