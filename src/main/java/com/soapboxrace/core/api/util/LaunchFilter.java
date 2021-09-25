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

        String json_whitelisted_launchers = parameterBO.getStrParam("WHITELISTED_LAUNCHERS_ONLY_JSON", null);

        if (json_whitelisted_launchers != null) {
            String get_useragent = "";
            String get_launcher = "";
            String[] ua_split = null;
            boolean lock_access = false;
            boolean lock_unsigned = false;
            boolean lock_insider = false;

            JSONObject obj_json_whitelisted_launchers = new JSONObject(json_whitelisted_launchers);

            if (requestContext.getHeaderString("X-UserAgent") != null) {
                get_useragent = requestContext.getHeaderString("X-UserAgent");
                ua_split = get_useragent.split(" ");
                if (get_useragent.startsWith("GameLauncherReborn")) {
                    get_launcher = "sbrw";
                } else if (get_useragent.startsWith("LegacyLauncher")) {
                    get_launcher = "legacy";
                }
            } else if (requestContext.getHeaderString("user-agent") != null) {
                get_useragent = requestContext.getHeaderString("user-agent");
                get_launcher = "electron";
                ua_split = get_useragent.split("/");
            } else {
                get_launcher = "JLAUNCHER";
            }

            if (!json_whitelisted_launchers.contains(get_launcher) || obj_json_whitelisted_launchers.getString(get_launcher).equals("0")) {
                lock_access = true;
            } else if (ua_split != null && (compareVersions(ua_split[1], obj_json_whitelisted_launchers.getString(get_launcher)) == -1)) {
                lock_access = true;
            }

            if (Boolean.TRUE.equals(parameterBO.getBoolParam("ENABLE_SIGNED_LAUNCHERS_ONLY")) && get_launcher.equals("sbrw")) {
                // Signed certificates work only on sbrw v2.1.8.0 and later
                String signedLaunchersCert = parameterBO.getStrParam("SIGNED_LAUNCHER_CERTIFICATE", "");    
                String signedLaunchersHash = parameterBO.getStrParam("SIGNED_LAUNCHER_HASH", "");
                String signedLaunchersHwid = parameterBO.getStrParam("SIGNED_LAUNCHER_HWID_WHITELIST", "");
                String userLauncherCert = "";
                String userLauncherHash = requestContext.getHeaderString("X-GameLauncherHash");
                if (signedLaunchersHwid.contains(hwid)) {
                    lock_access = false;
                } else if (!lock_access) {
                    ua_split = get_useragent.split(" ");
                    String[] uaVerSplit = ua_split[1].split("\\.");
                    if (requestContext.getHeaderString("X-GameLauncherCertificate") != null) {
                        if (Boolean.TRUE.equals(signedLaunchersHash.contains(ua_split[1])) && !Boolean.TRUE.equals(signedLaunchersCert.contains(userLauncherCert))) {
                            lock_access = true;
                            lock_unsigned = true;
                        } else if (!Boolean.TRUE.equals(signedLaunchersHash.contains(userLauncherHash))) {
                            lock_access = true;
                            if (Integer.valueOf(uaVerSplit[3]) % 2 == 1) {
                                lock_insider = true;
                            } else {
                                lock_unsigned = true;
                            } 
                        }
                    } else {
                        lock_access = true;
                    }
                }
            }

            if (lock_access) {
                LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
                String sbrwLink = "SBRW Launcher: https://git.io/Download_NFSW\n";
                String electronLink = "Electron Launcher: https://launcher.sparkserver.eu/";
                if (lock_unsigned) {
                    loginStatusVO.setDescription("You're using an UNSIGNED build of this launcher\n" +
                        "To play on this server please update to the latest OFFICIAL build:\n\n" + sbrwLink);
                } else if (lock_insider) {
                    loginStatusVO.setDescription("You're using a Dev/Insider version of this launcher\n" +
                    "To play on this server you need to use a Release build:\n\n" + sbrwLink);
                } else {
                    loginStatusVO.setDescription("You're using the wrong launcher or version\n" +
                    "To play on this server please update to the latest one:\n\n" + sbrwLink + electronLink);
                }
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(loginStatusVO).build());
            }
        }
    }
}
