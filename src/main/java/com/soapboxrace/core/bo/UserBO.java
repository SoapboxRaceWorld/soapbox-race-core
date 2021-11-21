/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.google.common.collect.Iterables;
import com.soapboxrace.core.auth.AuthException;
import com.soapboxrace.core.auth.verifiers.PasswordVerifier;
import com.soapboxrace.core.dao.InviteTicketDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.InviteTicketEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.jaxb.http.ArrayOfProfileData;
import com.soapboxrace.jaxb.http.ProfileData;
import com.soapboxrace.jaxb.http.User;
import com.soapboxrace.jaxb.http.UserInfo;
import org.apache.commons.validator.routines.EmailValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Stateless
public class UserBO {

    @EJB
    private UserDAO userDao;

    @EJB
    private InviteTicketDAO inviteTicketDAO;

    @EJB
    private OpenFireRestApiCli xmppRestApiCli;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private AchievementBO achievementBO;

    public void createXmppUser(UserInfo userInfo) {
        String securityToken = userInfo.getUser().getSecurityToken();
        String xmppPasswd = securityToken.substring(0, 16);
        List<ProfileData> profileData = userInfo.getPersonas().getProfileData();
        for (ProfileData persona : profileData) {
            createXmppUser(persona.getPersonaId(), xmppPasswd);
        }
    }

    public void createXmppUser(Long personaId, String xmppPasswd) {
        xmppRestApiCli.createUpdatePersona(personaId, xmppPasswd);
    }

    public UserEntity createUser(String email, String password, String ip) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setIpAddress(ip);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setLastLogin(LocalDateTime.now());
        userDao.insert(userEntity);
        return userEntity;
    }

    public void createUserWithTicket(String email, PasswordVerifier password, String ip, String ticket) throws AuthException {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(email)) {
            throw new AuthException("Invalid email address!");
        }

        InviteTicketEntity inviteTicketEntity = null;
        String ticketToken = parameterBO.getStrParam("TICKET_TOKEN");

        if (ticketToken != null && !ticketToken.equals("null")) {
            inviteTicketEntity = inviteTicketDAO.findByTicket(ticket);
            
            if (inviteTicketEntity == null || inviteTicketEntity.getTicket() == null || inviteTicketEntity.getTicket().isEmpty()) {
                throw new AuthException("Invalid ticket!");
            }

            if (inviteTicketEntity.getUser() != null) {
                throw new AuthException("Ticket already used!");
            }
        }

        UserEntity findByEmail = userDao.findByEmail(email);
        if (findByEmail != null) {
            throw new AuthException("You're already registered!");
        }

        int countIpAddress = userDao.countUsersByIpAddress(ip);
        if (countIpAddress >= parameterBO.getIntParam("MAX_IP_REGISTRATIONS", 5)) {
            throw new AuthException("Registration limit reached for this IP!");
        }

        UserEntity userEntity = createUser(email, password.createHash(), ip);
        if (inviteTicketEntity != null) {
            inviteTicketEntity.setUser(userEntity);
            inviteTicketDAO.insert(inviteTicketEntity);
        }
    }

    public void secureLoginPersona(Long userId, Long personaId) {
        PersonaEntity personaEntity = personaDAO.find(personaId);

        if (personaEntity != null) {
            UserEntity user = personaEntity.getUser();
            if (user.getId().equals(userId)) {
                if (personaEntity.getFirstLogin() == null) {
                    personaEntity.setFirstLogin(LocalDateTime.now());
                }

                personaEntity.setLastLogin(LocalDateTime.now());
                personaDAO.update(personaEntity);
                AchievementTransaction transaction = achievementBO.createTransaction(personaId);
                transaction.add("LOGIN", Map.of("persona", personaEntity));
                achievementBO.commitTransaction(personaEntity, transaction);
                int index = Iterables.indexOf(user.getPersonas(), p -> p != null && p.getPersonaId().equals(personaId));
                user.setSelectedPersonaIndex(index);
                userDao.update(user);
            }
        }
    }

    public UserInfo getUserInfo(UserEntity userEntity) {
        UserInfo userInfo = new UserInfo();
        ArrayOfProfileData arrayOfProfileData = new ArrayOfProfileData();
        List<PersonaEntity> listOfProfile = userEntity.getPersonas();
        for (PersonaEntity personaEntity : listOfProfile) {
            // switch to apache beanutils copy
            ProfileData profileData = new ProfileData();
            profileData.setName(personaEntity.getName());
            profileData.setCash(personaEntity.getCash());
            profileData.setIconIndex(personaEntity.getIconIndex());
            profileData.setPersonaId(personaEntity.getPersonaId());
            profileData.setLevel(personaEntity.getLevel());
            profileData.setBoost(personaEntity.getBoost());
            arrayOfProfileData.getProfileData().add(profileData);
        }
        userInfo.setPersonas(arrayOfProfileData);
        User user = new User();
        user.setUserId(userEntity.getId());
        userInfo.setUser(user);
        userInfo.setDefaultPersonaIdx(userEntity.getSelectedPersonaIndex());
        return userInfo;
    }

}
