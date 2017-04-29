package com.soapboxrace.core.servlet.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfChatRoom;
import com.soapboxrace.jaxb.http.ChatRoom;
import com.soapboxrace.jaxb.http.ChatServer;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/Session/GetChatInfo" })
public class GetChatInfo extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570940629263972931L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ChatServer chatServer = new ChatServer();
		chatServer.setIp("127.0.0.1");
		chatServer.setPort(5222);
		chatServer.setPrefix("sbrw");
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setChannelCount(2);
		chatRoom.setLongName("TXT_CHAT_LANG_ENGLISH");
		chatRoom.setShortName("EN");
		ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();
		arrayOfChatRoom.getChatRoom().add(chatRoom);
		chatServer.setRooms(arrayOfChatRoom);

		JAXBElement<ChatServer> createChatServer = new ObjectFactory().createChatServer(chatServer);
		String marshal = MarshalXML.marshal(createChatServer);
		answer(request, response, marshal);
	}
}
