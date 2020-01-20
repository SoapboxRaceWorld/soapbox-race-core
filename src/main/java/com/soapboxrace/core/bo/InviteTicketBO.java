/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.InviteTicketDAO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class InviteTicketBO {

    @EJB
    private InviteTicketDAO inviteTicketDAO;

    public InviteTicketEntity createTicket(String discordName) {
        InviteTicketEntity findByTicket = inviteTicketDAO.findByDiscordName(discordName);
        if (findByTicket != null) {
            return findByTicket;
        }
        Long time = new Date().getTime();
        String ticket = "SBRW-" + time.intValue();
        InviteTicketEntity inviteTicketEntity = new InviteTicketEntity();
        inviteTicketEntity.setTicket(ticket);
        inviteTicketEntity.setDiscordName(discordName);
        inviteTicketDAO.insert(inviteTicketEntity);
        return inviteTicketEntity;
    }

}
