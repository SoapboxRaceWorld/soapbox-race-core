package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.OwnedCarEntity;

@Stateless
public class OwnedCarDAO extends BaseDAO<OwnedCarEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public OwnedCarEntity findById(Long id) {
		return entityManager.find(OwnedCarEntity.class, id);
	}
}
