package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.NewsArticleEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class NewsArticleDAO extends BaseDAO<NewsArticleEntity> {
    @Override
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public NewsArticleEntity findById(Long id) {
        return this.entityManager.find(NewsArticleEntity.class, id);
    }
    
    public List<NewsArticleEntity> findAllByPersona(Long personaId) {
        TypedQuery<NewsArticleEntity> query = entityManager.createNamedQuery("NewsArticleEntity.findAllByPersona", NewsArticleEntity.class);

        query.setParameter("id", personaId);

        return query.getResultList();
    }

    public List<NewsArticleEntity> findAllByReferencedPersona(Long personaId) {
        TypedQuery<NewsArticleEntity> query = entityManager.createNamedQuery("NewsArticleEntity.findAllByReferencedPersona", NewsArticleEntity.class);

        query.setParameter("id", personaId);

        return query.getResultList();
    }
}
