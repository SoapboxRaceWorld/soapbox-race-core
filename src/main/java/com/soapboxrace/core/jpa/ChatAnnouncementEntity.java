/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_ANNOUNCEMENT")
@NamedQueries({ //
        @NamedQuery(name = "ChatAnnouncementEntity.findAll", query = "SELECT obj FROM ChatAnnouncementEntity obj") //
})
public class ChatAnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer announcementInterval;

    private String announcementMessage;

    private String channelMask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAnnouncementInterval() {
        return announcementInterval;
    }

    public void setAnnouncementInterval(Integer announcementInterval) {
        this.announcementInterval = announcementInterval;
    }

    public String getAnnouncementMessage() {
        return announcementMessage;
    }

    public void setAnnouncementMessage(String announcementMessage) {
        this.announcementMessage = announcementMessage;
    }

    public String getChannelMask() {
        return channelMask;
    }

    public void setChannelMask(String channelMask) {
        this.channelMask = channelMask;
    }
}