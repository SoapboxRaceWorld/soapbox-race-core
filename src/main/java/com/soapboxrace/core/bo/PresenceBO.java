/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Singleton
@Startup
public class PresenceBO {
    @EJB
    private RedisBO redisBO;

    private StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private StatefulRedisConnection<String, String> connection;

    @PostConstruct
    public void init() {
        this.pubSubConnection = this.redisBO.createPubSub();
        this.connection = this.redisBO.getConnection();
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("PresenceBO shutdown");

        List<String> keys = this.connection.sync().keys("game_presence.*");
        if (!keys.isEmpty())
            this.connection.sync().del(keys.toArray(new String[0]));
        this.pubSubConnection.close();
    }

    public void updatePresence(Long personaId, Long presence) {
        if (personaId != 0L) {
            Long currentPresence = this.getPresence(personaId);

            if (!currentPresence.equals(presence)) {
                this.connection.sync().set(getPresenceKey(personaId), presence.toString());
                this.pubSubConnection.sync().publish("game_presence_updates", personaId + "|" + presence);
            }
        }
    }

    public void removePresence(Long personaId) {
        if (personaId != 0L) {
            updatePresence(personaId, 0L);
            this.connection.sync().del(getPresenceKey(personaId));
        }
    }

    public Long getPresence(Long personaId) {
        String value = this.connection.sync().get(getPresenceKey(personaId));

        if (value == null || value.trim().isEmpty())
            return 0L;

        return Long.parseLong(value);
    }

    private String getPresenceKey(Long personaId) {
        return "game_presence." + personaId;
    }
}