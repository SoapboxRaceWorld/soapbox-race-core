/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ChatAnnouncementEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ChatAnnouncementDAO extends BaseDAO<ChatAnnouncementEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ChatAnnouncementEntity> findAll() {
        return entityManager.createNamedQuery("ChatAnnouncementEntity.findAll", ChatAnnouncementEntity.class)
                .getResultList();
    }

    public ChatAnnouncementEntity findById(int id) {
        return entityManager.find(ChatAnnouncementEntity.class, id);
    }
}
