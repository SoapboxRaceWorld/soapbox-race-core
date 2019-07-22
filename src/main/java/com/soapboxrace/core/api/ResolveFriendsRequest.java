package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PresenceBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.exception.EngineException;
import com.soapboxrace.core.exception.EngineExceptionCode;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/resolvefriendsrequest")
public class ResolveFriendsRequest {
    @EJB
    private TokenSessionBO sessionBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private FriendDAO friendDAO;

    @EJB
    private PresenceBO presenceBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response resolveFriendsRequest(@HeaderParam("securityToken") String securityToken,
                                          @QueryParam("friendPersonaId") Long friendPersonaId, @QueryParam("resolution") int resolution) {
        PersonaEntity recipient = personaDAO.findById(sessionBO.getActivePersonaId(securityToken));
        PersonaEntity sender = personaDAO.findById(friendPersonaId);

        if (recipient == null) {
            throw new EngineException(EngineExceptionCode.FailedReadSession);
        }

        if (sender == null) {
            throw new EngineException(EngineExceptionCode.PersonaNotFound);
        }

        FriendEntity friendEntity = friendDAO.findBySenderAndRecipient(sender.getUser().getId(), recipient.getUser().getId());

        if (friendEntity == null) {
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        if (friendEntity.getStatus() == 1) {
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        if (resolution == 1) {
            PersonaBase personaBase = new PersonaBase();

            personaBase.setBadges(new ArrayOfBadgePacket());
            personaBase.setIconIndex(sender.getIconIndex());
            personaBase.setLevel(sender.getLevel());
            personaBase.setMotto(sender.getMotto());
            personaBase.setName(sender.getName());
            personaBase.setPersonaId(sender.getPersonaId());
            personaBase.setPresence(presenceBO.getPresence(sender.getPersonaId()));
            personaBase.setScore(sender.getScore());
            personaBase.setUserId(sender.getUser().getId());

            // Send the list update to the sender
            {
                XMPP_ResponseTypePersonaBase recipientToSender = new XMPP_ResponseTypePersonaBase();
                PersonaBase xmppPersonaBase = new PersonaBase();

                xmppPersonaBase.setBadges(new ArrayOfBadgePacket());
                xmppPersonaBase.setIconIndex(recipient.getIconIndex());
                xmppPersonaBase.setLevel(recipient.getLevel());
                xmppPersonaBase.setMotto(recipient.getMotto());
                xmppPersonaBase.setName(recipient.getName());
                xmppPersonaBase.setPersonaId(recipient.getPersonaId());
                xmppPersonaBase.setPresence(1);
                xmppPersonaBase.setScore(recipient.getScore());
                xmppPersonaBase.setUserId(recipient.getUser().getId());

                recipientToSender.setPersonaBase(xmppPersonaBase);

                openFireSoapBoxCli.send(recipientToSender, sender.getPersonaId());
            }

            FriendEntity otherFriendEntity = new FriendEntity();
            otherFriendEntity.setStatus(1);
            otherFriendEntity.setUser(sender.getUser());
            otherFriendEntity.setFromUser(recipient.getUser());
            otherFriendEntity.setFromPersonaId(recipient.getPersonaId());

            friendDAO.insert(otherFriendEntity);

            friendEntity.setStatus(1);
            friendDAO.update(friendEntity);

            return Response.ok(personaBase).build();
        } else {
            friendDAO.delete(friendEntity);

            return Response.ok().build();
        }
    }
}