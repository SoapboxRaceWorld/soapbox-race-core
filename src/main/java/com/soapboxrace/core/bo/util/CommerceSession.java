/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import java.util.ArrayList;
import java.util.List;

public class CommerceSession {
    private final SessionType sessionType;
    private final List<CommerceSessionItem> items;

    public CommerceSession(SessionType sessionType) {
        this.sessionType = sessionType;
        this.items = new ArrayList<>();
    }

    public List<CommerceSessionItem> getItems() {
        return items;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public enum SessionType {
        PAINT, SKILL_MODS, PERFORMANCE, VINYLS, AFTERMARKET, UNKNOWN
    }
}
