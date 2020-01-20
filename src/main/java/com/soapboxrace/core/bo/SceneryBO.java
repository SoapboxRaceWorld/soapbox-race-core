/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class SceneryBO {
    private final Map<String, Long> sceneryIds = new HashMap<>();

    {
        sceneryIds.put("SCENERY_GROUP_NORMAL", 0L);
        sceneryIds.put("SCENERY_GROUP_OKTOBERFEST", 1L);
        sceneryIds.put("SCENERY_GROUP_HALLOWEEN", 2L);
        sceneryIds.put("SCENERY_GROUP_CHRISTMAS", 3L);
        sceneryIds.put("SCENERY_GROUP_NEWYEARS", 5L);
    }

    public long getSceneryId(String scenery) {
        return sceneryIds.getOrDefault(scenery, 0L);
    }

    public boolean isValid(String scenery) {
        return sceneryIds.containsKey(scenery);
    }
}
