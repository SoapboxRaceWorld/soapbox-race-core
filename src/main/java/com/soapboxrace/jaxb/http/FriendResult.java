/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FriendResult", propOrder = { "persona", "result" })
@XmlRootElement(name = "FriendResult")
public class FriendResult {

    protected FriendPersona persona;
    protected int result;

    public FriendPersona getFriendPersona() {
        return persona;
    }

    public void setFriendPersona(FriendPersona persona) {
        this.persona = persona;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setResult(FriendResultStatus status) {
        this.result = status.getValue();
    }
}