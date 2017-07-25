package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ProductEntity;

@Stateless
public class ProductDAO extends BaseDAO<ProductEntity> {

	@PersistenceContext
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ProductEntity findById(Long id) {
		return entityManager.find(ProductEntity.class, id);
	}

	public List<ProductEntity> findByLevelEnabled(String categoryName, String productType, int minLevel, boolean enabled, boolean premium) {
		TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByLevelEnabled", ProductEntity.class);
		query.setParameter("categoryName", categoryName);
		query.setParameter("productType", productType);
		query.setParameter("enabled", enabled);
		query.setParameter("minLevel", minLevel);
		query.setParameter("premium", premium);
		return query.getResultList();
	}
	
	public List<ProductEntity> findForEndRace(String categoryName, String productType, int level) {
		TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findForEndRace", ProductEntity.class);
		query.setParameter("categoryName", categoryName);
		query.setParameter("productType", productType);
		query.setParameter("level", level);
		return query.getResultList();
	}
	
	public ProductEntity findByProductId(String productId) {
		TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByProductId", ProductEntity.class);
		query.setParameter("productId", productId);
		
		List<ProductEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

}
