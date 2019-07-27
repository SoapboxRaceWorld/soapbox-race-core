package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.SocialRelationshipDAO;
import com.soapboxrace.core.exception.EngineException;
import com.soapboxrace.core.exception.EngineExceptionCode;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.SocialRelationshipEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Singleton
@Startup
public class SocialRelationshipBO {
    @EJB
    private SocialRelationshipDAO socialRelationshipDAO;
    @EJB
    private PersonaDAO personaDAO;
    @EJB
    private RedisBO redisBO;
    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;
    @EJB
    private DriverPersonaBO driverPersonaBO;
    @EJB
    private PresenceBO presenceBO;
    private StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private SocialRelationshipListener listener;

    @PostConstruct
    public void init() {
        System.out.println("SocialRelationshipBO init");

        this.listener = new SocialRelationshipListener();
        this.pubSubConnection = this.redisBO.createPubSub();
        this.pubSubConnection.addListener(this.listener);
        this.pubSubConnection.sync().subscribe("game_presence_updates");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("SocialRelationshipBO shutdown");

        this.pubSubConnection.removeListener(this.listener);
        this.pubSubConnection.close();
        this.listener = null;
    }

    public PersonaFriendsList getFriendsList(Long userId) {
        PersonaFriendsList personaFriendsList = new PersonaFriendsList();
        personaFriendsList.setFriendPersona(new ArrayOfFriendPersona());

        List<SocialRelationshipEntity> socialRelationshipEntityList = this.socialRelationshipDAO.findByUserId(userId);

        for (SocialRelationshipEntity socialRelationshipEntity : socialRelationshipEntityList) {
            if (socialRelationshipEntity.getStatus() == 0) {
                this.addPendingRequestToList(personaFriendsList, socialRelationshipEntity);
            } else if (socialRelationshipEntity.getStatus() == 1) {
                this.addFriendToList(personaFriendsList, socialRelationshipEntity);
            }
        }

        return personaFriendsList;
    }

    public ArrayOfBasicBlockPlayerInfo getBlockedUserList(Long userId) {
        List<SocialRelationshipEntity> socialRelationshipEntityList =
                this.socialRelationshipDAO.findByUserIdAndStatus(userId, 2L);
        ArrayOfBasicBlockPlayerInfo arrayOfBasicBlockPlayerInfo = new ArrayOfBasicBlockPlayerInfo();

        for (SocialRelationshipEntity socialRelationshipEntity : socialRelationshipEntityList) {
            this.addBlockedUserToList(arrayOfBasicBlockPlayerInfo, socialRelationshipEntity);
        }

        return arrayOfBasicBlockPlayerInfo;
    }

    public ArrayOfLong getBlockersByUsers(Long personaId) {
        PersonaEntity personaEntity = personaDAO.findById(personaId);

        if (personaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        ArrayOfLong arrayOfLong = new ArrayOfLong();

        for (SocialRelationshipEntity socialRelationshipEntity :
                this.socialRelationshipDAO.findByRemoteUserIdAndStatus(personaEntity.getUser().getId(), 2L)) {
            for (PersonaEntity remotePersonaEntity : socialRelationshipEntity.getUser().getPersonas()) {
                arrayOfLong.getLong().add(remotePersonaEntity.getPersonaId());
            }
        }

        return arrayOfLong;
    }

    public FriendResult addFriend(Long activePersonaId, String displayName) {
        PersonaEntity activePersonaEntity = personaDAO.findById(activePersonaId);
        PersonaEntity targetPersonaEntity = personaDAO.findByName(displayName);
        FriendResult friendResult = new FriendResult();

        // This shouldn't happen, but we have to catch it anyway.
        if (activePersonaId == 0L || activePersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        // Sanity check
        if (targetPersonaEntity == null) {
            friendResult.setResult(FriendResultStatus.CannotFindDriver);
            return friendResult;
        }

        // I know SOMEONE will try this. It's inevitable.
        if (targetPersonaEntity.getPersonaId().equals(activePersonaId)) {
            friendResult.setResult(FriendResultStatus.CannotAddSelf);
            return friendResult;
        }

        // Look for an existing relationship between the active user and the target user.
        // Ideally, there won't be one.
        // Scoped block is used to avoid accidental usage of the temporary variable.
        {
            SocialRelationshipEntity socialRelationshipEntity =
                    this.socialRelationshipDAO.findByLocalAndRemoteUser(activePersonaEntity.getUser().getId(),
                            targetPersonaEntity.getUser().getId());

            if (socialRelationshipEntity != null) {
                // Here, we're assuming that the players are, in fact, already friends.
                // Of course, this should be the case for any ordinary player, who probably
                // isn't messing around with the game.
                // It should be safe to assume that a driver won't try to add
                // a driver they've blocked, as a friend.
                friendResult.setResult(FriendResultStatus.AlreadyFriends);
                return friendResult;
            }
        }

        {
            SocialRelationshipEntity socialRelationshipEntity =
                    this.socialRelationshipDAO.findByLocalAndRemoteUser(targetPersonaEntity.getUser().getId(),
                            activePersonaEntity.getUser().getId());

            if (socialRelationshipEntity != null) {
                // check to see if target has blocked active player
                if (socialRelationshipEntity.getStatus() == 2L) {
                    // no dedicated exception code for this, so we just use SocialFriendRequestNotResolvable. game is
                    // fine with EngineExceptions.
                    throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
                } else {
                    friendResult.setResult(FriendResultStatus.AlreadyFriends);
                    return friendResult;
                }
            }
        }
        // Create remote relationship
        SocialRelationshipEntity remoteSocialRelationshipEntity = new SocialRelationshipEntity();
        remoteSocialRelationshipEntity.setRemoteUser(activePersonaEntity.getUser());
        remoteSocialRelationshipEntity.setUser(targetPersonaEntity.getUser());
        remoteSocialRelationshipEntity.setStatus(0L);
        remoteSocialRelationshipEntity.setRemotePersonaId(activePersonaId);
        socialRelationshipDAO.insert(remoteSocialRelationshipEntity);

        sendFriendPersonaPacket(activePersonaEntity, targetPersonaEntity.getPersonaId());

        friendResult.setResult(FriendResultStatus.Success);
        friendResult.setFriendPersona(copyPersonaEntityToFriendPersona(targetPersonaEntity));

        return friendResult;
    }

    public PersonaBase resolveFriendsRequest(Long activePersonaId, Long friendPersonaId, int resolution) {
        if (activePersonaId == 0L) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        if (friendPersonaId == 0L) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        PersonaEntity activePersonaEntity = personaDAO.findById(activePersonaId);

        if (activePersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        PersonaEntity friendPersonaEntity = personaDAO.findById(friendPersonaId);

        if (friendPersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        SocialRelationshipEntity socialRelationshipEntity =
                socialRelationshipDAO.findByLocalAndRemoteUser(activePersonaEntity.getUser().getId(),
                        friendPersonaEntity.getUser().getId());

        if (socialRelationshipEntity == null) {
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        switch (resolution) {
            case 0: // reject
                socialRelationshipDAO.delete(socialRelationshipEntity);
                this.sendPresencePacket(activePersonaEntity, 0L, friendPersonaId);
                return null;
            case 1: // accept
                this.createNewRelationship(friendPersonaEntity, activePersonaEntity, 1L);
                this.sendPresencePacket(activePersonaEntity, presenceBO.getPresence(activePersonaId), friendPersonaId);

                return driverPersonaBO.getPersonaBase(friendPersonaEntity);
            default:
                throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }
    }

    public PersonaBase removeFriend(Long activePersonaId, Long friendPersonaId) {
        PersonaEntity activePersonaEntity = personaDAO.findById(activePersonaId);

        if (activePersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        PersonaEntity friendPersonaEntity = personaDAO.findById(friendPersonaId);

        if (friendPersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        // Possible cases:
        // - driver sends friend request and then cancels it: just delete the remote relationship
        // - one side of a friendship unfriends the other side - delete both sides
        // DO NOT MESS WITH BLOCKS!
        SocialRelationshipEntity activeSide =
                socialRelationshipDAO.findByLocalAndRemoteUser(activePersonaEntity.getUser().getId(),
                        friendPersonaEntity.getUser().getId());
        SocialRelationshipEntity remoteSide =
                socialRelationshipDAO.findByLocalAndRemoteUser(friendPersonaEntity.getUser().getId(),
                        activePersonaEntity.getUser().getId());
        if (remoteSide == null) {
            // for lack of a better exception code, we just use the "not resolvable" error... they're all the same
            // to the game.
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        if (activeSide == null) {
            // nothing on this side, there might be a friend request
            if (remoteSide.getStatus() == 0) {
                socialRelationshipDAO.delete(remoteSide);
            } else {
                throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
            }
        } else if (activeSide.getStatus() == 1 && remoteSide.getStatus() == 1) {
            socialRelationshipDAO.delete(activeSide);
            socialRelationshipDAO.delete(remoteSide);
        } else {
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        return driverPersonaBO.getPersonaBase(activePersonaEntity);
    }

    public PersonaBase blockPlayer(Long userId, Long activePersonaId, Long otherPersonaId) {
        if (activePersonaId == 0L) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        PersonaEntity otherPersonaEntity = personaDAO.findById(otherPersonaId);

        if (otherPersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        SocialRelationshipEntity localSide = socialRelationshipDAO.findByLocalAndRemoteUser(userId,
                otherPersonaEntity.getUser().getId());
        SocialRelationshipEntity remoteSide =
                socialRelationshipDAO.findByLocalAndRemoteUser(otherPersonaEntity.getUser().getId(),
                        userId);

        if (localSide != null) {
            if (localSide.getStatus() == 2) {
                throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
            }

            localSide.setStatus(2L);
            socialRelationshipDAO.update(localSide);
        }

        if (remoteSide != null) {
            if (remoteSide.getStatus() != 2L) {
                sendPresencePacket(personaDAO.findById(activePersonaId), 0L, otherPersonaId);
                socialRelationshipDAO.delete(remoteSide);
            }
        }

        return driverPersonaBO.getPersonaBase(otherPersonaEntity);
    }

    public PersonaBase unblockPlayer(Long userId, Long otherPersonaId) {
        PersonaEntity otherPersonaEntity = personaDAO.findById(otherPersonaId);

        if (otherPersonaEntity == null) {
            throw new EngineException(EngineExceptionCode.RemotePersonaIdInvalid);
        }

        SocialRelationshipEntity localSide = socialRelationshipDAO.findByLocalAndRemoteUser(userId,
                otherPersonaEntity.getUser().getId());

        if (localSide != null && localSide.getStatus() == 2L) {
            socialRelationshipDAO.delete(localSide);
        } else {
            throw new EngineException(EngineExceptionCode.SocialFriendRequestNotResolvable);
        }

        return driverPersonaBO.getPersonaBase(otherPersonaEntity);
    }

    private void createNewRelationship(PersonaEntity localPersona, PersonaEntity remotePersona, long status) {
        SocialRelationshipEntity socialRelationshipEntity = new SocialRelationshipEntity();
        socialRelationshipEntity.setRemotePersonaId(remotePersona.getPersonaId());
        socialRelationshipEntity.setStatus(status);
        socialRelationshipEntity.setUser(localPersona.getUser());
        socialRelationshipEntity.setRemoteUser(remotePersona.getUser());
        socialRelationshipDAO.insert(socialRelationshipEntity);
    }

    private void addBlockedUserToList(ArrayOfBasicBlockPlayerInfo arrayOfBasicBlockPlayerInfo,
                                      SocialRelationshipEntity socialRelationshipEntity) {
        for (PersonaEntity personaEntity : socialRelationshipEntity.getRemoteUser().getPersonas()) {
            BasicBlockPlayerInfo basicBlockPlayerInfo = new BasicBlockPlayerInfo();

            basicBlockPlayerInfo.setPersonaId(personaEntity.getPersonaId());
            basicBlockPlayerInfo.setUserId(socialRelationshipEntity.getRemoteUser().getId());

            arrayOfBasicBlockPlayerInfo.getBasicBlockPlayerInfo().add(basicBlockPlayerInfo);
        }
    }

    private void addFriendToList(PersonaFriendsList personaFriendsList,
                                 SocialRelationshipEntity socialRelationshipEntity) {
        for (PersonaEntity personaEntity : socialRelationshipEntity.getRemoteUser().getPersonas()) {
            FriendPersona friendPersona = copyPersonaEntityToFriendPersona(personaEntity);
            friendPersona.setPresence(presenceBO.getPresence(personaEntity.getPersonaId()));
            personaFriendsList.getFriendPersona().getFriendPersona().add(friendPersona);
        }
    }

    private void addPendingRequestToList(PersonaFriendsList personaFriendsList,
                                         SocialRelationshipEntity socialRelationshipEntity) {
        FriendPersona friendPersona =
                copyPersonaEntityToFriendPersona(personaDAO.findById(socialRelationshipEntity.getRemotePersonaId()));
        friendPersona.setPresence(3L);
        personaFriendsList.getFriendPersona().getFriendPersona().add(friendPersona);
    }

    private FriendPersona copyPersonaEntityToFriendPersona(PersonaEntity personaEntity) {
        Objects.requireNonNull(personaEntity, "personaEntity is null!");
        FriendPersona friendPersona = new FriendPersona();
        friendPersona.setPersonaId(personaEntity.getPersonaId());
        friendPersona.setName(personaEntity.getName());
        friendPersona.setOriginalName("test");
        friendPersona.setSocialNetwork(0);
        friendPersona.setUserId(personaEntity.getUser().getId());
        friendPersona.setLevel(personaEntity.getLevel());
        friendPersona.setIconIndex(personaEntity.getIconIndex());

        return friendPersona;
    }

    private void sendPresencePackets(PersonaEntity personaEntity, Long presence) {
        Objects.requireNonNull(personaEntity, "personaEntity is null!");
        for (SocialRelationshipEntity socialRelationshipEntity :
                socialRelationshipDAO.findByUserId(personaEntity.getUser().getId())) {
            sendPresencePacket(personaEntity, presence, socialRelationshipEntity);
        }
    }

    private void sendPresencePacket(PersonaEntity personaEntity, Long presence,
                                    Long targetPersonaId) {
        XMPP_ResponseTypePersonaBase personaPacket = new XMPP_ResponseTypePersonaBase();
        PersonaBase xmppPersonaBase = driverPersonaBO.getPersonaBase(personaEntity);
        xmppPersonaBase.setPresence(presence);
        personaPacket.setPersonaBase(xmppPersonaBase);

        openFireSoapBoxCli.send(personaPacket, targetPersonaId);
    }

    private void sendPresencePacket(PersonaEntity personaEntity, Long presence,
                                    SocialRelationshipEntity socialRelationshipEntity) {
        XMPP_ResponseTypePersonaBase personaPacket = new XMPP_ResponseTypePersonaBase();
        PersonaBase xmppPersonaBase = driverPersonaBO.getPersonaBase(personaEntity);
        xmppPersonaBase.setPresence(presence);
        personaPacket.setPersonaBase(xmppPersonaBase);

        openFireSoapBoxCli.send(personaPacket, socialRelationshipEntity.getRemotePersonaId());
    }

    private void sendFriendPersonaPacket(PersonaEntity personaEntity, Long targetPersonaId) {
        XMPP_FriendPersonaType personaPacket = new XMPP_FriendPersonaType();
        PersonaBase xmppPersonaBase = driverPersonaBO.getPersonaBase(personaEntity);
        xmppPersonaBase.setPresence(3L);

        // copy to XMPP_FriendPersonaType
        personaPacket.setIconIndex(xmppPersonaBase.getIconIndex());
        personaPacket.setLevel(xmppPersonaBase.getLevel());
        personaPacket.setPresence(xmppPersonaBase.getPresence());
        personaPacket.setName(xmppPersonaBase.getName());
        personaPacket.setOriginalName("test");
        personaPacket.setSocialNetwork(0);
        personaPacket.setUserId(xmppPersonaBase.getUserId());
        personaPacket.setPersonaId(personaEntity.getPersonaId());

        openFireSoapBoxCli.send(personaPacket, targetPersonaId);
    }

    private class SocialRelationshipListener implements RedisPubSubListener<String, String> {
        @Override
        public void message(String channel, String message) {
            if ("game_presence_updates".equals(channel)) {
                System.out.println(message);

                Long[] parts = Arrays.stream(message.split("\\|")).map(Long::parseLong).toArray(Long[]::new);

                sendPresencePackets(personaDAO.findById(parts[0]), parts[1]);
            }
        }

        @Override
        public void message(String pattern, String channel, String message) {

        }

        @Override
        public void subscribed(String channel, long count) {

        }

        @Override
        public void psubscribed(String pattern, long count) {

        }

        @Override
        public void unsubscribed(String channel, long count) {

        }

        @Override
        public void punsubscribed(String pattern, long count) {

        }
    }
}
