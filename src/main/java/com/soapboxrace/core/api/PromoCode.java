package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.PromoCodeBO;

@Path("/PromoCode")
public class PromoCode {

	@EJB
	private PromoCodeBO bo;

	@EJB
	private ParameterBO parameterBO;

	@POST
	@Path("/createPromoCode")
	@Produces(MediaType.TEXT_HTML)
	public String createPromoCode(@FormParam("promoCodeToken") String promoCodeToken) {
		if (parameterBO.getStrParam("PROMO_CODE_TOKEN").equals(promoCodeToken)) {
			return bo.createPromoCode();
		}
		return "invalid promoCodeToken";
	}

	@POST
	@Path("/usePromoCode")
	@Produces(MediaType.TEXT_HTML)
	public String usePromoCode(@FormParam("promoCode") String promoCode, @FormParam("email") String email, @FormParam("password") String password) {
		if (promoCode.isEmpty() || email.isEmpty() || password.isEmpty()) {
			return "empty fields";
		}
		return bo.usePromoCode(promoCode, email, password);
	}

}
