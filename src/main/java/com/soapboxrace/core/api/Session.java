package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.ArrayOfChatRoom;
import com.soapboxrace.jaxb.http.ChatRoom;
import com.soapboxrace.jaxb.http.ChatServer;

@Path("/Session")
public class Session {

	@GET
	@Secured
	@Path("/GetChatInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ChatServer getChatInfo() {
		ChatServer chatServer = new ChatServer();
		chatServer.setIp(Config.getXmppIp());
		chatServer.setPort(Config.getXmppPort());
		chatServer.setPrefix("sbrw");
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChannelCount(1);
		chatRoom.setLongName("TXT_CHAT_LANG_ENGLISH");
		chatRoom.setShortName("EN");
		ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();
		arrayOfChatRoom.getChatRoom().add(chatRoom);
		chatServer.setRooms(arrayOfChatRoom);
		return chatServer;
	}
}
