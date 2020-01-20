/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.ModdingBO;
import com.soapboxrace.core.vo.ModInfoVO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Modding")
public class Modding {

    @EJB
    private ModdingBO moddingBO;

    @GET
    @Path("GetModInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModInfo() {
        ModInfoVO modInfoVO = moddingBO.getModInfo();

        if (modInfoVO == null) {
            return Response.status(404).build();
        }

        return Response.ok().entity(modInfoVO).build();
    }
}
