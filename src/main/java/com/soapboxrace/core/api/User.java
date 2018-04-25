package com.soapboxrace.core.api;

import java.net.URI;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.soapboxrace.core.api.util.LaunchFilter;
import com.soapboxrace.core.api.util.LauncherChecks;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AuthenticationBO;
import com.soapboxrace.core.bo.InviteTicketBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.bo.UserBO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.UserInfo;
import com.soapboxrace.jaxb.login.LoginStatusVO;

@Path("User")
public class User {

	@Context
	UriInfo uri;

	@Context
	private HttpServletRequest sr;

	@EJB
	private AuthenticationBO authenticationBO;

	@EJB
	private UserBO userBO;

	@EJB
	private TokenSessionBO tokenBO;

	@EJB
	private InviteTicketBO inviteTicketBO;

	@POST
	@Secured
	@Path("GetPermanentSession")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPermanentSession(@HeaderParam("securityToken") String securityToken, @HeaderParam("userId") Long userId) {
		UserEntity userEntity = tokenBO.getUser(securityToken);
		BanEntity ban = authenticationBO.checkUserBan(userEntity);

		if (ban != null && ban.stillApplies()) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(new LaunchFilter.BanUtil(ban).invoke()).build();
		}

		tokenBO.deleteByUserId(userId);
		URI myUri = uri.getBaseUri();
		String randomUUID = tokenBO.createToken(userId, myUri.getHost());
		UserInfo userInfo = userBO.getUserById(userId);
		userInfo.getUser().setSecurityToken(randomUUID);
		userBO.createXmppUser(userInfo);
		return Response.ok(userInfo).build();
	}

	@POST
	@Secured
	@Path("SecureLoginPersona")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLoginPersona(@HeaderParam("securityToken") String securityToken, @HeaderParam("userId") Long userId,
			@QueryParam("personaId") Long personaId) {
		tokenBO.setActivePersonaId(securityToken, personaId, false);
		userBO.secureLoginPersona(userId, personaId);
		return "";
	}

	@POST
	@Secured
	@Path("SecureLogoutPersona")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLogoutPersona(@HeaderParam("securityToken") String securityToken, @HeaderParam("userId") Long userId,
			@QueryParam("personaId") Long personaId) {
		tokenBO.setActivePersonaId(securityToken, 0L, true);
		return "";
	}

	@POST
	@Secured
	@Path("SecureLogout")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLogout() {
		return "";
	}

	@GET
	@Path("authenticateUser")
	@Produces(MediaType.APPLICATION_XML)
//	@LauncherChecks
	public Response authenticateUser(@QueryParam("email") String email, @QueryParam("password") String password) {
		LoginStatusVO loginStatusVO = tokenBO.login(email, password, sr);
		if (loginStatusVO.isLoginOk()) {
			return Response.ok(loginStatusVO).build();
		}
		return Response.serverError().entity(loginStatusVO).build();
	}

	@GET
	@Path("createUser") // temporary
	@Produces(MediaType.APPLICATION_XML)
//	@LauncherChecks
	public Response createUser(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("inviteTicket") String inviteTicket) {
		LoginStatusVO loginStatusVO = userBO.createUserWithTicket(email, password, inviteTicket);
		if (loginStatusVO != null && loginStatusVO.isLoginOk()) {
			loginStatusVO = tokenBO.login(email, password, sr);
			return Response.ok(loginStatusVO).build();
		}
		return Response.serverError().entity(loginStatusVO).build();
	}

}
