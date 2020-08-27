/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.HardwareInfoBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.HardwareInfo;
import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.ejb.EJB;
import javax.inject.Inject;
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

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @POST
    @Secured
    @Path("/SendHardwareInfo")
    @Produces(MediaType.APPLICATION_XML)
    public String sendHardwareInfo(InputStream is) {
        HardwareInfo hardwareInfo = JAXBUtility.unMarshal(is, HardwareInfo.class);
        if (hardwareInfo.getCpuid0().equals("GenuineIntel") || hardwareInfo.getCpuid0().equals("AuthenticAMD")) {
            HardwareInfoEntity hardwareInfoEntity = hardwareInfoBO.save(hardwareInfo);
            UserEntity user = requestSessionInfo.getUser();
            user.setGameHardwareHash(hardwareInfoEntity.getHardwareHash());
            userDAO.update(user);
        } else {
            tokenBO.deleteByUserId(requestSessionInfo.getUser().getId());
        }
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
        return "";
    }
}
