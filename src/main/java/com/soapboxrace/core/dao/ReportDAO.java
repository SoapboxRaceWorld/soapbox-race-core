package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ReportEntity;

@Stateless
public class ReportDAO extends BaseDAO<ReportEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ReportEntity findById(Long id) {
		return entityManager.find(ReportEntity.class, id);
	}

}
