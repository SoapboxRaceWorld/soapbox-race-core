package com.soapboxrace.xmpp.openfire;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.igniterealtime.restclient.entity.UserEntity;
import org.igniterealtime.restclient.entity.MUCRoomEntities;
import org.igniterealtime.restclient.entity.MUCRoomEntity;
import org.igniterealtime.restclient.entity.ParticipantEntities;
import org.igniterealtime.restclient.entity.ParticipantEntity;

import com.soapboxrace.core.api.util.Config;

@Singleton
public class OpenFireRestApiCli {

	private String openFireToken;
	private String openFireAddress;

	public OpenFireRestApiCli() {
		openFireToken = Config.getOpenFireToken();
		openFireAddress = Config.getOpenFireAddress();
		if (openFireToken != null && openFireAddress != null) {
			createUpdatePersona("sbrw.engine.engine", "1234567890123456");
		}
	}

	private Builder getBuilder(String path) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(openFireAddress).path(path);
		Builder request = target.request(MediaType.APPLICATION_XML);
		request.header("Authorization", openFireToken);
		return request;
	}

	public void createUpdatePersona(String user, String password) {
		Builder builder = getBuilder("users/" + user);
		Response response = builder.get();
		if (response.getStatus() == 200) {
			response.close();
			UserEntity userEntity = builder.get(UserEntity.class);
			userEntity.setPassword(password);
			builder = getBuilder("users/" + user);
			builder.put(Entity.entity(userEntity, MediaType.APPLICATION_XML));
		} else {
			response.close();
			builder = getBuilder("users");
			UserEntity userEntity = new UserEntity(user, null, null, password);
			builder.post(Entity.entity(userEntity, MediaType.APPLICATION_XML));
		}
		response.close();
	}

	public void createUpdatePersona(Long personaId, String password) {
		String user = "sbrw." + personaId.toString();
		createUpdatePersona(user, password);
	}

	public int getTotalOnlineUsers() {
		Builder builder = getBuilder("system/statistics/sessions");
		SessionsCount sessionsCount = builder.get(SessionsCount.class);
		int clusterSessions = sessionsCount.getClusterSessions();
		if (clusterSessions > 1) {
			return clusterSessions - 1;
		}
		return 0;
	}
	
	public List<Long> getAllPersonaByGroup(Long personaId) {
		Builder builder = getBuilder("chatrooms");
		MUCRoomEntities roomEntities = builder.get(MUCRoomEntities.class);
		List<MUCRoomEntity> listRoomEntity = roomEntities.getMucRooms();
		for(MUCRoomEntity entity : listRoomEntity) {
			if(entity.getRoomName().contains("group.channel.")) {
				Long idOwner = Long.parseLong(entity.getRoomName().substring(23));
				if(idOwner == personaId) {
					return getAllOccupantInGroup(entity.getRoomName(), entity.getOwners());
				}
			}
		}
		return new ArrayList<Long>();
	}
	
	private List<Long> getAllOccupantInGroup(String roomName, List<String> owner) {
		Builder builder = getBuilder("chatrooms/" + roomName + "/participants");
		ParticipantEntities participantEntities = builder.get(ParticipantEntities.class);
		List<Long> listOfPersona = new ArrayList<Long>();
		for(String string : owner) {
			listOfPersona.add(Long.parseLong(string.substring(string.indexOf(".") + 1, string.lastIndexOf("@"))));
		}
		for(ParticipantEntity entity : participantEntities.getParticipants()) {
			listOfPersona.add(Long.parseLong(entity.getJid().substring(entity.getJid().lastIndexOf(".") + 1)));
		}
		return listOfPersona;
	}

}
