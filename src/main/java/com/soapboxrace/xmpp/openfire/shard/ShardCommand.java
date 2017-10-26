package com.soapboxrace.xmpp.openfire.shard;

import javax.persistence.Column;
import javax.xml.bind.annotation.*;

@XmlType(name = "ShardCommand")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShardCommand {
    @XmlElement(name = "Token")
    private String token;
    
    @XmlElementWrapper(name = "Payload")
    private Object payload;
    
    @Column
    private Long targetPersonaId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Long getTargetPersonaId() {
        return targetPersonaId;
    }

    public void setTargetPersonaId(Long targetPersonaId) {
        this.targetPersonaId = targetPersonaId;
    }
}
