/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum FriendResultStatus {
    Success(0),
    CannotAddSelf(1),
    AlreadyFriends(2),
    CannotFindDriver(3),
    OurFriendsListIsFull(4),
    TargetFriendsListIsFull(6);

    private final int value;

    FriendResultStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
