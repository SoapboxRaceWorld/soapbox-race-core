/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.StringKeyedDAO;
import com.soapboxrace.core.jpa.TokenSessionEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class TokenSessionDAO extends StringKeyedDAO<TokenSessionEntity> {

    public TokenSessionDAO() {
        super(TokenSessionEntity.class);
    }

    public TokenSessionEntity findByUserId(Long userId) {
        TypedQuery<TokenSessionEntity> query = entityManager.createNamedQuery("TokenSessionEntity.findByUserId",
                TokenSessionEntity.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    public void deleteByUserId(Long userId) {
        Query query = entityManager.createNamedQuery("TokenSessionEntity.deleteByUserId");
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    public void updateRelayCrytoTicketByPersonaId(Long personaId, String relayCryptoTicket) {
        Query query = entityManager.createNamedQuery("TokenSessionEntity.updateRelayCrytoTicket");
        query.setParameter("personaId", personaId);
        query.setParameter("relayCryptoTicket", relayCryptoTicket);
        query.executeUpdate();
    }

}
