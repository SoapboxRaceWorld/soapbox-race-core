package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.TreasureHuntEntity;

@Stateless
public class TreasureHuntDAO extends BaseDAO<TreasureHuntEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public TreasureHuntEntity findById(Long personaId) {
		return entityManager.find(TreasureHuntEntity.class, personaId);
	}
	
	public void deleteByPersona(Long personaId) {
		Query query = entityManager.createNamedQuery("TreasureHuntEntity.deleteByPersona");
		query.setParameter("personaId", personaId);
		query.executeUpdate();
	}

}
