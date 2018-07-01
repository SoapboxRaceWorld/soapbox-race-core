package com.soapboxrace.core.bo;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PresenceManager
{
    private final Map<Long, Integer> presenceMap;

    public PresenceManager()
    {
        presenceMap = new HashMap<>();
    }

    public void setPresence(long personaId, int presence)
    {
        presenceMap.put(personaId, presence);
    }

    public void removePresence(long personaId)
    {
        presenceMap.remove(personaId);
    }

    public int getPresence(long personaId)
    {
        return presenceMap.getOrDefault(personaId, 0);
    }
}