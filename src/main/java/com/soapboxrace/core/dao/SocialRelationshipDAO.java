/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.SocialRelationshipEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class SocialRelationshipDAO extends LongKeyedDAO<SocialRelationshipEntity> {

    public SocialRelationshipDAO() {
        super(SocialRelationshipEntity.class);
    }

    public void deleteAllByPersonaId(Long personaId) {
        entityManager.createNamedQuery("SocialRelationshipEntity.deleteAllByPersonaId")
                .setParameter("personaId", personaId)
                .executeUpdate();
    }

    public List<SocialRelationshipEntity> findByUserId(Long userId) {
        TypedQuery<SocialRelationshipEntity> query = entityManager.createNamedQuery(
                "SocialRelationshipEntity.findByUser", SocialRelationshipEntity.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public List<SocialRelationshipEntity> findByUserIdAndStatus(Long userId, Long status) {
        TypedQuery<SocialRelationshipEntity> query = entityManager.createNamedQuery(
                "SocialRelationshipEntity.findByUserAndStatus", SocialRelationshipEntity.class);
        query.setParameter("id", userId);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public List<SocialRelationshipEntity> findByRemoteUserIdAndStatus(Long remoteUserId, Long status) {
        TypedQuery<SocialRelationshipEntity> query = entityManager.createNamedQuery(
                "SocialRelationshipEntity.findByRemoteUserAndStatus", SocialRelationshipEntity.class);
        query.setParameter("remoteId", remoteUserId);
        query.setParameter("status", status);
        return query.getResultList();
    }


    public SocialRelationshipEntity findByLocalAndRemoteUser(Long localUserId, Long remoteUserId) {
        TypedQuery<SocialRelationshipEntity> query = entityManager.createNamedQuery(
                "SocialRelationshipEntity.findByLocalAndRemoteUser",
                SocialRelationshipEntity.class);
        query.setParameter("remoteId", remoteUserId);
        query.setParameter("localId", localUserId);
        List<SocialRelationshipEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}