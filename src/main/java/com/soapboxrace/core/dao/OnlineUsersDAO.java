package com.soapboxrace.core.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;

@Stateless
public class OnlineUsersDAO extends BaseDAO<OnlineUsersEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public OnlineUsersEntity findById(Long id) {
		return entityManager.find(OnlineUsersEntity.class, id);
	}

	public OnlineUsersEntity findByTime(Date time) {
		Long timeLong = time.getTime() / 1000L;
		
		TypedQuery<OnlineUsersEntity> query = entityManager.createNamedQuery("OnlineUsersEntity.findByTime", OnlineUsersEntity.class);
		query.setParameter("time", timeLong.intValue());
		
		List<OnlineUsersEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
