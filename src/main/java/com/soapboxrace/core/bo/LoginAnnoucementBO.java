package com.soapboxrace.core.bo;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.LoginAnnoucementDAO;
import com.soapboxrace.core.jpa.LoginAnnoucementEntity;
import com.soapboxrace.jaxb.http.ArrayOfLoginAnnouncementDefinition;
import com.soapboxrace.jaxb.http.LoginAnnouncementContext;
import com.soapboxrace.jaxb.http.LoginAnnouncementDefinition;
import com.soapboxrace.jaxb.http.LoginAnnouncementType;
import com.soapboxrace.jaxb.http.LoginAnnouncementsDefinition;

@Stateless
public class LoginAnnoucementBO {

	@EJB
	private LoginAnnoucementDAO loginAnnoucementDao;
	
	public LoginAnnouncementsDefinition getLoginAnnouncements() {
		ArrayOfLoginAnnouncementDefinition arrayOfLoginAnnouncementDefinition = new ArrayOfLoginAnnouncementDefinition();
		
		List<LoginAnnoucementEntity> listOfLoginAnnoucement = loginAnnoucementDao.findAll();
		for(LoginAnnoucementEntity entity : listOfLoginAnnoucement) {
			LoginAnnouncementDefinition loginAnnouncementDefinition = new LoginAnnouncementDefinition();
			loginAnnouncementDefinition.setContext(LoginAnnouncementContext.NOT_APPLICABLE);
			loginAnnouncementDefinition.setId(entity.getId());
			loginAnnouncementDefinition.setImageChecksum(-1);
			loginAnnouncementDefinition.setImageUrl(entity.getImageUrl());
			loginAnnouncementDefinition.setType(LoginAnnouncementType.fromValue(entity.getType()));
			loginAnnouncementDefinition.setTarget(entity.getTarget());
			arrayOfLoginAnnouncementDefinition.getLoginAnnouncementDefinition().add(loginAnnouncementDefinition);
		}
		
		LoginAnnouncementsDefinition loginAnnouncementsDefinition = new LoginAnnouncementsDefinition();
		loginAnnouncementsDefinition.setAnnouncements(arrayOfLoginAnnouncementDefinition);
		loginAnnouncementsDefinition.setImagesPath("http://i.imgur.com/");
		return loginAnnouncementsDefinition;
	}
}
