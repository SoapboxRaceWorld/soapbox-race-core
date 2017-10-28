package com.soapboxrace.core.xmpp;

import java.net.Socket;

public interface IOpenFireTalk
{
    String read();

    void write(String msg);

    void send(String msg, String to);

    void send(String msg, Long personaId);

    void handleMessage(String msg);

    Socket getSocket();

    void setSocket(Socket socket);

    void startReader();
}
