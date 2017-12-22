package com.soapboxrace.core.xmpp.shard;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum CNCCommandAction
{
    SendPowerup,
    SendRaceEnd,
    SendTimingOut,
    SendLobbyMsg
}
