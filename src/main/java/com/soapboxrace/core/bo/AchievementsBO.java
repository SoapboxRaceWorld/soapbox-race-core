package com.soapboxrace.core.bo;

import com.soapboxrace.jaxb.http.AchievementsPacket;

import javax.ejb.Stateless;

@Stateless
public class AchievementsBO
{
    
    public AchievementsPacket loadall(Long personaId)
    {
        return new AchievementsPacket();
    }
}