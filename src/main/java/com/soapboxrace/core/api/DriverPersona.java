package com.soapboxrace.core.api;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.soapboxrace.core.api.util.ConcurrentUtil;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.DriverPersonaBO;
import com.soapboxrace.core.bo.PresenceManager;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.bo.UserBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;

@Path("/DriverPersona")
public class DriverPersona
{
	private final Pattern NAME_PATTERN = Pattern.compile("^[A-Z0-9]{3,15}$");

	@EJB
	private DriverPersonaBO bo;

	@EJB
	private UserBO userBo;

	@EJB
	private TokenSessionBO tokenSessionBo;

	@EJB
	private PresenceManager presenceManager;

	@EJB
	private FriendDAO friendDAO;

	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

	@GET
	@Secured
	@Path("/GetExpLevelPointsMap")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfInt getExpLevelPointsMap()
	{
		return bo.getExpLevelPointsMap();
	}

	@GET
	@Secured
	@Path("/GetPersonaInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ProfileData getPersonaInfo(@QueryParam("personaId") Long personaId)
	{
		return bo.getPersonaInfo(personaId);
	}

	@POST
	@Secured
	@Path("/ReserveName")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfString reserveName(@QueryParam("name") String name)
	{
		return bo.reserveName(name);
	}

	@POST
	@Secured
	@Path("/UnreserveName")
	@Produces(MediaType.APPLICATION_XML)
	public String UnreserveName(@QueryParam("name") String name)
	{
		return "";
	}

	@POST
	@Secured
	@Path("/CreatePersona")
	@Produces(MediaType.APPLICATION_XML)
	public Response createPersona(@HeaderParam("userId") Long userId, @HeaderParam("securityToken") String securityToken, @QueryParam("name") String name,
								  @QueryParam("iconIndex") int iconIndex, @QueryParam("clan") String clan, @QueryParam("clanIcon") String clanIcon)
	{
		if (!NAME_PATTERN.matcher(name).matches())
		{
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid name. Can only contain A-Z, 0-9, and can be between 3 and 15 characters.")
					.build();
		}

		ArrayOfString nameReserveResult = bo.reserveName(name);

		if (!nameReserveResult.getString().isEmpty())
		{
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Player with this name already exists!").build();
		}

		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setName(name);
		personaEntity.setIconIndex(iconIndex);
		ProfileData persona = bo.createPersona(userId, personaEntity);

		if (persona == null)
		{
			return Response.status(Response.Status.FORBIDDEN).entity("Can't have more than 3 personas").build();
		}

		long personaId = persona.getPersonaId();
		userBo.createXmppUser(personaId, securityToken.substring(0, 16));
		return Response.ok(persona).build();
	}

	@POST
	@Secured
	@Path("/DeletePersona")
	@Produces(MediaType.APPLICATION_XML)
	public String deletePersona(@QueryParam("personaId") Long personaId, @HeaderParam("securityToken") String securityToken)
	{
		tokenSessionBo.verifyPersona(securityToken, personaId);
		bo.deletePersona(personaId);
		return "<long>0</long>";
	}

	@POST
	@Path("/GetPersonaBaseFromList")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfPersonaBase getPersonaBaseFromList(InputStream is)
	{
		PersonaIdArray personaIdArray = UnmarshalXML.unMarshal(is, PersonaIdArray.class);
		ArrayOfLong personaIds = personaIdArray.getPersonaIds();
		return bo.getPersonaBaseFromList(personaIds.getLong());
	}

	@POST
	@Secured
	@Path("/UpdatePersonaPresence")
	@Produces(MediaType.APPLICATION_XML)
	public String updatePersonaPresence(@HeaderParam("securityToken") String securityToken, @QueryParam("presence") int presence)
	{
		if (tokenSessionBo.getActivePersonaId(securityToken) == 0L)
			return "";

		PersonaEntity personaEntity = personaDAO.findById(tokenSessionBo.getActivePersonaId(securityToken));
		presenceManager.setPresence(personaEntity.getPersonaId(), presence);

		ConcurrentUtil.EXECUTOR_SERVICE.submit(() -> {
			List<FriendEntity> friends = friendDAO.findByUserId(personaEntity.getUser().getId());

			for (FriendEntity friend : friends)
			{
				XMPP_ResponseTypePersonaBase personaPacket = new XMPP_ResponseTypePersonaBase();
				PersonaBase xmppPersonaBase = new PersonaBase();

				xmppPersonaBase.setBadges(new ArrayOfBadgePacket());
				xmppPersonaBase.setIconIndex(personaEntity.getIconIndex());
				xmppPersonaBase.setLevel(personaEntity.getLevel());
				xmppPersonaBase.setMotto(personaEntity.getMotto());
				xmppPersonaBase.setName(personaEntity.getName());
				xmppPersonaBase.setPersonaId(personaEntity.getPersonaId());
				xmppPersonaBase.setPresence(presence);
				xmppPersonaBase.setScore(personaEntity.getScore());
				xmppPersonaBase.setUserId(personaEntity.getUser().getId());

				personaPacket.setPersonaBase(xmppPersonaBase);

				openFireSoapBoxCli.send(personaPacket, friend.getPersonaId());
			}
		});

		return "";
	}

	@GET
	@Secured
	@Path("/GetPersonaPresenceByName")
	@Produces(MediaType.APPLICATION_XML)
	public String getPersonaPresenceByName(@QueryParam("displayName") String displayName)
	{
		PersonaPresence personaPresenceByName = bo.getPersonaPresenceByName(displayName);
		if (personaPresenceByName.getPersonaId() == 0)
		{
			return "";
		}
		return MarshalXML.marshal(personaPresenceByName);
	}

	@POST
	@Secured
	@Path("/UpdateStatusMessage")
	@Produces(MediaType.APPLICATION_XML)
	public PersonaMotto updateStatusMessage(InputStream statusXml, @HeaderParam("securityToken") String securityToken, @Context Request request)
	{
		PersonaMotto personaMotto = UnmarshalXML.unMarshal(statusXml, PersonaMotto.class);
		tokenSessionBo.verifyPersona(securityToken, personaMotto.getPersonaId());

		bo.updateStatusMessage(personaMotto.getMessage(), personaMotto.getPersonaId());
		return personaMotto;
	}
}