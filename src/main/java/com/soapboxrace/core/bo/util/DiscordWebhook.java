package com.soapboxrace.core.bo.util;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.*; 

import com.soapboxrace.core.bo.ParameterBO;

import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import com.mrpowergamerbr.temmiewebhook.embed.ThumbnailEmbed;

@Stateless
public class DiscordWebhook {
	@EJB
	private ParameterBO parameterBO;

	public void sendMessage(String message, String webHookUrl, String botName, int color) {
		TemmieWebhook temmie = new TemmieWebhook(webHookUrl);

		DiscordEmbed de = DiscordEmbed.builder().description(message).color(color).build();

		DiscordMessage dm = DiscordMessage.builder().username(botName).embeds(Arrays.asList(de)).build();
		temmie.sendMessage(dm);
	}

	public void sendMessage(String message, String webHookUrl, int color) {
		sendMessage(message, webHookUrl, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}

	public void sendMessage(String message, int color) {
		sendMessage(message, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTURL"), parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}
}