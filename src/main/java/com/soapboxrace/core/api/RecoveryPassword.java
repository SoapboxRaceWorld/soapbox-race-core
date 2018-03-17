package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.RecoveryPasswordBO;

@Path("/RecoveryPassword")
public class RecoveryPassword {

	@EJB
	private RecoveryPasswordBO bo;

	@POST
	@Path("/resetPassword")
	@Produces(MediaType.TEXT_HTML)
	public String resetPassord(@FormParam("password") String password, @FormParam("passwordconf") String passwordconf, @FormParam("randomKey") String randomKey) {
		if (password == null || passwordconf == null || randomKey == null || password.isEmpty() || passwordconf.isEmpty() || randomKey.isEmpty()) {
			return "ERROR: empty values!";
		}
		if (!password.equals(passwordconf)) {
			return "ERROR: Passwords not match!";
		}
		return bo.resetPassword(password, passwordconf, randomKey);
	}

	@POST
	@Path("/forgotPassword")
	@Produces(MediaType.TEXT_HTML)
	public String forgotPassword(@FormParam("email") String email) {
		return bo.forgotPassword(email);
	}

}
