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

		return isShardingEnabled() && property != null && Boolean.parseBoolean(property.trim());
	}

	public String getShardId() {
		String property = System.getProperty("sharding.id");

		return isShardingEnabled() ? (property != null && !property.trim().isEmpty() ? property.trim() : "sbrw") : "sbrw";
	}

	public String getCncToken() {
		String property = System.getProperty("sharding.cncToken");

		return isShardingEnabled() ? (property != null && !property.trim().isEmpty() ? property.trim() : null) : null;
	}

	public String getShardToken() {
		String property = System.getProperty("sharding.clientToken");

		return isShardingEnabled() ? (property != null && !property.trim().isEmpty() ? property.trim() : null) : null;
	}

	public int getMaxCarSlotsFree() {
		String parameter = getParameter("MAX_CAR_SLOTS_FREE");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 6;
	}

	public int getMaxCarSlotsPremium() {
		String parameter = getParameter("MAX_CAR_SLOTS_PREMIUM");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 30;
	}

	public int getStartingCash() {
		String parameter = getParameter("STARTING_CASH_AMOUNT");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 350_000;
	}

	/**
	 * minimum pursuit time in MS
	 * @return
	 */
	public int getMinPursuitTime() {
		String parameter = getParameter("PURSUIT_MINIMUM_TIME");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 18_500;
	}

	/**
	 * minimum sprint/circuit time in MS
	 * @return
	 */
	public int getMinRouteTime() {
		String parameter = getParameter("ROUTE_MINIMUM_TIME");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 50_000;
	}

	/**
	 * minimum drag time in MS
	 * @return
	 */
	public int getMinDragTime() {
		String parameter = getParameter("DRAG_MINIMUM_TIME");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 10_000;
	}

	/**
	 * minimum TE time in MS
	 * @return
	 */
	public int getMinTETime() {
		String parameter = getParameter("TE_MINIMUM_TIME");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 60_000;
	}

	public int getMaxFreePlayerCash() {
		String parameter = getParameter("MAX_PLAYER_CASH_FREE");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 5_000_000;
	}

	public int getMaxPremiumPlayerCash() {
		String parameter = getParameter("MAX_PLAYER_CASH_PREMIUM");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 8_500_000;
	}
	
	public boolean getPremiumCarChangerProtection() {
		String parameter = getParameter("PREMIUM_CARCHANGER_PROTECTION");
		if (parameter != null) {
			return Boolean.valueOf(parameter);
		}
		return false;
	}

	public int getCarLimit(String securityToken) {
		TokenSessionEntity tokenSession = tokenDAO.findById(securityToken);
		if (tokenSession.isPremium()) {
			return getMaxCarSlotsPremium();
		}
		return getMaxCarSlotsFree();
	}

	public int getMaxCash(String securityToken) {
		TokenSessionEntity tokenSession = tokenDAO.findById(securityToken);
		if (tokenSession.isPremium()) {
			return getMaxPremiumPlayerCash();
		}
		return getMaxFreePlayerCash();
	}
}
