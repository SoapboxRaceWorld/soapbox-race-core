/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class RedisBO {

    @EJB
    private ParameterBO parameterBO;

    @Inject
    private Logger logger;

    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisURI redisURI;

    @PostConstruct
    public void init() {
        if (this.parameterBO.getBoolParam("ENABLE_REDIS")) {
            String redisHost = parameterBO.getStrParam("REDIS_HOST", "localhost");
            Integer redisPort = parameterBO.getIntParam("REDIS_PORT", 6379);
            String redisPassword = parameterBO.getStrParam("REDIS_PASSWORD", "");

            this.redisURI = RedisURI.builder().withHost(redisHost).withPort(redisPort).withPassword(redisPassword).build();
            this.redisClient = RedisClient.create();

            try {
                this.connection = this.redisClient.connect(redisURI);
                logger.info("Connected to Redis server at {}:{}", redisHost, redisPort);
            } catch (RedisException exception) {
                throw new RuntimeException("Failed to connect to Redis server at " + redisHost + ":" + redisPort, exception);
            }
        }
    }

    public StatefulRedisPubSubConnection<String, String> createPubSub() {
        if (this.redisClient == null) {
            throw new RuntimeException("Redis is disabled!");
        }
        return this.redisClient.connectPubSub(redisURI);
    }

    public RedisClient getRedisClient() {
        if (this.redisClient == null) {
            throw new RuntimeException("Redis is disabled!");
        }
        return redisClient;
    }

    public StatefulRedisConnection<String, String> getConnection() {
        if (this.redisClient == null) {
            throw new RuntimeException("Redis is disabled!");
        }
        return connection;
    }
}
