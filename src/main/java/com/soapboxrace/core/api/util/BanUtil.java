/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;

import java.time.format.DateTimeFormatter;

public class BanUtil {
    public static final DateTimeFormatter banEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private BanEntity banEntity;

    public BanUtil(BanEntity banEntity) {
        this.banEntity = banEntity;
    }

    public LoginStatusVO invoke() {
        LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
        LoginStatusVO.Ban ban = new LoginStatusVO.Ban();
        ban.setReason(banEntity.getReason());
        ban.setExpires(banEntity.getEndsAt() == null ? null : banEndFormatter.format(banEntity.getEndsAt()));

        loginStatusVO.setBan(ban);
        return loginStatusVO;
    }

}
