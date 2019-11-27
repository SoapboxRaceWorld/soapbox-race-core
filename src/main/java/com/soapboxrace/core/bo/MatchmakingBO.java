/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.bo;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MatchmakingBO {
    private final Map<Long, Integer> queue;

    public MatchmakingBO() {
        queue = new HashMap<>();
    }

    public void addToQueue(Long personaId, Integer carClass) {
        queue.put(personaId, carClass);
    }

    public Long get(Integer carClass) {
        for (Map.Entry<Long, Integer> entry : queue.entrySet()) {
            if (entry.getValue().equals(carClass)) {
                return entry.getKey();
            }
        }

        if (queue.isEmpty()) {
            return null;
        }

        final Optional<Long> firstEntry = queue.entrySet().stream().findFirst().map(Map.Entry::getKey);

        return firstEntry.orElse(null);
    }

    public void removeFromQueue(Long personaId) {
        queue.remove(personaId);
    }
}
