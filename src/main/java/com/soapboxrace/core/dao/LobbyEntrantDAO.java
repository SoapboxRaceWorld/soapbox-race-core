package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

@Stateless
public class LobbyEntrantDAO extends BaseDAO<LobbyEntrantEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public LobbyEntrantEntity findById(Long id) {
		return entityManager.find(LobbyEntrantEntity.class, id);
	}

	public void deleteByPersona(PersonaEntity personaEntity) {
		Query query = entityManager.createNamedQuery("LobbyEntrantEntity.deleteByPersona");
		query.setParameter("persona", personaEntity);
		query.executeUpdate();
	}
}
