package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.xmpp.openfire.OnlineUsersMNG;
import com.soapboxrace.xmpp.openfire.OpenFireRestApiCli;
import com.soapboxrace.xmpp.openfire.shard.ShardedOnlineUsersMNG;
import com.soapboxrace.xmpp.openfire.standard.StandardOnlineUsersMNG;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class OnlineUsersBO
{
    @EJB
    private OnlineUsersDAO onlineUsersDAO;

    private OpenFireRestApiCli openFireRestApiCli = new OpenFireRestApiCli();

    private OnlineUsersMNG onlineUsersMNG;

    {
        ParameterBO parameterBO = new ParameterBO();

        if (parameterBO.isShardingEnabled())
        {
            onlineUsersMNG = new ShardedOnlineUsersMNG(openFireRestApiCli, onlineUsersDAO);
        } else
        {
            onlineUsersMNG = new StandardOnlineUsersMNG(openFireRestApiCli, onlineUsersDAO);
        }
    }

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void insertNumberOfUsesOnlineNow()
    {
        onlineUsersMNG.insert();
    }

    public Integer getNumberOfUsersOnlineNow() {
        return onlineUsersMNG.getNumberOfUsersOnlineNow();
    }
}
