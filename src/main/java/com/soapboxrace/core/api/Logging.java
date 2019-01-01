package com.soapboxrace.core.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.ArrayOfClientConfig;
import com.soapboxrace.jaxb.http.ClientConfig;
import com.soapboxrace.jaxb.http.ClientConfigTrans;
import com.soapboxrace.jaxb.http.ClientLog;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Path("/logging")
public class Logging {

	@GET
	@Secured
	@Path("/client")
	@Produces(MediaType.APPLICATION_XML)
	public String client() {
		return "<ClientConfigTrans><clientConfigs><ClientConfig></ClientConfig></clientConfigs></ClientConfigTrans>";
	}
	
	@POST
	@Secured
	@Path("/client/{group}")
	public String doLog(InputStream inputStream, @PathParam("group") String group) {
		String xml = new BufferedReader(new InputStreamReader(inputStream))
				.lines().collect(Collectors.joining("\n"));
		try {
			ClientLog clientLog = UnmarshalXML.unMarshal(xml, ClientLog.class);
			
			System.out.println("CLIENT LOG [" + group + "]: " + clientLog.getMessage());
		} catch (Exception e) {
			System.out.println("Could not parse client log: " + xml);
		}
		
		return "";
	}
}
