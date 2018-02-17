package com.soapboxrace.core.bo;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.ReportDAO;
import com.soapboxrace.core.jpa.ReportEntity;

@Stateless
public class SocialBO {

	@EJB
	private ReportDAO reportDao;
	
	public void sendReport(Long personaId, Long abuserPersonaId, Integer petitionType, String description, Integer customCarID, Integer chatMinutes, Long hacksDetected) {
		ReportEntity reportEntity = new ReportEntity();
		reportEntity.setAbuserPersonaId(abuserPersonaId);
		reportEntity.setChatMinutes(chatMinutes);
		reportEntity.setCustomCarID(customCarID);
		reportEntity.setDescription(description);
		reportEntity.setPersonaId(personaId);
		reportEntity.setPetitionType(petitionType);
		reportEntity.setHacksDetected(hacksDetected);
		reportDao.insert(reportEntity);
	}

}
