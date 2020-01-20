/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.BuildInfo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupBO {
    @PostConstruct
    public void init() {
        BuildInfo.load();

        String template = "StartupBO: Server running on code version %s (%s) - branch %s";

        System.out.println(String.format(template, BuildInfo.getCommitID(), BuildInfo.getLongCommitID(),
                BuildInfo.getBranch()));
    }
}
