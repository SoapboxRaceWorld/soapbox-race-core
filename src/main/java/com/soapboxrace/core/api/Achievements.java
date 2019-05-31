package com.soapboxrace.core.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.AchievementsPacket;

@Path("/achievements")
public class Achievements
{
    @GET
    @Secured
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementsPacket loadall(@HeaderParam("securityToken") String securityToken)
    {
        return new AchievementsPacket();
    }
}
