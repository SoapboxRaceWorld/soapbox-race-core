package com.soapboxrace.core.xmpp;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.inject.InjectorFactory;
import com.soapboxrace.core.xmpp.shard.CNCHandshake;
import com.soapboxrace.core.xmpp.shard.ClientHandshake;
import com.soapboxrace.core.xmpp.standard.Handshake;
import com.soapboxrace.jaxb.util.MarshalXML;

public class OpenFireSoapBoxCli
{
    private static final ParameterBO parameterBO = new ParameterBO();
    private final Injector injector = InjectorFactory.getInjector();

    private IOpenFireTalk xmppTalk;

    private static OpenFireSoapBoxCli instance;

    public static OpenFireSoapBoxCli getInstance()
    {
        if (instance == null)
        {
            instance = new OpenFireSoapBoxCli();
        }
        return instance;
    }

    public static void bindHandshake(Binder binder)
    {
        Class<? extends IHandshake> handshakeClass;

        if (parameterBO.isShardingEnabled())
        {
            // We have specific code for sharding
            if (parameterBO.isShardingMaster())
            {
                System.out.println("[XMPP-DI] Using CNCHandshake");
                handshakeClass = CNCHandshake.class; // Master shard handshake
            } else
            {
                handshakeClass = ClientHandshake.class; // Client shard handshake
            }
        } else
        {
            // Normal handshake
            System.out.println("[XMPP-DI] Using (normal) Handshake");
            handshakeClass = Handshake.class; // yes
        }

        binder.bind(IHandshake.class).to(handshakeClass);
    }

    private OpenFireSoapBoxCli()
    {
        IHandshake handshake = this.resolveHandshake();
        this.xmppTalk = handshake.getXmppTalk();
    }

    public void send(String msg, String to)
    {
        xmppTalk.send(msg, to);
    }

    public void send(String msg, Long to)
    {
        xmppTalk.send(msg, to);
    }

    public void send(Object object, Long to)
    {
        String responseXmlStr = MarshalXML.marshal(object);
        this.send(responseXmlStr, to);
    }

    public void disconnect()
    {
        instance = null;
    }

    private IHandshake resolveHandshake()
    {
        return injector.getInstance(IHandshake.class);
    }
}