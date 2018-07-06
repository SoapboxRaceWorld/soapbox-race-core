package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AchievementsBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.AchievementRankDAO;
import com.soapboxrace.core.dao.PersonaAchievementDAO;
import com.soapboxrace.core.dao.PersonaAchievementRankDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.AchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.AchievementRewards;
import com.soapboxrace.jaxb.http.AchievementsPacket;

@Path("/achievements")
public class Achievements
{
    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private AchievementRankDAO achievementRankDAO;

    @EJB
    private PersonaAchievementDAO personaAchievementDAO;

    @EJB
    private PersonaAchievementRankDAO personaAchievementRankDAO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private AchievementsBO achievementsBO;

    @GET
    @Secured
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementsPacket loadall(@HeaderParam("securityToken") String securityToken)
    {
        return achievementsBO.loadall(tokenSessionBO.getActivePersonaId(securityToken));
    }

    @GET
    @Secured
    @Path("/redeemreward")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementRewards redeemreward(@HeaderParam("securityToken") String securityToken, @QueryParam("achievementRankId") Long achievementRankId)
    {
        PersonaEntity persona = personaDAO.findById(tokenSessionBO.getActivePersonaId(securityToken));
        AchievementRankEntity rank = achievementRankDAO.findById(achievementRankId);

        if (rank == null)
        {
            System.err.println("rank is null");
            return new AchievementRewards();
        }
        
        PersonaAchievementEntity personaAchievementEntity = personaAchievementDAO.getForPersonaAchievement(persona, rank.getAchievementDefinition());
        PersonaAchievementRankEntity personaAchievementRankEntity = personaAchievementRankDAO.findByPersonaAchievement(persona, rank.getAchievementDefinition(), rank);

        if (personaAchievementEntity == null || personaAchievementRankEntity == null)
        {
            System.out.println(personaAchievementEntity);
            System.out.println(personaAchievementRankEntity);
            return new AchievementRewards();
        }

        if (!personaAchievementRankEntity.getState().equalsIgnoreCase("RewardWaiting"))
        {
            System.err.println(personaAchievementRankEntity.getState());
            return new AchievementRewards();
        }

        if (rank.getRank() == rank.getAchievementDefinition().getRanks().size())
        {
            personaAchievementEntity.setCanProgress(false);
            personaAchievementDAO.update(personaAchievementEntity);
        }

        AchievementRewards result = achievementsBO.redeemReward(persona, rank);
        personaAchievementRankEntity.setState("Completed");
        personaAchievementRankDAO.update(personaAchievementRankEntity);
        return result;
    }
}
