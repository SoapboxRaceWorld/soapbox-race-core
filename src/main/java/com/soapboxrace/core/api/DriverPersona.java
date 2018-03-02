package com.soapboxrace.core.api;

import java.io.InputStream;
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
import javax.xml.ws.WebServiceException;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.DriverPersonaBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.bo.UserBO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ArrayOfInt;
import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ArrayOfPersonaBase;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.http.PersonaIdArray;
import com.soapboxrace.jaxb.http.PersonaMotto;
import com.soapboxrace.jaxb.http.PersonaPresence;
import com.soapboxrace.jaxb.http.ProfileData;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Path("/DriverPersona")
public class DriverPersona {

	private final Pattern NAME_PATTERN = Pattern.compile("^[A-Z0-9]{3,15}$");
	
	@EJB
	private DriverPersonaBO bo;

	@EJB
	private UserBO userBo;

	@EJB
	private TokenSessionBO tokenSessionBo;

	@GET
	@Secured
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
	@Secured
	@Path("/GetPersonaInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ProfileData getPersonaInfo(@QueryParam("personaId") Long personaId) {
		return bo.getPersonaInfo(personaId);
	}

	@POST
	@Secured
	@Path("/ReserveName")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfString reserveName(@QueryParam("name") String name) {
		return bo.reserveName(name);
	}

	@POST
	@Secured
	@Path("/UnreserveName")
	@Produces(MediaType.APPLICATION_XML)
	public String UnreserveName(@QueryParam("name") String name) {
		return "";
	}

	@POST
	@Secured
	@Path("/CreatePersona")
	@Produces(MediaType.APPLICATION_XML)
	public Response createPersona(@HeaderParam("userId") Long userId, @HeaderParam("securityToken") String securityToken, @QueryParam("name") String name,
								  @QueryParam("iconIndex") int iconIndex, @QueryParam("clan") String clan, @QueryParam("clanIcon") String clanIcon) {
		if (!NAME_PATTERN.matcher(name).matches()) {
			return Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("Invalid name. Can only contain A-Z, 0-9, and can be between 3 and 15 characters.")
					.build();
		}
		
		ArrayOfString nameReserveResult = bo.reserveName(name);
		
		if (!nameReserveResult.getString().isEmpty()) {
			return Response
					.status(Response.Status.NOT_ACCEPTABLE)
					.entity("Player with this name already exists!")
					.build();
//			throw new WebServiceException("Player with this name already exists!");
		}
		
		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setName(name);
		personaEntity.setIconIndex(iconIndex);
		ProfileData persona = bo.createPersona(userId, personaEntity);
		
		if (persona == null) {
			return Response
					.status(Response.Status.FORBIDDEN)
					.entity("Can't have more than 3 personas")
					.build();
		}
		
		long personaId = persona.getPersonaId();
		userBo.createXmppUser(personaId, securityToken.substring(0, 16));
		return Response.ok(persona).build();
	}

	@POST
	@Secured
	@Path("/DeletePersona")
	@Produces(MediaType.APPLICATION_XML)
	public String deletePersona(@QueryParam("personaId") Long personaId, @HeaderParam("securityToken") String securityToken) {
		tokenSessionBo.verifyPersona(securityToken, personaId);
		bo.deletePersona(personaId);
		return "<long>0</long>";
	}

	@POST
	@Path("/GetPersonaBaseFromList")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfPersonaBase getPersonaBaseFromList(InputStream is) {
		PersonaIdArray personaIdArray = UnmarshalXML.unMarshal(is, PersonaIdArray.class);
		ArrayOfLong personaIds = personaIdArray.getPersonaIds();
		return bo.getPersonaBaseFromList(personaIds.getLong());
	}

	@POST
	@Secured
	@Path("/UpdatePersonaPresence")
	@Produces(MediaType.APPLICATION_XML)
	public String updatePersonaPresence() {
		return "";
	}

	@GET
	@Secured
	@Path("/GetPersonaPresenceByName")
	@Produces(MediaType.APPLICATION_XML)
	public String getPersonaPresenceByName(@QueryParam("displayName") String displayName) {
		PersonaPresence personaPresenceByName = bo.getPersonaPresenceByName(displayName);
		if (personaPresenceByName.getPersonaId() == 0) {
			return "";
		}
		return MarshalXML.marshal(personaPresenceByName);
	}

	@POST
	@Secured
	@Path("/UpdateStatusMessage")
	@Produces(MediaType.APPLICATION_XML)
	public PersonaMotto updateStatusMessage(InputStream statusXml, @HeaderParam("securityToken") String securityToken, @Context Request request) {
		PersonaMotto personaMotto = UnmarshalXML.unMarshal(statusXml, PersonaMotto.class);
		tokenSessionBo.verifyPersona(securityToken, personaMotto.getPersonaId());

		bo.updateStatusMessage(personaMotto.getMessage(), personaMotto.getPersonaId());
		return personaMotto;
	}
}
