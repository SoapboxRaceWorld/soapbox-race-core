/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.BuildInfo;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupBO {

    @EJB
    private OnlineUsersBO onlineUsersBO;

    @PostConstruct
    public void init() {
        System.out.printf("StartupBO: Server running on code version %s (%s) - branch %s%n", BuildInfo.getCommitID(), BuildInfo.getLongCommitID(),
                BuildInfo.getBranch());
        onlineUsersBO.insertNumberOfUsesOnlineNow();
    }
}
