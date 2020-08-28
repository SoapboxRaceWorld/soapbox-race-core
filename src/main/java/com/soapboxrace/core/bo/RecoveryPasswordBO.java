/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.RecoveryPasswordDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.RecoveryPasswordEntity;
import com.soapboxrace.core.jpa.UserEntity;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

@Stateless
public class RecoveryPasswordBO {

    @EJB
    private RecoveryPasswordDAO recoveryPasswordDao;

    @EJB
    private UserDAO userDao;

    @EJB
    private ParameterBO parameterBO;

    @Resource(mappedName = "java:jboss/mail/Gmail")
    private Session mailSession;

    public String resetPassword(String password, String randomKey) {
        RecoveryPasswordEntity recoveryPasswordEntity = recoveryPasswordDao.findByRandomKey(randomKey);
        if (recoveryPasswordEntity == null) {
            return "ERROR: invalid randomKey!";
        }

        long currentTime = new Date().getTime();
        long recoveryTime = recoveryPasswordEntity.getExpirationDate().getTime();
        if (recoveryPasswordEntity.getIsClose() || currentTime > recoveryTime) {
            return "ERROR: randomKey expired";
        }

        UserEntity userEntity = userDao.find(recoveryPasswordEntity.getUserId());
        userEntity.setPassword(DigestUtils.sha1Hex(password));
        userDao.update(userEntity);

        recoveryPasswordEntity.setIsClose(true);
        recoveryPasswordDao.update(recoveryPasswordEntity);

        return "Password changed!";
    }

    private String createRecoveryPassword(UserEntity userEntity) {
        List<RecoveryPasswordEntity> listRecoveryPasswordEntity =
                recoveryPasswordDao.findAllOpenByUserId(userEntity.getId());
        if (!listRecoveryPasswordEntity.isEmpty()) {
            return "ERROR: Recovery password link already sent, please check your spam mail box or try again in 1 " +
                    "hour.";
        }

        String randomKey = createSecretKey(userEntity.getId());
        if (!sendEmail(randomKey, userEntity)) {
            return "ERROR: Can't send email!";
        }

        RecoveryPasswordEntity recoveryPasswordEntity = new RecoveryPasswordEntity();
        recoveryPasswordEntity.setRandomKey(randomKey);
        recoveryPasswordEntity.setExpirationDate(getHours(1));
        recoveryPasswordEntity.setUserId(userEntity.getId());
        recoveryPasswordEntity.setIsClose(false);
        recoveryPasswordDao.insert(recoveryPasswordEntity);

        return "Link to reset password sent to: [" + userEntity.getEmail() + "]";
    }

    private String createSecretKey(Long userId) {
        return (Long.toHexString(Double.doubleToLongBits(Math.random())) + userId.toString());
    }

    private Boolean sendEmail(String randomKey, UserEntity userEntity) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(parameterBO.getStrParam("EMAIL_FROM")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEntity.getEmail()));
            message.setSubject("Recovery Password Email");
            String stringBuilder = "Dear user,\n\n" +
                    "Someone requested to recover forgotten password in our soapbox race world server" +
                    ".\n\n" +
                    "If wasn't you, just ignore this email.\n\n" +
                    "You can click this link to reset your password:\n\n" +
                    parameterBO.getStrParam("SERVER_ADDRESS") +
                    parameterBO.getStrParam("SERVER_PASSWORD_RESET_PATH", "/soapbox-race-core/password.jsp") +
                    "?randomKey=" +
                    randomKey +
                    "\n\nThanks for playing!\n\n" +
                    "\n\nSBRW Team.\n";
            message.setText(stringBuilder);
            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    private Date getHours(int hours) {
        long time = new Date().getTime();
        time += hours * 60000 * 60;
        return new Date(time);
    }

    public String forgotPassword(String email) {
        UserEntity userEntity = userDao.findByEmail(email);
        if (userEntity == null) {
            return "ERROR: Invalid email!";
        }
        return createRecoveryPassword(userEntity);
    }

}
