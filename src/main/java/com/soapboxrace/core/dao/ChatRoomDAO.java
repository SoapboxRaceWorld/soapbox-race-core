/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ChatRoomEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ChatRoomDAO extends BaseDAO<ChatRoomEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ChatRoomEntity findById(int id) {
        return entityManager.find(ChatRoomEntity.class, id);
    }

    public List<ChatRoomEntity> findAll() {
        TypedQuery<ChatRoomEntity> query = entityManager.createNamedQuery("ChatRoomEntity.findAll",
                ChatRoomEntity.class);
        return query.getResultList();
    }

}
