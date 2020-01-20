/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.jaxb.http.ArrayOfUdpRelayInfo;
import com.soapboxrace.jaxb.http.UdpRelayInfo;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/getrebroadcasters")
public class GetRebroadcasters {

    @Context
    UriInfo uri;

    @EJB
    private ParameterBO parameterBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfUdpRelayInfo getRebroadcasters() {
        ArrayOfUdpRelayInfo arrayOfUdpRelayInfo = new ArrayOfUdpRelayInfo();
        UdpRelayInfo udpRelayInfo = new UdpRelayInfo();
        String freeroamIp = parameterBO.getStrParam("UDP_FREEROAM_IP");
        udpRelayInfo.setHost(freeroamIp);
        udpRelayInfo.setPort(parameterBO.getIntParam("UDP_FREEROAM_PORT"));
        arrayOfUdpRelayInfo.getUdpRelayInfo().add(udpRelayInfo);
        return arrayOfUdpRelayInfo;
    }
}
