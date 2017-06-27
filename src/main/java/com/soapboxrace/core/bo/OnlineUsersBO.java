package com.soapboxrace.core.bo;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.OnlineUsersDAO;
import com.soapboxrace.core.jpa.OnlineUsersEntity;
import com.soapboxrace.xmpp.openfire.OpenFireRestApiCli;

@Stateless
public class OnlineUsersBO {

	@EJB
	private OnlineUsersDAO onlineUsersDAO;

	private OpenFireRestApiCli openFireRestApiCli = new OpenFireRestApiCli();

	public int getNumberOfUsersOnlineNow() {
		Date lastMinutes = getLastMinutes(1);
		OnlineUsersEntity onlineUsersEntity = onlineUsersDAO.findByTime(lastMinutes);
		return onlineUsersEntity.getNumberOfUsers();
	}

	@Schedule(minute = "*", hour = "*", persistent = false)
	public void insertNumberOfUsesOnlineNow() {
		Long timeLong = new Date().getTime() / 1000L;
		OnlineUsersEntity onlineUsersEntity = new OnlineUsersEntity();
		onlineUsersEntity.setNumberOfUsers(openFireRestApiCli.getTotalOnlineUsers());
		onlineUsersEntity.setTimeRecord(timeLong.intValue());
		onlineUsersDAO.insert(onlineUsersEntity);
	}

	private Date getLastMinutes(int minutes) {
		long time = new Date().getTime();
		time = time - (minutes * 90000);

		return new Date(time);
	}
}
