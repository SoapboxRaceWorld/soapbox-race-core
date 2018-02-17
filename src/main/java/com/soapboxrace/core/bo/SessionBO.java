package com.soapboxrace.core.bo;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.ChatRoomDAO;
import com.soapboxrace.core.jpa.ChatRoomEntity;
import com.soapboxrace.jaxb.http.ArrayOfChatRoom;
import com.soapboxrace.jaxb.http.ChatRoom;

@Stateless
public class SessionBO {

	@EJB
	private ChatRoomDAO chatRoomDao;
	
	public ArrayOfChatRoom getAllChatRoom() {
		List<ChatRoomEntity> chatRoomList = chatRoomDao.findAll();
		ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();
		for(ChatRoomEntity entity : chatRoomList) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.setChannelCount(entity.getAmount());
			chatRoom.setLongName(entity.getLongName());
			chatRoom.setShortName(entity.getShortName());
			arrayOfChatRoom.getChatRoom().add(chatRoom);
		}
		return arrayOfChatRoom;
	}

}
