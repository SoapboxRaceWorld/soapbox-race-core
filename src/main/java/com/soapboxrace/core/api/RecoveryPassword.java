package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.RecoveryPasswordBO;

@Path("/RecoveryPassword")
public class RecoveryPassword {

	@EJB
	private RecoveryPasswordBO bo;

	@POST
	@Path("/sendRecoveryPassword")
	@Produces(MediaType.TEXT_HTML)
	public String sendRecoveryPassword(@FormParam("password") String password, @FormParam("passwordconf") String passwordconf, @FormParam("randomKey") String randomKey) {
		return bo.sendRecoveryPassword(password, passwordconf, randomKey);
	}

	@GET
	@Path("/createRecoveryPassword")
	public String createRecoveryPassword(@QueryParam("userId") Long userId) {
		return bo.createRecoveryPassword(userId);
	}

}
