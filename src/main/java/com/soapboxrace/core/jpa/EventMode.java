/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;

public enum EventMode {

    SPRINT(9), CIRCUIT(4), DRAG(19), PURSUIT_SP(12), PURSUIT_MP(24), MEETINGPLACE(22);
    private final int eventModeId;

    EventMode(int eventModeId) {
        this.eventModeId = eventModeId;
    }

    public static EventMode fromId(int id) {
        for (EventMode type : EventMode.values()) {
            if (type.getEventModeId() == id) {
                return type;
            }
        }
        throw new EngineException("Invalid eventModeId: " + id, EngineExceptionCode.UnspecifiedError, true);
    }

    public int getEventModeId() {
        return eventModeId;
    }
}
