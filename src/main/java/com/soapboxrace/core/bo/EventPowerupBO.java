package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventPowerupDAO;
import com.soapboxrace.core.jpa.EventPowerupEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventPowerupBO {

    @EJB
    private EventPowerupDAO eventPowerupDAO;

    public void createPowerupRecord(Long eventSessionId, Long activePersonaId, Integer powerupHash) {
        EventPowerupEntity eventPowerupEntity = new EventPowerupEntity();
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(activePersonaId);
        EventSessionEntity eventSessionEntity = new EventSessionEntity();
        eventSessionEntity.setId(eventSessionId);
        eventPowerupEntity.setPersonaEntity(personaEntity);
        eventPowerupEntity.setEventSessionEntity(eventSessionEntity);
        eventPowerupEntity.setPowerupHash(powerupHash);

        eventPowerupDAO.insert(eventPowerupEntity);
    }
}
