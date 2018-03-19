package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.soapboxrace.core.dao.AchievementDAO;
import com.soapboxrace.core.dao.BadgeDefinitionDAO;
import com.soapboxrace.core.jpa.AchievementDefinitionEntity;
import com.soapboxrace.core.jpa.AchievementRankEntity;
import com.soapboxrace.core.jpa.BadgeDefinitionEntity;
import com.soapboxrace.jaxb.http.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class AchievementsBO
{
    @EJB
    private AchievementDAO achievementDAO;
    
    @EJB
    private BadgeDefinitionDAO badgeDefinitionDAO;
    
    public AchievementsPacket loadall(Long personaId)
    {
        AchievementsPacket achievementsPacket = new AchievementsPacket();

        List<AchievementDefinitionEntity> achievements = achievementDAO.getAll();
        List<BadgeDefinitionEntity> badges = badgeDefinitionDAO.getAll();
        
        achievementsPacket.setDefinitions(new ArrayOfAchievementDefinitionPacket());
        achievementsPacket.setBadges(new ArrayOfBadgeDefinitionPacket());
        
        for (AchievementDefinitionEntity achievement : achievements)
        {
            AchievementDefinitionPacket packet = new AchievementDefinitionPacket();
            
            packet.setAchievementDefinitionId(achievement.getId());
            packet.setStatConversion(StatConversion.fromValue(achievement.getStatConversion()));
            packet.setBadgeDefinitionId(achievement.getBadgeDefinition().getId().intValue());
            packet.setProgressText(achievement.getProgressText());
            packet.setIsVisible(true);
            
            packet.setAchievementRanks(new ArrayOfAchievementRankPacket());
            
            for (AchievementRankEntity rank : achievement.getRanks())
            {
                AchievementRankPacket rankPacket = new AchievementRankPacket();
                rankPacket.setIsRare(rank.isRare());
                rankPacket.setAchievementRankId(rank.getId());
                rankPacket.setPoints(rank.getPoints());
                rankPacket.setRarity(0.0f);
                rankPacket.setRank(rank.getRank());
                
                rankPacket.setRewardDescription(rank.getRewardDescription());
                rankPacket.setRewardType(rank.getRewardType());
                rankPacket.setRewardVisualStyle(rank.getRewardVisualStyle());
               
                rankPacket.setThresholdValue(rank.getThresholdValue());
                rankPacket.setState(AchievementState.COMPLETED);
                
                packet.getAchievementRanks().getAchievementRankPacket().add(rankPacket);
            }
            
            packet.setCurrentValue(achievement.getMaximumValue());
            achievementsPacket.getDefinitions().getAchievementDefinitionPacket().add(packet);
        }
        
        for (BadgeDefinitionEntity badge : badges)
        {
            BadgeDefinitionPacket packet = new BadgeDefinitionPacket();
            packet.setBackground(badge.getBackground());
            packet.setBadgeDefinitionId(badge.getId().intValue());
            packet.setBorder(badge.getBorder());
            packet.setDescription(badge.getDescription());
            packet.setIcon(badge.getIcon());
            packet.setName(badge.getName());
            
            achievementsPacket.getBadges().getBadgeDefinitionPacket().add(packet);
        }

        return achievementsPacket;
    }
}