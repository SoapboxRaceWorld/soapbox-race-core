/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.Date;

@Singleton
@Lock(LockType.READ)
public class OnlineUsersBO {

    @EJB
    private OpenFireRestApiCli openFireRestApiCli;

    @EJB
    private OnlineUsersDAO onlineUsersDAO;

    @EJB
    private UserDAO userDAO;

    private OnlineUsersEntity lastRecordedStats;

    public OnlineUsersEntity getOnlineUsersStats() {
        return lastRecordedStats;
    }

    @PostConstruct
    public void init() {
        insertOnlineStats();
    }

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void insertOnlineStats() {
        long timeLong = new Date().getTime() / 1000L;
        OnlineUsersEntity onlineUsersEntity = new OnlineUsersEntity();
        onlineUsersEntity.setNumberOfOnline(openFireRestApiCli.getTotalOnlineUsers());
        onlineUsersEntity.setNumberOfRegistered(userDAO.countUsers());
        onlineUsersEntity.setTimeRecord((int) timeLong);
        onlineUsersDAO.insert(onlineUsersEntity);
        lastRecordedStats = onlineUsersEntity;
    }
}
