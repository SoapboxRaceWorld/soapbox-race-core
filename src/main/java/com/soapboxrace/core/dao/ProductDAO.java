package com.soapboxrace.core.dao;

import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	public ProductEntity findByHash(Integer hash) {
		TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByHash", ProductEntity.class);
		query.setParameter("hash", hash);

		List<ProductEntity> resultList = query.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

	public ProductEntity getRandomDrop(String productType) {
		StringBuilder sqlWhere = new StringBuilder();
		sqlWhere.append(" WHERE obj.isDropable=true ");
		sqlWhere.append(" AND obj.productType=:productType");

		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT COUNT(*) FROM ProductEntity obj ");
		sqlCount.append(sqlWhere.toString());

		Query countQuery = entityManager.createQuery(sqlCount.toString());
		countQuery.setParameter("productType", productType);
		Long count = (Long) countQuery.getSingleResult();

		Random random = new Random();
		int number = random.nextInt(count.intValue());

		StringBuilder sqlProduct = new StringBuilder();
		sqlProduct.append("SELECT obj FROM ProductEntity obj");
		sqlProduct.append(sqlWhere.toString());

		TypedQuery<ProductEntity> productQuery = entityManager.createQuery(sqlProduct.toString(), ProductEntity.class);
		productQuery.setParameter("productType", productType);

		productQuery.setFirstResult(number);
		productQuery.setMaxResults(1);
		return productQuery.getSingleResult();
	}

}
