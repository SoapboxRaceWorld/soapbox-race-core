package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.PromoCodeBO;

@Path("/PromoCode")
public class PromoCode {
	
	@EJB
	private PromoCodeBO bo;

	@GET
	@Path("/createPromoCode")
	@Produces(MediaType.TEXT_HTML)
	public String createPromoCode(@QueryParam("userId") Long userId, @QueryParam("isPremium") Boolean isPremium) {
		return bo.createPromoCode(userId, isPremium);
	}
	
	@POST
	@Path("/sendPromoCode")
	@Produces(MediaType.TEXT_HTML)
	public String sendPromoCode(@FormParam("promoCode") String promoCode, @FormParam("email") String email, @FormParam("password") String password) {
		return bo.sendPromoCode(promoCode, email, password);
	}
}
