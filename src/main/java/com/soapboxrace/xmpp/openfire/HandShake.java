package com.soapboxrace.xmpp.openfire;

import com.soapboxrace.core.api.util.Config;

public class HandShake {

	private OpenFireTalk openFireTalk;

	public HandShake() {
		String xmppIp = Config.getXmppIp();
		int xmppPort = Config.getXmppPort();

		SocketClient socketClient = new SocketClient(xmppIp, xmppPort);
		socketClient.send(
				"<?xml version='1.0' ?><stream:stream to='" + xmppIp + "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
		String receive = socketClient.receive();
		while (!receive.contains("</stream:features>")) {
			receive = socketClient.receive();
		}
		socketClient.send("<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>");
		socketClient.receive();
		openFireTalk = new OpenFireTalk(socketClient.getSocket());
		TlsWrapper.wrapXmppTalk(openFireTalk);
		openFireTalk.write(
				"<?xml version='1.0' ?><stream:stream to='" + xmppIp + "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
		openFireTalk.write("<iq id='EA-Chat-1' type='get'><query xmlns='jabber:iq:auth'><username>sbrw.engine.engine</username></query></iq>");
		openFireTalk.read();
		openFireTalk.write("<iq xml:lang='en' id='EA-Chat-2' type='set'><query xmlns='jabber:iq:auth'><username>sbrw.engine.engine</username><password>" + Config.getOpenFireToken()
				+ "</password><resource>EA_Chat</resource><clientlock xmlns='http://www.jabber.com/schemas/clientlocking.xsd' id='900'>57b8914527daff651df93557aef0387e5aa60fae</clientlock></query></iq>");
		openFireTalk.read();
		openFireTalk.write("<presence><show>chat</show><status>Online</status><priority>0</priority></presence>");
		openFireTalk.write(" ");
		openFireTalk.write("<presence to='channel.CMD__1@conference." + xmppIp + "/sbrw.engine.engine'/>");
		openFireTalk.startReader();
	}

	public OpenFireTalk getXmppTalk() {
		return openFireTalk;
	}

}
