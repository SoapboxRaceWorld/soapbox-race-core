/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.RecoveryPasswordBO;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/RecoveryPassword")
public class RecoveryPassword {

    @EJB
    private RecoveryPasswordBO bo;

    @POST
    @Path("/resetPassword")
    @Produces(MediaType.TEXT_HTML)
    public String resetPassord(@FormParam("password") String password, @FormParam("passwordconf") String passwordconf
            , @FormParam("randomKey") String randomKey) {
        if (password == null || passwordconf == null || randomKey == null || password.isEmpty() || passwordconf.isEmpty() || randomKey.isEmpty()) {
            return "ERROR: empty values!";
        }
        if (!password.equals(passwordconf)) {
            return "ERROR: Passwords not match!";
        }
        return bo.resetPassword(password, randomKey);
    }

    @POST
    @Path("/forgotPassword")
    @Produces(MediaType.TEXT_HTML)
    public String forgotPassword(@FormParam("email") String email) {
        return bo.forgotPassword(email);
    }

}
