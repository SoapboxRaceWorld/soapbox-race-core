package com.soapboxrace.core.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;

@Stateless
public class LobbyDAO extends BaseDAO<LobbyEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public LobbyEntity findById(Long id) {
		LobbyEntity lobbyEntity = entityManager.find(LobbyEntity.class, id);
		lobbyEntity.getEntrants().size();
		return lobbyEntity;
	}

	public List<LobbyEntity> findAllOpen() {
		Date dateNow = new Date();
		Date datePast = new Date(dateNow.getTime() - 35000);

		TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findAllOpen", LobbyEntity.class);
		query.setParameter("dateTime1", datePast);
		query.setParameter("dateTime2", dateNow);
		return query.getResultList();
	}

	public List<LobbyEntity> findAllRunning() {
		Date dateNow = new Date();
		Date datePast = new Date(dateNow.getTime() - 50000);

		TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findAllOpen", LobbyEntity.class);
		query.setParameter("dateTime1", datePast);
		query.setParameter("dateTime2", dateNow);
		List<LobbyEntity> resultList = query.getResultList();
		for (LobbyEntity lobbyEntity : resultList) {
			List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();
			for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
				lobbyEntrantEntity.getPersona();
			}
		}
		return resultList;
	}

	public List<LobbyEntity> findByEventStarted(int eventId) {
		Date dateNow = new Date();
		Date datePast = new Date(dateNow.getTime() - 35000);
		EventEntity eventEntity = new EventEntity();
		eventEntity.setId(eventId);

		TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findByEventStarted", LobbyEntity.class);
		query.setParameter("event", eventEntity);
		query.setParameter("dateTime1", datePast);
		query.setParameter("dateTime2", dateNow);
		return query.getResultList();
	}

	public LobbyEntity findByEventAndPersona(int eventId, Long personaId) {
		Date dateNow = new Date();
		Date datePast = new Date(dateNow.getTime() - 35000);
		EventEntity eventEntity = new EventEntity();
		eventEntity.setId(eventId);

		TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findByEventAndPersona", LobbyEntity.class);
		query.setParameter("event", eventEntity);
		query.setParameter("dateTime1", datePast);
		query.setParameter("dateTime2", dateNow);
		query.setParameter("personaId", personaId);

		List<LobbyEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
}
