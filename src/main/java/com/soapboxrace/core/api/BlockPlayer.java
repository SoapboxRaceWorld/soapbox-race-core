package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/blockplayer")
public class BlockPlayer {

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response blockPlayer(@HeaderParam("securityToken") String securityToken,
                                @HeaderParam("userId") Long userId,
                                @QueryParam("otherPersonaId") Long otherPersonaId) {
        Long activePersonaId = tokenSessionBO.getActivePersonaId(securityToken);

        return Response.ok().entity(socialRelationshipBO.blockPlayer(userId, activePersonaId, otherPersonaId)).build();
    }
}
