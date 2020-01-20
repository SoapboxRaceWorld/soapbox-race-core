/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.api.util.GeoIp2;
import com.soapboxrace.core.api.util.UUIDGen;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.TokenSessionEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

@Stateless
public class TokenSessionBO {
    @EJB
    private TokenSessionDAO tokenDAO;

    @EJB
    private UserDAO userDAO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private GetServerInformationBO serverInfoBO;

    @EJB
    private AuthenticationBO authenticationBO;

    public boolean verifyToken(Long userId, String securityToken) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        if (tokenSessionEntity == null || !tokenSessionEntity.getUserEntity().getId().equals(userId)) {
            return false;
        }
        long time = new Date().getTime();
        long tokenTime = tokenSessionEntity.getExpirationDate().getTime();
        if (time > tokenTime) {
            return false;
        }
        tokenSessionEntity.setExpirationDate(getMinutes(3));
        tokenDAO.update(tokenSessionEntity);
        return true;
    }

    public String createToken(Long userId, String clientHostName) {
        TokenSessionEntity tokenSessionEntity = new TokenSessionEntity();
        Date expirationDate = getMinutes(15);
        tokenSessionEntity.setExpirationDate(expirationDate);
        String randomUUID = UUIDGen.getRandomUUID();
        tokenSessionEntity.setSecurityToken(randomUUID);
        UserEntity userEntity = userDAO.findById(userId);
        tokenSessionEntity.setUserEntity(userEntity);
        tokenSessionEntity.setPremium(userEntity.isPremium());
        tokenSessionEntity.setClientHostIp(clientHostName);
        tokenSessionEntity.setActivePersonaId(0L);
        tokenDAO.insert(tokenSessionEntity);
        return randomUUID;
    }

    public void verifyPersonaOwnership(String securityToken, Long personaId) {
        TokenSessionEntity tokenSession = tokenDAO.findById(securityToken);
        if (tokenSession == null) {
            throw new EngineException(EngineExceptionCode.NoSuchSessionInSessionStore);
        }

        if (!tokenSession.getUserEntity().ownsPersona(personaId)) {
            throw new EngineException(EngineExceptionCode.RemotePersonaDoesNotBelongToUser);
        }
    }

    public void deleteByUserId(Long userId) {
        tokenDAO.deleteByUserId(userId);
    }

    private Date getMinutes(int minutes) {
        long time = new Date().getTime();
        time = time + (minutes * 60000);
        return new Date(time);
    }

    private LoginStatusVO checkGeoIp(String ip) {
        LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
        String allowedCountries = serverInfoBO.getServerInformation().getAllowedCountries();
        if (allowedCountries != null && !allowedCountries.isEmpty()) {
            String geoip2DbFilePath = parameterBO.getStrParam("GEOIP2_DB_FILE_PATH");
            GeoIp2 geoIp2 = GeoIp2.getInstance(geoip2DbFilePath);
            if (geoIp2.isCountryAllowed(ip, allowedCountries)) {
                return new LoginStatusVO(0L, "", true);
            } else {
                loginStatusVO.setDescription("GEOIP BLOCK ACTIVE IN THIS SERVER, ALLOWED COUNTRIES: [" + allowedCountries + "]");
            }
        } else {
            return new LoginStatusVO(0L, "", true);
        }
        return loginStatusVO;
    }

    public LoginStatusVO login(String email, String password, HttpServletRequest httpRequest) {
        LoginStatusVO loginStatusVO = checkGeoIp(httpRequest.getRemoteAddr());
        if (!loginStatusVO.isLoginOk()) {
            return loginStatusVO;
        }
        loginStatusVO = new LoginStatusVO(0L, "", false);

        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            UserEntity userEntity = userDAO.findByEmail(email);
            if (userEntity != null) {
                if (password.equals(userEntity.getPassword())) {
                    if (userEntity.isLocked()) {
                        loginStatusVO.setDescription("Account locked. Contact an administrator.");
                        return loginStatusVO;
                    }

                    BanEntity banEntity = authenticationBO.checkUserBan(userEntity);

                    if (banEntity != null) {
                        LoginStatusVO.Ban ban = new LoginStatusVO.Ban();
                        ban.setReason(banEntity.getReason());
                        if (banEntity.getEndsAt() != null)
                            ban.setExpires(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.systemDefault()).format(banEntity.getEndsAt()));
                        loginStatusVO.setBan(ban);
                        return loginStatusVO;
                    }

                    userEntity.setLastLogin(LocalDateTime.now());
                    userDAO.update(userEntity);
                    Long userId = userEntity.getId();
                    deleteByUserId(userId);
                    String randomUUID = createToken(userId, null);
                    loginStatusVO = new LoginStatusVO(userId, randomUUID, true);
                    loginStatusVO.setDescription("");

                    return loginStatusVO;
                }
            }
        }
        loginStatusVO.setDescription("LOGIN ERROR");
        return loginStatusVO;
    }

    public Long getActivePersonaId(String securityToken) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        return tokenSessionEntity.getActivePersonaId();
    }

    public void setActivePersonaId(String securityToken, Long personaId, Boolean isLogout) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);

        if (!isLogout) {
            if (!tokenSessionEntity.getUserEntity().ownsPersona(personaId)) {
                throw new EngineException(EngineExceptionCode.RemotePersonaDoesNotBelongToUser);
            }
        }

        tokenSessionEntity.setActivePersonaId(personaId);
        tokenDAO.update(tokenSessionEntity);
    }

    public String getActiveRelayCryptoTicket(String securityToken) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        return tokenSessionEntity.getRelayCryptoTicket();
    }

    public Long getActiveLobbyId(String securityToken) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        return tokenSessionEntity.getActiveLobbyId();
    }

    public void setActiveLobbyId(String securityToken, Long lobbyId) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        tokenSessionEntity.setActiveLobbyId(lobbyId);
        tokenDAO.update(tokenSessionEntity);
    }

    public boolean isAdmin(String securityToken) {
        return getUser(securityToken).isAdmin();
    }

    public UserEntity getUser(String securityToken) {
        return tokenDAO.findById(securityToken).getUserEntity();
    }
}