package com.soapboxrace.core.xmpp.shard;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.xmpp.BaseOpenFireTalk;
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

            if (body.contains("<ChatMsg"))
            {
                // <message xmlns="" to="sbrw.xxx@xx.xxx.xxx.xx/EA-Chat" type="groupchat" from="channel.en__1@conference.xxx.xxx.xxx.xxx/sbrw.xxxxx">
                //    <channel>Chat_All</channel>
                //    <body>
                //      <ChatMsg Type="0" Uid="xxxxx" Time="-5006886331642481131" Cs="8217472555062347827">
                //          <From>SOMEONE</From>
                //          <Msg>lmao</Msg>;
                //      </ChatMsg>;
                //    </body>
                // </message>
            } else if (body.contains("<CNCCommand "))
            {
                CNCCommand command = UnmarshalXML.unMarshal(body, CNCCommand.class);

                if (PARAMETER_BO.getCncToken() == null || command.getToken().equals(PARAMETER_BO.getCncToken()))
                {
                    send(command.getPayload().getBody(), command.getToPersonaId());
//                    OpenFireSoapBoxCli.getInstance().send(command.getPayload().getBody());
                } else
                {
                    System.err.println("[CNC] Received unauthenticated command. Uuuuuhhhhhhh....");
                }
            }
        }
    }
}
