package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.ArrayOfFriendPersona;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.PersonaFriendsList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getfriendlistfromuserid")
public class GetFriendListFromUserId {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	@XsiSchemaLocation(schemaLocation = "http://schemas.datacontract.org/2004/07/Victory.TransferObjects.DriverPersona")
	public PersonaFriendsList getFriendListFromUserId() {
		ArrayOfFriendPersona arrayOfFriendPersona = new ArrayOfFriendPersona();
		FriendPersona friendPersona = new FriendPersona();
		friendPersona.setIconIndex(2);
		friendPersona.setLevel(5);
		friendPersona.setName("AFRIEND");
		friendPersona.setOriginalName("AFRIEND");
		friendPersona.setPersonaId(100);
		friendPersona.setPresence(1);
		friendPersona.setSocialNetwork(1);
		friendPersona.setUserId(1);
		
		arrayOfFriendPersona.getFriendPersona().add(friendPersona);
		
		PersonaFriendsList personaFriendsList = new PersonaFriendsList();
		personaFriendsList.setFriendPersona(arrayOfFriendPersona);
		
		return personaFriendsList;
	}
}
