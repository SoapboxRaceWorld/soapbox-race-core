package com.soapboxrace.core.events;

import lombok.Data;

@Data
public class PersonaPresenceUpdated {
    private final Long personaId;

    private final Long presence;
}
