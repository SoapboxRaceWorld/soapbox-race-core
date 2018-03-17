package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LevelRepEntity;

@Stateless
public class LevelRepDAO extends BaseDAO<LevelRepEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public LevelRepEntity findByLevel(Long level) {
		TypedQuery<LevelRepEntity> query = entityManager.createNamedQuery("LevelRepEntity.findByLevel", LevelRepEntity.class);
		query.setParameter("level", level);
		
		List<LevelRepEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
