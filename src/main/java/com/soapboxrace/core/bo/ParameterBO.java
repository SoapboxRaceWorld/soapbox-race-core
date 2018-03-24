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
		String property = System.getProperty("sharding.master");

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

		if (!isShardingEnabled())
			return -1;
		return property != null && !property.trim().isEmpty() ? Integer.parseInt(property) : -1;
		// return isShardingEnabled() ? (property != null && !property.trim().isEmpty()
		// ? property.trim() : null) : null;
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

	public int getStartingInventoryPerfSlots() {
		String parameter = getParameter("STARTING_INVENTORY_PERF_SLOTS");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 100;
	}

	public int getStartingInventorySkillSlots() {
		String parameter = getParameter("STARTING_INVENTORY_SKILL_SLOTS");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 150;
	}

	public int getStartingInventoryVisualSlots() {
		String parameter = getParameter("STARTING_INVENTORY_VISUAL_SLOTS");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 200;
	}

	public float getCashRewardMultiplier() {
		String parameter = getParameter("CASH_REWARD_MULTIPLIER");
		if (parameter != null) {
			return Float.valueOf(parameter);
		}
		return 1.0f;
	}

	public float getRepRewardMultiplier() {
		String parameter = getParameter("REP_REWARD_MULTIPLIER");
		if (parameter != null) {
			return Float.valueOf(parameter);
		}
		return 1.0f;
	}

	/**
	 * minimum pursuit time in MS
	 * 
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
	 * 
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
	 * 
	 * @return
	 */
	public int getMinDragTime() {
		String parameter = getParameter("DRAG_MINIMUM_TIME");
		if (parameter != null) {
			return Integer.valueOf(parameter);
		}
		return 10_000;
	}

	public boolean useForwardedFor() {
		return Boolean.valueOf(getParameter("USE_FORWARDED_FOR"));
	}

	public boolean googleLoadBalancing() {
		return Boolean.valueOf(getParameter("GOOGLE_LB_ENABLED"));
	}

	/**
	 * minimum TE time in MS
	 * 
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
