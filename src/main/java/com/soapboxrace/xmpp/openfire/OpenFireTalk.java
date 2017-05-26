package com.soapboxrace.xmpp.openfire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPingType;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPongType;

public class OpenFireTalk {

	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;

	public OpenFireTalk(Socket socket) {
		this.socket = socket;
		setReaderWriter();
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
		setReaderWriter();
	}

	public Socket getSocket() {
		return socket;
	}

	private void setReaderWriter() {
		try {
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String read() {
		String msg = null;
		char[] buffer = new char[8192];
		int charsRead = 0;
		try {
			if ((charsRead = reader.read(buffer)) != -1) {
				msg = new String(buffer).substring(0, charsRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("S->C [" + msg + "]");
		if (msg != null && !msg.isEmpty() && msg.contains("<ping xmlns=\"urn:xmpp:ping\"/>")) {
			XMPP_IQPingType openfirePing = (XMPP_IQPingType) UnmarshalXML.unMarshal(msg, XMPP_IQPingType.class);
			write(MarshalXML.marshal(new XMPP_IQPongType(openfirePing.getId())));
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

	public void startReader() {
		XmppTalkReader xmppTalkReader = new XmppTalkReader(this);
		xmppTalkReader.start();
	}

	private static class XmppTalkReader extends Thread {

		private OpenFireTalk xmppTalk;

		public XmppTalkReader(OpenFireTalk xmppTalk) {
			this.xmppTalk = xmppTalk;
		}

		@Override
		public void run() {
			while (true) {
				xmppTalk.read();
			}
		}

	}

}
