package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/unblockplayer")
public class UnblockPlayer {

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response unblockPlayer(@HeaderParam("userId") Long userId,
                                  @HeaderParam("securityToken") String securityToken,
                                  @QueryParam("otherPersonaId") Long otherPersonaId) {
        return Response.ok().entity(socialRelationshipBO.unblockPlayer(userId, otherPersonaId)).build();
    }
}
