package com.soapboxrace.core.xmpp;

public class SendMessagePayload
{
    private String to;
    
    private String data;

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}
