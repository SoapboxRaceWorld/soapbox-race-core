/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2021.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.KCrewMemberEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class KCrewMemberDAO extends LongKeyedDAO<KCrewMemberEntity> {
    public KCrewMemberDAO() {
        super(KCrewMemberEntity.class);
    }

    public KCrewMemberEntity findCrewMembershipByPersonaId(Long personaId) {
        TypedQuery<KCrewMemberEntity> query = entityManager.createNamedQuery("KCrewMemberEntity.findCrewMembershipByPersonaId", KCrewMemberEntity.class);
        query.setParameter("personaid", personaId);

        List<KCrewMemberEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }
}
