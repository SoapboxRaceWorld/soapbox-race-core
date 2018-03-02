package com.soapboxrace.core.inject;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;

public class XMPPModule implements Module
{
    @Override
    public void configure(Binder binder)
    {
        System.out.println("[DI] XMPP bind");
        OpenFireSoapBoxCli.bindHandshake(binder);
        OpenFireSoapBoxCli.bindOnlineUsers(binder);
    }
}
