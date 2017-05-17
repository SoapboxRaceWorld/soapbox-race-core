package com.soapboxrace.core.bo;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.LobbyDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.TokenSessionEntity;
import com.soapboxrace.jaxb.http.ArrayOfLobbyEntrantInfo;
import com.soapboxrace.jaxb.http.Entrants;
import com.soapboxrace.jaxb.http.LobbyCountdown;
import com.soapboxrace.jaxb.http.LobbyEntrantAdded;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyEntrantState;
import com.soapboxrace.jaxb.http.LobbyInfo;
import com.soapboxrace.jaxb.xmpp.ChallengeType;
import com.soapboxrace.jaxb.xmpp.XMPP_CryptoTicketsType;
import com.soapboxrace.jaxb.xmpp.XMPP_EventSessionType;
import com.soapboxrace.jaxb.xmpp.XMPP_LobbyInviteType;
import com.soapboxrace.jaxb.xmpp.XMPP_LobbyLaunchedType;
import com.soapboxrace.jaxb.xmpp.XMPP_P2PCryptoTicketType;
import com.soapboxrace.xmpp.openfire.XmppLobby;

@Stateless
public class LobbyBO {

	@EJB
	private EventDAO eventDao;

	@EJB
	private EventSessionDAO eventSessionDao;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private TokenSessionDAO tokenDAO;

	@EJB
	private LobbyDAO lobbyDao;

	public void joinQueueEvent(String securityToken, int eventId) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		Long activePersonaId = tokenSessionEntity.getActivePersonaId();
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		List<LobbyEntity> lobbys = lobbyDao.findByEventStarted(eventId);
		if (lobbys.size() == 0) {
			createLobby(personaEntity, eventId);
		} else {
			joinLobby(personaEntity, lobbys);
		}
	}

	private void createLobby(PersonaEntity personaEntity, int eventId) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setId(eventId);
		LobbyEntity lobbyEntity = new LobbyEntity();
		lobbyEntity.setEvent(eventEntity);
		lobbyDao.insert(lobbyEntity);
		sendJoinEvent(personaEntity.getPersonaId(), lobbyEntity);
		new LobbyCountDown(lobbyEntity.getId(), lobbyDao, eventSessionDao).start();
	}

	private void joinLobby(PersonaEntity personaEntity, List<LobbyEntity> lobbys) {
		LobbyEntity lobbyEntity = null;
		for (LobbyEntity lobbyEntityTmp : lobbys) {
			int maxEntrants = 2;
			List<LobbyEntrantEntity> lobbyEntrants = lobbyEntityTmp.getEntrants();
			int entrantsSize = lobbyEntrants.size();
			if (entrantsSize < maxEntrants) {
				lobbyEntity = lobbyEntityTmp;
				if (!isPersonaInside(personaEntity.getPersonaId(), lobbyEntrants)) {
					LobbyEntrantEntity lobbyEntrantEntity = new LobbyEntrantEntity();
					lobbyEntrantEntity.setPersona(personaEntity);
					lobbyEntrantEntity.setLobby(lobbyEntity);
					lobbyEntrants.add(lobbyEntrantEntity);
				}
				break;
			}
		}
		if (lobbyEntity != null) {
			sendJoinEvent(personaEntity.getPersonaId(), lobbyEntity);
		}
	}

	private boolean isPersonaInside(Long personaId, List<LobbyEntrantEntity> lobbyEntrants) {
		for (LobbyEntrantEntity lobbyEntrantEntity : lobbyEntrants) {
			Long entrantPersonaId = lobbyEntrantEntity.getPersona().getPersonaId();
			if (entrantPersonaId == personaId) {
				return true;
			}
		}
		return false;
	}

	private void sendJoinEvent(Long personaId, LobbyEntity lobbyEntity) {
		XMPP_LobbyInviteType xMPP_LobbyInviteType = new XMPP_LobbyInviteType();
		int eventId = lobbyEntity.getEvent().getId();
		xMPP_LobbyInviteType.setEventId(eventId);
		Long lobbyId = lobbyEntity.getId();
		xMPP_LobbyInviteType.setLobbyInviteId(lobbyId);
		XmppLobby xmppLobby = new XmppLobby(personaId);
		xmppLobby.joinQueueEvent(xMPP_LobbyInviteType);
	}

	public LobbyInfo acceptinvite(Long personaId, Long lobbyInviteId) {
		LobbyEntity lobbyEntity = lobbyDao.findById(lobbyInviteId);
		int eventId = lobbyEntity.getEvent().getId();

		LobbyCountdown lobbyCountdown = new LobbyCountdown();
		lobbyCountdown.setLobbyId(lobbyInviteId);
		lobbyCountdown.setEventId(eventId);
		lobbyCountdown.setLobbyCountdownInMilliseconds(lobbyEntity.getLobbyCountdownInMilliseconds());

		ArrayOfLobbyEntrantInfo arrayOfLobbyEntrantInfo = new ArrayOfLobbyEntrantInfo();
		List<LobbyEntrantInfo> lobbyEntrantInfo = arrayOfLobbyEntrantInfo.getLobbyEntrantInfo();

		List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();
		sendJoinMsg(personaId, entrants);
		boolean personaInside = false;
		for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
			LobbyEntrantInfo LobbyEntrantInfo = new LobbyEntrantInfo();
			LobbyEntrantInfo.setPersonaId(lobbyEntrantEntity.getPersona().getPersonaId());
			LobbyEntrantInfo.setLevel(lobbyEntrantEntity.getPersona().getLevel());
			LobbyEntrantInfo.setGridIndex(lobbyEntrantEntity.getGridIndex());
			lobbyEntrantInfo.add(LobbyEntrantInfo);
			if (lobbyEntrantEntity.getPersona().getPersonaId().equals(personaId)) {
				personaInside = true;
			}
		}
		if (!personaInside) {
			LobbyEntrantEntity lobbyEntrantEntity = new LobbyEntrantEntity();
			PersonaEntity personaEntity = personaDao.findById(personaId);
			lobbyEntrantEntity.setPersona(personaEntity);
			lobbyEntrantEntity.setLobby(lobbyEntity);
			lobbyEntrantEntity.setGridIndex(entrants.size());
			lobbyEntity.getEntrants().add(lobbyEntrantEntity);
			lobbyDao.update(lobbyEntity);
			LobbyEntrantInfo LobbyEntrantInfo = new LobbyEntrantInfo();
			LobbyEntrantInfo.setPersonaId(lobbyEntrantEntity.getPersona().getPersonaId());
			LobbyEntrantInfo.setLevel(lobbyEntrantEntity.getPersona().getLevel());
			LobbyEntrantInfo.setGridIndex(lobbyEntrantEntity.getGridIndex());
			lobbyEntrantInfo.add(LobbyEntrantInfo);
		}

		LobbyInfo lobbyInfoType = new LobbyInfo();
		lobbyInfoType.setCountdown(lobbyCountdown);
		lobbyInfoType.setEntrants(arrayOfLobbyEntrantInfo);
		lobbyInfoType.setEventId(eventId);
		lobbyInfoType.setLobbyInviteId(lobbyInviteId);
		lobbyInfoType.setLobbyId(lobbyInviteId);

		return lobbyInfoType;
	}

	private void sendJoinMsg(Long personaId, List<LobbyEntrantEntity> lobbyEntrants) {
		for (LobbyEntrantEntity lobbyEntrantEntity : lobbyEntrants) {
			LobbyEntrantAdded lobbyEntrantAdded = new LobbyEntrantAdded();
			if (personaId != lobbyEntrantEntity.getPersona().getPersonaId()) {
				lobbyEntrantAdded.setHeat(1);
				lobbyEntrantAdded.setLevel(lobbyEntrantEntity.getPersona().getLevel());
				lobbyEntrantAdded.setPersonaId(personaId);
				lobbyEntrantAdded.setLobbyId(lobbyEntrantEntity.getLobby().getId());
				XmppLobby xmppLobby = new XmppLobby(lobbyEntrantEntity.getPersona().getPersonaId());
				xmppLobby.sendJoinMsg(lobbyEntrantAdded);
			}
		}
	}

	private static class LobbyCountDown extends Thread {
		private LobbyDAO lobbyDao;

		private EventSessionDAO eventSessionDao;

		private Long lobbyId;

		public LobbyCountDown(Long lobbyId, LobbyDAO lobbyDao, EventSessionDAO eventSessionDao) {
			this.lobbyId = lobbyId;
			this.lobbyDao = lobbyDao;
			this.eventSessionDao = eventSessionDao;
		}

		public void run() {
			try {
				Thread.sleep(60000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			LobbyEntity lobbyEntity = lobbyDao.findById(lobbyId);
			List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();
			if (entrants.size() < 2) {
				return;
			}
			Collections.sort(entrants);
			XMPP_LobbyLaunchedType lobbyLaunched = new XMPP_LobbyLaunchedType();
			Entrants entrantsType = new Entrants();
			List<LobbyEntrantInfo> lobbyEntrantInfo = entrantsType.getLobbyEntrantInfo();
			XMPP_CryptoTicketsType xMPP_CryptoTicketsType = new XMPP_CryptoTicketsType();
			List<XMPP_P2PCryptoTicketType> p2pCryptoTicket = xMPP_CryptoTicketsType.getP2PCryptoTicket();
			int i = 0;
			byte numOfRacers = (byte) entrants.size();
			EventSessionEntity eventDataEntity = new EventSessionEntity();
			eventDataEntity.setEvent(lobbyEntity.getEvent());
			eventSessionDao.insert(eventDataEntity);
			for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
				// eventDataEntity.setIsSinglePlayer(false);
				Long personaId = lobbyEntrantEntity.getPersona().getPersonaId();
				// eventDataEntity.setPersonaId(personaId);
				byte gridIndex = (byte) i;
				byte[] helloPacket = { 10, 11, 12, 13 };
				ByteBuffer byteBuffer = ByteBuffer.allocate(48);
				byteBuffer.put(gridIndex);
				byteBuffer.put(helloPacket);
				byteBuffer.putInt(eventDataEntity.getId().intValue());
				byteBuffer.put(numOfRacers);
				byteBuffer.putInt(personaId.intValue());
				byte[] cryptoTicketBytes = byteBuffer.array();
				String cryptoTicketBase64 = Base64.getEncoder().encodeToString(cryptoTicketBytes);
				Long userId = lobbyEntrantEntity.getPersona().getUser().getId();
				// HttpSessionVO httpSessionVo = Router.getHttpSessionVo(userId);
				// if (httpSessionVo != null) {
				// httpSessionVo.setRelayCryptoTicket(cryptoTicketBase64);
				// }

				XMPP_P2PCryptoTicketType p2pCryptoTicketType = new XMPP_P2PCryptoTicketType();
				p2pCryptoTicketType.setPersonaId(personaId);
				p2pCryptoTicketType.setSessionKey("AAAAAAAAAAAAAAAAAAAAAA==");
				p2pCryptoTicket.add(p2pCryptoTicketType);

				LobbyEntrantInfo lobbyEntrantInfoType = new LobbyEntrantInfo();
				lobbyEntrantInfoType.setPersonaId(personaId);
				lobbyEntrantInfoType.setLevel(lobbyEntrantEntity.getPersona().getLevel());
				lobbyEntrantInfoType.setHeat(1);
				lobbyEntrantInfoType.setGridIndex(i++);
				lobbyEntrantInfoType.setState(LobbyEntrantState.UNKNOWN);
				lobbyEntrantInfo.add(lobbyEntrantInfoType);
			}
			XMPP_EventSessionType xMPP_EventSessionType = new XMPP_EventSessionType();
			ChallengeType challengeType = new ChallengeType();
			challengeType.setChallengeId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			challengeType.setPattern("FFFFFFFFFFFFFFFF");
			challengeType.setLeftSize(14);
			challengeType.setRightSize(50);

			xMPP_EventSessionType.setEventId(lobbyEntity.getEvent().getId());
			xMPP_EventSessionType.setChallenge(challengeType);
			xMPP_EventSessionType.setSessionId(eventDataEntity.getId());
			lobbyLaunched.setNewRelayServer(true);
			lobbyLaunched.setLobbyId(lobbyEntity.getId());
			lobbyLaunched.setUdpRelayHost(Config.getUdpRaceIp());
			lobbyLaunched.setUdpRelayPort(Config.getUdpRacePort());

			lobbyLaunched.setEntrants(entrantsType);

			lobbyLaunched.setEventSession(xMPP_EventSessionType);
			XmppLobby.sendRelay(lobbyLaunched, xMPP_CryptoTicketsType);
		}
	}
}
