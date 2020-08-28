/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class LobbyEntrantDAO extends LongKeyedDAO<LobbyEntrantEntity> {

    public LobbyEntrantDAO() {
        super(LobbyEntrantEntity.class);
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        Query query = entityManager.createNamedQuery("LobbyEntrantEntity.deleteByPersona");
        query.setParameter("persona", personaEntity);
        query.executeUpdate();
    }

    public void deleteByPersonaAndLobby(PersonaEntity personaEntity, LobbyEntity lobbyEntity) {
        Query query = entityManager.createNamedQuery("LobbyEntrantEntity.deleteByPersonaAndLobby");
        query.setParameter("persona", personaEntity);
        query.setParameter("lobby", lobbyEntity);
        query.executeUpdate();
    }
}
