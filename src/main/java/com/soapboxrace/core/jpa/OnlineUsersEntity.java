/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ONLINE_USERS")
@NamedQueries({ //
        @NamedQuery(name = "OnlineUsersEntity.findByTime", //
                query = "SELECT obj FROM OnlineUsersEntity obj WHERE obj.timeRecord > :time ORDER BY obj.timeRecord") //

})
public class OnlineUsersEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private int timeRecord;

    private long numberOfOnline = 0;

    private long numberOfRegistered = 0;

    public long getNumberOfOnline() {
        return numberOfOnline;
    }

    public void setNumberOfOnline(long numberOfUsers) {
        this.numberOfOnline = numberOfUsers;
    }

    public int getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(int timeRecord) {
        this.timeRecord = timeRecord;
    }

    public long getNumberOfRegistered() {
        return numberOfRegistered;
    }

    public void setNumberOfRegistered(long numberOfRegistered) {
        this.numberOfRegistered = numberOfRegistered;
    }
}
