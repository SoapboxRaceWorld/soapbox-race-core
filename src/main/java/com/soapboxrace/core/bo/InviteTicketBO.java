package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.InviteTicketDAO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

@Stateless
public class InviteTicketBO {

	@EJB
	private InviteTicketDAO inviteTicketDAO;

	public boolean createTicket(String ticket) {
		InviteTicketEntity findByTicket = inviteTicketDAO.findByTicket(ticket);
		if (findByTicket != null) {
			return false;
		}
		InviteTicketEntity inviteTicketEntity = new InviteTicketEntity();
		inviteTicketEntity.setTicket(ticket);
		inviteTicketDAO.insert(inviteTicketEntity);
		return true;
	}

}
