package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.xmpp.BaseOpenFireTalk;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.util.UnmarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_MessageType;

import java.net.Socket;

public class CNCOpenFireTalk extends BaseOpenFireTalk
{
    private static final ParameterBO PARAMETER_BO = new ParameterBO();

    CNCOpenFireTalk(Socket socket)
    {
        super(socket);
    }

    @Override
    public void handleMessage(String msg)
    {
        if (msg.contains("<message "))
        {
            XMPP_MessageType messageType = UnmarshalXML.unMarshal(msg, XMPP_MessageType.class);
            String body = messageType.getBody();

            if (body.contains("<ChatMsg")) return;

            if (body.contains("<CNCCommand "))
            {
                CNCCommand command = UnmarshalXML.unMarshal(body, CNCCommand.class);

                if (PARAMETER_BO.getCncToken() == null || command.getToken().equals(PARAMETER_BO.getCncToken()))
                {
//                    OpenFireSoapBoxCli.getInstance().send(command.getPayload().getBody());
                } else
                {
                    System.err.println("[CNC] Received unauthenticated command. Uuuuuhhhhhhh....");
                }
            }
        }
    }
}
