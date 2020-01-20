/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class OnlineUsersBO {

    @EJB
    OpenFireRestApiCli openFireRestApiCli;
    @EJB
    private OnlineUsersDAO onlineUsersDAO;
    @EJB
    private ParameterBO parameterBO;

    public int getNumberOfUsersOnlineNow() {
        Date lastMinutes = getLastMinutes(1);
        OnlineUsersEntity onlineUsersEntity = onlineUsersDAO.findByTime(lastMinutes);
        return onlineUsersEntity != null ? onlineUsersEntity.getNumberOfUsers() : 0;
    }

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void insertNumberOfUsesOnlineNow() {
        if (parameterBO.isShardingEnabled()) {
            if (!parameterBO.isShardingMaster())
                return;
        }

        Long timeLong = new Date().getTime() / 1000L;
        OnlineUsersEntity onlineUsersEntity = new OnlineUsersEntity();
        onlineUsersEntity.setNumberOfUsers(openFireRestApiCli.getTotalOnlineUsers());
        onlineUsersEntity.setTimeRecord(timeLong.intValue());
        onlineUsersDAO.insert(onlineUsersEntity);
    }

    private Date getLastMinutes(int minutes) {
        long time = new Date().getTime();
        time = time - (minutes * 90000);

        return new Date(time);
    }
}
