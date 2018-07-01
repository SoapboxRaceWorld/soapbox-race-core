package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PresenceManager;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.ArrayOfFriendPersona;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.PersonaFriendsList;

@Path("/getfriendlistfromuserid")
public class GetFriendListFromUserId
{
	@EJB
	private FriendDAO friendDAO;

	@EJB
	private OpenFireRestApiCli restApiCli;

	@EJB
	private PresenceManager presenceManager;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	@XsiSchemaLocation(schemaLocation = "http://schemas.datacontract.org/2004/07/Victory.TransferObjects.DriverPersona")
	public PersonaFriendsList getFriendListFromUserId(@HeaderParam("userId") Long userId)
	{
		PersonaFriendsList personaFriendsList = new PersonaFriendsList();
		ArrayOfFriendPersona arrayOfFriendPersona = new ArrayOfFriendPersona();

		for (FriendEntity friendEntity : friendDAO.findByUserId(userId))
		{
			FriendPersona friendPersona = new FriendPersona();
			friendPersona.setUserId(friendEntity.getOtherPersona().getUser().getId());
			friendPersona.setSocialNetwork(0);
			friendPersona.setPresence(
					friendEntity.getStatus() == 1
							? presenceManager.getPresence(friendEntity.getOtherPersona().getPersonaId())
							: 3
			);
			friendPersona.setPersonaId(friendEntity.getOtherPersona().getPersonaId());
			friendPersona.setLevel(friendEntity.getOtherPersona().getLevel());
			friendPersona.setName(friendEntity.getOtherPersona().getName());
			friendPersona.setOriginalName(friendEntity.getOtherPersona().getName());
			friendPersona.setIconIndex(friendEntity.getOtherPersona().getIconIndex());

			arrayOfFriendPersona.getFriendPersona().add(friendPersona);
		}
		
		personaFriendsList.setFriendPersona(arrayOfFriendPersona);

		return personaFriendsList;
	}
}