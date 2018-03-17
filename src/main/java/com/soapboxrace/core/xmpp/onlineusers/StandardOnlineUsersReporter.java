package com.soapboxrace.core.xmpp.onlineusers;

import java.util.Date;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;
import com.soapboxrace.core.xmpp.OnlineUsersReporter;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;

public class StandardOnlineUsersReporter implements OnlineUsersReporter
{
    private OpenFireRestApiCli openFireRestApiCli = new OpenFireRestApiCli();
    
    @Override
    public void insertNumberOfUsesOnlineNow(OnlineUsersDAO onlineUsersDAO)
    {
        Long timeLong = new Date().getTime() / 1000L;
        OnlineUsersEntity onlineUsersEntity = new OnlineUsersEntity();
        onlineUsersEntity.setNumberOfUsers(openFireRestApiCli.getTotalOnlineUsers());
        onlineUsersEntity.setTimeRecord(timeLong.intValue());
        onlineUsersDAO.insert(onlineUsersEntity);
    }

    @Override
    public int getNumberOfUsersOnlineNow(OnlineUsersDAO onlineUsersDAO)
    {
        Date lastMinutes = getLastMinutes(1);
        OnlineUsersEntity onlineUsersEntity = onlineUsersDAO.findByTime(lastMinutes);
        return onlineUsersEntity != null ? onlineUsersEntity.getNumberOfUsers() : 0;
    }

    private Date getLastMinutes(int minutes)
    {
        long time = new Date().getTime();
        time = time - (minutes * 90000);

        return new Date(time);
    }
}
