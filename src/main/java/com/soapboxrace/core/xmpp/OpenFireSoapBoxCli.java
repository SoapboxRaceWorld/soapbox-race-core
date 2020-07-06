/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.annotation.PostConstruct;
import javax.ejb.*;

@Startup
@Singleton
public class OpenFireSoapBoxCli {

    @EJB
    private OpenFireConnector openFireConnector;

    @PostConstruct
    public void init() {
        openFireConnector.connect();
    }

    @Lock(LockType.READ)
    public void send(String msg, Long to) {
        openFireConnector.send(msg, to);
    }

    @Lock(LockType.READ)
    public void send(Object object, Long to) {
        openFireConnector.send(JAXBUtility.marshal(object), to);
    }
}