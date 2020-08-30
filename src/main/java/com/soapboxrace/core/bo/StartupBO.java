/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.BuildInfo;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartupBO {

    @EJB
    private OnlineUsersBO onlineUsersBO;

    @Inject
    private Logger logger;

    @PostConstruct
    public void init() {
        logger.info("StartupBO: Server running on code version {} ({}) - branch {}", BuildInfo.getCommitID(), BuildInfo.getLongCommitID(), BuildInfo.getBranch());
        onlineUsersBO.insertOnlineStats();
    }
}
