package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/getblockeduserlist")
public class GetBlockedUserList {

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response getBlockedUserList(@HeaderParam("userId") Long userId) {
        return Response.ok().entity(socialRelationshipBO.getBlockedUserList(userId)).build();
    }
}
