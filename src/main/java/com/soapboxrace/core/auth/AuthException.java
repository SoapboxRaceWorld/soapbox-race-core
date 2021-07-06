/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.auth;

import com.soapboxrace.jaxb.login.LoginStatusVO;

public class AuthException extends Exception {
    private BanInfoVO banInfo;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(BanInfoVO banInfo) {
        super(banInfo.getBanMessage());
        this.banInfo = banInfo;
    }

    public BanInfoVO getBanInfo() {
        return banInfo;
    }

    public LoginStatusVO toLoginStatus() {
        LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
        if (banInfo != null) {
            LoginStatusVO.Ban ban = new LoginStatusVO.Ban();
            ban.setReason(banInfo.getReason());
            if (banInfo.getEndsAt() != null) {
                ban.setExpires(banInfo.getFormattedEndsAt());
            }
            loginStatusVO.setBan(ban);
            return loginStatusVO;
        }
        loginStatusVO.setDescription(getMessage());
        return loginStatusVO;
    }
}