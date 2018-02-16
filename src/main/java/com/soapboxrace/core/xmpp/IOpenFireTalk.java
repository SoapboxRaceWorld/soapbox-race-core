package com.soapboxrace.core.xmpp;

import java.net.Socket;

import com.soapboxrace.core.bo.ParameterBO;

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
