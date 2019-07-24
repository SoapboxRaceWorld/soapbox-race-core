package com.soapboxrace.core.bo;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
public class PresenceBO {
    @EJB
    private RedisBO redisBO;

    private StatefulRedisPubSubConnection<String, String> pubSubConnection;

    @PostConstruct
    public void init() {
        this.pubSubConnection = this.redisBO.createPubSub();
    }

    public void updatePresence(Long personaId, Long presence) {
        if (personaId != 0L) {
            Long currentPresence = this.getPresence(personaId);

            if (!currentPresence.equals(presence)) {
                this.redisBO.getConnection().sync().set(getPresenceKey(personaId), presence.toString());
                this.pubSubConnection.sync().publish("game_presence_updates", personaId + "|" + presence);
            }
        }
    }

    public void removePresence(Long personaId) {
        if (personaId != 0L) {
            updatePresence(personaId, 0L);
            this.redisBO.getConnection().sync().del(getPresenceKey(personaId));
        }
    }

    public Long getPresence(Long personaId) {
        String value = this.redisBO.getConnection().sync().get(getPresenceKey(personaId));

        if (value == null || value.trim().isEmpty())
            return 0L;

        return Long.parseLong(value);
    }

    private String getPresenceKey(Long personaId) {
        return "game_presence." + personaId;
    }
}