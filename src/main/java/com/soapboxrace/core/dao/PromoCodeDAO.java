package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PromoCodeEntity;
import com.soapboxrace.core.jpa.UserEntity;

@Stateless
public class PromoCodeDAO extends BaseDAO<PromoCodeEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public PromoCodeEntity findByUser(UserEntity userEntity) {
		TypedQuery<PromoCodeEntity> query = entityManager.createNamedQuery("PromoCodeEntity.findByUser", PromoCodeEntity.class);
		query.setParameter("user", userEntity);
		
		List<PromoCodeEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
	
	public PromoCodeEntity findByCode(String promoCode) {
		TypedQuery<PromoCodeEntity> query = entityManager.createNamedQuery("PromoCodeEntity.findByCode", PromoCodeEntity.class);
		query.setParameter("promoCode", promoCode);
		
		List<PromoCodeEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
