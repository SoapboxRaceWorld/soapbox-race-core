package com.soapboxrace.xmpp.openfire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPingType;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPongType;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;
import com.soapboxrace.xmpp.openfire.shard.ShardCommand;

public class OpenFireTalk {

	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private final ParameterBO parameterBO = new ParameterBO();

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
		if (msg != null && !msg.isEmpty()) {
			if (msg.contains("<ping xmlns=\"urn:xmpp:ping\"/>")) {
				XMPP_IQPingType openfirePing = UnmarshalXML.unMarshal(msg, XMPP_IQPingType.class);
				write(MarshalXML.marshal(new XMPP_IQPongType(openfirePing.getId())));
			} else if (msg.contains("<message")) {
				XMPP_MessageType messageType = UnmarshalXML.unMarshal(msg, XMPP_MessageType.class);
				String body = messageType.getBody();
				if (!body.contains("<ChatMsg") && body.contains("<ShardCommand")) {
					ShardCommand command = UnmarshalXML.unMarshal(body, ShardCommand.class);
					
					if ((command.getToken() == null && parameterBO.getCncToken() != null)
							|| (!command.getToken().equals(parameterBO.getCncToken()))) {
						System.err.println("[CNC] Received command with invalid token!");
						System.err.println(body);
					} else {
						OpenFireSoapBoxCli.getInstance().send(command.getPayload(), command.getTargetPersonaId());
					}
				}
			}
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
				String read = xmppTalk.read();
				if (read == null) {
					OpenFireSoapBoxCli.getInstance().disconnect();
					try {
						xmppTalk.socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					OpenFireSoapBoxCli.getInstance();
					this.interrupt();
					break;
				}
			}
		}

	}

}
