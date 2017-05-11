package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.ArrayOfChatRoom;
import com.soapboxrace.jaxb.http.ChatRoom;
import com.soapboxrace.jaxb.http.ChatServer;

@Path("/Session")
public class Session {

	@GET
	@Path("/GetChatInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ChatServer ae() {
		ChatServer chatServer = new ChatServer();
		chatServer.setIp("127.0.0.1");
		chatServer.setPort(5222);
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
