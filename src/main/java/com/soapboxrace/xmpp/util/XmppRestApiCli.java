package com.soapboxrace.xmpp.util;

import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.igniterealtime.restclient.entity.UserEntity;

@Singleton
public class XmppRestApiCli {

	private String openFireToken;
	private String openFireAddress;

	public XmppRestApiCli() {
		openFireToken = System.getProperty("openFireToken");
		openFireAddress = System.getProperty("openFireAddress");
	}

	private Builder getBuilder(String path) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(openFireAddress).path(path);
		Builder request = target.request(MediaType.APPLICATION_XML);
		request.header("Authorization", openFireToken);
		return request;
	}

	public void createUpdatePersona(Long personaId, String password) {
		String user = "sbrw." + personaId.toString();
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

}
