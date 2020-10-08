/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import java.util.HashMap;
import java.util.Map;

public class SceneryUtil {
    private static final Map<String, Long> sceneryIds = new HashMap<>();

    static {
        sceneryIds.put("SCENERY_GROUP_NORMAL", 0L);
        sceneryIds.put("SCENERY_GROUP_OKTOBERFEST", 1L);
        sceneryIds.put("SCENERY_GROUP_HALLOWEEN", 2L);
        sceneryIds.put("SCENERY_GROUP_CHRISTMAS", 3L);
        sceneryIds.put("SCENERY_GROUP_NEWYEARS", 5L);
    }

    public static long getSceneryId(String scenery) {
        return sceneryIds.getOrDefault(scenery, 0L);
    }

    public static boolean isValid(String scenery) {
        return sceneryIds.containsKey(scenery);
    }
}
