package com.soapboxrace.core.xmpp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sessions")
public class SessionsCount {

	private int clusterSessions;
    private int localSessions;

	public int getClusterSessions() {
		return clusterSessions;
	}

	public void setClusterSessions(int clusterSessions) {
		this.clusterSessions = clusterSessions;
	}

	public int getLocalSessions() {
		return localSessions;
	}

	public void setLocalSessions(int localSessions) {
		this.localSessions = localSessions;
	}

}