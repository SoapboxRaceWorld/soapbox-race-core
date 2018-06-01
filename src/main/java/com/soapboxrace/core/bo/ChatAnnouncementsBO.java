package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.ChatAnnouncementDAO;
import com.soapboxrace.core.jpa.ChatAnnouncementEntity;
import com.soapboxrace.core.xmpp.OpenFireRestApiCli;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppChat;
import org.igniterealtime.restclient.entity.MUCRoomEntity;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ChatAnnouncementsBO
{
    @EJB
    private ChatAnnouncementDAO chatAnnouncementDAO;

    @EJB
    private OpenFireRestApiCli restApiCli;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    private Long ticks = 0L;

    @Schedule(minute = "*", hour = "*", second = "*/5", persistent = false)
    public void sendMessages()
    {
        ticks += 5;

        for (ChatAnnouncementEntity announcementEntity : chatAnnouncementDAO.findAll())
        {
            if (announcementEntity.getAnnouncementInterval() % 5 != 0) continue;

            List<MUCRoomEntity> channels = restApiCli.getAllRooms()
                    .stream()
                    .filter(r -> r.getRoomName().startsWith(announcementEntity.getChannelMask().replace("*", "")))
                    .collect(Collectors.toList());

            String message = XmppChat.createSystemMessage(announcementEntity.getAnnouncementMessage());

            if (ticks % announcementEntity.getAnnouncementInterval() == 0)
            {
                for (MUCRoomEntity channel : channels)
                {
                    List<Long> members = restApiCli.getAllOccupantsInRoom(channel.getRoomName());

                    for (Long member : members)
                    {
                        openFireSoapBoxCli.send(message, member);
                    }
                }
            }
        }
    }
}
