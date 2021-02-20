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

        if (parameterBO.getBoolParam("ENABLE_WHITELISTED_LAUNCHERS_ONLY")) {
            String json_whitelisted_launchers = parameterBO.getStrParam("WHITELISTED_LAUNCHERS_ONLY", null);
            String get_useragent = "";
            String get_launcher;
            String[] ua_split;
            boolean lock_access = false;

            if (json_whitelisted_launchers != null) {
                JSONObject obj_json_whitelisted_launchers = new JSONObject(json_whitelisted_launchers);


                if (requestContext.getHeaderString("X-UserAgent") != null) {
                    get_useragent = requestContext.getHeaderString("X-UserAgent");
                    get_launcher = "SBRW";
                } else if (requestContext.getHeaderString("User-Agent") != null) {
                    get_useragent = requestContext.getHeaderString("User-Agent");
                    get_launcher = "ELECTRON";
                } else {
                    get_launcher = "JLAUNCHER";
                }

                if (get_launcher.equals("SBRW")) {
                    ua_split = get_useragent.split(" ");
                    
                    if(get_useragent.startsWith("LegacyLauncher")) { 
                        //MeTonaTOR's Own Launcher - Legacy
                        if (compareVersions(ua_split[1], obj_json_whitelisted_launchers.getString("legacy")) == -1) {
                            lock_access = true;
                        }               
                    } else if(get_useragent.startsWith("GameLauncherReborn")) { 
                        //SBRW Official Launcher, since 2018 using this ID
                        if (compareVersions(ua_split[1], obj_json_whitelisted_launchers.getString("sbrw")) == -1) {
                            lock_access = true;
                        }
                    } else if(get_useragent.startsWith("WebLauncher")) { 
                        //MeTonaTOR WebBrowser Based Launcher - WIP
                        if (compareVersions(ua_split[1], obj_json_whitelisted_launchers.getString("weblauncher")) == -1) {
                            lock_access = true;
                        }
                    } else {
                        lock_access = true;
                    }
                } else if (get_launcher.equals("ELECTRON")) {
                    ua_split = get_useragent.split("/");

                    //RedBlueScreen Launcher - Electron based
                    if (compareVersions(ua_split[1], obj_json_whitelisted_launchers.getString("electron")) == -1) {
                        lock_access = true;
                    }
                } else {
                    lock_access = true;
                }

                if (parameterBO.getBoolParam("SIGNED_LAUNCHER")) {        
                    //Temporarely this works for SBRW Only, starting from 2.1.6.3
                    if (requestContext.getHeaderString("X-UserAgent") != null) {
                        String allowedLaunchersHash = parameterBO.getStrParam("SIGNED_LAUNCHER_HASH", "");
                        String allowedLaunchersHwid = parameterBO.getStrParam("SIGNED_LAUNCHER_HWID_WL", "");
                        String currentLauncherHash = requestContext.getHeaderString("X-GameLauncherHash");
        
                        if(allowedLaunchersHash.contains(currentLauncherHash) == false) lock_access = true;
        
                        if(lock_access == true) {
                            if(allowedLaunchersHwid.contains(hwid)) lock_access = false;
                        }
                    }
                }

                if (lock_access) {
                    LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
                    loginStatusVO.setDescription("You're using the wrong (or unsigned) launcher, please update to the latest one:\n\n" +
                    "    SOAPBOX  Launcher: " + parameterBO.getStrParam("CUSTOM_SOAPBOX_LAUNCHER_URL",  "https://git.io/Download_NFSW") + "\n" + 
                    "    Electron Launcher: " + parameterBO.getStrParam("CUSTOM_ELECTRON_LAUNCHER_URL", "https://launcher.sparkserver.eu/") + "\n" + 
                    "    WebBased Launcher: " + parameterBO.getStrParam("CUSTOM_WEBBASED_LAUNCHER_URL", "Not Yet Released"));

                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(loginStatusVO).build());
                }
            }
        }
    }
}