package com.soapboxrace.core.bo;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.UserDAO;

@Stateless
public class SocialBO {

	@EJB
	private UserDAO userDao;
	
	public String getReportType(Integer petitionType) {
		return "";
	}
	
	public void sendReport(Long personaId, Long abuserPersonaId, Integer petitionType, String description, Integer customCarID, Integer chatMinutes) {
		
	}

}
