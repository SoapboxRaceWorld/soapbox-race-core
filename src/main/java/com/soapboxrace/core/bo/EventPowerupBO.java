package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventPowerupDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventPowerupEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventPowerupBO {

    @EJB
    private EventPowerupDAO eventPowerupDAO;

    @EJB
    private EventSessionDAO eventSessionDAO;

    @EJB
    private PersonaDAO personaDAO;

    public void createPowerupRecord(Long eventSessionId, Long activePersonaId, Integer powerupHash) {
        EventPowerupEntity eventPowerupEntity = new EventPowerupEntity();
        eventPowerupEntity.setPersonaEntity(personaDAO.findById(activePersonaId));
        eventPowerupEntity.setEventSessionEntity(eventSessionDAO.findById(eventSessionId));
        eventPowerupEntity.setPowerupHash(powerupHash);

        eventPowerupDAO.insert(eventPowerupEntity);
    }
}
