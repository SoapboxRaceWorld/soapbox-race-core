/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiscUtils {
    public static long lengthToSeconds(String string) {
        if (string.equals("0") || string.equals("")) return 0;
        String[] lifeMatch = new String[]{"d", "h", "m", "s"};
        int[] lifeInterval = new int[]{86400, 3600, 60, 1};
        long seconds = 0L;

        for (int i = 0; i < lifeMatch.length; i++) {
            Matcher matcher = Pattern.compile("([0-9]+)" + lifeMatch[i]).matcher(string);
            while (matcher.find()) {
                seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i];
            }

        }
        return seconds;
    }

    public static String argsToString(String[] args, int start, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(args[i]);
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static long lengthToMiliseconds(String string) {
        return lengthToSeconds(string) * 1000;
    }
}
