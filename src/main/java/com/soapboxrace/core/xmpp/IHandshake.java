/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

/**
 * Base interface for XMPP handshake.
 * This is necessary because reasons.
 */
public interface IHandshake {
    IOpenFireTalk getXmppTalk();
}