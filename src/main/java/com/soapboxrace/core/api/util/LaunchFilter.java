/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;
import org.json.JSONObject;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@LauncherChecks
@Provider
@Priority(Priorities.AUTHORIZATION)
public class LaunchFilter implements ContainerRequestFilter {

    @EJB
    private ParameterBO parameterBO;

    @Context
    private HttpServletRequest sr;

    @EJB
    private UserDAO userDao;

    public static int compareVersions(String v1, String v2) {
        String[] components1 = v1.split("\\.");
        String[] components2 = v2.split("\\.");
        int length = Math.min(components1.length, components2.length);
        for (int i = 0; i < length; i++) {
            int result = Integer.valueOf(components1[i]).compareTo(Integer.valueOf(components2[i]));
            if (result != 0) {
                return result;
            }
        }
        return Integer.compare(components1.length, components2.length);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String hwid = requestContext.getHeaderString("X-HWID");
        String email = sr.getParameter("email");

        UserEntity userEntity = userDao.findByEmail(email);
        if (userEntity != null) {
            userEntity.setHwid(hwid);
            userDao.update(userEntity);
        }
    }
}
