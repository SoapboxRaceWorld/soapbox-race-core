package com.soapboxrace.core.bo;

import com.google.inject.Injector;
import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.inject.InjectorFactory;
import com.soapboxrace.core.xmpp.OnlineUsersReporter;

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
        Injector injector = InjectorFactory.getInjector();
        OnlineUsersReporter reporter = injector.getInstance(OnlineUsersReporter.class);
        
        reporter.insertNumberOfUsesOnlineNow(onlineUsersDAO);
    }

    public Integer getNumberOfUsersOnlineNow() {
        Injector injector = InjectorFactory.getInjector();
        OnlineUsersReporter reporter = injector.getInstance(OnlineUsersReporter.class);

        return reporter.getNumberOfUsersOnlineNow(onlineUsersDAO);
    }
}
