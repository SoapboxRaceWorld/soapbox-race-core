package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;

@Stateless
public class EventBO {

	@EJB
	private EventDAO eventDao;

	@EJB
	private EventSessionDAO eventSessionDao;

	public List<EventEntity> availableAtLevel() {
		return eventDao.findAll();
	}

	public EventSessionEntity createEventSession(int eventId) {
		EventEntity eventEntity = eventDao.findById(eventId);
		EventSessionEntity eventSessionEntity = new EventSessionEntity();
		eventSessionEntity.setEvent(eventEntity);
		eventSessionDao.insert(eventSessionEntity);
		return eventSessionEntity;
	}

	public EventSessionEntity findEventSessionById(Long id) {
		return eventSessionDao.findById(id);
	}
}
