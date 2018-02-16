package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.PersonaFriendsList;

@Path("/getfriendlistfromuserid")
public class GetFriendListFromUserId {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	@XsiSchemaLocation(schemaLocation = "http://schemas.datacontract.org/2004/07/Victory.TransferObjects.DriverPersona")
	public PersonaFriendsList getFriendListFromUserId() {
		return new PersonaFriendsList();
	}
}
