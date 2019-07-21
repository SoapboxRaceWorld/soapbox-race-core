package com.soapboxrace.core.xmpp;

import com.soapboxrace.jaxb.xmpp.*;

public class XmppEvent {

    private long personaId;

    private OpenFireSoapBoxCli openFireSoapBoxCli;

    public XmppEvent(long personaId, OpenFireSoapBoxCli openFireSoapBoxCli) {
        this.personaId = personaId;
        this.openFireSoapBoxCli = openFireSoapBoxCli;
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
        eventTimingOut.setEventSessionId(eventSessionId);
        XMPP_ResponseTypeEventTimingOut eventTimingOutResponse = new XMPP_ResponseTypeEventTimingOut();
        eventTimingOutResponse.setEventTimingOut(eventTimingOut);
        openFireSoapBoxCli.send(eventTimingOutResponse, personaId);
    }

}