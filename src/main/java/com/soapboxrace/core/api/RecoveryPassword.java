package com.soapboxrace.core.api;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.bo.RecoveryPasswordBO;

@Path("/RecoveryPassword")
public class RecoveryPassword {

	@EJB
	private RecoveryPasswordBO bo;

	@Resource(mappedName = "java:jboss/mail/Gmail")
	private Session mailSession;

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

	@GET
	@Path("/testEmail")
	@Produces(MediaType.TEXT_HTML)
	public String testEmail() {
		MimeMessage message = new MimeMessage(mailSession);
		try {
			message.setFrom(new InternetAddress(Config.getEmailFrom()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("someone@email.com"));
			message.setSubject("Testing wildfly email jndi");
			message.setText(Config.getServerAddress() + "/soapbox-race-core/password.jsp?randomKey=");
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "done";
	}
}
