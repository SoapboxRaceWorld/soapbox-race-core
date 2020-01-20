/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp.standard;

import com.soapboxrace.core.xmpp.BaseOpenFireTalk;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPingType;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPongType;

import java.net.Socket;

public class StandardOpenFireTalk extends BaseOpenFireTalk {
    public StandardOpenFireTalk(Socket socket) {
        super(socket);
    }

    @Override
    public void handleMessage(String msg) {
        if (msg.contains("<ping xmlns=\"urn:xmpp:ping\"/>")) {
            String hostAddress = socket.getInetAddress().getHostAddress();
            XMPP_IQPingType openfirePing = UnmarshalXML.unMarshal(msg, XMPP_IQPingType.class);
            write(MarshalXML.marshal(new XMPP_IQPongType(openfirePing.getId(), hostAddress)));
        }
    }
}
