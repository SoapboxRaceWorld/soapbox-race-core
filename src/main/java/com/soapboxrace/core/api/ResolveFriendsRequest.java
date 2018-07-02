package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PresenceManager;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.FriendResult;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeFriendPersona;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/resolvefriendsrequest")
public class ResolveFriendsRequest
{
    @EJB
    private TokenSessionBO sessionBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private OpenFireRestApiCli openFireRestApiCli;

    @EJB
    private FriendDAO friendDAO;

    @EJB
    private PresenceManager presenceManager;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public String resolveFriendsRequest(@HeaderParam("securityToken") String securityToken, @QueryParam("friendPersonaId") Long friendPersonaId, @QueryParam("resolution") int resolution)
    {
        PersonaEntity recipient = personaDAO.findById(sessionBO.getActivePersonaId(securityToken));
        PersonaEntity sender = personaDAO.findById(friendPersonaId);

        if (sender == null || recipient == null)
        {
            System.err.println("Hit a bad spot!");
            return "";
        }

        System.out.println("Recipient: " + recipient.getName() + " - Sender: " + sender.getName() + " - Resolution: " + resolution);

        FriendEntity friendEntity = friendDAO.findBySenderAndRecipient(recipient.getUser().getId(), sender.getPersonaId());

        if (friendEntity == null)
        {
            System.err.println("Invalid friend request!");
            return "";
        }

        if (friendEntity.getStatus() == 1)
        {
            System.err.println("Already resolved friend request!");
            return "";
        }

        if (resolution == 1)
        {
            PersonaBase personaBase = new PersonaBase();

            personaBase.setBadges(new ArrayOfBadgePacket());
            personaBase.setIconIndex(sender.getIconIndex());
            personaBase.setLevel(sender.getLevel());
            personaBase.setMotto(sender.getMotto());
            personaBase.setName(sender.getName());
            personaBase.setPersonaId(sender.getPersonaId());
            personaBase.setPresence(presenceManager.getPresence(sender.getPersonaId()));
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
            otherFriendEntity.setPersonaId(recipient.getPersonaId());

            friendDAO.insert(otherFriendEntity);

            friendEntity.setStatus(1);
            friendDAO.update(friendEntity);

            return MarshalXML.marshal(personaBase);
        } else
        {
            friendDAO.delete(friendEntity);

            return "";
        }
    }
}