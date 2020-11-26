package com.soapboxrace.core.bo;

import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

public abstract class RewardEventBO<TA extends ArbitrationPacket> extends RewardBO {
    public abstract Accolades getAccolades(Long activePersonaId, TA dragArbitrationPacket,
                                           EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity,
                                           AchievementTransaction achievementTransaction);

    protected EventRewardEntity getRewardConfiguration(EventSessionEntity eventSessionEntity) {
        EventEntity eventEntity = eventSessionEntity.getEvent();
        LobbyEntity lobbyEntity = eventSessionEntity.getLobby();
        EventRewardEntity eventRewardEntity;

        if (lobbyEntity != null) {
            if (lobbyEntity.getIsPrivate()) {
                eventRewardEntity = eventEntity.getPrivateRewardConfig();
            } else {
                eventRewardEntity = eventEntity.getMultiplayerRewardConfig();
            }
        } else {
            eventRewardEntity = eventEntity.getSingleplayerRewardConfig();
        }

        if (eventRewardEntity == null) {
            throw new EngineException("Cannot find appropriate reward configuration for event [" + eventEntity.getId() + "] (session: " + eventSessionEntity.getId() + ")",
                    EngineExceptionCode.LuckyDrawNoTableDefinedForRace, true);
        }

        return eventRewardEntity;
    }
}
