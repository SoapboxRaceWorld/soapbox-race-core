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

@Stateless
public class LobbyDAO extends BaseDAO<LobbyEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public LobbyEntity findById(int id) {
		return entityManager.find(LobbyEntity.class, id);
	}

	public List<LobbyEntity> findByEventStarted(int eventId) {
		Date dateNow = new Date();
		Date datePast = new Date(dateNow.getTime() - 35000);
		TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findByEventStarted", LobbyEntity.class);
		EventEntity eventEntity = new EventEntity();
		eventEntity.setId(eventId);
		query.setParameter("event", eventEntity);
		query.setParameter("dateTime1", datePast);
		query.setParameter("dateTime2", dateNow);
		return query.getResultList();
	}
}
