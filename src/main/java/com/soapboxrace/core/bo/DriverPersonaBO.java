package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.LobbyEntrantDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.ArrayOfPersonaBase;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.http.PersonaPresence;
import com.soapboxrace.jaxb.http.ProfileData;

@Stateless
public class DriverPersonaBO {

	@EJB
	private UserDAO userDao;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private LobbyEntrantDAO lobbyEntrantDAO;

	@EJB
	private CarSlotDAO carSlotDAO;

	public ProfileData createPersona(Long userId, PersonaEntity personaEntity) {
		UserEntity userEntity = userDao.findById(userId);
		personaEntity.setUser(userEntity);
		personaEntity.setCash(6000000);
		personaEntity.setLevel(60);
		personaDao.insert(personaEntity);
		return castPersonaEntity(personaEntity);
	}

	private ProfileData castPersonaEntity(PersonaEntity personaEntity) {
		ProfileData profileData = new ProfileData();
		// switch to apache beanutils copy
		profileData.setName(personaEntity.getName());
		profileData.setCash(personaEntity.getCash());
		profileData.setIconIndex(personaEntity.getIconIndex());
		profileData.setPersonaId(personaEntity.getPersonaId());
		profileData.setLevel(personaEntity.getLevel());
		return profileData;
	}

	public ProfileData getPersonaInfo(Long personaId) {
		PersonaEntity personaEntity = personaDao.findById(personaId);
		ProfileData profileData = castPersonaEntity(personaEntity);
		profileData.setBadges(new ArrayOfBadgePacket());
		profileData.setMotto(personaEntity.getMotto());
		profileData.setPercentToLevel(0);
		profileData.setRating(0);
		profileData.setRep(0);
		profileData.setRepAtCurrentLevel(0);
		profileData.setScore(0);
		return profileData;
	}

	public ArrayOfPersonaBase getPersonaBaseFromList(List<Long> personaIdList) {
		ArrayOfPersonaBase arrayOfPersonaBase = new ArrayOfPersonaBase();
		for (Long personaId : personaIdList) {
			PersonaEntity personaEntity = personaDao.findById(personaId);
			PersonaBase personaBase = new PersonaBase();
			personaBase.setBadges(new ArrayOfBadgePacket());
			personaBase.setIconIndex(personaEntity.getIconIndex());
			personaBase.setLevel(personaEntity.getLevel());
			personaBase.setMotto(personaEntity.getMotto());
			personaBase.setName(personaEntity.getName());
			personaBase.setPresence(1);
			personaBase.setPersonaId(personaEntity.getPersonaId());
			personaBase.setScore(personaEntity.getScore());
			personaBase.setUserId(personaEntity.getUser().getId());
			arrayOfPersonaBase.getPersonaBase().add(personaBase);
		}
		return arrayOfPersonaBase;
	}

	public void deletePersona(Long personaId) {
		PersonaEntity personaEntity = personaDao.findById(personaId);
		carSlotDAO.deleteByPersona(personaEntity);
		lobbyEntrantDAO.deleteByPersona(personaEntity);
		personaDao.delete(personaEntity);
	}

	public PersonaPresence getPersonaPresenceByName(String name) {
		PersonaEntity personaEntity = personaDao.findByName(name);
		if (personaEntity == null) {
			return null;
		}
		
		PersonaPresence personaPresence = new PersonaPresence();
		personaPresence.setPersonaId(personaEntity.getPersonaId());
		personaPresence.setPresence(1);
		personaPresence.setUserId(personaEntity.getUser().getId());
		return personaPresence;
	}

}
