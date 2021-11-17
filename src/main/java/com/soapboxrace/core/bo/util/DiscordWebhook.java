package com.soapboxrace.core.bo.util;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.net.URI;
import java.util.*; 

import com.soapboxrace.core.bo.ParameterBO;

import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;

@Stateless
public class DiscordWebhook {
	@EJB
	private ParameterBO parameterBO;

	public void sendMessage(String message, String webHookUrl, String botName, int color) {
		try {
			URI webHookUrlParsed = new URI(webHookUrl);
			if(webHookUrlParsed.getScheme().equals("https") || webHookUrlParsed.getScheme().equals("http")) {
				TemmieWebhook temmie = new TemmieWebhook(webHookUrl);
				DiscordEmbed de = DiscordEmbed.builder().description(message).color(color).build();
				DiscordMessage dm = DiscordMessage.builder().username(botName).embeds(Arrays.asList(de)).build();
				temmie.sendMessage(dm);
			} else {
				System.out.println("Discord WebHooks are disabled.");
			}
		} catch (Exception e) {
			System.out.println(e);	
		}
	}

	public void sendMessage(String message, String webHookUrl, int color) {
		sendMessage(message, webHookUrl, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}

	public void sendMessage(String message, int color) {
		sendMessage(message, parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTURL"), parameterBO.getStrParam("DISCORD_WEBHOOK_DEFAULTNAME"), color);
	}
}
