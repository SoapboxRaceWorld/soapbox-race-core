package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.xmpp.IHandshake;
import com.soapboxrace.core.xmpp.IOpenFireTalk;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MultiServerHandshake implements IHandshake
{
    private IOpenFireTalk openFireTalk;
    private int connections = 0;

    public MultiServerHandshake()
    {
        doConnect();
    }

    private void doConnect()
    {
        ParameterBO parameterBO = new ParameterBO();
        CountDownLatch loginResponseLatch = new CountDownLatch(1);
        CountDownLatch sessionSetLatch = new CountDownLatch(1);
        AtomicReference<Session> wsSession = new AtomicReference<>();
        final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
        ClientManager client = ClientManager.createClient();
        client.getProperties().put("org.glassfish.tyrus.client.sharedContainerIdleTimeout", Integer.MAX_VALUE);

        try
        {
            MessageHandler mh = new MessageHandler.Whole<String>()
            {

                @Override
                public void onMessage(String message)
                {
                    System.out.println("Received message: " + message);
                    loginResponseLatch.countDown();
                }
            };
            client.connectToServer(new Endpoint()
            {
                @Override
                public void onOpen(Session session, EndpointConfig config)
                {
                    connections++;
                    wsSession.set(session);
                    sessionSetLatch.countDown();

                    System.out.println("ONOPEN CALLED");
                    session.addMessageHandler(mh);
//                        session.getBasicRemote().sendText("{\"action\":\"authenticate\",\"content\":\"HELLOYES\"}");
                }

                @Override
                public void onClose(Session session, CloseReason closeReason)
                {
                    System.out.println("Connection closed w/ reason: " + closeReason);

                    if (closeReason.getCloseCode() != CloseReason.CloseCodes.NORMAL_CLOSURE && connections > 10)
                    {
                        System.err.println("Something went wrong! Code 1");
                        System.exit(1);
                    } else
                    {
                        doConnect();
                    }
                }
            }, cec, new URI(String.format("ws://%s:%d/ws/xmpp", parameterBO.getMultiXmppHost(), parameterBO.getMultiXmppPort())));

            loginResponseLatch.await(10, TimeUnit.SECONDS);
            sessionSetLatch.await(10, TimeUnit.SECONDS);
            wsSession.get().removeMessageHandler(mh);

            openFireTalk = new MultiServerTalk(null, wsSession.get(), parameterBO.getMultiXmppToken());
            openFireTalk.startReader();
            ((MultiServerTalk) openFireTalk).startKeepalive();
            System.out.println("Ready!");
        } catch (DeploymentException | IOException | URISyntaxException | InterruptedException e)
        {
            e.printStackTrace();

            if (connections > 10)
            {
                System.err.println("Something went wrong! Code 2");
                System.exit(1);
            } else
            {
                doConnect();
            }
        }
    }

    @Override
    public IOpenFireTalk getXmppTalk()
    {
        return openFireTalk;
    }
}
