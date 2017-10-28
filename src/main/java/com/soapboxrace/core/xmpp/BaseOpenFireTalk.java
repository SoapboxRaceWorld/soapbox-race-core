package com.soapboxrace.core.xmpp;

import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPingType;
import com.soapboxrace.jaxb.xmpp.XMPP_IQPongType;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;

import java.io.*;
import java.net.Socket;

public abstract class BaseOpenFireTalk implements IOpenFireTalk
{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public BaseOpenFireTalk(Socket socket)
    {
        setSocket(socket);
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
        setReaderWriter();
    }

    @Override
    public String read()
    {
        String msg = null;
        char[] buffer = new char[8192];
        int charsRead;
        try
        {
            if ((charsRead = reader.read(buffer)) != -1)
            {
                msg = new String(buffer).substring(0, charsRead);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("S->C [" + msg + "]");
        if (msg != null && !msg.isEmpty())
        {
            if (msg.contains("<ping xmlns=\"urn:xmpp:ping\"/>"))
            {
                XMPP_IQPingType openfirePing = UnmarshalXML.unMarshal(msg, XMPP_IQPingType.class);
                write(MarshalXML.marshal(new XMPP_IQPongType(openfirePing.getId())));
            }
            
            this.handleMessage(msg);
        }
        return msg;
    }

    public void write(String msg)
    {
        try
        {
            char[] cbuf = new char[msg.length()];
            msg.getChars(0, msg.length(), cbuf, 0);
            System.out.println("C->S [" + msg + "]");
            writer.write(cbuf);
            writer.flush();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setReaderWriter()
    {
        try
        {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startReader()
    {
        XmppTalkReader xmppTalkReader = new XmppTalkReader(this);
        xmppTalkReader.start();
    }

    @Override
    public void send(String msg, String to)
    {
        XMPP_MessageType messageType = new XMPP_MessageType();
        messageType.setTo(to);
        messageType.setBody(msg);
        messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
        String packet = MarshalXML.marshal(messageType);
        write(packet);
    }

    @Override
    public void send(String msg, Long personaId)
    {
        XMPP_MessageType messageType = new XMPP_MessageType();
        messageType.setToPersonaId(personaId);
        messageType.setBody(msg);
        messageType.setSubject(SubjectCalc.calculateHash(messageType.getTo().toCharArray(), msg.toCharArray()));
        String packet = MarshalXML.marshal(messageType);
        write(packet);
    }

    private static class XmppTalkReader extends Thread
    {
        private BaseOpenFireTalk xmppTalk;

        public XmppTalkReader(BaseOpenFireTalk xmppTalk)
        {
            this.xmppTalk = xmppTalk;
        }

        @Override
        public void run()
        {
            while (true)
            {
                String read = xmppTalk.read();
                if (read == null)
                {
                    OpenFireSoapBoxCli.getInstance().disconnect();
                    try
                    {
                        xmppTalk.socket.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    OpenFireSoapBoxCli.getInstance();
                    this.interrupt();
                    break;
                }
            }
        }
    }
}
