/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppLobby;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Comparator;
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
    private EventSessionDAO eventSessionDao;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private TokenSessionDAO tokenDAO;

    @EJB
    private LobbyDAO lobbyDao;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private LobbyEntrantDAO lobbyEntrantDao;

    @EJB
    private OpenFireRestApiCli openFireRestApiCli;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    public void joinFastLobby(Long personaId, int carClassHash) {
        List<LobbyEntity> lobbys = lobbyDao.findAllOpen(carClassHash);

        if (lobbys.isEmpty()) {
            matchmakingBO.addPlayerToQueue(personaId, carClassHash);
        } else {
            PersonaEntity personaEntity = personaDao.findById(personaId);
            joinLobby(personaEntity, lobbys);
        }
    }

    public void joinQueueEvent(Long personaId, int eventId) {
        PersonaEntity personaEntity = personaDao.findById(personaId);
        EventEntity eventEntity = eventDao.findById(eventId);

        CarSlotEntity defaultCarEntity = personaBO.getDefaultCarEntity(personaId);
        OwnedCarEntity ownedCarEntity = defaultCarEntity.getOwnedCar();
        CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();

        // only check restriction on non-open events
        if (eventEntity.getCarClassHash() != 607077938) {
            if (customCarEntity.getCarClassHash() != eventEntity.getCarClassHash()) {
                // The client UI does not allow you to join events outside your current car's class
                throw new EngineException(EngineExceptionCode.CarDataInvalid);
            }
        }

        List<LobbyEntity> lobbys = lobbyDao.findByEventStarted(eventId);
        if (lobbys.size() == 0) {
            createLobby(personaEntity, eventId, eventEntity.getCarClassHash(), false);
        } else {
            joinLobby(personaEntity, lobbys);
        }
    }

    public void createPrivateLobby(Long personaId, int eventId) {
        List<Long> listOfPersona = openFireRestApiCli.getAllPersonaByGroup(personaId);
        EventEntity eventEntity = eventDao.findById(eventId);
        if (!listOfPersona.isEmpty()) {
            PersonaEntity personaEntity = personaDao.findById(personaId);
            createLobby(personaEntity, eventId, eventEntity.getCarClassHash(), true);

            LobbyEntity lobbys = lobbyDao.findByEventAndPersona(eventId, personaId);
            if (lobbys != null) {
                XMPP_LobbyInviteType lobbyInviteType = new XMPP_LobbyInviteType();
                lobbyInviteType.setEventId(eventId);
                lobbyInviteType.setInvitedByPersonaId(personaId);
                lobbyInviteType.setInviteLifetimeInMilliseconds(parameterBO.getIntParam("LOBBY_COUNTDOWN_TIME", 60000));
                lobbyInviteType.setPrivate(true);
                lobbyInviteType.setLobbyInviteId(lobbys.getId());

                for (Long idPersona : listOfPersona) {
                    if (!idPersona.equals(personaId)) {
                        XmppLobby xmppLobby = new XmppLobby(idPersona, openFireSoapBoxCli);
                        xmppLobby.sendLobbyInvite(lobbyInviteType);
                    }
                }
            }
        }
    }

    private void createLobby(PersonaEntity personaEntity, int eventId, int carClassHash, Boolean isPrivate) {
        EventEntity eventEntity = eventDao.findById(eventId);

        LobbyEntity lobbyEntity = new LobbyEntity();
        lobbyEntity.setEvent(eventEntity);
        lobbyEntity.setIsPrivate(isPrivate);
        lobbyEntity.setPersonaId(personaEntity.getPersonaId());
        lobbyEntity.setStartedTime(LocalDateTime.now());

        lobbyDao.insert(lobbyEntity);

        sendJoinEvent(personaEntity.getPersonaId(), lobbyEntity);

        if (!isPrivate) {
            XMPP_LobbyInviteType lobbyInviteType = new XMPP_LobbyInviteType();
            lobbyInviteType.setEventId(eventId);
            lobbyInviteType.setInvitedByPersonaId(personaEntity.getPersonaId());
            lobbyInviteType.setInviteLifetimeInMilliseconds(parameterBO.getIntParam("LOBBY_COUNTDOWN_TIME", 60000));
            lobbyInviteType.setPrivate(false);
            lobbyInviteType.setLobbyInviteId(lobbyEntity.getId());

            for (int i = 1; i <= lobbyEntity.getEvent().getMaxPlayers() - 1; i++) {
                if (lobbyEntity.getEntrants().size() >= lobbyEntity.getEvent().getMaxPlayers()) break;

                Long queuePersona = matchmakingBO.getPlayerFromQueue(carClassHash);

                if (!queuePersona.equals(-1L)) {
                    if (lobbyEntity.getEntrants().size() < lobbyEntity.getEvent().getMaxPlayers()) {
                        XmppLobby xmppLobby = new XmppLobby(queuePersona, openFireSoapBoxCli);
                        xmppLobby.sendLobbyInvite(lobbyInviteType);
                    }
                }
            }
        }

        new LobbyCountDown(lobbyEntity.getId(), lobbyDao, eventSessionDao, tokenDAO, parameterBO, openFireSoapBoxCli).start();
    }

    private void joinLobby(PersonaEntity personaEntity, List<LobbyEntity> lobbys) {
        LobbyEntity lobbyEntity = null;
        for (LobbyEntity lobbyEntityTmp : lobbys) {
            if (lobbyEntityTmp.getIsPrivate()) continue;

            int maxEntrants = lobbyEntityTmp.getEvent().getMaxPlayers();
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
            sendJoinEvent(personaEntity.getPersonaId(), lobbyEntity);
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

    private void sendJoinEvent(Long personaId, LobbyEntity lobbyEntity) {
        int eventId = lobbyEntity.getEvent().getId();
        Long lobbyId = lobbyEntity.getId();

        XMPP_LobbyInviteType xMPP_LobbyInviteType = new XMPP_LobbyInviteType();
        xMPP_LobbyInviteType.setEventId(eventId);
        xMPP_LobbyInviteType.setLobbyInviteId(lobbyId);

        XmppLobby xmppLobby = new XmppLobby(personaId, openFireSoapBoxCli);
        xmppLobby.sendLobbyInvite(xMPP_LobbyInviteType);
    }

    public LobbyInfo acceptinvite(Long personaId, Long lobbyInviteId) {
        LobbyEntity lobbyEntity = lobbyDao.findById(lobbyInviteId);
        int eventId = lobbyEntity.getEvent().getId();

        LobbyCountdown lobbyCountdown = new LobbyCountdown();
        lobbyCountdown.setLobbyId(lobbyInviteId);
        lobbyCountdown.setEventId(eventId);
        lobbyCountdown.setLobbyCountdownInMilliseconds(lobbyEntity.getLobbyCountdownInMilliseconds(parameterBO.getIntParam("LOBBY_COUNTDOWN_TIME", 60000)));
        lobbyCountdown.setLobbyStuckDurationInMilliseconds(10000);

        ArrayOfLobbyEntrantInfo arrayOfLobbyEntrantInfo = new ArrayOfLobbyEntrantInfo();
        List<LobbyEntrantInfo> lobbyEntrantInfo = arrayOfLobbyEntrantInfo.getLobbyEntrantInfo();

        List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();

        if (entrants.size() >= lobbyEntity.getEvent().getMaxPlayers()) {
            return new LobbyInfo();
        }

        matchmakingBO.removePlayerFromQueue(personaId);

        sendJoinMsg(personaId, entrants);
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
            PersonaEntity personaEntity = personaDao.findById(personaId);
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

    public void sendJoinMsg(Long personaId, List<LobbyEntrantEntity> lobbyEntrants) {
        for (LobbyEntrantEntity lobbyEntrantEntity : lobbyEntrants) {
            LobbyEntrantAdded lobbyEntrantAdded = new LobbyEntrantAdded();
            if (!Objects.equals(personaId, lobbyEntrantEntity.getPersona().getPersonaId())) {
                lobbyEntrantAdded.setHeat(1);
                lobbyEntrantAdded.setLevel(lobbyEntrantEntity.getPersona().getLevel());
                lobbyEntrantAdded.setPersonaId(personaId);
                lobbyEntrantAdded.setLobbyId(lobbyEntrantEntity.getLobby().getId());
                XmppLobby xmppLobby = new XmppLobby(lobbyEntrantEntity.getPersona().getPersonaId(), openFireSoapBoxCli);
                xmppLobby.sendJoinMsg(lobbyEntrantAdded);
            }
        }
    }

    public void deleteLobbyEntrant(Long personaId, Long lobbyId) {
        PersonaEntity personaEntity = personaDao.findById(personaId);
        lobbyEntrantDao.deleteByPersona(personaEntity);
        updateLobby(personaId, lobbyId);
    }

    private void updateLobby(Long personaId, Long lobbyId) {
        LobbyEntity lobbyEntity = lobbyDao.findById(lobbyId);
        List<LobbyEntrantEntity> listLobbyEntrantEntity = lobbyEntity.getEntrants();
        for (LobbyEntrantEntity entity : listLobbyEntrantEntity) {
            LobbyEntrantRemoved lobbyEntrantRemoved = new LobbyEntrantRemoved();
            if (!Objects.equals(entity.getPersona().getPersonaId(), personaId)) {
                lobbyEntrantRemoved.setPersonaId(personaId);
                lobbyEntrantRemoved.setLobbyId(lobbyId);
                XmppLobby xmppLobby = new XmppLobby(entity.getPersona().getPersonaId(), openFireSoapBoxCli);
                xmppLobby.sendExitMsg(lobbyEntrantRemoved);
            }
        }
    }

    private class LobbyCountDown extends Thread {
        private LobbyDAO lobbyDao;

        private EventSessionDAO eventSessionDao;

        private Long lobbyId;

        private TokenSessionDAO tokenDAO;

        private ParameterBO parameterBO;

        private OpenFireSoapBoxCli openFireSoapBoxCli;

        public LobbyCountDown(Long lobbyId, LobbyDAO lobbyDao, EventSessionDAO eventSessionDao,
                              TokenSessionDAO tokenDAO, ParameterBO parameterBO,
                              OpenFireSoapBoxCli openFireSoapBoxCli) {
            this.lobbyId = lobbyId;
            this.lobbyDao = lobbyDao;
            this.eventSessionDao = eventSessionDao;
            this.tokenDAO = tokenDAO;
            this.parameterBO = parameterBO;
            this.openFireSoapBoxCli = openFireSoapBoxCli;
        }

        public void run() {
            try {
                Thread.sleep(parameterBO.getIntParam("LOBBY_COUNTDOWN_TIME", 60000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            LobbyEntity lobbyEntity = lobbyDao.findById(lobbyId);
            List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();
            if (entrants.size() < 2) {
                return;
            }
            entrants.sort(Comparator.<LobbyEntrantEntity>comparingLong(e -> e.getPersona().getPersonaId()).reversed());
            XMPP_LobbyLaunchedType lobbyLaunched = new XMPP_LobbyLaunchedType();
            Entrants entrantsType = new Entrants();
            List<LobbyEntrantInfo> lobbyEntrantInfo = entrantsType.getLobbyEntrantInfo();
            XMPP_CryptoTicketsType xMPP_CryptoTicketsType = new XMPP_CryptoTicketsType();
            List<XMPP_P2PCryptoTicketType> p2pCryptoTicket = xMPP_CryptoTicketsType.getP2PCryptoTicket();
            int i = 0;
            byte numOfRacers = (byte) entrants.size();
            EventSessionEntity eventSessionEntity = new EventSessionEntity();
            eventSessionEntity.setStarted(System.currentTimeMillis());
            eventSessionEntity.setEvent(lobbyEntity.getEvent());
            eventSessionEntity.setLobby(lobbyEntity);
            eventSessionDao.insert(eventSessionEntity);
            String udpRaceIp = parameterBO.getStrParam("UDP_RACE_IP");
            for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
                // eventDataEntity.setIsSinglePlayer(false);
                Long personaId = lobbyEntrantEntity.getPersona().getPersonaId();
                // eventDataEntity.setPersonaId(personaId);
                byte gridIndex = (byte) i;
                byte[] helloPacket = {10, 11, 12, 13};
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                byteBuffer.put(gridIndex);
                byteBuffer.put(helloPacket);
                byteBuffer.putInt(eventSessionEntity.getId().intValue());
                byteBuffer.put(numOfRacers);
                byteBuffer.putInt(personaId.intValue());
                byte[] cryptoTicketBytes = byteBuffer.array();
                String relayCrypotTicket = Base64.getEncoder().encodeToString(cryptoTicketBytes);
                tokenDAO.updateRelayCrytoTicketByPersonaId(personaId, relayCrypotTicket);

                XMPP_P2PCryptoTicketType p2pCryptoTicketType = new XMPP_P2PCryptoTicketType();
                p2pCryptoTicketType.setPersonaId(personaId);
                p2pCryptoTicketType.setSessionKey("AAAAAAAAAAAAAAAAAAAAAA==");
                p2pCryptoTicket.add(p2pCryptoTicketType);

                LobbyEntrantInfo lobbyEntrantInfoType = new LobbyEntrantInfo();
                lobbyEntrantInfoType.setPersonaId(personaId);
                lobbyEntrantInfoType.setLevel(lobbyEntrantEntity.getPersona().getLevel());
                lobbyEntrantInfoType.setHeat(1);
                lobbyEntrantInfoType.setGridIndex(i++);
                lobbyEntrantInfoType.setState(LobbyEntrantState.UNKNOWN);

                lobbyEntrantInfo.add(lobbyEntrantInfoType);
            }
            XMPP_EventSessionType xMPP_EventSessionType = new XMPP_EventSessionType();
            ChallengeType challengeType = new ChallengeType();
            challengeType.setChallengeId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            challengeType.setPattern("FFFFFFFFFFFFFFFF");
            challengeType.setLeftSize(14);
            challengeType.setRightSize(50);

            xMPP_EventSessionType.setEventId(lobbyEntity.getEvent().getId());
            xMPP_EventSessionType.setChallenge(challengeType);
            xMPP_EventSessionType.setSessionId(eventSessionEntity.getId());
            lobbyLaunched.setNewRelayServer(true);
            lobbyLaunched.setLobbyId(lobbyEntity.getId());
            lobbyLaunched.setUdpRelayHost(udpRaceIp);
            lobbyLaunched.setUdpRelayPort(parameterBO.getIntParam("UDP_RACE_PORT"));

            lobbyLaunched.setEntrants(entrantsType);

            lobbyLaunched.setEventSession(xMPP_EventSessionType);

            XmppLobby xmppLobby = new XmppLobby(0L, openFireSoapBoxCli);
            xmppLobby.sendRelay(lobbyLaunched, xMPP_CryptoTicketsType);
        }
    }

}
