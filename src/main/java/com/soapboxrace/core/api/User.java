package com.soapboxrace.core.api;

import java.io.StringReader;
import java.net.URI;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.soapboxrace.core.api.util.HwBan;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.dao.UserDAO;
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

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private GetServerInformationBO serverInformationBO;

	@EJB
	private UserDAO userDAO;

	@POST
	@Secured
	@Path("GetPermanentSession")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPermanentSession(@HeaderParam("securityToken") String securityToken, @HeaderParam("userId") Long userId) {
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
	@HwBan
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
	@Path("authenticateUserAuthserv")
	@Produces(MediaType.APPLICATION_XML)
	public Response authenticateAuthservUser(@QueryParam("token") String token) throws GeneralSecurityException {
		LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
		if (!serverInformationBO.getServerInformation().isAuthservEnabled()) {
			loginStatusVO.setDescription("AUTHSERV NOT ENABLED");
			return Response.serverError().entity(loginStatusVO).build();
		}
		if (token == null || token.equals("")) {
			loginStatusVO.setDescription("LOGIN ERROR");
			return Response.serverError().entity(loginStatusVO).build();
		}

		TrustManager[] trustAllCerts = new TrustManager[] {
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
				}
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
				}
			}
		};

		SSLContext sc;
		sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());

		Response res = ClientBuilder.newBuilder().sslContext(sc).build()
			.target(parameterBO.getStrParam("AUTHSERV_ENDPOINT"))
			.path("/api/v1/token/verify")
			.queryParam("token", token)
			.request(MediaType.APPLICATION_JSON)
			.header("User-Agent", "soapbox-race-core")
			.post(null);
		String resStr = res.readEntity(String.class);
		JsonReader jsonReader = Json.createReader(new StringReader(resStr));
		JsonObject reply = jsonReader.readObject();
		if (!reply.getBoolean("valid")) {
			loginStatusVO.setDescription("LOGIN ERROR");
			return Response.serverError().entity(loginStatusVO).build();
		}
		String uuid = reply.getString("uuid");
		UserEntity user = userDAO.findByAuthservUUID(uuid);
		if (user == null) {
			userBO.createUserAuthserv(uuid);
		}
		loginStatusVO = tokenBO.loginAuthserv(uuid, sr);
		if (loginStatusVO.isLoginOk()) {
			return Response.ok(loginStatusVO).build();
		}
		return Response.serverError().entity(loginStatusVO).build();
	}

	@GET
	@Path("createUser")
	@Produces(MediaType.APPLICATION_XML)
//	@LauncherChecks
	public Response createUser(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("inviteTicket") String inviteTicket) {
		LoginStatusVO loginStatusVO = tokenBO.checkGeoIp(sr.getRemoteAddr());
		if (!loginStatusVO.isLoginOk()) {
			return Response.serverError().entity(loginStatusVO).build();
		}
		loginStatusVO = userBO.createUserWithTicket(email, password, inviteTicket);
		if (loginStatusVO != null && loginStatusVO.isLoginOk()) {
			loginStatusVO = tokenBO.login(email, password, sr);
			return Response.ok(loginStatusVO).build();
		}
		return Response.serverError().entity(loginStatusVO).build();
	}

}
