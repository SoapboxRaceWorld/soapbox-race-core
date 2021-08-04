package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.AdminBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.security.MessageDigest;

@Path("/ofcmdhook")
public class OpenfireHook {
    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private AdminBO adminBO;

    @POST
    public Response openfireHook(@HeaderParam("Authorization") String token, @QueryParam("cmd") String command, @QueryParam("pid") long persona, @QueryParam("webhook") Boolean webHook) {
        String correctToken = parameterBO.getStrParam("OPENFIRE_TOKEN");

        if (token == null || !MessageDigest.isEqual(token.getBytes(), correctToken.getBytes())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("invalid token").build();
        }

        PersonaEntity personaEntity = personaDAO.find(persona);

        if (personaEntity != null && personaEntity.getUser().isAdmin()) {
            Boolean sendOrNot = Boolean.valueOf(webHook);
            adminBO.sendChatCommand(persona, command, personaEntity.getName(), sendOrNot);
        }
        
        return Response.noContent().build();
    }
}
