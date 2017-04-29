package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.ArrayOfInt;
import com.soapboxrace.jaxb.http.ArrayOfPersonaBase;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.http.ProfileData;

@Path("/DriverPersona")
public class DriverPersona {

	@GET
	@Path("/GetExpLevelPointsMap")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfInt getExpLevelPointsMap() {
		ArrayOfInt arrayOfInt = new ArrayOfInt();
		arrayOfInt.getInt().add(100);
		arrayOfInt.getInt().add(975);
		arrayOfInt.getInt().add(2025);
		arrayOfInt.getInt().add(3625);
		arrayOfInt.getInt().add(5875);
		arrayOfInt.getInt().add(8875);
		arrayOfInt.getInt().add(12725);
		arrayOfInt.getInt().add(17525);
		arrayOfInt.getInt().add(23375);
		arrayOfInt.getInt().add(30375);
		arrayOfInt.getInt().add(39375);
		arrayOfInt.getInt().add(50575);
		arrayOfInt.getInt().add(64175);
		arrayOfInt.getInt().add(80375);
		arrayOfInt.getInt().add(99375);
		arrayOfInt.getInt().add(121375);
		arrayOfInt.getInt().add(146575);
		arrayOfInt.getInt().add(175175);
		arrayOfInt.getInt().add(207375);
		arrayOfInt.getInt().add(243375);
		arrayOfInt.getInt().add(283375);
		arrayOfInt.getInt().add(327575);
		arrayOfInt.getInt().add(376175);
		arrayOfInt.getInt().add(429375);
		arrayOfInt.getInt().add(487375);
		arrayOfInt.getInt().add(550375);
		arrayOfInt.getInt().add(618575);
		arrayOfInt.getInt().add(692175);
		arrayOfInt.getInt().add(771375);
		arrayOfInt.getInt().add(856375);
		arrayOfInt.getInt().add(950875);
		arrayOfInt.getInt().add(1055275);
		arrayOfInt.getInt().add(1169975);
		arrayOfInt.getInt().add(1295375);
		arrayOfInt.getInt().add(1431875);
		arrayOfInt.getInt().add(1579875);
		arrayOfInt.getInt().add(1739775);
		arrayOfInt.getInt().add(1911975);
		arrayOfInt.getInt().add(2096875);
		arrayOfInt.getInt().add(2294875);
		arrayOfInt.getInt().add(2506375);
		arrayOfInt.getInt().add(2731775);
		arrayOfInt.getInt().add(2971475);
		arrayOfInt.getInt().add(3225875);
		arrayOfInt.getInt().add(3495375);
		arrayOfInt.getInt().add(3780375);
		arrayOfInt.getInt().add(4081275);
		arrayOfInt.getInt().add(4398475);
		arrayOfInt.getInt().add(4732375);
		arrayOfInt.getInt().add(5083375);
		arrayOfInt.getInt().add(5481355);
		arrayOfInt.getInt().add(5898805);
		arrayOfInt.getInt().add(6336165);
		arrayOfInt.getInt().add(6793875);
		arrayOfInt.getInt().add(7272375);
		arrayOfInt.getInt().add(7772105);
		arrayOfInt.getInt().add(8293505);
		arrayOfInt.getInt().add(8837015);
		arrayOfInt.getInt().add(9403075);
		arrayOfInt.getInt().add(9992125);
		return arrayOfInt;
	}

	@GET
	@Path("/GetPersonaInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ProfileData getPersonaInfo() {
		ProfileData profileData = new ProfileData();
		profileData.setBadges(new ArrayOfBadgePacket());
		profileData.setCash(5000000);
		profileData.setIconIndex(0);
		profileData.setLevel(60);
		profileData.setMotto("");
		profileData.setName("NOBODY");
		profileData.setPercentToLevel(0);
		profileData.setPersonaId(100);
		profileData.setRating(0);
		profileData.setRep(0);
		profileData.setRepAtCurrentLevel(0);
		profileData.setScore(0);
		return profileData;
	}

	@POST
	@Path("/ReserveName")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfString reserveName() {
		return new ArrayOfString();
	}

	@POST
	@Path("/CreatePersona")
	@Produces(MediaType.APPLICATION_XML)
	public ProfileData createPersona() {
		ProfileData profileData = new ProfileData();
		profileData.setName("NOBODY");
		profileData.setCash(5000000);
		profileData.setIconIndex(0);
		profileData.setPersonaId(100);
		profileData.setLevel(60);
		return profileData;
	}

	@GET
	@Path("/GetPersonaBaseFromList")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfPersonaBase getPersonaBaseFromList(@HeaderParam("userId") Long userId) {
		ArrayOfPersonaBase arrayOfPersonaBase = new ArrayOfPersonaBase();
		PersonaBase personaBase = new PersonaBase();
		personaBase.setBadges(new ArrayOfBadgePacket());
		personaBase.setIconIndex(0);
		personaBase.setLevel(60);
		personaBase.setMotto("");
		personaBase.setName("NOBODY");
		personaBase.setPresence(1);
		personaBase.setPersonaId(100);
		personaBase.setScore(0);
		personaBase.setUserId(userId);
		arrayOfPersonaBase.getPersonaBase().add(personaBase);
		return arrayOfPersonaBase;
	}

	@POST
	@Path("/UpdatePersonaPresence")
	@Produces(MediaType.APPLICATION_XML)
	public String UpdatePersonaPresence() {
		return "";
	}
}
