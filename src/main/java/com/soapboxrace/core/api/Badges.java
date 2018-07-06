package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.BadgeBundle;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.io.InputStream;

@Path("/badges")
public class Badges
{
    @EJB
    private PersonaBO personaBO;
    
    @EJB
    private TokenSessionBO tokenSessionBO;
    
    @PUT
    @Secured
    @Path("/set")
    public String set(@HeaderParam("securityToken") String securityToken, InputStream is)
    {
        BadgeBundle badgeBundle = UnmarshalXML.unMarshal(is, BadgeBundle.class);
        
        personaBO.updateBadges(tokenSessionBO.getActivePersonaId(securityToken), badgeBundle);
        
        return "";
    }
}
