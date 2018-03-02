package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LoginAnnouncementEntity;

@Stateless
public class LoginAnnouncementDAO extends BaseDAO<LoginAnnouncementEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<LoginAnnouncementEntity> findAll() {
        TypedQuery<LoginAnnouncementEntity> query = entityManager.createNamedQuery("LoginAnnouncementEntity.findAll", LoginAnnouncementEntity.class);
        return query.getResultList();
    }
}
