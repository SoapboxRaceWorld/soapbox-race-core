package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.xmpp.BaseOpenFireTalk;
import com.soapboxrace.jaxb.util.MarshalXML;

import java.net.Socket;

public class ClientOpenFireTalk extends BaseOpenFireTalk
{
    private static final ParameterBO PARAMETER_BO = new ParameterBO();

    ClientOpenFireTalk(Socket socket)
    {
        super(socket);
    }

    @Override
    public void handleMessage(String msg)
    {

    }

    @Override
    public void send(String msg, Long personaId)
    {
        CNCCommand command = new CNCCommand();
        command.setToPersonaId(personaId);
        command.setToken(PARAMETER_BO.getCncToken());
        
        CNCCommandPayload payload = new CNCCommandPayload();
        payload.setBody(msg);
        
        command.setPayload(payload);
        
        super.send(MarshalXML.marshal(command), "sbrw.engine.engine");
    }

    @Override
    public void send(String msg, String to)
    {
        throw new UnsupportedOperationException("I kindly request that someone try to figure out why this even happened.");
    }
}
