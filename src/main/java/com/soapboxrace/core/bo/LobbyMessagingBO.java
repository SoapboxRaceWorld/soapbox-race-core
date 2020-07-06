package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.LobbyEntrantAdded;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyEntrantRemoved;
import com.soapboxrace.jaxb.xmpp.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Sends XMPP messages related to lobbies.
 *
 * @author coder
 */
@Stateless
public class LobbyMessagingBO {
    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    /**
     * Prepares and sends a new {@link com.soapboxrace.jaxb.http.LobbyEntrantAdded} message
     * to the given recipient {@link com.soapboxrace.core.jpa.PersonaEntity}.
     *
     * @param lobbyEntity      The {@link LobbyEntity} attached to the message.
     * @param sourcePersona    The {@link PersonaEntity} attached to the message.
     * @param recipientPersona The {@link PersonaEntity} receiving the message.
     * @throws IllegalArgumentException if the source and recipient persona IDs are the same
     */
    public void sendJoinMessage(LobbyEntity lobbyEntity, PersonaEntity sourcePersona, PersonaEntity recipientPersona) {
        if (sourcePersona.getPersonaId().equals(recipientPersona.getPersonaId())) {
            throw new IllegalArgumentException("Source and recipient personas cannot be the same!");
        }

        LobbyEntrantAdded lobbyEntrantAdded = new LobbyEntrantAdded();
        lobbyEntrantAdded.setHeat(1);
        lobbyEntrantAdded.setLevel(sourcePersona.getLevel());
        lobbyEntrantAdded.setPersonaId(sourcePersona.getPersonaId());
        lobbyEntrantAdded.setLobbyId(lobbyEntity.getId());

        XMPP_ResponseTypeEntrantAdded response = new XMPP_ResponseTypeEntrantAdded();
        response.setLobbyInvite(lobbyEntrantAdded);

        openFireSoapBoxCli.send(response, recipientPersona.getPersonaId());
    }

    /**
     * Prepares and sends a new {@link com.soapboxrace.jaxb.http.LobbyEntrantRemoved} message
     * to the given recipient {@link com.soapboxrace.core.jpa.PersonaEntity}.
     *
     * @param lobbyEntity      The {@link LobbyEntity} attached to the message.
     * @param sourcePersona    The {@link PersonaEntity} attached to the message.
     * @param recipientPersona The {@link PersonaEntity} receiving the message.
     * @throws IllegalArgumentException if the source and recipient persona IDs are the same
     */
    public void sendLeaveMessage(LobbyEntity lobbyEntity, PersonaEntity sourcePersona, PersonaEntity recipientPersona) {
        LobbyEntrantRemoved lobbyEntrantRemoved = new LobbyEntrantRemoved();
        lobbyEntrantRemoved.setLobbyId(lobbyEntity.getId());
        lobbyEntrantRemoved.setPersonaId(sourcePersona.getPersonaId());

        XMPP_ResponseTypeEntrantRemoved response = new XMPP_ResponseTypeEntrantRemoved();
        response.setLobbyEntrantRemoved(lobbyEntrantRemoved);

        openFireSoapBoxCli.send(response, recipientPersona.getPersonaId());
    }

    /**
     * Prepares and sends a new {@link com.soapboxrace.jaxb.xmpp.XMPP_LobbyInviteType} message
     * to the given recipient {@link com.soapboxrace.core.jpa.PersonaEntity}.
     *
     * @param lobbyEntity      The {@link LobbyEntity} attached to the message.
     * @param recipientPersona The {@link PersonaEntity} receiving the message.
     * @param inviteLifetime   The lifetime of the invitation in milliseconds.
     */
    public void sendLobbyInvitation(LobbyEntity lobbyEntity, PersonaEntity recipientPersona, long inviteLifetime) {
        XMPP_LobbyInviteType lobbyInvite = new XMPP_LobbyInviteType();
        lobbyInvite.setEventId(lobbyEntity.getEvent().getId());
        lobbyInvite.setLobbyInviteId(lobbyEntity.getId());

        if (!lobbyEntity.getPersonaId().equals(recipientPersona.getPersonaId())) {
            lobbyInvite.setInvitedByPersonaId(lobbyEntity.getPersonaId());
            lobbyInvite.setInviteLifetimeInMilliseconds(inviteLifetime);
            lobbyInvite.setPrivate(lobbyEntity.getIsPrivate());
        }

        XMPP_ResponseTypeLobbyInvite response = new XMPP_ResponseTypeLobbyInvite();
        response.setLobbyInvite(lobbyInvite);

        openFireSoapBoxCli.send(response, recipientPersona.getPersonaId());
    }

    public void sendRelay(XMPP_LobbyLaunchedType lobbyLaunched, XMPP_CryptoTicketsType xMPP_CryptoTicketsType) {
        List<LobbyEntrantInfo> lobbyEntrantInfo = lobbyLaunched.getEntrants().getLobbyEntrantInfo();
        for (LobbyEntrantInfo lobbyEntrantInfoType : lobbyEntrantInfo) {
            long personaId = lobbyEntrantInfoType.getPersonaId();
            XMPP_CryptoTicketsType cryptoTicketsTypeTmp = new XMPP_CryptoTicketsType();
            List<XMPP_P2PCryptoTicketType> p2pCryptoTicket = xMPP_CryptoTicketsType.getP2PCryptoTicket();
            for (XMPP_P2PCryptoTicketType p2pCryptoTicketType : p2pCryptoTicket) {
                if (personaId != p2pCryptoTicketType.getPersonaId()) {
                    cryptoTicketsTypeTmp.getP2PCryptoTicket().add(p2pCryptoTicketType);
                }
            }
            String udpRaceHostIp = lobbyEntrantInfoType.getUdpRaceHostIp();
            if (udpRaceHostIp != null) {
                lobbyLaunched.setUdpRelayHost(udpRaceHostIp);
            }
            lobbyLaunched.setCryptoTickets(cryptoTicketsTypeTmp);
            XMPP_ResponseTypeLobbyLaunched responseType = new XMPP_ResponseTypeLobbyLaunched();
            responseType.setLobbyInvite(lobbyLaunched);
            openFireSoapBoxCli.send(responseType, personaId);
        }
    }
}
