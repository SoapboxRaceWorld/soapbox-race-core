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
        List<String> extraChannels = parameterBO.getStrListParam("SBRWR_GEO_EXTRACHANNELS");
        String countryIso = geoIp2.getCountryIso(ip);

        //Let's add first the country specific:
        if(!extraChannels.contains(countryIso)) {
            ChatRoom chatRoomCountry = new ChatRoom();
            chatRoomCountry.setChannelCount(parameterBO.getIntParam("SBRWR_GEO_MAX_CHANNELS", 2));
            chatRoomCountry.setLongName(geoIp2.getCountryIso(ip));
            chatRoomCountry.setShortName(geoIp2.getCountryIso(ip));
            arrayOfChatRoom.getChatRoom().add(chatRoomCountry);
        }

        //Let's add extra channels now
        for (String extraChannelsSingle : extraChannels) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setChannelCount(parameterBO.getIntParam("SBRWR_GEO_MAX_CHANNELS", 2));
            chatRoom.setLongName("TXT_CHAT_LANG_" + extraChannelsSingle);
            chatRoom.setShortName(extraChannelsSingle);
            arrayOfChatRoom.getChatRoom().add(chatRoom);
        }

        return arrayOfChatRoom;
    }

}
