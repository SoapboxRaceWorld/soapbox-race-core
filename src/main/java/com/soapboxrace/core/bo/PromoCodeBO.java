/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.PromoCodeDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.PromoCodeEntity;
import com.soapboxrace.core.jpa.UserEntity;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Session;

@Stateless
public class PromoCodeBO {

    @EJB
    private PromoCodeDAO promoCodeDao;

    @EJB
    private UserDAO userDao;

    @Resource(mappedName = "java:jboss/mail/Gmail")
    private Session mailSession;

    public String createPromoCode() {
        String promoCode = "SBRW-" + (Long.toHexString(Double.doubleToLongBits(Math.random()))).toUpperCase();
        PromoCodeEntity promoCodeEntity = new PromoCodeEntity();
        promoCodeEntity.setIsUsed(false);
        promoCodeEntity.setPromoCode(promoCode);
        promoCodeDao.insert(promoCodeEntity);
        return "PromoCode [" + promoCode + "] created ";
    }

    private UserEntity checkLogin(String email, String password) {
        password = (DigestUtils.sha1Hex(password));
        if (email != null && !email.isEmpty() && !password.isEmpty()) {
            UserEntity userEntity = userDao.findByEmail(email);
            if (userEntity != null) {
                if (password.equals(userEntity.getPassword())) {
                    return userEntity;
                }
            }
        }
        return null;
    }

    public String usePromoCode(String promoCode, String email, String password) {
        UserEntity userEntity = checkLogin(email, password);
        if (userEntity == null) {
            return "ERROR: invalid email/password";
        }
        if (userEntity.isPremium()) {
            return "ERROR: account already premium";
        }
        PromoCodeEntity promoCodeEntity = promoCodeDao.findByCode(promoCode);
        if (promoCodeEntity == null) {
            return "ERROR: invalid promo code";
        }
        if (promoCodeEntity.getIsUsed()) {
            return "ERROR: promo code already in use";
        }
        userEntity.setPremium(true);
        userDao.update(userEntity);
        promoCodeEntity.setIsUsed(true);
        promoCodeEntity.setUser(userEntity);
        promoCodeDao.update(promoCodeEntity);
        return "Enjoy your premium account!";
    }

}
