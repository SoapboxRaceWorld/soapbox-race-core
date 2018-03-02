package com.soapboxrace.core.xmpp;

import com.soapboxrace.jaxb.xmpp.XMPP_EventTimingOutType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeDragEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeEventTimingOut;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeRouteEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeTeamEscapeEntrantResult;

public class XmppEvent {

	private long personaId;
    private static OpenFireSoapBoxCli openFireSoapBoxCli = OpenFireSoapBoxCli.getInstance();

	public XmppEvent(long personaId) {
		this.personaId = personaId;
	}

	public void sendRaceEnd(XMPP_ResponseTypeRouteEntrantResult routeEntrantResultResponse) {
		openFireSoapBoxCli.send(routeEntrantResultResponse, personaId);
	}
	
	public void sendTeamEscapeEnd(XMPP_ResponseTypeTeamEscapeEntrantResult teamEscapeEntrantResultResponse) {
		openFireSoapBoxCli.send(teamEscapeEntrantResultResponse, personaId);
	}
	
	public void sendDragEnd(XMPP_ResponseTypeDragEntrantResult dragEntrantResultResponse) {
		openFireSoapBoxCli.send(dragEntrantResultResponse, personaId);
	}
	
	public void sendEventTimingOut(Long eventSessionId) {
		XMPP_EventTimingOutType eventTimingOut = new XMPP_EventTimingOutType();
		eventTimingOut.setEventSessionId( eventSessionId );
		XMPP_ResponseTypeEventTimingOut eventTimingOutResponse = new XMPP_ResponseTypeEventTimingOut();
		eventTimingOutResponse.setEventTimingOut( eventTimingOut );
		openFireSoapBoxCli.send(eventTimingOutResponse, personaId);
	}

}