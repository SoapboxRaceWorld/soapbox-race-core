package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ParameterEntity;

@Stateless
public class ParameterDAO extends BaseDAO<ParameterEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ParameterEntity findById(String name) {
		return entityManager.find(ParameterEntity.class, name);
	}

}
