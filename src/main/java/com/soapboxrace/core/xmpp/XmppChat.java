/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

public class XmppChat {
    public static String createSystemMessage(String message) {
        return String.format("<response status='1' ticket='0'>\n" +
                "<ChatBroadcast>\n" +
                "<ChatBlob>\n" +
                "<FromName>System</FromName>\n" +
                "<FromPersonaId>0</FromPersonaId>\n" +
                "<FromUserId>0</FromUserId>\n" +
                "<Message>%s</Message>\n" +
                "<ToId>0</ToId>\n" +
                "<Type>2</Type>\n" +
                "</ChatBlob>\n" +
                "</ChatBroadcast>\n" +
                "</response>", message);
    }
}
