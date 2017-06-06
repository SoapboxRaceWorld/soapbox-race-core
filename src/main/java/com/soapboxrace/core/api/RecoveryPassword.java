package com.soapboxrace.core.api;

import java.util.Properties;

import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.RecoveryPasswordBO;

@Path("/RecoveryPassword")
public class RecoveryPassword {
	
	@EJB
	private RecoveryPasswordBO bo;
	
	@GET
	@Path("/sendEmail")
	public void sendEmail() {
        // Assuming you are sending email from localhost
        final String username = "user@gmail.com";// Change Username Here
        final String password = "password"; // Change Password Here
        String from = "from@gmail.com"; // Change From Mail ID
        String to = "to@gmail.com";// Change To Mail ID
        
        // Get system properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("sujet");
            message.setText("message");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
	
	@POST
	@Path("/sendRecoveryPassword")
	@Produces(MediaType.TEXT_HTML)
	public String sendRecoveryPassword(@FormParam("password") String password, @FormParam("passwordconf") String passwordconf) {
		return bo.sendRecoveryPassword(password, passwordconf);
	}
	
}
