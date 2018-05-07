package com.soapboxrace.core.bo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.api.util.BanCommands;
import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.dao.HardwareInfoDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.BanEntity.BanType;
import com.soapboxrace.core.jpa.HardwareInfoEntity;
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
	private HardwareInfoDAO hardwareInfoDAO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

	public void sendCommand(Long personaId, Long abuserPersonaId, String command) {
		String upperCaseCommand = command.toUpperCase();
		String cleanCommand = upperCaseCommand.replaceAll("[^A-Z_]", "");
		StringBuilder stringBuilder = new StringBuilder();
		BanCommands banCommand = null;
		try {
			banCommand = BanCommands.valueOf(cleanCommand);
		} catch (Exception e) {
			stringBuilder.append("\nUNKNOWN COMMAND /");
			stringBuilder.append(cleanCommand);
			stringBuilder.append("\n");
			stringBuilder.append("comands available:");
			BanCommands[] values = BanCommands.values();
			for (BanCommands banCommandEnum : values) {
				stringBuilder.append("\n /");
				stringBuilder.append(banCommandEnum.toString());
			}
		}
		if (banCommand != null) {
			PersonaEntity personaEntity = personaDao.findById(abuserPersonaId);
			Date date;
			long totalBanTime = 0;
			LocalDateTime localDateTime;
			switch (banCommand) {
			case UNBAN:
				banDAO.unbanUser(personaEntity.getUser());
				HardwareInfoEntity hardwareInfoEntity = hardwareInfoDAO.findByUserId(personaEntity.getUser().getId());
				if (hardwareInfoEntity != null) {
					hardwareInfoEntity.setBanned(false);
					hardwareInfoDAO.update(hardwareInfoEntity);
				}
				break;
			case BAN:
				totalBanTime = 10 * 365 * 24 * 60 * 60 * 1000;
				break;
			case BAN_DAY:
				totalBanTime = 1 * 24 * 60 * 60 * 1000;
				break;
			case BAN_WEEK:
				totalBanTime = 7 * 24 * 60 * 60 * 1000;
				break;
			default:
				break;
			}
			if (totalBanTime > 0) {
				date = new Date(System.currentTimeMillis() + totalBanTime);
				localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				sendBan(personaId, personaEntity, localDateTime);
			}
			stringBuilder.append("\nCOMMAND /");
			stringBuilder.append(cleanCommand);
			stringBuilder.append(" DONE!");
			stringBuilder.append("\n to driver: [");
			stringBuilder.append(personaEntity.getName());
			stringBuilder.append("]");
		}
		openFireSoapBoxCli.send(XmppChat.createSystemMessage(stringBuilder.toString()), personaId);

	}

	private void sendBan(Long actor, PersonaEntity personaEntity, LocalDateTime endsOn) {
		UserEntity userEntity = personaEntity.getUser();
		BanEntity banEntity = new BanEntity();
		banEntity.setUserEntity(userEntity);
		banEntity.setData(userEntity.getHwid());
		banEntity.setType(BanType.HWID_BAN.toString());
		banEntity.setEndsAt(endsOn);
		banDAO.insert(banEntity);

		HardwareInfoEntity hardwareInfoEntity = hardwareInfoDAO.findByUserId(userEntity.getId());
		if (hardwareInfoEntity != null) {
			hardwareInfoEntity.setBanned(true);
			hardwareInfoDAO.update(hardwareInfoEntity);
		}

		sendKick(userEntity.getId(), personaEntity.getPersonaId());
		openFireSoapBoxCli.send(XmppChat.createSystemMessage("Ban successful!"), actor);
	}

	private void sendKick(Long userId, Long personaId) {
		openFireSoapBoxCli.send("<NewsArticleTrans><ExpiryTime><", personaId);
		tokenSessionBo.deleteByUserId(userId);
	}
}