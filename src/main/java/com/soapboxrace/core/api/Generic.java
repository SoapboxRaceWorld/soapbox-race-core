package com.soapboxrace.core.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Generic {

    @GET
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public Response genericEmptyGet(@PathParam("path") String path) {
        System.out.println("empty GET!!!");
        return Response.ok().build();
    }

    @POST
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public Response genericEmptyPost(@PathParam("path") String path) {
        System.out.println("empty POST!!!");
        return Response.ok().build();
    }

    @PUT
    @Path("{path:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public Response genericEmptyPut(@PathParam("path") String path) {
        System.out.println("empty PUT!!!");
        return Response.ok().build();
    }

}
