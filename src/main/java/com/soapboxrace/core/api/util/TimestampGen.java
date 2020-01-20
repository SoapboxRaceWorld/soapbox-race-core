/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

public class TimestampGen {
    private static final long TICKS_AT_EPOCH = 621355968000000000L;

    /**
     * Generate a timestamp that is compatible with NFS:World.
     *
     * @param curtime The current time in milliseconds.
     * @return The generated timestamp
     */
    public static long generateTimestamp(long curtime) {
        return curtime * 10000 + TICKS_AT_EPOCH;
    }
}
