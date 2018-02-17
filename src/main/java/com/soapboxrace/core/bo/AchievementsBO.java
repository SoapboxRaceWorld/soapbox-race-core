package com.soapboxrace.core.bo;

import javax.ejb.Stateless;

import com.soapboxrace.jaxb.http.AchievementsPacket;

@Stateless
public class AchievementsBO
{
    
    public AchievementsPacket loadall(Long personaId)
    {
        return new AchievementsPacket();
    }
}