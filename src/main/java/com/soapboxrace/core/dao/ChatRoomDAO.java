package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ChatRoomEntity;

@Stateless
public class ChatRoomDAO extends BaseDAO<ChatRoomEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ChatRoomEntity findById(int id) {
		return entityManager.find(ChatRoomEntity.class, id);
	}

	public List<ChatRoomEntity> findAll() {
		TypedQuery<ChatRoomEntity> query = entityManager.createNamedQuery("ChatRoomEntity.findAll", ChatRoomEntity.class);
		return query.getResultList();
	}

}
