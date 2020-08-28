/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.NewsArticleEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class NewsArticleDAO extends LongKeyedDAO<NewsArticleEntity> {

    public NewsArticleDAO() {
        super(NewsArticleEntity.class);
    }

    public List<NewsArticleEntity> findAllByPersona(Long personaId) {
        TypedQuery<NewsArticleEntity> query = entityManager.createNamedQuery("NewsArticleEntity.findAllByPersona",
                NewsArticleEntity.class);

        query.setParameter("id", personaId);

        return query.getResultList();
    }
}
