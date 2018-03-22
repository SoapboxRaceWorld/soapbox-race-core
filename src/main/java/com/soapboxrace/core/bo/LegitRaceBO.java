package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;

@Stateless
public class LegitRaceBO {

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private SocialBO socialBo;

	public boolean isLegit(Long activePersonaId, ArbitrationPacket arbitrationPacket, EventSessionEntity sessionEntity) {
		int minimumTime = 0;

		if (arbitrationPacket instanceof PursuitArbitrationPacket)
			minimumTime = parameterBO.getMinPursuitTime();
		else if (arbitrationPacket instanceof RouteArbitrationPacket)
			minimumTime = parameterBO.getMinRouteTime();
		else if (arbitrationPacket instanceof TeamEscapeArbitrationPacket)
			minimumTime = parameterBO.getMinTETime();
		else if (arbitrationPacket instanceof DragArbitrationPacket)
			minimumTime = parameterBO.getMinDragTime();

		final long timeDiff = sessionEntity.getEnded() - sessionEntity.getStarted();
		boolean legit = timeDiff >= minimumTime;

		if (!legit) {
			socialBo.sendReport(0L, activePersonaId, 3, String.format("Abnormal event time: %d", timeDiff), (int) arbitrationPacket.getCarId(), 0, 0L);
		}
		if (arbitrationPacket.getHacksDetected() > 0) {
			socialBo.sendReport(0L, activePersonaId, 3, "Server sent a report for cheat", (int) arbitrationPacket.getCarId(), 0,
					arbitrationPacket.getHacksDetected());
		}
		return legit;
	}
}
