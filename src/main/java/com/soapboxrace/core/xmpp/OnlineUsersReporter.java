/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import com.soapboxrace.core.dao.OnlineUsersDAO;

public interface OnlineUsersReporter {
    void insertNumberOfUsesOnlineNow(OnlineUsersDAO onlineUsersDAO);

    int getNumberOfUsersOnlineNow(OnlineUsersDAO onlineUsersDAO);
}
