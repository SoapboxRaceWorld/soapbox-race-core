package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ProductEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductDAO extends BaseDAO<ProductEntity> {
    private final SecureRandom secureRandom = new SecureRandom();

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProductEntity findById(Long id) {
        return entityManager.find(ProductEntity.class, id);
    }

    public List<ProductEntity> findByLevelEnabled(String categoryName, String productType, int minLevel,
                                                  boolean enabled, boolean premium) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByLevelEnabled",
                ProductEntity.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("productType", productType);
        query.setParameter("enabled", enabled);
        query.setParameter("minLevel", minLevel);
        query.setParameter("premium", premium);
        return query.getResultList();
    }

    public ProductEntity findByProductId(String productId) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByProductId",
                ProductEntity.class);
        query.setParameter("productId", productId);

        return query.getSingleResult();
    }

    public ProductEntity findByEntitlementTag(String entitlementTag) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByEntitlementTag",
                ProductEntity.class);
        query.setParameter("entitlementTag", entitlementTag);

        return query.getSingleResult();
    }

    public ProductEntity findByHash(Integer hash) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByHash",
                ProductEntity.class);
        query.setParameter("hash", hash);

        return query.getSingleResult();
    }

    public List<ProductEntity> findDropsByType(String type) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findDropsByType",
                ProductEntity.class);
        query.setParameter("type", type);

        return query.getResultList();
    }

    public List<ProductEntity> findByTraits(String category, String type, String subType, Integer rating) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> cq = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> from = cq.from(ProductEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (category != null) {
            predicates.add(cb.equal(from.get("categoryName"), category));
        }

        if (type != null) {
            predicates.add(cb.equal(from.get("productType"), type));
        }

        if (subType != null) {
            predicates.add(cb.equal(from.get("subType"), subType));
        }

        if (rating != null) {
            predicates.add(cb.equal(from.get("rarity"), rating));
        }

        cq = cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
