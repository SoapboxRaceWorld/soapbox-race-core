package com.soapboxrace.core.xmpp.standard;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.xmpp.IHandshake;
import com.soapboxrace.core.xmpp.IOpenFireTalk;
import com.soapboxrace.core.xmpp.SocketClient;
import com.soapboxrace.core.xmpp.TlsWrapper;

@Singleton
public class Handshake implements IHandshake {
	private IOpenFireTalk openFireTalk;

	@EJB
	private ParameterBO parameterBO;

	@PostConstruct
	public void init() {
		String xmppIp = parameterBO.getStrParam("XMPP_IP");
		int xmppPort = parameterBO.getIntParam("XMPP_PORT");

		SocketClient socketClient = new SocketClient(xmppIp, xmppPort);
		socketClient.send("<?xml version='1.0' ?><stream:stream to='" + xmppIp
				+ "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
		String receive = socketClient.receive();
		while (!receive.contains("</stream:features>")) {
			receive = socketClient.receive();
		}
		socketClient.send("<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>");
		socketClient.receive();
		openFireTalk = new StandardOpenFireTalk(socketClient.getSocket());
		TlsWrapper.wrapXmppTalk(openFireTalk);
		openFireTalk.write("<?xml version='1.0' ?><stream:stream to='" + xmppIp
				+ "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
		openFireTalk.write("<iq id='EA-Chat-1' type='get'><query xmlns='jabber:iq:auth'><username>sbrw.engine.engine</username></query></iq>");
		openFireTalk.read();

		String resource = "EA_Chat";

		if (parameterBO.isShardingEnabled()) {
			if (!parameterBO.isShardingMaster()) {
				resource += "_" + (parameterBO.getShardId() == null ? "unkshard" : parameterBO.getShardId());
			} else {
				resource += "_master";
			}
		}

		openFireTalk.write("<iq xml:lang='en' id='EA-Chat-2' type='set'><query xmlns='jabber:iq:auth'><username>sbrw.engine.engine</username><password>"
				+ parameterBO.getStrParam("OPENFIRE_TOKEN") + "</password><resource>" + resource
				+ "</resource><clientlock xmlns='http://www.jabber.com/schemas/clientlocking.xsd' id='900'>57b8914527daff651df93557aef0387e5aa60fae</clientlock></query></iq>");
		openFireTalk.read();
		openFireTalk.write("<presence><show>chat</show><status>Online</status><priority>0</priority></presence>");
		openFireTalk.write(" ");
		openFireTalk.write("<presence to='channel.CMD__1@conference." + xmppIp + "/sbrw.engine.engine'/>");
		openFireTalk.startReader();

	}

	@Override
	public IOpenFireTalk getXmppTalk() {
		return this.openFireTalk;
	}
}
