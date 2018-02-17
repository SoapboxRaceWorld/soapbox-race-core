package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.soapboxrace.core.dao.LobbyDAO;
import com.soapboxrace.core.jpa.LobbyEntity;
import com.soapboxrace.core.jpa.LobbyEntrantEntity;

@Startup
@Singleton
public class LobbyKeepAliveBO {

	@EJB
	private LobbyDAO lobbyDao;

	@EJB
	private LobbyBO lobbyBO;

	@Schedule(second = "*/20", minute = "*", hour = "*", persistent = false)
	public void run() {
		List<LobbyEntity> findAllOpen = lobbyDao.findAllRunning();
		if (findAllOpen != null) {
			for (LobbyEntity lobbyEntity : findAllOpen) {
				List<LobbyEntrantEntity> entrants = lobbyEntity.getEntrants();
				if (entrants != null) {
					for (LobbyEntrantEntity lobbyEntrantEntity : entrants) {
						lobbyBO.sendJoinMsg(lobbyEntrantEntity.getPersona().getPersonaId(), entrants);
					}
				}
			}
		}
	}

}
