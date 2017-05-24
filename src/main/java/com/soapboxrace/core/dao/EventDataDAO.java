package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.EventDataEntity;

@Stateless
public class EventDataDAO extends BaseDAO<EventDataEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EventDataEntity findById(Long id) {
		return entityManager.find(EventDataEntity.class, id);
	}
	
	public List<EventDataEntity> findByPersona(Long personaId) {
		TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity.findByPersona", EventDataEntity.class);
		query.setParameter("personaId", personaId);
		List<EventDataEntity> resultList = query.getResultList();
		return resultList;
	}
	
	public List<EventDataEntity> findByPersonaAndRaceType(Long personaId, Integer type) {
		TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity.findByPersonaAndType", EventDataEntity.class);
		query.setParameter("personaId", personaId);
		query.setParameter("eventModeID", type);
		List<EventDataEntity> resultList = query.getResultList();
		return resultList;
	}
	
	public List<EventDataEntity> getRacers(Long eventSessionId) {
		TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity.getRacers", EventDataEntity.class);
		query.setParameter("eventSessionId", eventSessionId);
		List<EventDataEntity> resultList = query.getResultList();
		return resultList;
	}
	
	public EventDataEntity findByPersonaAndEventSessionId(Long personaId, Long eventSessionId) {
		TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity.findByPersonaAndEventSessionId", EventDataEntity.class);
		query.setParameter("personaId", personaId);
		query.setParameter("eventSessionId", eventSessionId);
		List<EventDataEntity> resultList = query.getResultList();
		EventDataEntity eventDataEntity = new EventDataEntity();
		if(resultList != null && !resultList.isEmpty()){
			eventDataEntity = resultList.get(0);
		}
		return eventDataEntity;
	}

}
