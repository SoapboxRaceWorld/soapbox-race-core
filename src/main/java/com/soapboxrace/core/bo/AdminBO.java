package com.soapboxrace.core.bo;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.api.util.LaunchFilter;
import com.soapboxrace.core.api.util.MiscUtils;
import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppChat;

@Stateless
public class AdminBO {
	@EJB
	private TokenSessionBO tokenSessionBo;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private UserDAO userDao;

	@EJB
	private BanDAO banDAO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

	public void sendCommand(Long personaId, Long abuserPersonaId, String command) {
		CommandInfo commandInfo = CommandInfo.parse(command);
		PersonaEntity personaEntity = personaDao.findById(abuserPersonaId);

		if (personaEntity == null)
			return;

		switch (commandInfo.action) {
		case BAN:
			if (commandInfo.type == null) {
				return;
			}

			sendBan(personaId, personaEntity, commandInfo.type, commandInfo.timeEnd, commandInfo.reason);
			break;
		case KICK:
			sendKick(personaEntity.getUser().getId(), personaEntity.getPersonaId());
			break;
		default:
			break;
		}
	}

	private void sendBan(Long actor, PersonaEntity personaEntity, String type, LocalDateTime endsOn, String reason) {
		UserEntity userEntity = personaEntity.getUser();
		BanEntity banEntity = new BanEntity();
		banEntity.setUserEntity(userEntity);
		banEntity.setEndsAt(endsOn);
		banEntity.setReason(reason);

		boolean doBan = true;
		String failReason = "";

		switch (type.toUpperCase()) {
		case "HWID":
			banEntity.setType(BanEntity.BanType.HWID_BAN);
			banEntity.setData(userEntity.getHwid());
			failReason = "This HWID is already banned.";
			doBan = banDAO.findByHWID(userEntity.getHwid()) == null;
			break;
		case "IP":
			banEntity.setType(BanEntity.BanType.IP_BAN);
			banEntity.setData(userEntity.getIpAddress());
			failReason = "This IP is already banned.";
			doBan = banDAO.findByIp(userEntity.getIpAddress()) == null;
			break;
		case "EMAIL":
			banEntity.setType(BanEntity.BanType.EMAIL_BAN);
			banEntity.setData(userEntity.getEmail());
			failReason = "This email is already banned.";
			doBan = banDAO.findByEmail(userEntity.getEmail()) == null;
			break;
		case "ACCOUNT":
			banEntity.setType(BanEntity.BanType.USER_BAN);
			failReason = "This account is already banned.";
			doBan = banDAO.findByUser(userEntity) == null;
			break;
		}

		if (doBan) {
			banDAO.insert(banEntity);

			sendKick(userEntity.getId(), personaEntity.getPersonaId());

			openFireSoapBoxCli.send(XmppChat.createSystemMessage("Ban successful!"), actor);
		} else {
			openFireSoapBoxCli.send(XmppChat.createSystemMessage(failReason), actor);
		}
	}

	private void sendKick(Long userId, Long personaId) {
		openFireSoapBoxCli.send("<NewsArticleTrans><ExpiryTime><", personaId);
		tokenSessionBo.deleteByUserId(userId);
	}

	private static class CommandInfo {
		public CommandInfo.CmdAction action;
		public String type;
		public String reason;
		public LocalDateTime timeEnd;

		public enum CmdAction {
			KICK, BAN, ALERT, UNKNOWN
		}

		public static CommandInfo parse(String cmd) {
			cmd = cmd.replaceFirst("/", "");

			String[] split = cmd.split(" ");
			CommandInfo.CmdAction action;
			CommandInfo info = new CommandInfo();

			switch (split[0].toLowerCase().trim()) {
			case "ban":
				action = CommandInfo.CmdAction.BAN;
				break;
			case "kick":
				action = CommandInfo.CmdAction.KICK;
				break;
			default:
				action = CommandInfo.CmdAction.UNKNOWN;
				break;
			}

			info.action = action;

			switch (action) {
			case BAN: {
				LocalDateTime endTime;
				String reason = null;

				System.out.println(Arrays.toString(split));

				if (split.length >= 3) {
					info.type = split[1];
					long givenTime = MiscUtils.lengthToMiliseconds(split[2]);
					if (givenTime != 0) {
						endTime = LocalDateTime.now().plusSeconds(givenTime / 1000);
						info.timeEnd = endTime;
						// length = System.currentTimeMillis() + givenTime;

						if (split.length > 3) {
							reason = MiscUtils.argsToString(split, 3, split.length);
						}
					} else {
						reason = MiscUtils.argsToString(split, 2, split.length);
					}
				} else if (split.length >= 2) {
					info.type = split[1];
				}

				info.reason = reason;
				break;
			}
			}

			return info;
		}

		@Override
		public String toString() {
			String desc = action.name();

			if (reason != null && !reason.isEmpty()) {
				desc += "|" + reason;
			}

			if (timeEnd != null) {
				desc += "|" + LaunchFilter.banEndFormatter.format(timeEnd);
			}

			if (type != null && !type.isEmpty()) {
				desc += "|" + type;
			}

			return desc;
		}
	}
}