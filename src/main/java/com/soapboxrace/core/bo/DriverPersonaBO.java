package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.ProfileData;

@Stateless
public class DriverPersonaBO {

	@EJB
	private UserDAO userDao;

	@EJB
	private PersonaDAO personaDao;

	public ProfileData createPersona(Long userId, PersonaEntity personaEntity) {
		UserEntity userEntity = userDao.findById(userId);
		personaEntity.setUser(userEntity);
		personaEntity.setCash(6000000);
		personaEntity.setLevel(60);
		personaDao.insert(personaEntity);
		ProfileData profileData = new ProfileData();
		// switch to apache beanutils copy
		profileData.setName(personaEntity.getName());
		profileData.setCash(personaEntity.getCash());
		profileData.setIconIndex(personaEntity.getIconIndex());
		profileData.setPersonaId(personaEntity.getPersonaId());
		profileData.setLevel(personaEntity.getLevel());
		return profileData;
	}

}
