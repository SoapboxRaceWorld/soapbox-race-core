/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeRouteEntrantResult", propOrder = {"routeEntrantResult"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeRouteEntrantResult {
    @XmlElement(name = "RouteEntrantResult", required = true)
    private XMPP_RouteEntrantResultType routeEntrantResult;

    @XmlAttribute(name = "status")
    private Integer status = 1;
    @XmlAttribute(name = "ticket")
    private Integer ticket = 0;

    public XMPP_RouteEntrantResultType getRouteEntrantResult() {
        return routeEntrantResult;
    }

    public void setRouteEntrantResult(XMPP_RouteEntrantResultType routeEntrantResult) {
        this.routeEntrantResult = routeEntrantResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
}