/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import com.soapboxrace.jaxb.util.MarshalXML;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class OpenFireSoapBoxCli {

    @EJB
    private OpenFireConnector openFireConnector;

    @PostConstruct
    public void init() {
        openFireConnector.connect();
        openFireConnector.send("hey!", 0L);
    }

    public void send(String msg, Long to) {
//        xmppTalk.send(msg, to, parameterBO);
        openFireConnector.send(msg, to);
    }

    public void send(Object object, Long to) {
        openFireConnector.send(MarshalXML.marshal(object), to);
    }
}