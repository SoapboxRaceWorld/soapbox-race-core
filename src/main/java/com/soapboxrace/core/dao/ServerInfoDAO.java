package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ServerInfoEntity;

@Stateless
public class ServerInfoDAO extends BaseDAO<ServerInfoEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ServerInfoEntity findInfo() {
		TypedQuery<ServerInfoEntity> query = entityManager.createNamedQuery("ServerInfoEntity.findAll", ServerInfoEntity.class);

		List<ServerInfoEntity> resultList = query.getResultList();
		ServerInfoEntity serverInfoEntity = !resultList.isEmpty() ? resultList.get(0) : null;

		if (serverInfoEntity != null) {
			serverInfoEntity.getActivatedHolidaySceneryGroups().size();
			serverInfoEntity.getDisactivatedHolidaySceneryGroups().size();
		}

		return serverInfoEntity;
	}

	public void updateNumberOfRegistered() {
		Query createNamedQuery = entityManager.createNamedQuery("ServerInfoEntity.updateNumberOfRegistered");
		createNamedQuery.executeUpdate();
	}

}
