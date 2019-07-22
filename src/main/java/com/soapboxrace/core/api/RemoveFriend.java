package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/removefriend")
public class RemoveFriend {
    @EJB
    private FriendDAO friendDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @GET
    @Secured
    public String removefriend(@QueryParam("friendPersonaId") Long friendPersonaId, @HeaderParam("securityToken") String securityToken) {
        PersonaEntity active = personaDAO.findById(tokenSessionBO.getActivePersonaId(securityToken));
        PersonaEntity friend = personaDAO.findById(friendPersonaId);

        if (active == null || friend == null) {
            return "";
        }

        FriendEntity friendEntity = friendDAO.findBySenderAndRecipient(friend.getUser().getId(), active.getUser().getId());
        FriendEntity friendEntity2 = friendDAO.findBySenderAndRecipient(active.getUser().getId(), friend.getUser().getId());

        if (friendEntity == null || friendEntity2 == null) {
            return "";
        }

        friendDAO.delete(friendEntity);
        friendDAO.delete(friendEntity2);

        // Send the update to the friend
        {
            XMPP_ResponseTypePersonaBase activeToFriend = new XMPP_ResponseTypePersonaBase();
            PersonaBase xmppPersonaBase = new PersonaBase();

            xmppPersonaBase.setBadges(new ArrayOfBadgePacket());
            xmppPersonaBase.setIconIndex(active.getIconIndex());
            xmppPersonaBase.setLevel(active.getLevel());
            xmppPersonaBase.setMotto(active.getMotto());
            xmppPersonaBase.setName(active.getName());
            xmppPersonaBase.setPersonaId(active.getPersonaId());
            xmppPersonaBase.setPresence(0);
            xmppPersonaBase.setScore(active.getScore());
            xmppPersonaBase.setUserId(active.getUser().getId());

            activeToFriend.setPersonaBase(xmppPersonaBase);

            openFireSoapBoxCli.send(activeToFriend, friend.getPersonaId());
        }

        return "";
    }
}