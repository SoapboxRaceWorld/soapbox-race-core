package com.soapboxrace.core.xmpp.standard;

import com.soapboxrace.core.xmpp.BaseOpenFireTalk;

import java.net.Socket;

public class StandardOpenFireTalk extends BaseOpenFireTalk
{
    public StandardOpenFireTalk(Socket socket)
    {
        super(socket);
    }

    @Override
    public void handleMessage(String msg)
    {
        
    }
}
