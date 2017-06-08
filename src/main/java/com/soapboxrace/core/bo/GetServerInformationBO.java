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

	public ServerInfoEntity getServerInformation() {
		ServerInfoEntity serverInfoEntity = serverInfoDAO.findInfo();
		serverInfoEntity.setOnlineNumber(onlineUsersBO.getNumberOfUsersOnlineNow());
		return serverInfoEntity;
	}

}
