package com.soapboxrace.core.events;

import lombok.Data;

@Data
public class PersonaPresenceUpdated {
    private Long personaId;
    private Long presence;

    public PersonaPresenceUpdated(Long personaId, Long presence) {
        this.presence = presence;
        this.personaId = personaId;
    }

    public Long getPresence() { 
        return this.presence;
    }

    public Long getPersonaId() {
        return this.personaId;
    }

    public void setPresence(Long presence) { 
        this.presence = presence;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }
}
