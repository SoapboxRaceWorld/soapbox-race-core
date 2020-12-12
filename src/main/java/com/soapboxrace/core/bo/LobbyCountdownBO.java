package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.LobbyDAO;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;
import com.soapboxrace.jaxb.http.Entrants;
import com.soapboxrace.jaxb.http.LobbyEntrantInfo;
import com.soapboxrace.jaxb.http.LobbyEntrantState;
import com.soapboxrace.jaxb.xmpp.*;

import javax.annotation.Resource;
import javax.ejb.*;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Singleton
@Lock(LockType.READ)
public class LobbyCountdownBO {
    @Resource
    private TimerService timerService;

    @EJB
    private LobbyDAO lobbyDAO;

    @EJB
    private EventSessionDAO eventSessionDAO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private LobbyMessagingBO lobbyMessagingBO;

    public void scheduleLobbyStart(LobbyEntity lobbyEntity) {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(lobbyEntity.getId());
        timerService.createSingleActionTimer(lobbyEntity.getEvent().getLobbyCountdownTime(), timerConfig);
    }

    @Timeout
    public void onTimeout(Timer timer) {
        Long lobbyId = (Long) timer.getInfo();
        LobbyEntity lobbyEntity = lobbyDAO.find(lobbyId);
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
        eventSessionDAO.insert(eventSessionEntity);
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
            String relayCryptoTicket = Base64.getEncoder().encodeToString(cryptoTicketBytes);
            tokenSessionBO.setRelayCryptoTicket(tokenSessionBO.findByUserId(lobbyEntrantEntity.getPersona().getUser().getId()), relayCryptoTicket);

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

        lobbyMessagingBO.sendRelay(lobbyLaunched, xMPP_CryptoTicketsType);
    }
}
