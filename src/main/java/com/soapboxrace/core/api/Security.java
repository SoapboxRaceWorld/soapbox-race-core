package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.FraudConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/security")
public class Security {
    @GET
    @Secured
    @Path("/fraudConfig")
    @Produces(MediaType.APPLICATION_XML)
    public FraudConfig fraudConfig(@HeaderParam("userId") Long userId) {
        FraudConfig fraudConfig = new FraudConfig();
        fraudConfig.setEnabledBitField(12);
        fraudConfig.setGameFileFreq(1000000);
        fraudConfig.setModuleFreq(360000);
        fraudConfig.setStartUpFreq(1000000);
        fraudConfig.setUserID(userId);
        return fraudConfig;
    }

    @POST
    @Secured
    @Path("/generateWebToken")
    @Produces(MediaType.APPLICATION_XML)
    public String generateWebToken() {
        return "";
    }
}
