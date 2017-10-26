package com.soapboxrace.xmpp.openfire.standard;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;
import com.soapboxrace.xmpp.openfire.OnlineUsersMNG;
import com.soapboxrace.xmpp.openfire.OpenFireRestApiCli;

import java.util.Date;

public class StandardOnlineUsersMNG implements OnlineUsersMNG {
    private OpenFireRestApiCli restApiCli;

    private OnlineUsersDAO onlineUsersDAO;

    public StandardOnlineUsersMNG(OpenFireRestApiCli restApiCli, OnlineUsersDAO onlineUsersDAO) {
        this.restApiCli = restApiCli;
        this.onlineUsersDAO = onlineUsersDAO;
    }

    public void insert() {
        Long timeLong = new Date().getTime() / 1000L;
        OnlineUsersEntity onlineUsersEntity = new OnlineUsersEntity();
        onlineUsersEntity.setNumberOfUsers(restApiCli.getTotalOnlineUsers());
        onlineUsersEntity.setTimeRecord(timeLong.intValue());
        onlineUsersDAO.insert(onlineUsersEntity);
    }
    
    public int getNumberOfUsersOnlineNow() {
        Date lastMinutes = getLastMinutes(1);
        OnlineUsersEntity onlineUsersEntity = onlineUsersDAO.findByTime(lastMinutes);
        return onlineUsersEntity != null ? onlineUsersEntity.getNumberOfUsers() : 0;
    }

    private Date getLastMinutes(int minutes) {
        long time = new Date().getTime();
        time = time - (minutes * 90000);

        return new Date(time);
    }
}
