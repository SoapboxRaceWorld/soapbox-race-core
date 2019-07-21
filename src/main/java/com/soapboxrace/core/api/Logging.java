package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/logging")
public class Logging {

    @GET
    @Secured
    @Path("/client")
    @Produces(MediaType.APPLICATION_XML)
    public String client() {
        return "<ClientConfigTrans/>";
    }
}
