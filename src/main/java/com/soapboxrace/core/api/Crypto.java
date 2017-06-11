package com.soapboxrace.core.api;

import java.nio.ByteBuffer;
import java.util.Base64;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.UdpRelayCryptoTicket;

@Path("/crypto")
public class Crypto {

	@EJB
	private TokenSessionBO tokenBO;

	@GET
	@Secured
	@Path("/relaycryptoticket/{personaId}")
	@Produces(MediaType.APPLICATION_XML)
	public UdpRelayCryptoTicket relayCryptoTicket(@HeaderParam("securityToken") String securityToken, @PathParam("personaId") Long personaId) {
		UdpRelayCryptoTicket udpRelayCryptoTicket = new UdpRelayCryptoTicket();
		String activeRelayCryptoTicket = tokenBO.getActiveRelayCryptoTicket(securityToken);
		udpRelayCryptoTicket.setCryptoTicket(activeRelayCryptoTicket);
		udpRelayCryptoTicket.setSessionKey("AAAAAAAAAAAAAAAAAAAAAA==");
		udpRelayCryptoTicket.setTicketIv("AAAAAAAAAAAAAAAAAAAAAA==");
		return udpRelayCryptoTicket;
	}

	@GET
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String genericEmptyGet(@PathParam("path") String path) {
		System.out.println("empty GET!!!");
		return "";
	}

	@GET
	@Path("/cryptoticket")
	@Produces(MediaType.APPLICATION_XML)
	public String cryptoticket() {
		byte[] helloPacket = { 10, 11, 12, 13 };
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		byteBuffer.put(helloPacket);
		byte[] cryptoTicketBytes = byteBuffer.array();
		String cryptoTicketBase64 = Base64.getEncoder().encodeToString(cryptoTicketBytes);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ClientServerCryptoTicket>\n");
		stringBuilder.append("<CryptoTicket>");
		stringBuilder.append(cryptoTicketBase64);
		stringBuilder.append("</CryptoTicket>\n");
		stringBuilder.append("<SessionKey>AAAAAAAAAAAAAAAAAAAAAA==</SessionKey>\n");
		stringBuilder.append("<TicketIv>AAAAAAAAAAAAAAAAAAAAAA==</TicketIv>\n");
		stringBuilder.append("</ClientServerCryptoTicket>");
		return stringBuilder.toString();
	}
}
