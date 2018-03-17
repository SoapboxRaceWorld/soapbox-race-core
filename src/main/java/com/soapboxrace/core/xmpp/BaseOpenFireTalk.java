package com.soapboxrace.core.xmpp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;

public abstract class BaseOpenFireTalk implements IOpenFireTalk {

	protected Socket socket;
	protected BufferedReader reader;
	protected BufferedWriter writer;

	public BaseOpenFireTalk(Socket socket) {
		setSocket(socket);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
		setReaderWriter();
	}

	@Override
	public void init() {
		//
	}

	@Override
	public String read() {
		String msg = null;
		char[] buffer = new char[8192];
		int charsRead;
		try {
			if ((charsRead = reader.read(buffer)) != -1) {
				msg = new String(buffer).substring(0, charsRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("S->C [" + msg + "]");
		if (msg != null && !msg.isEmpty()) {
			this.handleMessage(msg);
		}
		return msg;
	}

	public void write(String msg) {
		try {
			char[] cbuf = new char[msg.length()];
			msg.getChars(0, msg.length(), cbuf, 0);
			System.out.println("C->S [" + msg + "]");
			writer.write(cbuf);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setReaderWriter() {
		try {
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startReader() {
		XmppTalkReader xmppTalkReader = new XmppTalkReader(this);
		xmppTalkReader.start();
	}

	@Override
	public void send(String msg, String to, ParameterBO parameterBO) {
		XMPP_MessageType messageType = new XMPP_MessageType(parameterBO);
		messageType.setTo(to);
		messageType.setBody(msg);
		messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
		String packet = MarshalXML.marshal(messageType);
		write(packet);
	}

	@Override
	public void send(String msg, Long personaId, ParameterBO parameterBO) {
		XMPP_MessageType messageType = new XMPP_MessageType(parameterBO);
		messageType.setToPersonaId(personaId);
		messageType.setBody(msg);
		messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
		String packet = MarshalXML.marshal(messageType);
		write(packet);
	}

	private class XmppTalkReader extends Thread {
		private BaseOpenFireTalk xmppTalk;

		public XmppTalkReader(BaseOpenFireTalk xmppTalk) {
			this.xmppTalk = xmppTalk;
		}

		@Override
		public void run() {
			while (true) {
				String read = xmppTalk.read();
				if (read == null) {
					try {
						xmppTalk.socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.interrupt();
					break;
				}
			}
		}
	}
}
