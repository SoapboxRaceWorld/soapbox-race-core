package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AchievementBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.AchievementsPacket;

@Path("/achievements")
public class Achievements
{
    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private AchievementBO achievementBO;

    @GET
    @Secured
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementsPacket loadall(@HeaderParam("securityToken") String securityToken)
    {
        Long personaId = tokenSessionBO.getActivePersonaId(securityToken);

        if (personaId != 0L) {
            return achievementBO.loadAll(personaId);
        }

        return new AchievementsPacket();
    }
}
