package com.soapboxrace.core.bo.util;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppChat;
import org.igniterealtime.restclient.entity.MUCRoomEntity;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SendToAllXMPP {
	@EJB
    private OpenFireRestApiCli restApiCli;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

	public void _sendMessageToChannel(String message, String channelname, Boolean isRaw) {
		List<MUCRoomEntity> channels = restApiCli.getAllRooms()
			.stream()
			.collect(Collectors.toList());

		if(!isRaw) {
			message = XmppChat.createSystemMessage(message);
		}

        for (MUCRoomEntity channel : channels) {
        	if(channel.getRoomName().equals(channelname)) {
	            List<Long> members = restApiCli.getAllOccupantsInRoom(channel.getRoomName());

	            for (Long member : members) {
	                openFireSoapBoxCli.send(message, member);
	            }
	        }
        }
	}

	public void _sendMessage(String message, Boolean isRaw) {
		List<MUCRoomEntity> channels = restApiCli.getAllRooms()
			.stream()
			.collect(Collectors.toList());

		if(!isRaw) {
			message = XmppChat.createSystemMessage(message);
		}

        for (MUCRoomEntity channel : channels) {
            List<Long> members = restApiCli.getAllOccupantsInRoom(channel.getRoomName());

            for (Long member : members) {
                openFireSoapBoxCli.send(message, member);
            }
   	    }
   	}

	public void sendMessage(String message) {
		_sendMessage(message, false);
   	}

	public void sendRawMessage(String message) {
		_sendMessage(message, true);
   	}

	public void sendMessageToChannel(String message, String channelname) {
		_sendMessageToChannel(message, channelname, false);
   	}

	public void sendRawMessageToChannel(String message, String channelname) {
		_sendMessageToChannel(message, channelname, true);
   	}
} 