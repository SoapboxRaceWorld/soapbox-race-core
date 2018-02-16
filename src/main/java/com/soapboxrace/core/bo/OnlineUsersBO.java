package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.OnlineUsersDAO;

@Stateless
public class OnlineUsersBO {
	@EJB
	private OnlineUsersDAO onlineUsersDAO;

	@Schedule(minute = "*", hour = "*", persistent = false)
	public void insertNumberOfUsesOnlineNow() {
		// Injector injector = InjectorFactory.getInjector();
		// OnlineUsersReporter reporter =
		// injector.getInstance(OnlineUsersReporter.class);
		//
		// reporter.insertNumberOfUsesOnlineNow(onlineUsersDAO);
	}

	public Integer getNumberOfUsersOnlineNow() {
		// Injector injector = InjectorFactory.getInjector();
		// OnlineUsersReporter reporter =
		// injector.getInstance(OnlineUsersReporter.class);
		// return reporter.getNumberOfUsersOnlineNow(onlineUsersDAO);
		return 0;
	}
}
