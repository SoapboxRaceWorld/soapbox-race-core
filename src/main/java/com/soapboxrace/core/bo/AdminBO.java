package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;

@Stateless
public class AdminBO
{
    @EJB
    private SocialBO bo;

    @EJB
    private TokenSessionBO tokenSessionBo;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private UserDAO userDao;

    public void sendCommand(Long personaId, Long abuserPersonaId, String command)
    {
        switch (command)
        {
            case "BAN":
                sendBan(abuserPersonaId);
                break;
            case "MUTE":
                sendMute(abuserPersonaId);
                break;
            case "KICK":
                sendKick(personaDao.findById(abuserPersonaId).getUser().getId(), abuserPersonaId);
                break;
        }
    }

    private void sendBan(Long personaId)
    {
        UserEntity userEntity = userDao.findById(personaDao.findById(personaId).getUser().getId());
        userEntity.setBanned(true);
        userDao.update(userEntity);
        sendKick(userEntity.getId(), personaId);
    }

    private void sendMute(Long personaId)
    {
        
    }

    private void sendKick(Long userId, Long personaId)
    {
        OpenFireSoapBoxCli.getInstance().send("<NewsArticleTrans><ExpiryTime><", personaId);
        tokenSessionBo.deleteByUserId(userId);
    }
}