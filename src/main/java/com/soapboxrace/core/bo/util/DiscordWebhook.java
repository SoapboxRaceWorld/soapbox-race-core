package com.soapboxrace.core.bo.util;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.text.SimpleDateFormat;
import java.util.*; 

import com.soapboxrace.core.bo.ParameterBO;

import com.mrpowergamerbr.temmiewebhook.DiscordEmbed.DiscordEmbedBuilder;
import com.mrpowergamerbr.temmiewebhook.embed.FooterEmbed;
import com.mrpowergamerbr.temmiewebhook.embed.FieldEmbed;
import com.mrpowergamerbr.temmiewebhook.embed.AuthorEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;

@Stateless
public class DiscordWebhook {
	@EJB
	private ParameterBO parameterBO;

	public void sendMessage(String message, String webHookUrl, String botName, int color, Map<String, String> extra) {
		if(webHookUrl.contains("discord")) {
			TemmieWebhook temmie = new TemmieWebhook(webHookUrl);
			DiscordEmbedBuilder de = DiscordEmbed.builder();

			if(extra == null) {
				de.description(message);
			} else {
				de.author(
					AuthorEmbed.builder().name(extra.get("abuserPersonaName") + "").icon_url("https://cdn.nightriderz.world/images/website/icon-persona/" + extra.get("avatarId") + ".jpg").build()
				);
				de.fields(Arrays.asList(
					FieldEmbed.builder().name("Category").value(extra.get("petitionTypeText")).build(),
					FieldEmbed.builder().name("Reason").value(message).build(),
					FieldEmbed.builder().name("Reporter").value(extra.get("reporterName")).build()
				));
				de.footer(
					FooterEmbed.builder().text(extra.get("botName")).build()
				);
				de.timestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
			}

			de.color(color);

			DiscordMessage dm = DiscordMessage.builder().username(botName).embeds(Arrays.asList(de.build())).build();
			temmie.sendMessage(dm);
		} else {
			System.out.println("Discord WebHooks are disabled.");
		}
	}

	//Workaround
	public void sendMessage(String message, String webHookUrl, String botName, int color) {
		sendMessage(message, webHookUrl, botName, color, null);
	}

	public void sendMessage(String message, String webHookUrl, int color) {
		sendMessage(message, webHookUrl, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}

	public void sendMessage(String message, int color) {
		sendMessage(message, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTURL"), parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}
}
