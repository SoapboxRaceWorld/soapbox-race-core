/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.LoginAnnouncementDAO;
import com.soapboxrace.core.jpa.LoginAnnouncementEntity;
import com.soapboxrace.jaxb.http.*;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
@Lock(LockType.READ)
public class LoginAnnouncementBO {

    @EJB
    private LoginAnnouncementDAO loginAnnouncementDao;

    @EJB
    private ParameterBO parameterBO;

    @Inject
    private Logger logger;

    private final ConcurrentMap<String, LoginAnnouncementsDefinition> announcementsCache = new ConcurrentHashMap<>();

    public void resetCachedAnnouncements() {
        announcementsCache.clear();
        logger.info("Cleared announcement cache");
    }

    public LoginAnnouncementsDefinition getLoginAnnouncements(String language) {
        return announcementsCache.computeIfAbsent(language, key -> {
            LoginAnnouncementsDefinition loginAnnouncementsDefinition = new LoginAnnouncementsDefinition();
            ArrayOfLoginAnnouncementDefinition arrayOfLoginAnnouncementDefinition =
                    new ArrayOfLoginAnnouncementDefinition();

            List<LoginAnnouncementEntity> listOfLoginAnnoucement = loginAnnouncementDao.findAllByLanguage(key);
            for (LoginAnnouncementEntity entity : listOfLoginAnnoucement) {
                LoginAnnouncementDefinition loginAnnouncementDefinition = new LoginAnnouncementDefinition();
                loginAnnouncementDefinition.setContext(LoginAnnouncementContext.fromValue(entity.getContext()));
                loginAnnouncementDefinition.setId(entity.getId());
                loginAnnouncementDefinition.setImageChecksum(-1);
                loginAnnouncementDefinition.setImageUrl(entity.getImageUrl());
                loginAnnouncementDefinition.setType(LoginAnnouncementType.fromValue(entity.getType()));
                loginAnnouncementDefinition.setTarget(entity.getTarget());
                arrayOfLoginAnnouncementDefinition.getLoginAnnouncementDefinition().add(loginAnnouncementDefinition);
            }

            loginAnnouncementsDefinition.setAnnouncements(arrayOfLoginAnnouncementDefinition);
            loginAnnouncementsDefinition.setImagesPath(parameterBO.getStrParam("ANNOUNCEMENT_DOMAIN"));
            return loginAnnouncementsDefinition;
        });
    }
}
