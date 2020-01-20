/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import java.util.Date;

public class TimeUtil {
    private static final long TICKS_AT_EPOCH = 621355968000000000L;

    public static long getTicks() {
        return System.currentTimeMillis() * 10000 + TICKS_AT_EPOCH;
    }

    public static Date ticksToDate(long ticks) {
        return new Date((ticks - TICKS_AT_EPOCH) / 10000 /* ticks per millisecond */);
    }
}