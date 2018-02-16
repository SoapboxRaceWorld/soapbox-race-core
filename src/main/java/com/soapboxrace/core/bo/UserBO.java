package com.soapboxrace.core.bo;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.InviteTicketDAO;
import com.soapboxrace.core.dao.ServerInfoDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.InviteTicketEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.jaxb.http.ArrayOfProfileData;
import com.soapboxrace.jaxb.http.ProfileData;
import com.soapboxrace.jaxb.http.User;
import com.soapboxrace.jaxb.http.UserInfo;
import com.soapboxrace.jaxb.login.LoginStatusVO;

@Stateless
public class UserBO {

	@EJB
	private UserDAO userDao;

	@EJB
	private InviteTicketDAO inviteTicketDAO;

	@EJB
	private ServerInfoDAO serverInfoDAO;

	@EJB
	private OpenFireRestApiCli xmppRestApiCli;

	@EJB
	private ParameterBO parameterBO;

	public void createXmppUser(UserInfo userInfo) {
		String securityToken = userInfo.getUser().getSecurityToken();
		String xmppPasswd = securityToken.substring(0, 16);
		List<ProfileData> profileData = userInfo.getPersonas().getProfileData();
		for (ProfileData persona : profileData) {
			createXmppUser(persona.getPersonaId(), xmppPasswd);
		}
	}

	public void createXmppUser(Long personaId, String xmppPasswd) {
		xmppRestApiCli.createUpdatePersona(personaId, xmppPasswd);
	}

	public UserEntity createUser(String email, String passwd) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);
		userEntity.setPassword(passwd);
		userEntity.setCreated(LocalDateTime.now());
		userEntity.setLastLogin(LocalDateTime.now());
		userDao.insert(userEntity);
		return userEntity;
	}

	public LoginStatusVO createUserWithTicket(String email, String passwd, String ticket) {
		LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
		InviteTicketEntity inviteTicketEntity = new InviteTicketEntity();
		inviteTicketEntity.setTicket("empty-ticket");
		String ticketToken = parameterBO.getStrParam("TICKET_TOKEN");
		if (ticketToken != null && !ticketToken.equals("null")) {
			inviteTicketEntity = inviteTicketDAO.findByTicket(ticket);
			if (inviteTicketEntity == null || inviteTicketEntity.getTicket() == null || inviteTicketEntity.getTicket().isEmpty()) {
				loginStatusVO.setDescription("Registration Error: Invalid Ticket!");
				return loginStatusVO;
			}
			if (inviteTicketEntity.getUser() != null) {
				loginStatusVO.setDescription("Registration Error: Ticket already in use!");
				return loginStatusVO;
			}
		}
		UserEntity userEntityTmp = userDao.findByEmail(email);
		if (userEntityTmp != null) {
			if (userEntityTmp.getEmail() != null) {
				loginStatusVO.setDescription("Registration Error: Email already exists!");
				return loginStatusVO;
			}
		}
		UserEntity userEntity = createUser(email, passwd);
		inviteTicketEntity.setUser(userEntity);
		inviteTicketDAO.insert(inviteTicketEntity);
		loginStatusVO = new LoginStatusVO(userEntity.getId(), "", true);
		serverInfoDAO.updateNumberOfRegistered();
		return loginStatusVO;
	}

	public UserInfo secureLoginPersona(Long userId, Long personaId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setPersonas(new ArrayOfProfileData());
		com.soapboxrace.jaxb.http.User user = new com.soapboxrace.jaxb.http.User();
		user.setUserId(userId);
		userInfo.setUser(user);
		return userInfo;
	}

	public UserInfo getUserById(Long userId) {
		UserEntity userEntity = userDao.findById(userId);
		UserInfo userInfo = new UserInfo();
		ArrayOfProfileData arrayOfProfileData = new ArrayOfProfileData();
		List<PersonaEntity> listOfProfile = userEntity.getListOfProfile();
		for (PersonaEntity personaEntity : listOfProfile) {
			// switch to apache beanutils copy
			ProfileData profileData = new ProfileData();
			profileData.setName(personaEntity.getName());
			profileData.setCash(personaEntity.getCash());
			profileData.setIconIndex(personaEntity.getIconIndex());
			profileData.setPersonaId(personaEntity.getPersonaId());
			profileData.setLevel(personaEntity.getLevel());
			arrayOfProfileData.getProfileData().add(profileData);
		}
		userInfo.setPersonas(arrayOfProfileData);
		User user = new User();
		user.setUserId(userId);
		userInfo.setUser(user);
		return userInfo;
	}

}
