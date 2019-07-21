package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.EventSessionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EventSessionDAO extends BaseDAO<EventSessionEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EventSessionEntity findById(Long id) {
        return entityManager.find(EventSessionEntity.class, id);
    }

}
