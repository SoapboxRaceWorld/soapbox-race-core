/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa.util;

public enum NewsArticleFilters {
    None(0),
    Me(1),
    Friends(2),
    System(4),
    Item_Count(6),
    Crew(8),
    All(2147483647);

    private final int filterMask;

    NewsArticleFilters(int filterMask) {
        this.filterMask = filterMask;
    }

    public int getFilterMask() {
        return filterMask;
    }
}