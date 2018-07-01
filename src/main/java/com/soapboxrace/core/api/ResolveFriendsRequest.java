package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
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

        PersonaBase personaBase = new PersonaBase();

        personaBase.setBadges(new ArrayOfBadgePacket());
        personaBase.setIconIndex(sender.getIconIndex());
        personaBase.setLevel(sender.getLevel());
        personaBase.setMotto(sender.getMotto());
        personaBase.setName(sender.getName());
        personaBase.setPersonaId(sender.getPersonaId());
        personaBase.setPresence(1);
        personaBase.setScore(sender.getScore());
        personaBase.setUserId(sender.getUser().getId());

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

        return MarshalXML.marshal(personaBase);

        // attempt #1 - failed

//        FriendPersona friendPersona = new FriendPersona();
//        friendPersona.setUserId(sender.getUser().getId());
//        friendPersona.setSocialNetwork(0);
//        friendPersona.setPresence(1);
//        friendPersona.setPersonaId(sender.getPersonaId());
//        friendPersona.setOriginalName(null);
//        friendPersona.setName(sender.getName());
//        friendPersona.setLevel(sender.getLevel());
//        friendPersona.setIconIndex(sender.getIconIndex());
//        
//        FriendResult friendResult = new FriendResult();
//        friendResult.setResult(0);
//        friendResult.setFriendPersona(friendPersona);
//        
//        return MarshalXML.marshal(friendResult);

        //        XMPP_FriendPersonaType friendPersona = new XMPP_FriendPersonaType();
//        friendPersona.setIconIndex(active.getIconIndex());
//        friendPersona.setLevel(active.getLevel());
//        friendPersona.setName(active.getName());
//        friendPersona.setOriginalName(active.getName());
//        friendPersona.setPersonaId(activePersonaId);
//        friendPersona.setPresence(3);
//        friendPersona.setUserId(active.getUser().getId());
//
//        openFireSoapBoxCli.send(MarshalXML.marshal(friendPersona), target.getPersonaId());

//        return "";
//        long activePersonaId = sessionBO.getActivePersonaId(securityToken);
//        PersonaEntity active = personaDAO.findById(activePersonaId);
//        PersonaEntity sender = personaDAO.findById(friendPersonaId);
//
//        // Send recipient->sender
//        {
//            XMPP_ResponseTypePersonaBase recipientToSender = new XMPP_ResponseTypePersonaBase();
//            PersonaBase xmppPersonaBase = new PersonaBase();
//
//            xmppPersonaBase.setBadges(new ArrayOfBadgePacket());
//            xmppPersonaBase.setIconIndex(active.getIconIndex());
//            xmppPersonaBase.setLevel(active.getLevel());
//            xmppPersonaBase.setMotto(active.getMotto());
//            xmppPersonaBase.setName(active.getName());
//            xmppPersonaBase.setPersonaId(active.getPersonaId());
//            xmppPersonaBase.setPresence(1); // friend request
//            xmppPersonaBase.setScore(active.getScore());
//            xmppPersonaBase.setUserId(active.getUser().getId());
//
//            recipientToSender.setPersonaBase(xmppPersonaBase);
//
//            openFireSoapBoxCli.send(recipientToSender, sender.getPersonaId());
//        }
//        
//        // Send sender->recipient
//        {
//            XMPP_ResponseTypePersonaBase senderToTarget = new XMPP_ResponseTypePersonaBase();
//            PersonaBase xmppPersonaBase = new PersonaBase();
//
//            xmppPersonaBase.setBadges(new ArrayOfBadgePacket());
//            xmppPersonaBase.setIconIndex(sender.getIconIndex());
//            xmppPersonaBase.setLevel(sender.getLevel());
//            xmppPersonaBase.setMotto(sender.getMotto());
//            xmppPersonaBase.setName(sender.getName());
//            xmppPersonaBase.setPersonaId(sender.getPersonaId());
//            xmppPersonaBase.setPresence(1); // friend request
//            xmppPersonaBase.setScore(sender.getScore());
//            xmppPersonaBase.setUserId(sender.getUser().getId());
//
//            senderToTarget.setPersonaBase(xmppPersonaBase);
//
//            openFireSoapBoxCli.send(senderToTarget, active.getPersonaId());
//        }
//
//        FriendResult friendResult = new FriendResult();
//        FriendPersona resultFriendPersona = new FriendPersona();
//
//        resultFriendPersona.setIconIndex(sender.getIconIndex());
//        resultFriendPersona.setLevel(sender.getLevel());
//        resultFriendPersona.setName(sender.getName());
//        resultFriendPersona.setOriginalName(null);
//        resultFriendPersona.setPersonaId(sender.getPersonaId());
//        resultFriendPersona.setPresence(1);
//        resultFriendPersona.setSocialNetwork(0);
//        resultFriendPersona.setUserId(sender.getUser().getId());
//
//        friendResult.setResult(1);
//        friendResult.setFriendPersona(resultFriendPersona);
//        
//        return MarshalXML.marshal(friendResult);
    }
}
