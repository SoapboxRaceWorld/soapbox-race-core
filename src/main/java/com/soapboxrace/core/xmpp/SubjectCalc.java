package com.soapboxrace.core.xmpp;

public class SubjectCalc
{

    // CRT function converted to Java.
    // For UInt64 processing on 8086 UInt32 values.
    // Maybe not necessary, but I like to keep it close to the original.
    private static Object[] __allmul(long multiplier, long multiplicand)
    {
        long hiMultiplier = (multiplier >>> 32) & 0xffffffffL;
        long loMultiplier = ((multiplier << 32) >>> 32) & 0xffffffffL;

        long hiMultiplicand = (multiplicand >>> 32) & 0xffffffffL;
        long loMultiplicand = ((multiplicand << 32) >>> 32) & 0xffffffffL;

        long multiplied, loMultiplied, hiMultiplied, oldMultiplied;
        if (hiMultiplicand == 0 && hiMultiplier == 0)
            multiplied = loMultiplier * loMultiplicand;
        else
            multiplied = multiplier * multiplicand;
        oldMultiplied = multiplied;
        hiMultiplied = (multiplied >>> 32) & 0xffffffffL;
        loMultiplied = ((multiplied << 32) >>> 32) & 0xffffffffL;
        do
        { // modulo wrapper on UInt32
            if (loMultiplied < 0L || loMultiplied > 4294967295L)
                loMultiplied %= 4294967296L;
            if (hiMultiplied < 0L || hiMultiplied > 4294967295L)
                hiMultiplied %= 4294967296L;
        }
        while ((loMultiplied < 0L || hiMultiplied < 0L) && (loMultiplied > 4294967295L || hiMultiplied > 4294967295L));
        multiplied = hiMultiplied << 32 | loMultiplied;
        return new Object[]{multiplied, oldMultiplied == loMultiplied};
    }

    public static Long calculateHash(char[] jid, char[] response)
    {
        long multiplier = 0xffffffffL;
        boolean cFlag = true;
        for (char c : jid)
        {
            Object[] bHash = __allmul(multiplier, 33L & 0xffffffffL);
            long jidHash = (long) bHash[0];
            long hiJidHash = (jidHash >>> 32) & 0xffffffffL;
            long loJidHash = ((jidHash << 32) >>> 32) & 0xffffffffL;

            long loCdq = (long) c & 0xffffffffL;

            long hiMultiplier = (((hiJidHash >>> 32) + hiJidHash) + (cFlag ? 1L : 0L)) & 0xffffffffL;
            long loMultiplier = (((loJidHash << 32) >>> 32) + loCdq) & 0xffffffffL;

            multiplier = hiMultiplier << 32 | loMultiplier;
            cFlag = (boolean) bHash[1];
        }

        for (char c : response)
        {
            Object[] bHash = __allmul(multiplier, 33L & 0xffffffffL);
            long responseHash = (long) bHash[0];
            long hiJidHash = (responseHash >>> 32) & 0xffffffffL;
            long loJidHash = ((responseHash << 32) >>> 32) & 0xffffffffL;

            long loCdq = (long) c & 0xffffffffL;

            long hiMultiplier = (((hiJidHash >>> 32) + hiJidHash) + (cFlag ? 1L : 0L)) & 0xffffffffL;
            long loMultiplier = (((loJidHash << 32) >>> 32) + loCdq) & 0xffffffffL;

            multiplier = hiMultiplier << 32 | loMultiplier;
            cFlag = (boolean) bHash[1];
        }
        return multiplier;
    }
}