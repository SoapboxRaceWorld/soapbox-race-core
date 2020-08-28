/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.EventEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class EventDAO extends BaseDAO<EventEntity, Integer> {

    @Override
    public EventEntity find(Integer id) {
        return entityManager.find(EventEntity.class, id);
    }

    public List<EventEntity> findAll() {
        TypedQuery<EventEntity> query = entityManager.createNamedQuery("EventEntity.findAll", EventEntity.class);
        return query.getResultList();
    }

    public List<EventEntity> findAllRotatable() {
        TypedQuery<EventEntity> query = entityManager.createNamedQuery("EventEntity.findAllRotatable", EventEntity.class);
        return query.getResultList();
    }

    public List<EventEntity> findByLevel(int level) {
        TypedQuery<EventEntity> query = entityManager.createNamedQuery("EventEntity.findByLevel", EventEntity.class);
        query.setParameter("level", level);
        return query.getResultList();
    }

}
