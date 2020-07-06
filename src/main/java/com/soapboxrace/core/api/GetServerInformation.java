/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.GetServerInformationBO;
import com.soapboxrace.core.bo.util.ServerInformationVO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/GetServerInformation")
public class GetServerInformation {

    @EJB
    private GetServerInformationBO bo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ServerInformationVO getServerInformation() {
        return bo.getServerInformation();
    }
}
