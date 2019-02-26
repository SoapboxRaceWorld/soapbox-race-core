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
		return "<ClientConfigTrans/>";
	}
}
