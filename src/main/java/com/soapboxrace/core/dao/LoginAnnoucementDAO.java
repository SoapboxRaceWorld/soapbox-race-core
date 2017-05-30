package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LoginAnnoucementEntity;

@Stateless
public class LoginAnnoucementDAO extends BaseDAO<LoginAnnoucementEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<LoginAnnoucementEntity> findAll() {
		TypedQuery<LoginAnnoucementEntity> query = entityManager.createNamedQuery("LoginAnnoucementEntity.findAll", LoginAnnoucementEntity.class);
		return query.getResultList();
	}

}
