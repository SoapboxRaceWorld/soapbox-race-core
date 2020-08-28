package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementEventContext;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.EventResult;
import com.soapboxrace.jaxb.http.ExitPath;

import javax.ejb.EJB;
import java.util.Map;

/**
 * Base class for {@link ArbitrationPacket} -> {@link EventResult} converters
 *
 * @param <TA> The type of {@link ArbitrationPacket} that this converter accepts
 * @param <TR> The type of {@link EventResult} that this converter produces
 */
public abstract class EventResultBO<TA extends ArbitrationPacket, TR extends EventResult> {

    @EJB
    protected MatchmakingBO matchmakingBO;

    @EJB
    protected LobbyBO lobbyBO;

    @EJB
    protected PersonaBO personaBO;

    @EJB
    protected PersonaDAO personaDAO;

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
    protected final void prepareBasicEventData(EventDataEntity eventDataEntity, Long activePersonaId, TA packet) {
        eventDataEntity.setAlternateEventDurationInMilliseconds(packet.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setCarId(packet.getCarId());
        eventDataEntity.setEventDurationInMilliseconds(packet.getEventDurationInMilliseconds());
        eventDataEntity.setFinishReason(packet.getFinishReason());
        eventDataEntity.setHacksDetected(packet.getHacksDetected());
        eventDataEntity.setRank(packet.getRank());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
        eventDataEntity.setServerTimeEnded(System.currentTimeMillis());
        eventDataEntity.setServerTimeInMilliseconds(eventDataEntity.getServerTimeEnded() - eventDataEntity.getServerTimeStarted());
    }

    /**
     * Sets up Race Again and updates the info on the given {@link TR} instance
     *
     * @param eventSessionEntity the {@link EventSessionEntity} instance
     * @param result             the {@link TR} instance
     */
    protected final void prepareRaceAgain(EventSessionEntity eventSessionEntity, TR result) {
        ExitPath exitPath = ExitPath.EXIT_TO_FREEROAM;
        EventEntity eventEntity = eventSessionEntity.getEvent();

        // Only set up Race Again if it's enabled for the event AND the session was multiplayer
        if (eventSessionEntity.getLobby() != null && eventEntity.isRaceAgainEnabled()) {
            LobbyEntity nextLobbyEntity = eventSessionEntity.getNextLobby();

            // If nextLobby hasn't been set, create a new lobby
            if (nextLobbyEntity == null) {
                LobbyEntity oldLobby = eventSessionEntity.getLobby();
                nextLobbyEntity = lobbyBO.createLobby(
                        oldLobby.getPersonaId(),
                        oldLobby.getEvent().getId(),
                        oldLobby.getEvent().getCarClassHash(),
                        oldLobby.getIsPrivate());
                eventSessionEntity.setNextLobby(nextLobbyEntity);
            }

            int inviteLifetime = nextLobbyEntity.getLobbyCountdownInMilliseconds(eventEntity.getLobbyCountdownTime());

            // lobby must have more than 6 seconds left
            if (inviteLifetime > 6000) {
                result.setLobbyInviteId(nextLobbyEntity.getId());
                result.setInviteLifetimeInMilliseconds(inviteLifetime);
                exitPath = ExitPath.EXIT_TO_LOBBY;
            }
        }

        result.setExitPath(exitPath);
    }

    /**
     * Trigger an EVENT achievement progress update
     *
     * @param eventDataEntity    the {@link EventDataEntity} instance
     * @param eventSessionEntity the {@link EventSessionEntity} instance
     * @param activePersonaId    the active persona ID
     * @param packet             the {@link TA} instance
     * @param transaction        the {@link AchievementTransaction} instance
     */
    protected void updateEventAchievements(EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity, Long activePersonaId, TA packet, AchievementTransaction transaction) {
        PersonaEntity personaEntity = personaDAO.find(activePersonaId);
        EventEntity eventEntity = eventDataEntity.getEvent();

        transaction.add("EVENT", Map.of(
                "persona", personaEntity,
                "event", eventEntity,
                "eventData", eventDataEntity,
                "eventSession", eventSessionEntity,
                "eventContext", new AchievementEventContext(EventMode.fromId(eventEntity.getEventModeId()), packet, eventSessionEntity),
                "car", personaBO.getDefaultCarEntity(activePersonaId)
        ));
    }
}
