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
