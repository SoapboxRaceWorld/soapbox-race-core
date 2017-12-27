package com.soapboxrace.core;

import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupBean
{
    @PostConstruct
    public void onStartup()
    {
        OpenFireSoapBoxCli.getInstance();
    }
    
    @PreDestroy
    public void onShutdown()
    {
        OpenFireSoapBoxCli.getInstance().disconnect();
    }
}
