package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.GroupEntity;

@Stateless
public class GroupDAO extends BaseDAO<GroupEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public GroupEntity findById(Long id) {
		return entityManager.find(GroupEntity.class, id);
	}
	
	public GroupEntity findByOwnerId(Long ownerId) {
		TypedQuery<GroupEntity> query = entityManager.createNamedQuery("GroupEntity.findByOwnerId", GroupEntity.class);
		query.setParameter("ownerId", ownerId);
		
		List<GroupEntity> resultList = query.getResultList();
		GroupEntity groupEntity = new GroupEntity();
		if(resultList != null && !resultList.isEmpty()){
			groupEntity = resultList.get(0);
		}
		return groupEntity;
	}
}
