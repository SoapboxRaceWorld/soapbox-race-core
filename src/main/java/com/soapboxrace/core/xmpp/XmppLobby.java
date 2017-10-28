package com.soapboxrace.core.xmpp;

import java.util.List;

import com.soapboxrace.jaxb.http.LobbyEntrantAdded;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyEntrantRemoved;
import com.soapboxrace.jaxb.xmpp.XMPP_CryptoTicketsType;
import com.soapboxrace.jaxb.xmpp.XMPP_LobbyInviteType;
import com.soapboxrace.jaxb.xmpp.XMPP_LobbyLaunchedType;
import com.soapboxrace.jaxb.xmpp.XMPP_P2PCryptoTicketType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeEntrantAdded;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeEntrantRemoved;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeLobbyInvite;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeLobbyLaunched;

public class XmppLobby {

	private long personaId;
    private static OpenFireSoapBoxCli openFireSoapBoxCli = OpenFireSoapBoxCli.getInstance();

	public XmppLobby(long personaId) {
		this.personaId = personaId;
	}

	public void joinQueueEvent(XMPP_LobbyInviteType xMPP_LobbyInviteType) {
		XMPP_ResponseTypeLobbyInvite responseType = new XMPP_ResponseTypeLobbyInvite();
		responseType.setLobbyInvite(xMPP_LobbyInviteType);
		try {
			Thread.sleep(1000);
			openFireSoapBoxCli.send(responseType, personaId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendJoinMsg(LobbyEntrantAdded lobbyInfo) {
		XMPP_ResponseTypeEntrantAdded responseType = new XMPP_ResponseTypeEntrantAdded();
		responseType.setLobbyInvite(lobbyInfo);
		try {
			Thread.sleep(1000);
			openFireSoapBoxCli.send(responseType, personaId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendExitMsg(LobbyEntrantRemoved lobbyInfo) {
		XMPP_ResponseTypeEntrantRemoved responseType = new XMPP_ResponseTypeEntrantRemoved();
		responseType.setLobbyExit(lobbyInfo);
		openFireSoapBoxCli.send(responseType, personaId);
	}

	public static void sendRelay(XMPP_LobbyLaunchedType lobbyLaunched, XMPP_CryptoTicketsType xMPP_CryptoTicketsType) {
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
			lobbyLaunched.setCryptoTickets(cryptoTicketsTypeTmp);
			XMPP_ResponseTypeLobbyLaunched responseType = new XMPP_ResponseTypeLobbyLaunched();
			responseType.setLobbyInvite(lobbyLaunched);
			openFireSoapBoxCli.send(responseType, personaId);
		}
	}
	
	public void sendLobbyInvite(XMPP_LobbyInviteType lobbyInviteType) {
		openFireSoapBoxCli.send(lobbyInviteType, personaId);
	}

}