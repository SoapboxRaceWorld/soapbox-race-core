package com.soapboxrace.xmpp.openfire;

public interface OnlineUsersMNG {
    void insert();

    int getNumberOfUsersOnlineNow();
}
