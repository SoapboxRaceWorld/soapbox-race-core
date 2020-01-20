/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sessions")
public class SessionsCount {

    private int clusterSessions;
    private int localSessions;

    public int getClusterSessions() {
        return clusterSessions;
    }

    public void setClusterSessions(int clusterSessions) {
        this.clusterSessions = clusterSessions;
    }

    public int getLocalSessions() {
        return localSessions;
    }

    public void setLocalSessions(int localSessions) {
        this.localSessions = localSessions;
    }

}