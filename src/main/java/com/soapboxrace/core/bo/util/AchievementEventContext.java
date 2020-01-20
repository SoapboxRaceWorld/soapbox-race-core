/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.EventMode;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;

public class AchievementEventContext {
    private final EventMode eventMode;

    private final int eventModeInt;

    private final ArbitrationPacket arbitrationPacket;

    private final EventSessionEntity eventSessionEntity;

    public AchievementEventContext(EventMode eventMode, ArbitrationPacket arbitrationPacket,
                                   EventSessionEntity eventSessionEntity) {
        this.eventMode = eventMode;
        this.eventModeInt = eventMode.getEventModeId();
        this.arbitrationPacket = arbitrationPacket;
        this.eventSessionEntity = eventSessionEntity;
    }

    public EventMode getEventMode() {
        return eventMode;
    }

    public int getEventModeInt() {
        return eventModeInt;
    }

    public ArbitrationPacket getArbitrationPacket() {
        return arbitrationPacket;
    }

    public EventSessionEntity getEventSessionEntity() {
        return eventSessionEntity;
    }
}
