package com.soapboxrace.core.jpa.util;

public enum NewsArticleFilters
{
    None(0),
    Me(1),
    Friends(2),
    System(4),
    Item_Count(6),
    Crew(8),
    All(2147483647);

    private final int filterMask;

    NewsArticleFilters(int filterMask)
    {
        this.filterMask = filterMask;
    }

    public int getFilterMask()
    {
        return filterMask;
    }
}