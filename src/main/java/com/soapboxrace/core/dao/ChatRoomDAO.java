/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.ChatRoomEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ChatRoomDAO extends LongKeyedDAO<ChatRoomEntity> {

    public ChatRoomDAO() {
        super(ChatRoomEntity.class);
    }

    public List<ChatRoomEntity> findAll() {
        TypedQuery<ChatRoomEntity> query = entityManager.createNamedQuery("ChatRoomEntity.findAll",
                ChatRoomEntity.class);
        return query.getResultList();
    }

}
