/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.bo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class RedisBO {

    private RedisClient redisClient;

    @EJB
    private ParameterBO parameterBO;
    private StatefulRedisConnection<String, String> connection;
    private RedisURI redisURI;

    @PostConstruct
    public void init() {
        String redisHost = parameterBO.getStrParam("REDIS_HOST", "localhost");
        Integer redisPort = parameterBO.getIntParam("REDIS_PORT", 6379);
        String redisPassword = parameterBO.getStrParam("REDIS_PASSWORD", "");

        this.redisURI = RedisURI.builder().withHost(redisHost).withPort(redisPort).withPassword(redisPassword).build();
        this.redisClient = RedisClient.create();
        this.connection = this.redisClient.connect(redisURI);
    }

    public StatefulRedisPubSubConnection<String, String> createPubSub() {
        return this.redisClient.connectPubSub(redisURI);
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }
}
