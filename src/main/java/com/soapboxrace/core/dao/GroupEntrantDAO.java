package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.GroupEntrantEntity;

@Stateless
public class GroupEntrantDAO extends BaseDAO<GroupEntrantEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public GroupEntrantEntity findById(Long id) {
		return entityManager.find(GroupEntrantEntity.class, id);
	}
	
	public List<GroupEntrantEntity> getEntrantByGroupId(Long groupId) {
		TypedQuery<GroupEntrantEntity> query = entityManager.createNamedQuery("GroupEntrantEntity.getEntrantByGroupId", GroupEntrantEntity.class);
		query.setParameter("groupId", groupId);
		return query.getResultList();
	}

}
