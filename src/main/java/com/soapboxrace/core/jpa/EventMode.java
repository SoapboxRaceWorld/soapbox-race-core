/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.jpa;

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
        return null;
    }

    public int getEventModeId() {
        return eventModeId;
    }
}
