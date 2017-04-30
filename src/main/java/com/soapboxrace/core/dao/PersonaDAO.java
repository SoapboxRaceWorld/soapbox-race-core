package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PersonaEntity;

@Stateless
public class PersonaDAO extends BaseDAO<PersonaEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public PersonaEntity findById(Long id) {
		return entityManager.find(PersonaEntity.class, id);
	}

}
