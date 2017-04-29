package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.ArrayOfNewsArticleTrans;

@Path("/NewsArticles")
public class NewsArticles {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfNewsArticleTrans newsArticles() {
		return new ArrayOfNewsArticleTrans();
	}
}
