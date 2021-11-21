/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.SessionBO;
import com.soapboxrace.jaxb.http.ChatServer;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/Session")
public class Session {

    @Context
    UriInfo uri;

    @EJB
    private SessionBO bo;

    @EJB
    private ParameterBO parameterBO;

    @GET
    @Secured
    @Path("/GetChatInfo")
    @Produces(MediaType.APPLICATION_XML)
    public ChatServer getChatInfo() {
        ChatServer chatServer = new ChatServer();
        String xmppIp = parameterBO.getStrParam("XMPP_IP");
        chatServer.setIp(xmppIp);
        chatServer.setPort(parameterBO.getIntParam("XMPP_PORT"));
        chatServer.setPrefix("sbrw");
        chatServer.setRooms(bo.getAllChatRoom());
        return chatServer;
    }
}
