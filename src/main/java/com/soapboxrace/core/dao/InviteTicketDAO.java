package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

@Stateless
public class InviteTicketDAO extends BaseDAO<InviteTicketEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public InviteTicketEntity findById(Long id) {
		return entityManager.find(InviteTicketEntity.class, id);
	}

	public InviteTicketEntity findByTicket(String ticket) {
		TypedQuery<InviteTicketEntity> query = entityManager.createNamedQuery("InviteTicketEntity.findByTicket", InviteTicketEntity.class);
		query.setParameter("ticket", ticket);
		
		List<InviteTicketEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

	public InviteTicketEntity findByDiscordName(String discordName) {
		TypedQuery<InviteTicketEntity> query = entityManager.createNamedQuery("InviteTicketEntity.findByDiscordName", InviteTicketEntity.class);
		query.setParameter("discordName", discordName);
		List<InviteTicketEntity> resultList = query.getResultList();
		InviteTicketEntity inviteTicketEntity = null;
		if (resultList != null && !resultList.isEmpty()) {
			inviteTicketEntity = resultList.get(0);
		}
		return inviteTicketEntity;
	}

}
