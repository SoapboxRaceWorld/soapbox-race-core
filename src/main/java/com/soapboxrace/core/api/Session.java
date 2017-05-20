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
		ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_ENGLISH", "EN"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_GERMAN", "DE"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_FRENCH", "FR"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_SPANISH", "ES"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_POLISH", "PL"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_BRAZILIANPORTUGUESE", "BR"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_RUSSIAN", "RU"));
		arrayOfChatRoom.getChatRoom().add(getRoom("TXT_CHAT_LANG_GENERAL", "GN"));
		chatServer.setRooms(arrayOfChatRoom);
		return chatServer;
	}

	private ChatRoom getRoom(String longName, String shortName) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChannelCount(10);
		chatRoom.setLongName(longName);
		chatRoom.setShortName(shortName);
		return chatRoom;
	}
}
