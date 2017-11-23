package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.LoginAnnouncementDAO;
import com.soapboxrace.core.jpa.LoginAnnouncementEntity;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LoginAnnouncementBO
{
    @EJB
    private LoginAnnouncementDAO loginAnnoucementDao;

    public LoginAnnouncementsDefinition getLoginAnnouncements()
    {
        ArrayOfLoginAnnouncementDefinition arrayOfLoginAnnouncementDefinition = new ArrayOfLoginAnnouncementDefinition();

        List<LoginAnnouncementEntity> listOfLoginAnnoucement = loginAnnoucementDao.findAll();
        for (LoginAnnouncementEntity entity : listOfLoginAnnoucement)
        {
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