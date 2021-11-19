/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.dao.ReportDAO;
import com.soapboxrace.core.jpa.ReportEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.DiscordWebhook;

@Stateless
public class SocialBO {

    @EJB
    private ReportDAO reportDao;

    @EJB
	private ParameterBO parameterBO;

	@EJB
    private PersonaDAO personaDao;

    @EJB
	private DiscordWebhook discord;

    public void sendReport(Long personaId, Long abuserPersonaId, Integer petitionType, String description,
                           Integer customCarID, Integer chatMinutes, Long hacksDetected) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setAbuserPersonaId(abuserPersonaId);
        reportEntity.setChatMinutes(chatMinutes);
        reportEntity.setCustomCarID(customCarID);
        reportEntity.setDescription(description);
        reportEntity.setPersonaId(personaId);
        reportEntity.setPetitionType(petitionType);
        reportEntity.setHacksDetected(hacksDetected);
        reportDao.insert(reportEntity);

        if(parameterBO.getStrParam("DISCORD_WEBHOOK_REPORT_URL") != null) {
			PersonaEntity personaEntity = personaDao.find(abuserPersonaId);
			PersonaEntity personaEntity1 = personaDao.find(personaId);
            String petitionTypeText = "";
            
            description = description.replace("*", "\\*");
            description = description.replace("_", "\\_");
            description = description.replace("~", "\\~");

            switch(petitionType) {
                case 0:     petitionTypeText = "CHAT"; break;
                case 1:     petitionTypeText = "STATUS MESSAGE"; break;
                case 2:     petitionTypeText = "DRIVERNAME"; break;
                case 3:     petitionTypeText = "CAR CUSTOMIZATION"; break;
                case 4:     petitionTypeText = "CHEAT"; break;
                default:    petitionTypeText = "Unknown"; break;
            }

            if(personaEntity1 == null) {
                discord.sendMessage("[" + petitionTypeText + "] **" + personaEntity.getName() + "** has been reported by **" + parameterBO.getStrParam("SBRWR_DEFAULTREPORTER", "SBRW Reloaded") + "**." + "\n Reason: **" + description + "**", 
                    parameterBO.getStrParam("DISCORD_WEBHOOK_REPORT_URL"), 
                    parameterBO.getStrParam("DISCORD_WEBHOOK_REPORT_NAME", "Botte"),
                    0xff9900
                );
            } else {
                discord.sendMessage("[" + petitionTypeText + "] **" + personaEntity.getName() + "** has been reported by **" + personaEntity1.getName() + "**." + "\n Reason: **" + description + "**", 
                    parameterBO.getStrParam("DISCORD_WEBHOOK_REPORT_URL"), 
                    parameterBO.getStrParam("DISCORD_WEBHOOK_REPORT_NAME", "Botte"),
                    0xff9900
                );
            }
		}
    }

}
