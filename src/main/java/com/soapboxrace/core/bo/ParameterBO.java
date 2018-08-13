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

		return property != null && Boolean.parseBoolean(property);
	}

	public boolean isShardingMaster() {
		String property = System.getProperty("sharding.master");

		return property != null && Boolean.parseBoolean(property);
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

	public String getMultiXmppHost() {
		String property = System.getProperty("sharding.multixmpp.host");

		return isShardingEnabled() ? (property != null && !property.trim().isEmpty() ? property.trim() : null) : null;
	}

	public String getMultiXmppToken() {
		String property = System.getProperty("sharding.multixmpp.token");

		return isShardingEnabled() ? (property != null && !property.trim().isEmpty() ? property.trim() : null) : null;
	}

	public int getMultiXmppPort() {
		String property = System.getProperty("sharding.multixmpp.port");

		if (!isShardingEnabled()) {
			return -1;
		}
		return property != null && !property.trim().isEmpty() ? Integer.parseInt(property) : -1;
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

	public Integer getIntParam(String parameter) {
		String parameterFromDB = getParameter(parameter);
		return Integer.valueOf(parameterFromDB);
	}

	public Integer getIntParam(String parameter, Integer defaultValue) {
		String parameterFromDB = getParameter(parameter);
		return parameterFromDB == null || parameterFromDB.isEmpty() ? defaultValue : Integer.valueOf(parameterFromDB);
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