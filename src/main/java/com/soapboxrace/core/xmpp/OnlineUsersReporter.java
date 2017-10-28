package com.soapboxrace.core.xmpp;

import com.soapboxrace.core.dao.OnlineUsersDAO;

public interface OnlineUsersReporter
{
    void insertNumberOfUsesOnlineNow(OnlineUsersDAO onlineUsersDAO);

    int getNumberOfUsersOnlineNow(OnlineUsersDAO onlineUsersDAO);
}
