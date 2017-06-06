package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.RecoveryPasswordEntity;

@Stateless
public class RecoveryPasswordDAO extends BaseDAO<RecoveryPasswordEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public RecoveryPasswordEntity findById(int id) {
		return entityManager.find(RecoveryPasswordEntity.class, id);
	}
	
	public RecoveryPasswordEntity findByUserId(Long userId) {
		TypedQuery<RecoveryPasswordEntity> query = entityManager.createNamedQuery("RecoveryPasswordEntity.findByUserId", RecoveryPasswordEntity.class);
		query.setParameter("userId", userId);
		
		List<RecoveryPasswordEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
	
	public List<RecoveryPasswordEntity> findAllByUserId(Long userId) {
		TypedQuery<RecoveryPasswordEntity> query = entityManager.createNamedQuery("RecoveryPasswordEntity.findAllByUserId", RecoveryPasswordEntity.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}
	
	public RecoveryPasswordEntity findByRandomKey(String randomKey) {
		TypedQuery<RecoveryPasswordEntity> query = entityManager.createNamedQuery("RecoveryPasswordEntity.findByRandomKey", RecoveryPasswordEntity.class);
		query.setParameter("randomKey", randomKey);
		
		List<RecoveryPasswordEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
