package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.TokenSessionEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestSessionInfo {

    private TokenSessionEntity tokenSessionEntity;

    @EJB
    private TokenSessionDAO tokenSessionDAO;

    public TokenSessionEntity getTokenSessionEntity() {
        return tokenSessionEntity;
    }

    public void setTokenSessionEntity(TokenSessionEntity tokenSessionEntity) {
        this.tokenSessionEntity = tokenSessionEntity;
    }

    public UserEntity getUser() {
        return tokenSessionEntity.getUserEntity();
    }

    public boolean isAdmin() {
        return getUser().isAdmin();
    }

    public Long getEventSessionId() {
        return tokenSessionEntity.getEventSessionId();
    }

    public Long getActiveLobbyId() {
        return tokenSessionEntity.getActiveLobbyId();
    }

    public Long getActivePersonaId() {
        return tokenSessionEntity.getActivePersonaId();
    }
}
