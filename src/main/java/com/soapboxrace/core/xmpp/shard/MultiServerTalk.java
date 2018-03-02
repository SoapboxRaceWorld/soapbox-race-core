package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.xmpp.BaseOpenFireTalk;
import com.soapboxrace.core.xmpp.Message;
import com.soapboxrace.core.xmpp.SendMessagePayload;
import com.soapboxrace.core.xmpp.SubjectCalc;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;
import com.soapboxrace.util.JSONUtils;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class MultiServerTalk extends BaseOpenFireTalk
{
    private final Session session;
    
    private final String token;

    MultiServerTalk(Socket socket, Session session, String token)
    {
        super(new Socket());

        this.socket = null;
        this.reader = null;
        this.writer = null;

        this.session = session;
        this.token = token;
    }

    @Override
    protected void setReaderWriter()
    {
        //
    }

    @Override
    public String read()
    {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<String> atomString = new AtomicReference<>();

        MessageHandler messageHandler = new MessageHandler.Whole<String>()
        {
            @Override
            public void onMessage(String message)
            {
//                System.out.println(message);
                atomString.set(message);
            }
        };

        session.addMessageHandler(messageHandler);

        try
        {
            countDownLatch.await();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        session.removeMessageHandler(messageHandler);

        String result = atomString.getAndSet(null);
        handleMessage(result);
        return result;
    }

    @Override
    public void write(String msg)
    {
        try
        {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void startKeepalive()
    {
        new KeepAliveThread().start();
    }

    @Override
    public void handleMessage(String msg)
    {
    }

    @Override
    public void send(String msg, String to)
    {
        XMPP_MessageType messageType = new XMPP_MessageType();
        messageType.setTo(to);
        messageType.setBody(msg);
        messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
        String packet = MarshalXML.marshal(messageType);

        SendMessagePayload smp = new SendMessagePayload();
        smp.setData(packet);
        smp.setTo(messageType.getTo());

        Message message = new Message();
        message.setAction("SEND_MESSAGE");
        message.setToken(token);
        message.setContent(JSONUtils.serialize(smp));

        write(JSONUtils.serialize(message));
    }

    @Override
    public void send(String msg, Long personaId)
    {
        XMPP_MessageType messageType = new XMPP_MessageType();
        messageType.setToPersonaId(personaId);
        messageType.setBody(msg);
        messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
        String packet = MarshalXML.marshal(messageType);

        SendMessagePayload smp = new SendMessagePayload();
        smp.setData(packet);
        smp.setTo(messageType.getTo());

        Message message = new Message();
        message.setAction("SEND_MESSAGE");
        message.setContent(JSONUtils.serialize(smp));
        message.setToken(token);
        
        write(JSONUtils.serialize(message));
        
//        write(packet);
    }
    
    private class KeepAliveThread extends Thread
    {
        @Override
        public void run()
        {
            while (session.isOpen())
            {
                try
                {
                    Message message = new Message();
                    message.setAction("KEEP_ALIVE");
                    message.setContent("yep");
                    message.setToken(token);
                    write(JSONUtils.serialize(message));
                    Thread.sleep(60000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
