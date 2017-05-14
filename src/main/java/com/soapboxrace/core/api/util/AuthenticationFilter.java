package com.soapboxrace.core.api.util;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.soapboxrace.core.bo.TokenSessionBO;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@EJB
	private TokenSessionBO tokenSessionBO;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String userIdStr = requestContext.getHeaderString("userId");
		String securityToken = requestContext.getHeaderString("securityToken");
		if (userIdStr == null || securityToken == null || userIdStr.isEmpty() || securityToken.isEmpty()) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}
		Long userId = Long.valueOf(userIdStr);
		try {
			validateToken(userId, securityToken);
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(Long userId, String securityToken) throws Exception {
		if (!tokenSessionBO.verifyToken(userId, securityToken)) {
			throw new Exception("Invalid Token");
		}
	}
}
