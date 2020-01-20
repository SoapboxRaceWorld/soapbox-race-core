/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/heartbeat")
public class HeartBeat {

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public com.soapboxrace.jaxb.http.HeartBeat getPermanentSession(@HeaderParam("userId") Long userId) {
        com.soapboxrace.jaxb.http.HeartBeat heartBeat = new com.soapboxrace.jaxb.http.HeartBeat();
        heartBeat.setEnabledBitField(0);
        heartBeat.setMetagameFlags(2);
        return heartBeat;
    }
}
