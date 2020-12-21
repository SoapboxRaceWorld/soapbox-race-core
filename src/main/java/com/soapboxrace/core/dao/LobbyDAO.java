/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.LobbyEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class LobbyDAO extends LongKeyedDAO<LobbyEntity> {

    public LobbyDAO() {
        super(LobbyEntity.class);
    }

    public LobbyEntity findById(Long id) {
        LobbyEntity lobbyEntity = entityManager.find(LobbyEntity.class, id);

        if (lobbyEntity != null) {
            lobbyEntity.getEntrants().size();
            return lobbyEntity;
        }

        return null;
    }

    public List<LobbyEntity> findAllOpen(int carClassHash) {
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime datePast = LocalDateTime.now().minusSeconds(35);

        TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findAllOpenByCarClass",
                LobbyEntity.class);
        query.setParameter("dateTime1", datePast);
        query.setParameter("dateTime2", dateNow);
        query.setParameter("carClassHash", carClassHash);
        return query.getResultList();
    }

    public List<LobbyEntity> findByEventStarted(int eventId) {
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime datePast = LocalDateTime.now().minusSeconds(35);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(eventId);

        TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findByEventStarted",
                LobbyEntity.class);
        query.setParameter("event", eventEntity);
        query.setParameter("dateTime1", datePast);
        query.setParameter("dateTime2", dateNow);
        return query.getResultList();
    }

    public LobbyEntity findByEventAndPersona(int eventId, Long personaId) {
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime datePast = LocalDateTime.now().minusSeconds(35);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(eventId);

        TypedQuery<LobbyEntity> query = entityManager.createNamedQuery("LobbyEntity.findByEventAndPersona",
                LobbyEntity.class);
        query.setParameter("event", eventEntity);
        query.setParameter("dateTime1", datePast);
        query.setParameter("dateTime2", dateNow);
        query.setParameter("personaId", personaId);

        List<LobbyEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }
}
