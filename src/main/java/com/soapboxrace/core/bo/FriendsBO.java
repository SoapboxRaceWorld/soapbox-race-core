package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.ArrayOfFriendPersona;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.http.PersonaFriendsList;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Singleton
@Startup
public class FriendsBO {

    private class FriendsListener implements RedisPubSubListener<String, String> {

        @Override
        public void message(String channel, String message) {
            switch (channel) {
                case "game_presence_updates":
                    this.handlePresenceUpdate(message);
                    break;
                default:
                    throw new RuntimeException("Message coming in on unknown channel: " + channel);
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

        private void handlePresenceUpdate(String message) {
            Long[] parts = Arrays.stream(message.split("\\|")).map(Long::parseLong).toArray(Long[]::new);
            Long personaId = parts[0];
            Long presence = parts[1];

            sendPresencePackets(personaDAO.findById(personaId), presence);
        }
    }

    @EJB
    private FriendDAO friendDAO;

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

    @PostConstruct
    public void init() {
        StatefulRedisPubSubConnection<String, String> pubSubConnection = this.redisBO.createPubSub();
        pubSubConnection.sync().subscribe("game_presence_updates");
        pubSubConnection.addListener(new FriendsListener());
    }

    public PersonaFriendsList getFriendsList(Long userId) {
        PersonaFriendsList personaFriendsList = new PersonaFriendsList();
        personaFriendsList.setFriendPersona(new ArrayOfFriendPersona());

        List<FriendEntity> friendEntityList = friendDAO.findByUserId(userId);

        for (FriendEntity friendEntity : friendEntityList) {
            if (friendEntity.getStatus() == 0) {
                this.addPendingRequestToList(personaFriendsList, friendEntity);
            } else {
                this.addPersonaFriendToList(personaFriendsList, friendEntity);
            }
        }

        return personaFriendsList;
    }

    private void addPersonaFriendToList(PersonaFriendsList personaFriendsList, FriendEntity friendEntity) {
        FriendPersona friendPersona = copyPersonaEntityToFriendPersona(personaDAO.findById(friendEntity.getFromPersonaId()));
        friendPersona.setPresence(presenceBO.getPresence(friendEntity.getFromPersonaId()));
        personaFriendsList.getFriendPersona().getFriendPersona().add(friendPersona);
    }

    private void addPendingRequestToList(PersonaFriendsList personaFriendsList, FriendEntity friendEntity) {
        FriendPersona friendPersona =
                copyPersonaEntityToFriendPersona(personaDAO.findById(friendEntity.getFromPersonaId()));
        friendPersona.setPresence(3);
        personaFriendsList.getFriendPersona().getFriendPersona().add(friendPersona);
    }

    private FriendPersona copyPersonaEntityToFriendPersona(PersonaEntity personaEntity) {
        Objects.requireNonNull(personaEntity, "personaEntity is null!");
        FriendPersona friendPersona = new FriendPersona();
        friendPersona.setPersonaId(personaEntity.getPersonaId());
        friendPersona.setName(personaEntity.getName());
        friendPersona.setUserId(personaEntity.getUser().getId());
        friendPersona.setLevel(personaEntity.getLevel());
        friendPersona.setIconIndex(personaEntity.getIconIndex());

        return friendPersona;
    }

    private void sendPresencePackets(PersonaEntity personaEntity, Long presence) {
        Objects.requireNonNull(personaEntity, "personaEntity is null!");
        for (FriendEntity friendEntity : friendDAO.findByUserId(personaEntity.getUser().getId())) {
            XMPP_ResponseTypePersonaBase personaPacket = new XMPP_ResponseTypePersonaBase();
            PersonaBase xmppPersonaBase = driverPersonaBO.getPersonaBase(personaEntity);
            xmppPersonaBase.setPresence(presence.intValue());
            personaPacket.setPersonaBase(xmppPersonaBase);

            openFireSoapBoxCli.send(personaPacket, friendEntity.getFromPersonaId());
        }
    }
}
