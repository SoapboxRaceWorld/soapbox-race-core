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
