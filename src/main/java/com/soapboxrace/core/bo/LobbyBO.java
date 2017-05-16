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
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.TokenSessionEntity;
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
}
