/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.OnlineUsersBO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/OnlineUsers")
public class OnlineUsers {

    @EJB
    private OnlineUsersBO onlineUsersBO;

    @GET
    @Path("/getOnline")
    @Produces(MediaType.APPLICATION_JSON)
    public int onlineUsers() {
        return onlineUsersBO.getNumberOfUsersOnlineNow();
    }

}
