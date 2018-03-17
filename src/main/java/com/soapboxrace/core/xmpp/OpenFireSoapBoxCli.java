package com.soapboxrace.core.xmpp;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.jaxb.util.MarshalXML;

@Startup
@Singleton
public class OpenFireSoapBoxCli {

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private IHandshake handshake;

	private IOpenFireTalk xmppTalk;

	@PostConstruct
	public void init() {
		this.xmppTalk = handshake.getXmppTalk();
	}

	public void send(String msg, String to) {
		xmppTalk.send(msg, to, parameterBO);
	}

	public void send(String msg, Long to) {
		xmppTalk.send(msg, to, parameterBO);
	}

	public void send(Object object, Long to) {
		String responseXmlStr = MarshalXML.marshal(object);
		this.send(responseXmlStr, to);
	}

	public void setXmppTalk(IOpenFireTalk xmppTalk) {
		this.xmppTalk = xmppTalk;
	}

}