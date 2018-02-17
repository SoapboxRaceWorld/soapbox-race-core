package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CategoryEntity;
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

	public VinylProductEntity findByHash(long hash) {
		TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByHash", VinylProductEntity.class);
		query.setParameter("hash", hash);

		List<VinylProductEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}
	
	public VinylProductEntity findByProductId(String productId) {
		TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByProductId", VinylProductEntity.class);
		query.setParameter("productId", productId);

		List<VinylProductEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

	public List<VinylProductEntity> findByCategoryLevelEnabled(CategoryEntity category, int minLevel, Boolean enabled, Boolean premium) {
		TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByCategoryLevelEnabled", VinylProductEntity.class);
		query.setParameter("category", category);
		query.setParameter("enabled", enabled);
		query.setParameter("minLevel", minLevel);
		query.setParameter("premium", premium);
		return query.getResultList();
	}

}
