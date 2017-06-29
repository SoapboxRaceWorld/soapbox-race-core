package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TreasureHuntDAO;
import com.soapboxrace.core.jpa.TreasureHuntEntity;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;

@Stateless
public class EventsBO {
	
	@EJB
	private PersonaDAO personaDao;
	
	@EJB
	private TreasureHuntDAO treasureHuntDao;

	public TreasureHuntEventSession getTreasureHuntEventSession(Long activePersonaId) {
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
		if(treasureHuntEntity == null)
			return new TreasureHuntEventSession();
		
		TreasureHuntEventSession treasureHuntEventSession = new TreasureHuntEventSession();
		treasureHuntEventSession.setCoinsCollected(treasureHuntEntity.getCoinsCollected());
		treasureHuntEventSession.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
		treasureHuntEventSession.setNumCoins(treasureHuntEntity.getNumCoins());
		treasureHuntEventSession.setSeed(treasureHuntEntity.getSeed());
		treasureHuntEventSession.setStreak(treasureHuntEntity.getStreak());
		return treasureHuntEventSession;
	}
}
