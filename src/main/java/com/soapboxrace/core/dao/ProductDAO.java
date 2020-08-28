/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.ProductEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductDAO extends LongKeyedDAO<ProductEntity> {

    public ProductDAO() {
        super(ProductEntity.class);
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

        List<ProductEntity> results = query.getResultList();

        if (results.isEmpty()) {
            throw new RuntimeException("Could not find product with ID: " + productId);
        }

        return results.get(0);
        //        return query.getSingleResult();
    }

    public ProductEntity findByEntitlementTag(String entitlementTag) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByEntitlementTag",
                ProductEntity.class);
        query.setParameter("entitlementTag", entitlementTag);

        List<ProductEntity> results = query.getResultList();

        if (results.isEmpty()) {
            throw new RuntimeException("Could not find product with tag: " + entitlementTag);
        }

        return results.get(0);
    }

    public ProductEntity findByHash(Integer hash) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByHash",
                ProductEntity.class);
        query.setParameter("hash", hash);

        List<ProductEntity> results = query.getResultList();

        if (results.isEmpty()) {
            throw new RuntimeException("Could not find product with hash: " + hash);
        }

        return results.get(0);
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
