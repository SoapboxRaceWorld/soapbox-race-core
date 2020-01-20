/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.SocialNetworkInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getsocialnetworkinfo")
public class GetSocialNetworkInfo {

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public SocialNetworkInfo getSocialNetworkInfo() {
        SocialNetworkInfo socialNetworkInfo = new SocialNetworkInfo();
        socialNetworkInfo.setFacebookName("test");
        return socialNetworkInfo;
    }

}
