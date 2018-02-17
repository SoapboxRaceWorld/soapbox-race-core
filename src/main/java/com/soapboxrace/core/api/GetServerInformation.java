package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.GetServerInformationBO;
import com.soapboxrace.core.jpa.ServerInfoEntity;

@Path("/GetServerInformation")
public class GetServerInformation {
	
	@EJB
	private GetServerInformationBO bo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ServerInfoEntity getServerInformation() {
		return bo.getServerInformation();
	}
}
