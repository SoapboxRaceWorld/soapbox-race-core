package com.soapboxrace.core.bo.util;

public class AchievementProgressionContext {
    private final Integer cash;

    private final Integer rep;

    private final Integer level;

    private final boolean levelChanged;

    private final boolean inEvent;

    public AchievementProgressionContext(Integer cash, Integer rep, Integer level, boolean levelChanged,
                                         boolean inEvent) {
        this.cash = cash;
        this.rep = rep;
        this.level = level;
        this.levelChanged = levelChanged;
        this.inEvent = inEvent;
    }

    public Integer getCash() {
        return cash;
    }

    public Integer getRep() {
        return rep;
    }

    public Integer getLevel() {
        return level;
    }

    public boolean isLevelChanged() {
        return levelChanged;
    }

    public boolean isInEvent() {
        return inEvent;
    }
}
