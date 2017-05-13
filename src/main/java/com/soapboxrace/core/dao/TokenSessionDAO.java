package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.TokenSessionEntity;

@Stateless
public class TokenSessionDAO extends BaseDAO<TokenSessionEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public TokenSessionEntity findById(String securityToken) {
		return entityManager.find(TokenSessionEntity.class, securityToken);
	}

	public void deleteByUserId(Long userId) {
		Query query = entityManager.createNamedQuery("TokenSessionEntity.deleteByUserId");
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

}
