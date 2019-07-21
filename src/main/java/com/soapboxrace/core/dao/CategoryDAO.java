package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CategoryEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoryDAO extends BaseDAO<CategoryEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CategoryEntity findById(Long id) {
        return entityManager.find(CategoryEntity.class, id);
    }

    public List<CategoryEntity> getAll() {
        TypedQuery<CategoryEntity> query = entityManager.createNamedQuery("CategoryEntity.getAll", CategoryEntity.class);
        return query.getResultList();
    }

}
