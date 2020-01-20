/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Generic {

    @GET
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public String genericEmptyGet(@PathParam("path") String path) {
        System.out.println("empty GET!!!");
        return "";
    }

    @POST
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public String genericEmptyPost(@PathParam("path") String path) {
        System.out.println("empty POST!!!");
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
