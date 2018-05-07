package com.soapboxrace.core.api.util;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.soapboxrace.core.bo.HardwareInfoBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.UserEntity;

@HwBan
@Provider
@Priority(Priorities.AUTHORIZATION)
public class HwBanFilter implements ContainerRequestFilter {

	@EJB
	private HardwareInfoBO hardwareInfoBO;

	@EJB
	private TokenSessionBO tokenBO;

	@Context
	private HttpServletRequest sr;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String securityToken = requestContext.getHeaderString("securityToken");
		UserEntity user = tokenBO.getUser(securityToken);
		String gameHardwareHash = user.getGameHardwareHash();
		if (hardwareInfoBO.isHardwareHashBanned(gameHardwareHash)) {
			requestContext.abortWith(Response.status(Response.Status.GONE).build());
		}
	}

}
