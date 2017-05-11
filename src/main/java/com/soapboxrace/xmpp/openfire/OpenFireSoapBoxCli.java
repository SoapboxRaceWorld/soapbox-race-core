package com.soapboxrace.xmpp.openfire;

import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;

public class OpenFireSoapBoxCli {

	private OpenFireTalk xmppTalk;

	public OpenFireSoapBoxCli() {
		HandShake xmppHandShake = new HandShake();
		xmppTalk = xmppHandShake.getXmppTalk();
	}

	public void send(String msg, Long to) {
		XMPP_MessageType messageType = new XMPP_MessageType();
		messageType.setToPersonaId(to);
		messageType.setBody(msg);
		messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
		String packet = MarshalXML.marshal(messageType);
		xmppTalk.write(packet);
	}

	public void send(Object object, Long to) {
		String responseXmlStr = MarshalXML.marshal(object);
		this.send(responseXmlStr, to);
	}

}
