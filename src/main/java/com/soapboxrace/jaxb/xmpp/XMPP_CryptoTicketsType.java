package com.soapboxrace.jaxb.xmpp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CryptoTicketsType", propOrder = { "p2PCryptoTicket" })
public class XMPP_CryptoTicketsType {

	@XmlElement(name = "P2PCryptoTicket")
	protected List<XMPP_P2PCryptoTicketType> p2PCryptoTicket;

	public List<XMPP_P2PCryptoTicketType> getP2PCryptoTicket() {
		if (p2PCryptoTicket == null) {
			p2PCryptoTicket = new ArrayList<XMPP_P2PCryptoTicketType>();
		}
		return this.p2PCryptoTicket;
	}

}
