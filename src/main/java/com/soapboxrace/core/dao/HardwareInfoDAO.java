package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;

@Stateless
public class HardwareInfoDAO extends BaseDAO<HardwareInfoEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public HardwareInfoEntity findByHardwareHash(String hardwareHash) {
		TypedQuery<HardwareInfoEntity> query = entityManager.createNamedQuery("HardwareInfoEntity.findByHardwareHash", HardwareInfoEntity.class);
		query.setParameter("hardwareHash", hardwareHash);
		List<HardwareInfoEntity> resultList = query.getResultList();
		if (resultList == null || resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}

	public HardwareInfoEntity findByUserId(Long userId) {
		TypedQuery<HardwareInfoEntity> query = entityManager.createNamedQuery("HardwareInfoEntity.findByUserId", HardwareInfoEntity.class);
		query.setParameter("userId", userId);
		List<HardwareInfoEntity> resultList = query.getResultList();
		if (resultList == null || resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}

}
