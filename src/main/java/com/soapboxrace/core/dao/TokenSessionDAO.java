package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

	public TokenSessionEntity findByUserId(Long userId) {
		TypedQuery<TokenSessionEntity> query = entityManager.createNamedQuery("TokenSessionEntity.findByUserId", TokenSessionEntity.class);
		query.setParameter("userId", userId);
		return query.getSingleResult();
	}

	public void deleteByUserId(Long userId) {
		Query query = entityManager.createNamedQuery("TokenSessionEntity.deleteByUserId");
		query.setParameter("userId", userId);
		query.executeUpdate();
	}

	public void updateRelayCrytoTicketByPersonaId(Long personaId, String relayCryptoTicket) {
		Query query = entityManager.createNamedQuery("TokenSessionEntity.updateRelayCrytoTicket");
		query.setParameter("personaId", personaId);
		query.setParameter("relayCryptoTicket", relayCryptoTicket);
		query.executeUpdate();
	}

	public void updateLobbyIdByPersonaId(Long personaId, Long lobbyId) {
		Query query = entityManager.createNamedQuery("TokenSessionEntity.updateRelayCrytoTicket");
		query.setParameter("personaId", personaId);
		query.setParameter("activeLobbyId", lobbyId);
		query.executeUpdate();
	}

}
