package com.soapboxrace.core.bo;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MatchmakingBO
{
    private final Map<Long, Integer> queue;

    public MatchmakingBO()
    {
        queue = new HashMap<>();
    }

    public void addToQueue(Long personaId, Integer carClass)
    {
        queue.put(personaId, carClass);
    }

    public Long getByClass(Integer carClass)
    {
        System.out.println("getByClass: " + carClass);
        for (Map.Entry<Long, Integer> entry : queue.entrySet()) {
            if (entry.getValue().equals(carClass)) {
                System.out.println("found one!");
                return entry.getKey();
            }
        }
        
        return null;
    }

    public void removeFromQueue(Long personaId)
    {
        queue.remove(personaId);
    }
}
