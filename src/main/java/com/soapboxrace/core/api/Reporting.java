/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.HardwareInfoBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.HardwareInfo;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/Reporting")
public class Reporting {

    @EJB
    private HardwareInfoBO hardwareInfoBO;

    @EJB
    private TokenSessionBO tokenBO;

    @EJB
    private UserDAO userDAO;

    @POST
    @Secured
    @Path("/SendHardwareInfo")
    @Produces(MediaType.APPLICATION_XML)
    public String sendHardwareInfo(InputStream is, @HeaderParam("securityToken") String securityToken) {
        HardwareInfo hardwareInfo = UnmarshalXML.unMarshal(is, HardwareInfo.class);
        HardwareInfoEntity hardwareInfoEntity = hardwareInfoBO.save(hardwareInfo);
        UserEntity user = tokenBO.getUser(securityToken);
        user.setGameHardwareHash(hardwareInfoEntity.getHardwareHash());
        userDAO.update(user);
        return "";
    }

    @POST
    @Secured
    @Path("/SendUserSettings")
    @Produces(MediaType.APPLICATION_XML)
    public String sendUserSettings() {
        return "";
    }

    @GET
    @Secured
    @Path("/SendMultiplayerConnect")
    @Produces(MediaType.APPLICATION_XML)
    public String sendMultiplayerConnect() {
        return "";
    }

    @GET
    @Secured
    @Path("/SendClientPingTime")
    @Produces(MediaType.APPLICATION_XML)
    public String sendClientPingTime() {
        return "";
    }

    @GET
    @Secured
    @Path("/LoginAnnouncementClicked")
    @Produces(MediaType.APPLICATION_XML)
    public String loginAnnouncementClicked() {
        return "";
    }

    @PUT
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public String genericEmptyPut(@PathParam("path") String path) {
        System.out.println("empty PUT!!!");
        return "";
    }
}
