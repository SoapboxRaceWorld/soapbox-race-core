/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;


import com.soapboxrace.core.api.util.GeoIp2;
import com.soapboxrace.core.dao.ChatRoomDAO;
import com.soapboxrace.core.jpa.ChatRoomEntity;
import com.soapboxrace.jaxb.http.ArrayOfChatRoom;
import com.soapboxrace.jaxb.http.ChatRoom;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SessionBO {

    @EJB
    private ChatRoomDAO chatRoomDao;

    @EJB
    private ParameterBO parameterBO;

    public ArrayOfChatRoom getAllChatRoom() {
        List<ChatRoomEntity> chatRoomList = chatRoomDao.findAll();
        ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();
        for (ChatRoomEntity entity : chatRoomList) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setChannelCount(entity.getAmount());
            chatRoom.setLongName(entity.getLongName());
            chatRoom.setShortName(entity.getShortName());
            arrayOfChatRoom.getChatRoom().add(chatRoom);
        }

        return arrayOfChatRoom;
    }

    public ArrayOfChatRoom getChatRoomBasedOnCountry(String ip) {
        ArrayOfChatRoom arrayOfChatRoom = new ArrayOfChatRoom();

        GeoIp2 geoIp2 = GeoIp2.getInstance(parameterBO.getStrParam("GEOIP2_DB_FILE_PATH"));

        //Let's add first the country specific:
        ChatRoom chatRoomCountry = new ChatRoom();
        chatRoomCountry.setChannelCount(parameterBO.getIntParam("SBRWR_GEO_MAX_CHANNELS", 2));
        chatRoomCountry.setLongName(geoIp2.getCountryIso(ip));
        chatRoomCountry.setShortName(geoIp2.getCountryIso(ip));
        arrayOfChatRoom.getChatRoom().add(chatRoomCountry);

        //Let's add WW now
        ChatRoom chatRoomWW = new ChatRoom();
        chatRoomWW.setChannelCount(parameterBO.getIntParam("SBRWR_GEO_MAX_CHANNELS", 2));
        chatRoomWW.setLongName(parameterBO.getStrParam("SBRWR_GEO_DEFAULT_LONGNAME", "WORLDWIDE"));
        chatRoomWW.setShortName(parameterBO.getStrParam("SBRWR_GEO_DEFAULT_LONGNAME", "WW"));
        arrayOfChatRoom.getChatRoom().add(chatRoomWW);

        return arrayOfChatRoom;
    }

}
