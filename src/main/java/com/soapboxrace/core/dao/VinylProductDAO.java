package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.VinylProductEntity;

@Stateless
public class VinylProductDAO extends BaseDAO<VinylProductEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public VinylProductEntity findById(Long id) {
		return entityManager.find(VinylProductEntity.class, id);
	}

	public List<VinylProductEntity> findByCategoryName(String categoryName) {
		TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByCategoryName", VinylProductEntity.class);
		query.setParameter("categoryName", categoryName);
		return query.getResultList();
	}
	
	public VinylProductEntity findByProductId(String productId) {
		TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByProductId", VinylProductEntity.class);
		query.setParameter("productId", productId);
		
		List<VinylProductEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
