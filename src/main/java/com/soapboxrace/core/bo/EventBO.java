package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.jpa.EventEntity;

@Stateless
public class EventBO {

	@EJB
	private EventDAO eventDao;

	public List<EventEntity> availableAtLevel() {
		return eventDao.findAll();
	}

}
