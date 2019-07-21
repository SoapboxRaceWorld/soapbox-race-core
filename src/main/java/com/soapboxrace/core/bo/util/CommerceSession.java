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
