package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.ServerInfoDAO;
import com.soapboxrace.core.jpa.ServerInfoEntity;

@Stateless
public class GetServerInformationBO {

	@EJB
	private ServerInfoDAO serverInfoDAO;

	@EJB
	private OnlineUsersBO onlineUsersBO;

	@EJB
	private ParameterBO parameterBO;

	public ServerInfoEntity getServerInformation() {
		ServerInfoEntity serverInfoEntity = serverInfoDAO.findInfo();
		serverInfoEntity.setOnlineNumber(onlineUsersBO.getNumberOfUsersOnlineNow());
		String ticketToken = parameterBO.getStrParam("TICKET_TOKEN");
		if (ticketToken != null && !ticketToken.equals("null")) {
			serverInfoEntity.setRequireTicket(true);
		}
		serverInfoEntity.setServerVersion("0.0.8");

		int maxOnlinePlayers = parameterBO.getIntParam("MAX_ONLINE_PLAYERS");
		if (maxOnlinePlayers != 0) {
//			serverInfoEntity.setMaxUsersAllowed(maxOnlinePlayers);
		}

		return serverInfoEntity;
	}

}
