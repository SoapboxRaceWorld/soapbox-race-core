package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.ParameterDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.ParameterEntity;
import com.soapboxrace.core.jpa.TokenSessionEntity;

@Stateless
public class ParameterBO {

	@EJB
	private ParameterDAO parameterDao;

	@EJB
	private TokenSessionDAO tokenDAO;

	private String getParameter(String name) {
		try {
			ParameterEntity findById = parameterDao.findById(name);
			return findById.getValue();
		} catch (Exception e) {
		}
		return null;
	}

	public boolean isShardingEnabled() {
		String property = System.getProperty("sharding.enabled");

		return Boolean.parseBoolean(property);
	}

	public boolean isShardingMaster() {
		String property = System.getProperty("sharding.master");

		return Boolean.parseBoolean(property);
	}

	public String getShardId() {
		String property = System.getProperty("sharding.id");

		if (property == null)
			return null;
		if (property.trim().isEmpty())
			return null;
		if (!isShardingEnabled() || isShardingMaster())
			return null;
		return property.trim();
	}

	public int getCarLimit(String securityToken) {
		TokenSessionEntity tokenSession = tokenDAO.findById(securityToken);
		if (tokenSession.isPremium()) {
			return getIntParam("MAX_CAR_SLOTS_PREMIUM");
		}
		return getIntParam("MAX_CAR_SLOTS_FREE");
	}

	public int getMaxCash(String securityToken) {
		TokenSessionEntity tokenSession = tokenDAO.findById(securityToken);
		if (tokenSession.isPremium()) {
			return getIntParam("MAX_PLAYER_CASH_PREMIUM");
		}
		return getIntParam("MAX_PLAYER_CASH_FREE");
	}
	
	public int getLobbyCountdownTime() {
		String parameter = getParameter("LOBBY_COUNTDOWN_TIME");
		
		if (parameter == null || parameter.isEmpty()) {
		    return 60000;
        }
        
        return Integer.valueOf(parameter);
	}

	public Integer getIntParam(String parameter) {
		String parameterFromDB = getParameter(parameter);
		return Integer.valueOf(parameterFromDB);
	}

	public Boolean getBoolParam(String parameter) {
		String parameterFromDB = getParameter(parameter);
		return Boolean.valueOf(parameterFromDB);
	}

	public String getStrParam(String parameter) {
		return getParameter(parameter);
	}

	public Float getFloatParam(String parameter) {
		String parameterFromDB = getParameter(parameter);
		return Float.valueOf(parameterFromDB);
	}

}