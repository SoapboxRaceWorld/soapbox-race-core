package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.ParameterBO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/GetFreeroamJson")
public class GetFreeroamJson {
    @EJB
    private ParameterBO parameterBO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerInformation() {
        try {
            String strParam = parameterBO.getStrParam("FREEROAM_JSON_PATH");
            return Response.ok().entity(Files.readAllBytes(Paths.get(strParam))).build();
        } catch (Exception e) {
            System.err.println("freeroam json file not found");
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
