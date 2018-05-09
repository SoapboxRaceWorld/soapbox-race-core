package com.soapboxrace.core.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CarClassesEntity;

@Stateless
public class CarClassesDAO extends BaseDAO<CarClassesEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public CarClassesEntity findById(String productId) {
		return entityManager.find(CarClassesEntity.class, productId);
	}

	public CarClassesEntity findByHash(int hash) {
		TypedQuery<CarClassesEntity> query = entityManager.createQuery("SELECT obj FROM CarClassesEntity obj WHERE obj.hash = :hash", CarClassesEntity.class);
		query.setParameter("hash", hash);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
