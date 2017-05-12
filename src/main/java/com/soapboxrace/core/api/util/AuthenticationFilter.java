package com.soapboxrace.core.api.util;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String userIdStr = requestContext.getHeaderString("userId");
		String securityToken = requestContext.getHeaderString("securityToken");
		if (userIdStr == null || securityToken == null || userIdStr.isEmpty() || securityToken.isEmpty()) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}
		// Long userId = Long.valueOf(userIdStr);
		try {
			validateToken(securityToken);
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String securityToken) throws Exception {
		// Check if it was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid
	}
}
