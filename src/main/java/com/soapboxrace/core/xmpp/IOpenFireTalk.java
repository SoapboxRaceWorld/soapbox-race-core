/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import com.soapboxrace.core.bo.ParameterBO;

import java.net.Socket;

public interface IOpenFireTalk {
    void init();

    String read();

    void write(String msg);

    void send(String msg, String to, ParameterBO parameterBO);

    void send(String msg, Long personaId, ParameterBO parameterBO);

    void handleMessage(String msg);

    Socket getSocket();

    void setSocket(Socket socket);

    void startReader();
}
