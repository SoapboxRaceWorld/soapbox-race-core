/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.LobbyDAO;
import com.soapboxrace.core.dao.LobbyEntrantDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.jaxb.http.ArrayOfLobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyCountdown;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyInfo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Stateless
public class LobbyBO {
    @EJB
    private MatchmakingBO matchmakingBO;

    @EJB
    private PersonaBO personaBO;

    @EJB
    private EventDAO eventDao;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LobbyDAO lobbyDao;

    @EJB
    private LobbyEntrantDAO lobbyEntrantDao;

    @EJB
    private OpenFireRestApiCli openFireRestApiCli;

    @EJB
    private LobbyCountdownBO lobbyCountdownBO;

    @EJB
    private LobbyMessagingBO lobbyMessagingBO;

    public void joinFastLobby(Long personaId, int carClassHash) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        List<LobbyEntity> lobbys = lobbyDao.findAllOpen(carClassHash, personaEntity.getLevel());

        if (lobbys.isEmpty()) {
            matchmakingBO.addPlayerToQueue(personaId, carClassHash);
        } else {
            Collections.shuffle(lobbys);
            joinLobby(personaEntity, lobbys, true);
        }
    }

    public void joinQueueEvent(Long personaId, int eventId) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        EventEntity eventEntity = eventDao.find(eventId);

        CarEntity carEntity = personaBO.getDefaultCarEntity(personaId);

        // only check restriction on non-open events
        if (eventEntity.getCarClassHash() != 607077938) {
            if (carEntity.getCarClassHash() != eventEntity.getCarClassHash()) {
                // The client UI does not allow you to join events outside your current car's class
                throw new EngineException(EngineExceptionCode.CarDataInvalid, true);
            }
        }

        List<LobbyEntity> lobbys = lobbyDao.findByEventStarted(eventEntity);
        if (lobbys.size() == 0) {
            createLobby(personaEntity.getPersonaId(), eventId, eventEntity.getCarClassHash(), false);
        } else {
            joinLobby(personaEntity, lobbys);
        }
    }

    public void createPrivateLobby(Long creatorPersonaId, int eventId) {
        List<Long> personaIdList = openFireRestApiCli.getAllPersonaByGroup(creatorPersonaId);
        EventEntity eventEntity = eventDao.find(eventId);
        if (!personaIdList.isEmpty()) {
            createLobby(creatorPersonaId, eventId, eventEntity.getCarClassHash(), true);

            LobbyEntity lobbyEntity = lobbyDao.findByEventAndPersona(eventEntity, creatorPersonaId);
            if (lobbyEntity != null) {
                for (Long recipientPersonaId : personaIdList) {
                    if (!recipientPersonaId.equals(creatorPersonaId)) {
                        PersonaEntity recipientPersonaEntity = personaDao.find(recipientPersonaId);

                        lobbyMessagingBO.sendLobbyInvitation(lobbyEntity, recipientPersonaEntity, eventEntity.getLobbyCountdownTime());
                    }
                }
            }
        }
    }

    public LobbyEntity createLobby(Long personaId, int eventId, int carClassHash, Boolean isPrivate) {
        EventEntity eventEntity = eventDao.find(eventId);

        LobbyEntity lobbyEntity = new LobbyEntity();
        lobbyEntity.setEvent(eventEntity);
        lobbyEntity.setIsPrivate(isPrivate);
        lobbyEntity.setPersonaId(personaId);
        lobbyEntity.setStartedTime(LocalDateTime.now());

        lobbyDao.insert(lobbyEntity);

        PersonaEntity personaEntity = personaDao.find(personaId);
        lobbyMessagingBO.sendLobbyInvitation(lobbyEntity, personaEntity, 10000);

        if (!isPrivate) {
            for (int i = 1; i <= lobbyEntity.getEvent().getMaxPlayers() - 1; i++) {
                if (lobbyEntity.getEntrants().size() >= lobbyEntity.getEvent().getMaxPlayers()) break;

                Long queuePersonaId = matchmakingBO.getPlayerFromQueue(carClassHash);

                if (!queuePersonaId.equals(-1L) && !matchmakingBO.isEventIgnored(queuePersonaId, eventId)) {
                    if (lobbyEntity.getEntrants().size() < lobbyEntity.getEvent().getMaxPlayers()) {
                        PersonaEntity queuePersona = personaDao.find(queuePersonaId);
                        lobbyMessagingBO.sendLobbyInvitation(lobbyEntity, queuePersona, eventEntity.getLobbyCountdownTime());
                    }
                }
            }
        }

        lobbyCountdownBO.scheduleLobbyStart(lobbyEntity);

        return lobbyEntity;
    }

    private void joinLobby(PersonaEntity personaEntity, List<LobbyEntity> lobbys) {
        joinLobby(personaEntity, lobbys, false);
    }

    private void joinLobby(PersonaEntity personaEntity, List<LobbyEntity> lobbys, boolean checkIgnoredEvents) {
        LobbyEntity lobbyEntity = null;
        for (LobbyEntity lobbyEntityTmp : lobbys) {
            if (lobbyEntityTmp.getIsPrivate()) continue;
            EventEntity event = lobbyEntityTmp.getEvent();
            if (checkIgnoredEvents && matchmakingBO.isEventIgnored(personaEntity.getPersonaId(), event.getId()))
                continue;
            int maxEntrants = event.getMaxPlayers();
            List<LobbyEntrantEntity> lobbyEntrants = lobbyEntityTmp.getEntrants();
            int entrantsSize = lobbyEntrants.size();
            if (entrantsSize < maxEntrants) {
                lobbyEntity = lobbyEntityTmp;
                if (!isPersonaInside(personaEntity.getPersonaId(), lobbyEntrants)) {
                    LobbyEntrantEntity lobbyEntrantEntity = new LobbyEntrantEntity();
                    lobbyEntrantEntity.setPersona(personaEntity);
                    lobbyEntrantEntity.setLobby(lobbyEntity);
                    lobbyEntrants.add(lobbyEntrantEntity);
                }
                break;
            }
        }
        if (lobbyEntity != null) {
            lobbyMessagingBO.sendLobbyInvitation(lobbyEntity, personaEntity, 10000);
        }
    }

    private boolean isPersonaInside(Long personaId, List<LobbyEntrantEntity> lobbyEntrants) {
        for (LobbyEntrantEntity lobbyEntrantEntity : lobbyEntrants) {
            Long entrantPersonaId = lobbyEntrantEntity.getPersona().getPersonaId();
            if (Objects.equals(entrantPersonaId, personaId)) {
                return true;
            }
        }
        return false;
    }

    public void declineinvite(Long activePersonaId, Long lobbyInviteId) {
        LobbyEntity lobbyEntity = lobbyDao.find(lobbyInviteId);

        if (lobbyEntity == null) {
            return;
        }

        matchmakingBO.ignoreEvent(activePersonaId, lobbyEntity.getEvent().getId());
    }

    public LobbyInfo acceptinvite(Long personaId, Long lobbyInviteId) {
        LobbyEntity lobbyEntity = lobbyDao.find(lobbyInviteId);
        PersonaEntity personaEntity = personaDao.find(personaId);

        if (lobbyEntity == null) {
            throw new EngineException(EngineExceptionCode.GameDoesNotExist, false);
        }

        if (personaEntity == null) {
            throw new EngineException(EngineExceptionCode.PersonaNotFound, false);
        }

        int eventId = lobbyEntity.getEvent().getId();

        LobbyCountdown lobbyCountdown = new LobbyCountdown();
        lobbyCountdown.setLobbyId(lobbyInviteId);
        lobbyCountdown.setEventId(eventId);
        lobbyCountdown.setLobbyCountdownInMilliseconds(lobbyEntity.getLobbyCountdownInMilliseconds(lobbyEntity.getEvent().getLobbyCountdownTime()));
        lobbyCountdown.setLobbyStuckDurationInMilliseconds(10000);

        ArrayOfLobbyEntrantInfo arrayOfLobbyEntrantInfo = new ArrayOfLobbyEntrantInfo();
        List<LobbyEntrantInfo> lobbyEntrantInfo = arrayOfLobbyEntrantInfo.getLobbyEntrantInfo();

        List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();

        if (entrants.size() >= lobbyEntity.getEvent().getMaxPlayers()) {
            throw new EngineException(EngineExceptionCode.GameLocked, false);
        }

        if (lobbyCountdown.getLobbyCountdownInMilliseconds() <= 6000) {
            throw new EngineException(EngineExceptionCode.GameLocked, false);
        }

        matchmakingBO.removePlayerFromQueue(personaId);
        for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
            if (!Objects.equals(personaEntity.getPersonaId(), lobbyEntrantEntity.getPersona().getPersonaId())) {
                lobbyMessagingBO.sendJoinMessage(lobbyEntity, personaEntity, lobbyEntrantEntity.getPersona());
            }
        }
        boolean personaInside = false;
        for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
            LobbyEntrantInfo LobbyEntrantInfo = new LobbyEntrantInfo();
            LobbyEntrantInfo.setPersonaId(lobbyEntrantEntity.getPersona().getPersonaId());
            LobbyEntrantInfo.setLevel(lobbyEntrantEntity.getPersona().getLevel());
            LobbyEntrantInfo.setGridIndex(lobbyEntrantEntity.getGridIndex());
            lobbyEntrantInfo.add(LobbyEntrantInfo);
            if (lobbyEntrantEntity.getPersona().getPersonaId().equals(personaId)) {
                personaInside = true;
            }
        }
        if (!personaInside) {
            LobbyEntrantEntity lobbyEntrantEntity = new LobbyEntrantEntity();
            lobbyEntrantEntity.setPersona(personaEntity);
            lobbyEntrantEntity.setLobby(lobbyEntity);
            lobbyEntrantEntity.setGridIndex(entrants.size());
            lobbyEntity.getEntrants().add(lobbyEntrantEntity);
            lobbyDao.update(lobbyEntity);
            LobbyEntrantInfo LobbyEntrantInfo = new LobbyEntrantInfo();
            LobbyEntrantInfo.setPersonaId(lobbyEntrantEntity.getPersona().getPersonaId());
            LobbyEntrantInfo.setLevel(lobbyEntrantEntity.getPersona().getLevel());
            LobbyEntrantInfo.setGridIndex(lobbyEntrantEntity.getGridIndex());
            lobbyEntrantInfo.add(LobbyEntrantInfo);
        }

        LobbyInfo lobbyInfoType = new LobbyInfo();
        lobbyInfoType.setCountdown(lobbyCountdown);
        lobbyInfoType.setEntrants(arrayOfLobbyEntrantInfo);
        lobbyInfoType.setEventId(eventId);
        lobbyInfoType.setLobbyInviteId(lobbyInviteId);
        lobbyInfoType.setLobbyId(lobbyInviteId);

        return lobbyInfoType;
    }

    public void removeEntrantFromLobby(Long personaId, Long lobbyId) {
        LobbyEntity lobbyEntity = lobbyDao.find(lobbyId);
        PersonaEntity personaEntity = personaDao.find(personaId);

        if (lobbyEntity == null) {
            throw new EngineException(EngineExceptionCode.GameDoesNotExist, false);
        }

        if (personaEntity == null) {
            throw new EngineException(EngineExceptionCode.PersonaNotFound, false);
        }

        lobbyEntrantDao.deleteByPersonaAndLobby(personaEntity, lobbyEntity);
        List<LobbyEntrantEntity> listLobbyEntrantEntity = lobbyEntity.getEntrants();
        for (LobbyEntrantEntity entity : listLobbyEntrantEntity) {
            if (!Objects.equals(entity.getPersona().getPersonaId(), personaId)) {
                lobbyMessagingBO.sendLeaveMessage(lobbyEntity, personaEntity, entity.getPersona());
            }
        }
    }
}
