package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resolvefriendsrequest")
public class ResolveFriendsRequest {
    @EJB
    private TokenSessionBO sessionBO;

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response resolveFriendsRequest(@HeaderParam("securityToken") String securityToken,
                                          @QueryParam("friendPersonaId") Long friendPersonaId, @QueryParam(
            "resolution") int resolution) {
        Long activePersonaId = sessionBO.getActivePersonaId(securityToken);

        return Response.ok(this.socialRelationshipBO.resolveFriendsRequest(activePersonaId, friendPersonaId,
                resolution)).build();
    }
}