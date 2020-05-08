package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

public abstract class RewardEventBO<TA extends ArbitrationPacket> extends RewardBO {
    public abstract Accolades getAccolades(Long activePersonaId, TA dragArbitrationPacket,
                                           EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity,
                                           AchievementTransaction achievementTransaction);
}
