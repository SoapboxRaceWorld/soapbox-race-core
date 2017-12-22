package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.xmpp.BaseOpenFireTalk;

import java.net.Socket;

public class ShardedOpenFireTalk extends BaseOpenFireTalk
{
    ShardedOpenFireTalk(Socket socket)
    {
        super(socket);
    }

    @Override
    public void handleMessage(String msg)
    {
        //
    }
}
