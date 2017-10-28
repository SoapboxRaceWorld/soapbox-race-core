package com.soapboxrace.core.xmpp.shard;

import com.google.inject.Injector;
import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.inject.InjectorFactory;
import com.soapboxrace.core.xmpp.*;

public class ClientHandshake implements IHandshake
{
    private static final ParameterBO PARAMETER_BO = new ParameterBO();

    private IOpenFireTalk openFireTalk;

    public ClientHandshake()
    {
        Injector injector = InjectorFactory.getInjector();
        OpenFireRestApiCli restApiCli = injector.getProvider(OpenFireRestApiCli.class).get();

        String xmppIp = Config.getXmppIp();
        int xmppPort = Config.getXmppPort();

        String user = "sbrw.engine.client-" + PARAMETER_BO.getShardId();
        String password = PARAMETER_BO.getShardToken();

        restApiCli.createUpdatePersona(user, password);

        SocketClient socketClient = new SocketClient(xmppIp, xmppPort);
        socketClient.send(
                "<?xml version='1.0' ?><stream:stream to='" + xmppIp + "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
        String receive = socketClient.receive();
        while (!receive.contains("</stream:features>")) {
            receive = socketClient.receive();
        }
        socketClient.send("<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>");
        socketClient.receive();
        openFireTalk = new ClientOpenFireTalk(socketClient.getSocket());
        TlsWrapper.wrapXmppTalk(openFireTalk);
        openFireTalk.write(
                "<?xml version='1.0' ?><stream:stream to='" + xmppIp + "' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0' xml:lang='en'>");
        openFireTalk.write("<iq id='EA-Chat-1' type='get'><query xmlns='jabber:iq:auth'><username>" + user + "</username></query></iq>");
        openFireTalk.read();
        openFireTalk.write("<iq xml:lang='en' id='EA-Chat-2' type='set'><query xmlns='jabber:iq:auth'><username>" + user + "</username><password>" + password
                + "</password><resource>EA_Chat</resource><clientlock xmlns='http://www.jabber.com/schemas/clientlocking.xsd' id='900'>57b8914527daff651df93557aef0387e5aa60fae</clientlock></query></iq>");
        openFireTalk.read();
        openFireTalk.write("<presence><show>chat</show><status>Online</status><priority>0</priority></presence>");
        openFireTalk.write(" ");
        openFireTalk.write("<presence to='channel.CMD__1@conference." + xmppIp + "/" + user + "'/>");
        openFireTalk.startReader();
    }

    @Override
    public IOpenFireTalk getXmppTalk()
    {
        return openFireTalk;
    }
}
