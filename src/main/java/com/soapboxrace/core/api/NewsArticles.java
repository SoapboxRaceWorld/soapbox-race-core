package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.NewsArticleBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.ArrayOfNewsArticleTrans;

@Path("/NewsArticles")
public class NewsArticles {
	@EJB
	private TokenSessionBO tokenSessionBO;
	
	@EJB
	private NewsArticleBO newsArticleBO;
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfNewsArticleTrans newsArticles(@HeaderParam("userID") Long userID, @HeaderParam("securityToken") String securityToken) {
        return newsArticleBO.getNewsArticles(tokenSessionBO.getActivePersonaId(securityToken));
	}
}
