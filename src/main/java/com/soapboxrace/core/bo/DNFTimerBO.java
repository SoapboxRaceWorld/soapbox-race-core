package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.xmpp.XMPP_EventTimedOutType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeEventTimedOut;

import javax.annotation.Resource;
import javax.ejb.*;
import java.io.Serializable;

@Singleton
@Lock(LockType.READ)
public class DNFTimerBO {
    @Resource
    private TimerService timerService;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    public void scheduleDNF(EventSessionEntity eventSessionEntity, Long personaId) {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(new DNFTimerInfo(eventSessionEntity.getId(), personaId));
        timerService.createSingleActionTimer(eventSessionEntity.getEvent().getDnfTimerTime(), timerConfig);
    }

    @Timeout
    public void onTimeout(Timer timer) {
        DNFTimerInfo timerInfo = (DNFTimerInfo) timer.getInfo();
        XMPP_EventTimedOutType eventTimedOut = new XMPP_EventTimedOutType();
        eventTimedOut.setEventSessionId(timerInfo.eventSessionId);
        XMPP_ResponseTypeEventTimedOut eventTimedOutResponse = new XMPP_ResponseTypeEventTimedOut();
        eventTimedOutResponse.setEventTimedOut(eventTimedOut);
        openFireSoapBoxCli.send(eventTimedOutResponse, timerInfo.personaId);
    }

    private static class DNFTimerInfo implements Serializable {
        public final Long eventSessionId;
        public final Long personaId;

        public DNFTimerInfo(Long eventSessionId, Long personaId) {
            this.eventSessionId = eventSessionId;
            this.personaId = personaId;
        }
    }
}
