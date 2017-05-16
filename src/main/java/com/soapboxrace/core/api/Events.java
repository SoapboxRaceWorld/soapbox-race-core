package com.soapboxrace.core.api;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.EventBO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.jaxb.http.ArrayOfEventDefinition;
import com.soapboxrace.jaxb.http.ArrayOfInt;
import com.soapboxrace.jaxb.http.EventDefinition;
import com.soapboxrace.jaxb.http.EventsPacket;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;
import com.soapboxrace.jaxb.http.Vector3;

@Path("/events")
public class Events {

	@EJB
	private EventBO eventBO;

	@GET
	@Secured
	@Path("/availableatlevel")
	@Produces(MediaType.APPLICATION_XML)
	public EventsPacket availableAtLevel() {
		EventsPacket eventsPacket = new EventsPacket();
		ArrayOfEventDefinition arrayOfEventDefinition = new ArrayOfEventDefinition();
		List<EventEntity> availableAtLevel = eventBO.availableAtLevel();
		for (EventEntity eventEntity : availableAtLevel) {
			arrayOfEventDefinition.getEventDefinition().add(getEventDefinitionWithId(eventEntity.getId()));
		}
		eventsPacket.setEvents(arrayOfEventDefinition);
		return eventsPacket;
	}

	private EventDefinition getEventDefinitionWithId(int eventId) {
		EventDefinition eventDefinition = new EventDefinition();
		eventDefinition.setCarClassHash(607077938);
		eventDefinition.setCoins(0);
		Vector3 vector3 = new Vector3();
		vector3.setX(0);
		vector3.setY(0);
		vector3.setZ(0);
		eventDefinition.setEngagePoint(vector3);
		eventDefinition.setEventId(eventId);
		eventDefinition.setEventLocalization(953953223);
		eventDefinition.setEventModeDescriptionLocalization(1204604434);
		eventDefinition.setEventModeIcon("GameModeIcon_Sprint");
		eventDefinition.setEventModeId(9);
		eventDefinition.setEventModeLocalization(-1152300457);
		eventDefinition.setIsEnabled(true);
		eventDefinition.setIsLocked(false);
		eventDefinition.setLaps(0);
		eventDefinition.setLength(0);
		eventDefinition.setMaxClassRating(999);
		eventDefinition.setMaxEntrants(2);
		eventDefinition.setMaxLevel(70);
		eventDefinition.setMinClassRating(0);
		eventDefinition.setMinEntrants(2);
		eventDefinition.setMinLevel(2);
		eventDefinition.setRegionLocalization(0);
		eventDefinition.setRewardModes(new ArrayOfInt());
		eventDefinition.setTimeLimit(0);
		eventDefinition.setTrackLayoutMap("nothing");
		eventDefinition.setTrackLocalization(-491522157);
		return eventDefinition;
	}

	@GET
	@Secured
	@Path("/gettreasurehunteventsession")
	@Produces(MediaType.APPLICATION_XML)
	public TreasureHuntEventSession getTreasureHuntEventSession() {
		return new TreasureHuntEventSession();
	}

	@GET
	@Secured
	@Path("/instancedaccolades")
	@Produces(MediaType.APPLICATION_XML)
	public String instancedAccolades(@QueryParam("eventSessionId") Long eventSessionId) {
		return "";
	}
}
