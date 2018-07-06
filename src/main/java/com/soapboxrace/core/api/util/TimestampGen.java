package com.soapboxrace.core.api.util;

public class TimestampGen
{
    private static final long TICKS_AT_EPOCH = 621355968000000000L;

    /**
     * Generate a timestamp that is compatible with NFS:World.
     * @param curtime The current time in milliseconds.
     * @return The generated timestamp
     */
    public static long generateTimestamp(long curtime)
    {
        return curtime * 10000 + TICKS_AT_EPOCH;
    }
}
