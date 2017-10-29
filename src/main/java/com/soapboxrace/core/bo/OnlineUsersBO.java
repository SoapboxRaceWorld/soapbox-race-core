package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.OnlineUsersDAO;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class OnlineUsersBO
{
    @EJB
    private OnlineUsersDAO onlineUsersDAO;

    @Schedule(minute = "*", hour = "*", persistent = false)
    public void insertNumberOfUsesOnlineNow() {
    }

    public Integer getNumberOfUsersOnlineNow() {
        return 0;
    }
}
