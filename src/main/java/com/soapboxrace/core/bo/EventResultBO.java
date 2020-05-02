package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.EventResult;

/**
 * Base class for {@link ArbitrationPacket} -> {@link EventResult} converters
 *
 * @param <TA> The type of {@link ArbitrationPacket} that this converter accepts
 * @param <TR> The type of {@link EventResult} that this converter produces
 */
public abstract class EventResultBO<TA extends ArbitrationPacket, TR extends EventResult> {
    /**
     * Converts the given {@link TA} instance to a new {@link TR} instance.
     *
     * @param eventSessionEntity The {@link EventSessionEntity} associated with the arbitration packet
     * @param activePersonaId    The ID of the current persona
     * @param packet             The {@link TA} instance
     * @return new {@link TR} instance
     */
    public TR handle(EventSessionEntity eventSessionEntity, Long activePersonaId, TA packet) {
        packet.setHacksDetected(packet.getHacksDetected() & ~32); // remove ModifiedFiles flag

        return handleInternal(eventSessionEntity, activePersonaId, packet);
    }

    /**
     * Internal method to convert the given {@link TA} instance to a new {@link TR} instance.
     *
     * @param eventSessionEntity The {@link EventSessionEntity} associated with the arbitration packet
     * @param activePersonaId    The ID of the current persona
     * @param packet             The {@link TA} instance
     * @return new {@link TR} instance
     */
    protected abstract TR handleInternal(EventSessionEntity eventSessionEntity, Long activePersonaId, TA packet);

    /**
     * Sets some basic properties of the given {@link EventDataEntity}
     *
     * @param eventDataEntity the {@link EventDataEntity} instance
     * @param activePersonaId the ID of the current persona
     * @param packet          the {@link TA} instance
     */
    protected void prepareBasicEventData(EventDataEntity eventDataEntity, Long activePersonaId, TA packet) {
        eventDataEntity.setAlternateEventDurationInMilliseconds(packet.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setCarId(packet.getCarId());
        eventDataEntity.setEventDurationInMilliseconds(packet.getEventDurationInMilliseconds());
        eventDataEntity.setFinishReason(packet.getFinishReason());
        eventDataEntity.setHacksDetected(packet.getHacksDetected());
        eventDataEntity.setRank(packet.getRank());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
    }
}
