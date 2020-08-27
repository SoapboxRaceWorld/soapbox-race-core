/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.NewsArticleBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.jaxb.http.ArrayOfNewsArticleTrans;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/NewsArticles")
public class NewsArticles {

    @EJB
    private NewsArticleBO newsArticleBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfNewsArticleTrans newsArticles() {
        return newsArticleBO.getNewsArticles(requestSessionInfo.getActivePersonaId());
    }
}
