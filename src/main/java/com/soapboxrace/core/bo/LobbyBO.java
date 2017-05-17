package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.LobbyDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.TokenSessionEntity;
import com.soapboxrace.jaxb.http.ArrayOfLobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyCountdown;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyInfo;
import com.soapboxrace.jaxb.xmpp.XMPP_LobbyInviteType;
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

		// LobbyEntrantEntity lobbyEntrantEntity = new LobbyEntrantEntity();
		// lobbyEntrantEntity.setPersona(personaEntity);
		// lobbyEntrantEntity.setLobby(lobbyEntity);
		// lobbyEntity.add(lobbyEntrantEntity);

		lobbyDao.insert(lobbyEntity);
		sendJoinEvent(personaEntity.getPersonaId(), lobbyEntity);
	}

	private void joinLobby(PersonaEntity personaEntity, List<LobbyEntity> lobbys) {

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
			LobbyInfo lobbyInfo = new LobbyInfo();
			LobbyEntrantInfo lobbyEntrantInfo = new LobbyEntrantInfo();
			if (personaId != lobbyEntrantEntity.getPersona().getPersonaId()) {
				lobbyEntrantInfo.setHeat(1);
				lobbyEntrantInfo.setLevel(lobbyEntrantEntity.getPersona().getLevel());
				lobbyEntrantInfo.setPersonaId(personaId);
				lobbyInfo.setLobbyId(lobbyEntrantEntity.getLobby().getId());
				ArrayOfLobbyEntrantInfo arrayOfLobbyEntrantInfo = new ArrayOfLobbyEntrantInfo();
				arrayOfLobbyEntrantInfo.getLobbyEntrantInfo().add(lobbyEntrantInfo);
				lobbyInfo.setEntrants(arrayOfLobbyEntrantInfo);
				XmppLobby xmppLobby = new XmppLobby(lobbyEntrantEntity.getPersona().getPersonaId());
				xmppLobby.sendJoinMsg(lobbyInfo);
			}
		}
	}
}
