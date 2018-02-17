package com.soapboxrace.core.bo;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

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
